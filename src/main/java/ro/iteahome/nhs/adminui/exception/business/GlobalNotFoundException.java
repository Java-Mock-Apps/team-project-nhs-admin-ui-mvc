package ro.iteahome.nhs.adminui.exception.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class GlobalNotFoundException extends RuntimeException {

    private final String restEntity;

    public GlobalNotFoundException(String restEntity) {
        super(restEntity + " NOT FOUND");
        this.restEntity = restEntity;
    }

    public String getRestEntity() {
        return restEntity;
    }
}
