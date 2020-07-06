package ro.iteahome.nhs.adminui.model.entity;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class Patient {

    // FIELDS: -------------------------------------------------------------------------------------------------------------
    @NotNull(message = "ID CANNOT BE EMPTY.")
    private int id;

    @NotNull(message = "CNP CANNOT BE EMPTY.")
    private String cnp;

    @NotNull(message = "EMAIL CANNOT BE EMPTY.")
    @Email(regexp = ".+@.+\\.\\w+", message = "INVALID EMAIL ADDRESS")
    private String email;

    @NotNull(message = "FIRST NAME CANNOT BE EMPTY.")
    private String firstName;

    @NotNull(message = "LAST NAME CANNOT BE EMPTY.")
    private String lastName;

    @NotNull(message = "PHONE NUMBER NAME CANNOT BE EMPTY.")
    @Pattern(regexp = "^0040\\d{9}$", message = "INVALID PHONE NUMBER")
    private String phoneNoRo;
    // METHODS: ------------------------------------------------------------------------------------------------------------

    public Patient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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