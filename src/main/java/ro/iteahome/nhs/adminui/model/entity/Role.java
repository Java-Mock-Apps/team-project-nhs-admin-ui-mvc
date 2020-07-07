package ro.iteahome.nhs.adminui.model.entity;

import javax.validation.constraints.NotNull;

public class Role {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    @NotNull
    private int id;

    @NotNull
    private String name;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public Role() {
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
