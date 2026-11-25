package com.snidd.spring_security_roles.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;


    // Bidirectional relation:
    // A role can have multiple authorities, an authority can have multiple roles.
    // role.getAuthorities() + authority.getRoles()
    // The relation is defined/mapped by Set 'authorities' in Role.
    // The definition is the join table of role_id & authority_id.
    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }
}