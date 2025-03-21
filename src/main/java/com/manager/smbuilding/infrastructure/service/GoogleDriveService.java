package com.manager.smbuilding.infrastructure.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.manager.smbuilding.infrastructure.configuration.GoogleDriveConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GoogleDriveService {

    private final String idDriveFolder = "1x6jYA7MR0DJvzC6QTpsIyACXHLeucpEG";
    private final String drivePath = "https://drive.google.com/file/d/";

    public String getIdDriveFolder() {
        return idDriveFolder;
    }


    public String getDrivePath() {
        return drivePath;
    }


    public String uploadFile(String folderId, java.io.File filePath, String mimeType) throws IOException {

        Drive driveService = GoogleDriveConfig.getDriveService();

        File fileMetadata = new File();
        fileMetadata.setName(filePath.getName());
        fileMetadata.setParents(Collections.singletonList(folderId));

        FileContent mediaContent = new FileContent(mimeType, filePath);

        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id, name, mimeType, webContentLink, webViewLink")
                .execute();

        setPublicPermission(driveService, uploadedFile.getId());

        String fileViewLink = uploadedFile.getWebViewLink();

        return fileViewLink;
    }


    public boolean deleteFileByLink(String fileViewLink) {
        if (fileViewLink == null || fileViewLink.isEmpty()) {
            return false;
        }

        String fileId = extractFileIdFromLink(fileViewLink);
        if (fileId == null) {
            return false;
        }

        try {
            Drive driveService = GoogleDriveConfig.getDriveService();
            driveService.files().delete(fileId).execute();
            return true;
        } catch (GoogleJsonResponseException e) {
            if (e.getStatusCode() == 404) {
                return false;
            }
            throw new RuntimeException("Error deleting file from Google Drive", e);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file from Google Drive", e);
        }
    }


    private String extractFileIdFromLink(String fileViewLink) {
        String regex = "https://drive.google.com/file/d/([a-zA-Z0-9_-]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileViewLink);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }


    private void setPublicPermission(Drive driveService, String fileId) throws IOException {
        Permission permission = new Permission()
                .setType("anyone")
                .setRole("reader");
        driveService.permissions().create(fileId, permission).execute();
    }
}