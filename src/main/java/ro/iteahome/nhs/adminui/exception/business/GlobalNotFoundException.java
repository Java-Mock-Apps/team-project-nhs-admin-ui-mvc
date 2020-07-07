package ro.iteahome.nhs.adminui.exception.business;

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
