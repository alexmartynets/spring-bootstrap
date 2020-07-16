package com.pp9.model;

import com.pp9.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Entity
@Table(name = "users_table")
public class User implements UserDetails {

    private static Logger log = Logger.getLogger(User.class.getName());
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( unique=true, name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "money")
    private Long money;

    /*
    JoinTable
    Самому сделать общую таблицу
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Role> roles = new LinkedHashSet<>();

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    private State state;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !state.equals(State.BANNED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return state.equals(State.ACTIVE);
    }

//    public String getRoleName() {
//        return role.getAuthority();
//    }

    public User() {
        log.info("USE 4");
    }

    public User(String username, String password, Long money, Set<Role> roles, State state) {
        log.info("USE 1");
        this.username = username;
        this.password = password;
        this.money = money;
        this.roles = roles;
        this.state = state;
    }

    public User(Long id, String username, String password, Long money, Set<Role> roles, State state) {
        log.info("USE 2");
        this.id = id;
        this.username = username;
        this.password = password;
        this.money = money;
        this.roles = roles;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        log.info("USE 5");
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        log.info("USE 7 username: " + username);
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        log.info("USE 6 password: " + password);
        this.password = password;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        log.info("USE 8 money: " + money);
        this.money = money;
    }

    public Set<Role> getRole() {
        return roles;
    }

    public Set<String> getRoleString() {
        return roles.stream().map(Role::getAuthority).collect(Collectors.toSet());
    }

    public Set<String> getMissingRole(Set<String> roleSet)  {

        log.info("ALL ROLES START: " + roleSet);
        log.info("getMissingRole USER NAME: " + username);
//        roleSet.removeAll(roles.stream().map(Role::getAuthority).collect(Collectors.toSet()));
        Set<String> existRoles = roles.stream().map(Role::getAuthority).collect(Collectors.toSet());
        Set<String> stringSet = roleSet.stream().filter(el-> !existRoles.contains(el)).collect(Collectors.toSet());
        log.info("MISSING ROLE END: " + stringSet);
        return stringSet;
    }

    public void setRole(Set<Role> roles) {
        log.info("USE 3");
        this.roles = roles;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", money=" + money +
                ", roles=" + roles +
                ", state=" + state +
                '}';
    }
}