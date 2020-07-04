package ro.iteahome.nhs.adminui.exception.business;

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
