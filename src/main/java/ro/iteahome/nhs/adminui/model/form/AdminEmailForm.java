package ro.iteahome.nhs.adminui.model.form;

import javax.validation.constraints.Email;

public class AdminEmailForm {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    @Email(regexp = ".+@.+\\.\\w+", message = "INVALID EMAIL ADDRESS")
    private String email; // Default validation through html "type" attribute (i.e. the "email" type) is not enough.

// METHODS: ------------------------------------------------------------------------------------------------------------

    public AdminEmailForm() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
