package ro.iteahome.nhs.adminui.model.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

public class Doctor {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    @NotNull(message = "ID CANNOT BE EMPTY")
    private int id;

    @NotNull(message = "CNP CANNOT BE EMPTY")

    private String cnp;

    @NotNull(message = "EMAIL CANNOT BE EMPTY")
    @Email(regexp = ".+@.+\\.\\w+", message = "INVALID EMAIL ADDRESS")
    private String email;

    @NotNull(message = "FIRST NAME CANNOT BE EMPTY")
    private String firstName;

    @NotNull(message = "LAST NAME CANNOT BE EMPTY")
    private String lastName;

    @NotNull(message = "MEDICAL LICENSE CANNOT BE EMPTY")
    private String licenseNo;

    @NotNull(message = "PHONE NUMBER NAME CANNOT BE EMPTY")

    @Pattern(regexp = "^0040\\d{9}$", message = "INVALID PHONE NUMBER")
    private String phoneNoRo;

    @NotNull(message = "SPECIALTIES CANNOT BE EMPTY")
    private String specialties;

    @NotNull(message = "TITLE CANNOT BE EMPTY")
    private String title;

    private Set<Institution> institutions;

    private String institutionCUIs;

    // METHODS: ------------------------------------------------------------------------------------------------------------

    public Doctor() {
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

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getPhoneNoRo() {
        return phoneNoRo;
    }

    public void setPhoneNoRo(String phoneNoRo) {
        this.phoneNoRo = phoneNoRo;
    }

    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }

    public void setInstitutions(Set<Institution> institutions) {
        this.institutions = institutions;
    }

    public Set<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutionCUIs(String institutionCUIs) {
        this.institutionCUIs = institutionCUIs;
    }

    public String getInstitutionCUIs() {
        return institutionCUIs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
