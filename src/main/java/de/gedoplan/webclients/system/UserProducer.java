package de.gedoplan.webclients.system;

import de.gedoplan.webclients.model.User;
import de.gedoplan.webclients.service.UserService;
import de.gedoplan.webclients.model.User;
import de.gedoplan.webclients.service.UserService;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@RequestScoped
public class UserProducer implements Serializable {

    private User user;

    @Inject
    private UserService userService;

    @Produces
    @CurrentUser
    @RequestScoped
    public User getUser() {
        this.user = userService.loadUser();

        return user;
    }
}
