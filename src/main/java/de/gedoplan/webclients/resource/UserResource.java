package de.gedoplan.webclients.resource;

import de.gedoplan.webclients.model.User;
import de.gedoplan.webclients.service.UserService;
import de.gedoplan.webclients.system.CurrentUser;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("user")
@Consumes("application/json")
@Produces("application/json")
public class UserResource {

    @Inject
    @CurrentUser
    User currentUser;

    @Inject
    private UserService userService;
    
    @GET
    public User getUser() {
        return userService.loadUser();
    }
    
    

}
