(function () {
    "use strict";

    angular.module("GedoplanWebClients.template", [])
            .controller("templateController", function (userService) {
                var vm = this;

                vm.getUser = function ()
                {
                    return userService.getCurrentUser();
                };
            });
})();


