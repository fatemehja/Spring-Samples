package com.springdemo.model;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@Table(name="ROLE", uniqueConstraints={@UniqueConstraint (columnNames="NAME")})
public class Role implements Serializable {

    private static final long serialVersionUID = -9162292216387180496L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NAME")
//    @NotNull
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Account> accounts = new HashSet<Account>(0);
}