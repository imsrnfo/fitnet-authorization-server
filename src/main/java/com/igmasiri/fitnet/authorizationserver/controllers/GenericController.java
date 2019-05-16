package com.igmasiri.fitnet.authorizationserver.controllers;

import com.igmasiri.fitnet.authorizationserver.config.exceptions.AplicationHandledExceptionDTO;
import com.igmasiri.fitnet.authorizationserver.config.exceptions.ValidationExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

public class GenericController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AplicationHandledExceptionDTO mostrarExcepcion(Exception e) {

        AplicationHandledExceptionDTO result = new AplicationHandledExceptionDTO();
        result.setStatus("Error");
        result.setMensaje(e.getMessage());

        return result;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO mostrarExcepcionValidacion(Exception e) {

        ValidationExceptionDTO result = new ValidationExceptionDTO();

        for(ConstraintViolation constraintViolation : ((ConstraintViolationException)e).getConstraintViolations()){
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
