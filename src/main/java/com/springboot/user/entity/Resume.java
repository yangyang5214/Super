package com.springboot.user.entity;

import com.springboot.common.entity.AggregateRoot;

import javax.persistence.*;

/**
 * Created by wangxiaosan on 2017/3/28.
 */
@Entity
@Table(name = "RESUME")
public class Resume  extends AggregateRoot {

    @Id
    @GeneratedValue()
    @Column(name = "ID", nullable = false, unique = true)
    private long id;



    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID",nullable = false)
    private User user;

}
