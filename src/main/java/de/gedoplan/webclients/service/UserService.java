package de.gedoplan.webclients.service;

import de.gedoplan.webclients.model.User;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;


@Stateless
public class UserService implements Serializable {

    @Inject
    private HttpServletRequest httpRequest;

    private static final String[] availableRoles = {"admin", "customer"};

    @RolesAllowed({User.UserRole.ADMINS, User.UserRole.CUSTOMERS})
    public User loadUser() {
        User user = new User();
        user.setLogin(httpRequest.getUserPrincipal().getName());

        List<String> roles = Arrays.stream(availableRoles).filter(role -> httpRequest.isUserInRole(role)).collect(Collectors.toList());
        user.setRoles(roles);

        if (user.getRoles().contains("customer")) {
            user.setCustomerID(user.getLogin());
        }

        return user;
    }
}
