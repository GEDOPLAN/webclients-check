(function () {
    "use strict";

    angular.module("GedoplanJAX.order.template", [])
            .controller("templateController", function (userService, $state, $timeout, $translate) {
                var vm = this;

                vm.getUser = function ()
                {
                    return userService.getCurrentUser();
                };

                vm.login = function () {
                    var loginResponse = userService.login(vm.username, vm.password);
                    if (loginResponse)
                    {
                        loginResponse.$promise.then(
                                function () {
                                    $state.transitionTo('app.home');
                                },
                                function (reject) {
                                    if (reject.status === 455)
                                    {
                                        vm.message = $translate.instant("login.invalid");
                                    }
                                }
                        );
                    }
                };

                vm.logout = function () {
                    userService.logout();
                    $timeout(function () {
                        $state.transitionTo('login');
                    }, 500);
                };
            });
})();


