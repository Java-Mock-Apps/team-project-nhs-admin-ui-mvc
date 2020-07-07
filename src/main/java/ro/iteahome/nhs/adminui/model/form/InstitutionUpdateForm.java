package ro.iteahome.nhs.adminui.model.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class InstitutionUpdateForm {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private int id;

    @NotEmpty(message = "TYPE CANNOT BE EMPTY")
    private String type;

    @NotEmpty(message = "CUI CANNOT BE EMPTY")
    private String cui;

    @NotEmpty(message = "NAME CANNOT BE EMPTY")
    private String name;

    @NotEmpty(message = "ADDRESS CANNOT BE EMPTY")
    private String address;

    @Pattern(regexp = "^0040\\d{9}$", message = "INVALID PHONE NUMBER")
    private String phoneNoRo;

    @Email(regexp = ".+@.+\\.\\w+", message = "INVALID EMAIL ADDRESS")
    private String email;

    private String website;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public InstitutionUpdateForm() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNoRo() {
        return phoneNoRo;
    }

    public void setPhoneNoRo(String phoneNoRo) {
        this.phoneNoRo = phoneNoRo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
