package ro.iteahome.nhs.adminui.service;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class ServiceUtil {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    Logger logger;

// METHODS: ------------------------------------------------------------------------------------------------------------

    boolean causedByInvalid(Exception ex) {
        return ex.getMessage().contains("VALIDATION ERROR IN FIELD");
    }

    boolean causedByDuplicate(Exception ex) {
        return ex.getMessage().contains("Duplicate entry");
    }

    String parseInvalid(Exception ex) {
        if (causedByInvalid(ex)) {
            return getInvalidMessages(ex.getMessage());
        } else {
            return null;
        }
    }

    String parseDuplicate(Exception ex) {
        if (causedByDuplicate(ex)) {
            return getDuplicateMessage(ex.getMessage());
        } else {
            return null;
        }
    }

    String parseInvalidOrDuplicate(Exception ex) {
        if (causedByInvalid(ex)) {
            return getInvalidMessages(ex.getMessage());
        } else if (causedByDuplicate(ex)) {
            return getDuplicateMessage(ex.getMessage());
        } else {
            return null;
        }
    }

    String getInvalidMessages(String message) {
        StringBuilder parsedMessageBuilder = new StringBuilder();
        String[] quotedParts = message.split("\"");
        for (String part : quotedParts) {
            if (part.contains("INVALID "))
                parsedMessageBuilder.append(part).append("\n");
        }
        return parsedMessageBuilder
                .delete(parsedMessageBuilder.length() - 2, parsedMessageBuilder.length())
                .toString();
    }

    String getDuplicateMessage(String message) {
        return "\"" + message.split("\'")[1] + "\" ALREADY EXISTS";
    }

    void logTechnicalWarning(String entityName, Exception ex) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.warn("TECHNICAL REST EXCEPTION IN REQUEST BY \"" + userEmail + "\" FOR ENTITY \"" + entityName + "\": \"" + ex.getMessage() + "\"");
    }

    public ServiceUtil() {
    }
}
