package com.manager.smbuilding.infrastructure.configuration;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

public class GoogleDriveConfig {

    public static Drive getDriveService() throws IOException {
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDENTIALS")))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential)
                .setApplicationName("SMBuilding").build();
    }
}
