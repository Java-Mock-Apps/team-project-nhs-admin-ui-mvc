package ro.iteahome.nhs.adminui.model.form;

import javax.validation.constraints.NotEmpty;

public class RoleNameForm {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    // NO ID.

    @NotEmpty(message = "ROLE NAME CANNOT BE EMPTY")
    private String name;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public RoleNameForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
