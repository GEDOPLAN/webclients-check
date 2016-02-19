(function () {
    "use strict";

    angular.module("GedoplanJAX.services.user", ['ngResource', 'ngCookies'])
            .service("userService", function ($resource, restbaseurl, $cookies) {

                var userResource = $resource(restbaseurl + "user");

                var user;

                this.login = function (username, password) {
                    this.user = null;
                    $cookies.putObject("authToken", "Basic " + btoa(username + ":" + password));
                    return this.getCurrentUser();
                };

                this.logout = function () {
                    $cookies.remove("authToken");
                    if (bowser.msie) {
                        document.execCommand('ClearAuthenticationCache', 'false');
                    } else if (bowser.gecko) {
                        $.ajax({
                            async: false,
                            url: restbaseurl + "user",
                            type: 'GET',
                            username: 'logout'
                        });
                    } else if (bowser.webkit) {
                        var xmlhttp = new XMLHttpRequest();
                        xmlhttp.open("GET", restbaseurl + "user", true);
                        xmlhttp.setRequestHeader("Authorization", "Basic logout");
                        xmlhttp.send();
                    } else {
                        alert("Logging out automatically is unsupported for " + bowser.name
                                + "\nYou must close the browser to log out.");
                    }
                };

                this.getCurrentUser = function () {
                    var context=this;
                    if (!this.user) {
                        this.user = userResource.get();
                        this.user.$promise.then(function (result) {
                                jQuery.each(["admin", "customer"], function (index, role) {
                                     context.user[role] = jQuery.inArray(role, context.user.roles);
                                });
                        });
                    }

                    return this.user;
                };
            });
})();


