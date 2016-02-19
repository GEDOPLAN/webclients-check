package de.gedoplan.jaxgui.jsf.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;

@Named
@RequestScoped
public class LoginController implements BaseController {

    private String loginname;

    private String password;

    @Inject
    private UserController userController;

    public String login() {
        try {
            BaseController.super.getRequest().login(loginname, password);
        } catch (ServletException ex) {
            addErrorMessage("login_invalid");
            return null;
        }
        return "home";
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
