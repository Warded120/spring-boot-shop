package com.ivan.usercrud.entity;

import com.ivan.usercrud.template.UserTemplate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user", schema = "shop")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", schema = "shop",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")  )
    private Collection<Role> roles;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_data_id", referencedColumnName = "id")
    private UserData userData;

    public User(UserTemplate userTemplate, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.id = userTemplate.getId();
        this.username = userTemplate.getUsername();
        this.password = bCryptPasswordEncoder.encode(userTemplate.getPassword()); // encode the password
        this.roles = getRolesFromTemplate(userTemplate);
        this.userData = new UserData(userTemplate.getUserDataId(),
                                     userTemplate.getFirstName(),
                                     userTemplate.getLastName(),
                                     userTemplate.getImageURL(),
                                     userTemplate.getAddress());
    }

    private List<Role> getRolesFromTemplate(UserTemplate userTemplate) {
        List<Role> tempAuthorities = new ArrayList<>();

        switch (userTemplate.getTopRole()) {
            case "customer":
                tempAuthorities.add(new Role("ROLE_CUSTOMER"));
                break;
            case "manager":
                tempAuthorities.add(new Role("ROLE_CUSTOMER"));
                tempAuthorities.add(new Role("ROLE_MANAGER"));
                break;
            case "admin":
                tempAuthorities.add(new Role("ROLE_CUSTOMER"));
                tempAuthorities.add(new Role("ROLE_MANAGER"));
                tempAuthorities.add(new Role("ROLE_ADMIN"));
                break;
        }
        return tempAuthorities;
    }
}
