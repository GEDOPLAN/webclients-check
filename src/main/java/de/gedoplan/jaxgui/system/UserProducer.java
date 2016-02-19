package de.gedoplan.jaxgui.system;

import de.gedoplan.jaxgui.model.User;
import de.gedoplan.jaxgui.service.UserService;
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
    public User getUser() {
        this.user = userService.loadUser();

        return user;
    }
}
