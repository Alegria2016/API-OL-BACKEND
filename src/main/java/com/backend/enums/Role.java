package com.backend.enums;

import java.util.Arrays;
import java.util.List;

public enum Role {
    //ROLE REGISTRATION_ASSISTANT
    REGISTRATION_ASSISTANT(Arrays.asList(
            Permission.GET_PROFILE_USER
    )),


    //ROLE ADMINISTRADOR
    ADMIN(Arrays.asList(
            //USERS
            Permission.READ_ALL_USERS,
            Permission.UPDATE_USER,
            Permission.DELETE_USER,
            Permission.READ_USER_BY_ID,
            Permission.GET_PROFILE_USER

    ));



    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
