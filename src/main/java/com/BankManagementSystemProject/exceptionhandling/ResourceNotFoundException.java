package com.BankManagementSystemProject.exceptionhandling;

import lombok.Getter;
import lombok.Setter;

public class ResourceNotFoundException extends RuntimeException{

    @Getter
    @Setter
   private String resourceName;
   private String fieldName;
   private Object  fieldValue;

    //  Custom message constructor (NEW)
    public ResourceNotFoundException(String message) {
        super(message);
    }

    //constructor using field
    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : %s",resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }}
