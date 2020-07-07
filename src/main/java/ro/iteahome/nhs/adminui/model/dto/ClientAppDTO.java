package ro.iteahome.nhs.adminui.model.dto;

import ro.iteahome.nhs.adminui.model.entity.Role;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class ClientAppDTO {

    private int id;

    private String name;

    private String password;

    private int status;

    List<Role> rolesList;

    String selectedRoleName;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public ClientAppDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Role> rolesList) {
        this.rolesList = rolesList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSelectedRoleName() {
        return selectedRoleName;
    }

    public void setSelectedRoleName(String selectedRoleName) {
        this.selectedRoleName = selectedRoleName;
    }
}
