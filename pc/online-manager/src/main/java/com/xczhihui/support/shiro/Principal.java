package com.xczhihui.support.shiro;

import com.xczhihui.bxg.online.common.domain.User;

public class Principal implements java.security.Principal {

    private String id;

    private String username;

    private String name;

    public Principal(User user) {
        this.id = user.getId();
        this.username = user.getLoginName();
        this.name = user.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Principal principal = (Principal) o;

        return username != null ? username.equals(principal.username) : principal.username == null;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return username;
    }
}
