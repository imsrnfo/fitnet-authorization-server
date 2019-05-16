package com.igmasiri.fitnet.authorizationserver.config.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationExceptionDTO extends AplicationHandledExceptionDTO {

   private List<FieldExceptionDTO> fieldExceptions = new ArrayList<>();

    public List<FieldExceptionDTO> getFieldExceptions() {
        return fieldExceptions;
    }

    public void setFieldExceptions(List<FieldExceptionDTO> fieldExceptions) {
        this.fieldExceptions = fieldExceptions;
    }

    public class FieldExceptionDTO {

       private String field;
       private String message;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
