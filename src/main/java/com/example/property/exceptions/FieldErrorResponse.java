package com.example.property.exceptions;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FieldErrorResponse {

    @Autowired
    private List<CustomFieldError> fieldErrors;

    public FieldErrorResponse() {
    }

    public List<CustomFieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<CustomFieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
