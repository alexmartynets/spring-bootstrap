package com.pp9.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

@Entity
@Table(name = "role_table")
public class Role implements GrantedAuthority {
    private static Logger log = Logger.getLogger(Role.class.getName());

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "role")
    private String role;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new LinkedHashSet<>();

    @Override
    public String getAuthority() {
        log.info("GET ROLE");
        return role;
    }

    public Role() {}

    public Role(String role) {
        log.info("ROLE 1");
        this.role = role;
    }

    public Role(Long id, Set<User> users) {
        log.info("ROLE 2");
        this.id = id;
        this.users = users;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        Role.log = log;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(id, role1.id) &&
                Objects.equals(role, role1.role) &&
                Objects.equals(users, role1.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, users);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}