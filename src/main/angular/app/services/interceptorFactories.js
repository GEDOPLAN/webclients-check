(function () {
    "use strict";

    angular.module("GedoplanJAX.services.interceptors", [])
            .factory("httpInterceptor", function ($cookies, $injector,  growl, $q) {
                return {
                    responseError: function (rejection) {
                        if (rejection.status === 455) {
                            $cookies.remove("authToken");
                            $injector.get('$state').transitionTo('login');
                        } else if (rejection.status === 456) {
                            growl.error("Not allowed");
                        } else
                        {
                            growl.error("Unexpected HTTP Error");
                        }
                        return $q.reject(rejection);
                    },
                    requestError: function (rejection) {
                        growl.error("Unexpected HTTP Error");
                        return $q.reject(rejection);
                    },
                    request: function (config) {
                        var auth = $cookies.getObject("authToken");
                        if (auth) {
                            config.headers.Authorization = auth;
                        }
                        return config;
                    }
                };
            });
})();


