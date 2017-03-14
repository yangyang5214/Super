package com.springboot.common.listener;

import java.io.Serializable;

public class StoredUser implements Serializable{
	public static final String KEY = "user";

    private Long id;

	private String name;

    private String token;

    private Boolean isActive = false;

    public static String getKEY() {
        return KEY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
