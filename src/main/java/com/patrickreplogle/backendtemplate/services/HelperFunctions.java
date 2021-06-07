package com.patrickreplogle.backendtemplate.services;

import com.patrickreplogle.backendtemplate.models.ValidationError;

import java.util.List;

public interface HelperFunctions {

    List<ValidationError> getConstraintViolation(Throwable cause);
}
