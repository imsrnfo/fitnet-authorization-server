package com.igmasiri.fitnet.authorizationserver.controllers;

import com.igmasiri.fitnet.authorizationserver.config.exceptions.AplicationHandledExceptionDTO;
import com.igmasiri.fitnet.authorizationserver.config.exceptions.ValidationExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class GenericController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AplicationHandledExceptionDTO mostrarExcepcion(Exception e) {

        ConstraintViolationException constraintViolationException = getConstraintViolationException(e);
        if (constraintViolationException!=null) return mostrarExcepcionValidacion(constraintViolationException);

        AplicationHandledExceptionDTO result = new AplicationHandledExceptionDTO();
        result.setStatus("Error");
        result.setMensaje(e.getMessage());

        return result;
    }

    private ConstraintViolationException getConstraintViolationException(Throwable e){
        if (e.getCause() == null) return null;
        if (e.getCause() instanceof  ConstraintViolationException) return (ConstraintViolationException) e.getCause();
        return getConstraintViolationException(e.getCause());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO mostrarExcepcionValidacion(ConstraintViolationException e) {

        ValidationExceptionDTO result = new ValidationExceptionDTO();

        for(ConstraintViolation constraintViolation : e.getConstraintViolations()){
            ValidationExceptionDTO.FieldExceptionDTO fieldExceptionDTO =  result.new FieldExceptionDTO();
            fieldExceptionDTO.setMessage(constraintViolation.getMessage());
            fieldExceptionDTO.setField(constraintViolation.getPropertyPath().toString());
            result.getFieldExceptions().add(fieldExceptionDTO);
        }

        result.setStatus("Error");
        result.setMensaje("Error en la validacion de los campos.");

        return result;
    }

}
