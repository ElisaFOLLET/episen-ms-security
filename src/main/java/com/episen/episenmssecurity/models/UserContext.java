package com.episen.episenmssecurity.models;

import java.util.List;

public class UserContext {

    private String subject;
    private String password;
    private List<String> roles;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserContext [subject=" + subject + "]";
    }

}
