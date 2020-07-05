package ro.iteahome.nhs.adminui.model.form;

import javax.validation.constraints.Pattern;

public class AdminIdForm {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    @Pattern(regexp = "\\d+", message = "INVALID ID")
    private int id; // Validation achieved through html "type" attribute (i.e. the "number" type).

// METHODS: ------------------------------------------------------------------------------------------------------------

    public AdminIdForm() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
