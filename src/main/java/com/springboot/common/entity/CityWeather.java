package com.springboot.common.entity;

import javax.persistence.*;

/**
 * Created by zhanglong on 2017/5/22.
 */
@Entity
@Table(name = "CITYWEATHER")
public class CityWeather extends AggregateRoot{

    @Id
    @GeneratedValue()
    @Column(name = "ID", nullable = false, unique = true)
    private long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
