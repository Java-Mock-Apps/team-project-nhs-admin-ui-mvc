package ro.iteahome.nhs.adminui.exception.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class GlobalAlreadyExistsException extends RuntimeException {

    private final String entityName;

    public GlobalAlreadyExistsException(String entityName) {
        super(entityName + " ALREADY EXISTS");
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
