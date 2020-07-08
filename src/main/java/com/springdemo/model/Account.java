package com.springdemo.model;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@Table(name="ACCOUNT", uniqueConstraints = {@UniqueConstraint (columnNames = "USERNAME"),
        @UniqueConstraint (columnNames = "EMAIL")})
public class Account implements Serializable {
    private static final long serialVersionUID = 2872791921224905344L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USERNAME")
//    @NotNull
    private String username;

    @Column(name = "PASSWORD")
//    @NotNull
    private String password;

    @Column(name = "EMAIL")
//    @NotNull
    //@Email
    private String email;

    @Column(name = "ENABLED")
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ACCOUNT_ROLE", joinColumns = {@JoinColumn(name = "ID_ACCOUNT")}, inverseJoinColumns = {@JoinColumn(name = "ID_ROLE")})
    private Set<Role> roles = new HashSet<Role>(0);

}