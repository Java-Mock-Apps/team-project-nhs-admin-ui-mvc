package ro.iteahome.nhs.adminui.model.form;

import javax.validation.constraints.NotEmpty;

public class RoleForm {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    // NO ID.

    @NotEmpty(message = "ROLE NAME CANNOT BE EMPTY")
    private String name;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public RoleForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
