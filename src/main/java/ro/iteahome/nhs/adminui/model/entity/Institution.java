package ro.iteahome.nhs.adminui.model.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Institution {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    @NotNull(message = "ID CANNOT BE EMPTY")
    private int id;

    @NotNull(message = "TYPE CANNOT BE EMPTY")
    private String type;

    @NotNull(message = "CUI CANNOT BE EMPTY")
    private String cui;

    @NotNull(message = "NAME CANNOT BE EMPTY")
    private String name;

    @NotNull(message = "ADDRESS CANNOT BE EMPTY")
    private String address;

    @NotNull(message = "PHONE NUMBER NAME CANNOT BE EMPTY")
    @Pattern(regexp = "^0040\\d{9}$", message = "INVALID PHONE NUMBER")
    private String phoneNoRo;

    @NotNull(message = "EMAIL CANNOT BE EMPTY")
    @Email(regexp = ".+@.+\\.\\w+", message = "INVALID EMAIL ADDRESS")
    private String email;

    private String website;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public Institution() {
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
