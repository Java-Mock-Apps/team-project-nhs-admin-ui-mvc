package ro.iteahome.nhs.adminui.exception.technical;

public class GlobalRequestFailedException extends RuntimeException {

    private final String restEntity;

    public GlobalRequestFailedException(String restEntity) {
        super("REQUEST FAILED FOR: " + restEntity + ". PLEASE CONTACT SUPPORT");
        this.restEntity = restEntity;
    }

    public String getRestEntity() {
        return restEntity;
    }
}
