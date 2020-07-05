package ro.iteahome.nhs.adminui.model.form;

import javax.validation.constraints.NotNull;

public class RoleCreationForm {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    // NO ID.

    @NotNull
    private String name;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public RoleCreationForm() {
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
