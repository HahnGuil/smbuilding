package com.manager.smbuilding.application.utils;

import com.manager.smbuilding.application.exception.ResourceNotFoundException;

import java.util.List;

//TODO - TRANSFER THIS CLASS FOR A OTHER SERVIVE, MORE GENERIC
public class ValidationUtils {
    public static <T> List<T> validateNonEmptyList(List<T> list, String errorMessage) {
        if (list == null || list.isEmpty()) {
            throw new ResourceNotFoundException(errorMessage);
        }
        return list;
    }
}
