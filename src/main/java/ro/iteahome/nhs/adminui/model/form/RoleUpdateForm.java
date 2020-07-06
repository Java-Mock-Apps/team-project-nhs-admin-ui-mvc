package ro.iteahome.nhs.adminui.model.form;

import javax.validation.constraints.NotEmpty;

public class RoleUpdateForm {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private int id;

    @NotEmpty(message = "ROLE NAME CANNOT BE EMPTY")
    private String name;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public RoleUpdateForm() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
