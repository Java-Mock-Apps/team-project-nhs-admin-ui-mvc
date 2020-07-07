package ro.iteahome.nhs.adminui.model.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AdminCreationForm {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    // NO ID. (REST-GENERATED)

    @Email(regexp = ".+@.+\\.\\w+", message = "INVALID EMAIL ADDRESS")
    private String email;

    @Pattern(regexp = "((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,32})", message = "INVALID PASSWORD")
    private String password;

    @NotBlank(message = "FIRST NAME CANNOT BE EMPTY")
    private String firstName;

    @NotBlank(message = "LAST NAME CANNOT BE EMPTY")
    private String lastName;

    @Pattern(regexp = "^0040\\d{9}$", message = "INVALID PHONE NUMBER")
    private String phoneNoRo;

    // NO STATUS. (REST-GENERATED)

    // NO ROLE. (REST-GENERATED)

// METHODS: ------------------------------------------------------------------------------------------------------------

    public AdminCreationForm() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNoRo() {
        return phoneNoRo;
    }

    public void setPhoneNoRo(String phoneNoRo) {
        this.phoneNoRo = phoneNoRo;
    }
}
