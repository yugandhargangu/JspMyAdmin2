package com.tracknix.jspmyadmin.application.database.users.beans;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token = null;
    private String user = null;
    private String type = null;
    private String[] obj_rights = null;
    private String[] ddl_rights = null;
    private String[] other_rights = null;

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the obj_rights
     */
    public String[] getObj_rights() {
        return obj_rights;
    }

    /**
     * @param obj_rights the obj_rights to set
     */
    public void setObj_rights(String[] obj_rights) {
        this.obj_rights = obj_rights;
    }

    /**
     * @return the ddl_rights
     */
    public String[] getDdl_rights() {
        return ddl_rights;
    }

    /**
     * @param ddl_rights the ddl_rights to set
     */
    public void setDdl_rights(String[] ddl_rights) {
        this.ddl_rights = ddl_rights;
    }

    /**
     * @return the other_rights
     */
    public String[] getOther_rights() {
        return other_rights;
    }

    /**
     * @param other_rights the other_rights to set
     */
    public void setOther_rights(String[] other_rights) {
        this.other_rights = other_rights;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    // extra getters

    /**
     * @param index
     * @return
     */
    public String obj_rights_val(int index) {
        return obj_rights[index];
    }

    /**
     * @param index
     * @return
     */
    public String ddl_rights_val(int index) {
        return ddl_rights[index];
    }

    /**
     * @param index
     * @return
     */
    public String other_rights_val(int index) {
        return other_rights[index];
    }
}
