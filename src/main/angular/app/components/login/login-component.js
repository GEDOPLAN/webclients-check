(function () {
    "use strict";
    angular.module('GedoplanWebClients.login', [])
            .controller("login", function (userService, $translate, $state, $timeout, $scope) {
                var vm = this;

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
            })
            .directive("wccLogout", function () {
                return {
                    template: '<a ng-click="vmLogin.logout()">Logout</a>',
                    controller: 'login',
                    controllerAs: 'vmLogin'
                };
            });
})();


