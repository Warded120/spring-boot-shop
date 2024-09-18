package com.ivan.usercrud.template;

import com.ivan.usercrud.entity.Address;
import com.ivan.usercrud.entity.Role;
import com.ivan.usercrud.entity.User;
import com.ivan.usercrud.validation.imageUrl.ImageUrl;
import com.ivan.usercrud.validation.passwordMatch.PasswordMatch;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@PasswordMatch
public class UserTemplate {
    private int id;

    @NotBlank(message = "required")
    @Email(message = "not an email")
    private String username;

    @NotBlank(message = "required")
    @Size(min = 6, max = 255, message = "must be longer than 6 and shorter than 255 characters")
    private String password;

    @NotBlank(message = "required")
    @Size(min = 6, max = 255, message = "must be longer than 6 and shorter than 255 characters")
    private String confirmPassword;

    private boolean enabled = true;
    private String topRole;

    private int userDataId;

    @NotBlank(message = "required")
    private String firstName;

    @NotBlank(message = "required")
    private String lastName;

    @ImageUrl
    private String imageURL;
    private Address address;

    public UserTemplate(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.confirmPassword = user.getPassword();
        this.enabled = user.isEnabled();
        this.topRole = getTopRoleFromRoles(user);

        this.userDataId = user.getUserData().getId();
        this.firstName = user.getUserData().getFirstName();
        this.lastName = user.getUserData().getLastName();
        this.imageURL = user.getUserData().getImageURL();
        this.address = user.getUserData().getAddress();
    }

    private String getTopRoleFromRoles(User user) {
        List<Role> roles = (List<Role>) user.getRoles();

        for (Role role : roles) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return "ROLE_ADMIN";
            }
        }

        for (Role role : roles) {
            if (role.getName().equals("ROLE_MANAGER")) {
                return "ROLE_MANAGER";
            }
        }

        return "ROLE_CUSTOMER";
    }
}
