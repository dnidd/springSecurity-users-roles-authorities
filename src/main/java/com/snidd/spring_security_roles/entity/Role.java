package com.snidd.spring_security_roles.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;


    // Bidirectional relation:
    // A user can have multiple roles, a role can have multiple users.
    // role.getUsers() + user.getRoles().
    // The relation is defined/mapped by Set 'roles' in User.
    // The definition is the join table of user_id & id_id.
    @ManyToMany(mappedBy = "roles")
    // A role can have many users.
    private Set<User> users;


    /**
     * Join table role_authorities.
     * role_authorities joins roles & authorities.
     * EAGER fetches authorities when a Role is fetched.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_authorities",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    // A Role can have many authorities.
    private Set<Authority> authorities;


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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", authorities=" + authorities +
                '}';
    }
}
