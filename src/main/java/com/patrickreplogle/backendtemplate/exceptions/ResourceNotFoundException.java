package com.patrickreplogle.backendtemplate.exceptions;

public class ResourceNotFoundException
        extends RuntimeException
{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}