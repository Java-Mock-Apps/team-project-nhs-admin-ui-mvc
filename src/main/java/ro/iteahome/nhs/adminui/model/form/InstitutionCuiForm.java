package ro.iteahome.nhs.adminui.model.form;

import javax.validation.constraints.NotEmpty;

public class InstitutionCuiForm {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    @NotEmpty(message = "CUI CANNOT BE EMPTY")
    private String cui;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public InstitutionCuiForm() {
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }
}
