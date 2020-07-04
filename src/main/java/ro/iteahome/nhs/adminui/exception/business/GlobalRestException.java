package ro.iteahome.nhs.adminui.exception.business;

public class GlobalRestException extends RuntimeException{

    private final String entityName;

    public GlobalRestException(String entityName, String originalMessage) {
        super("REST REQUEST FAILED FOR: " + entityName + ". MESSAGE: " + originalMessage);
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
