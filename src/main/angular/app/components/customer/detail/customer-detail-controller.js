(function () {
    "use strict";

    angular.module("GedoplanWebClients.customer.detail", ['ui.router'])
            .controller("customer-detail", function (growl, $translate, customerService, $stateParams) {
                var vm = this;
                vm.customer = customerService.getCustomerDetail($stateParams.id);
                vm.customer.$promise.then(function (c) {
                    vm.custometDiscount = customerService.getCustomerDiscount(c.customerID);
                });

                vm.saveCustomer = function () {
                    vm.customer.$save().then(function () {
                        growl.info($translate.instant("save-success"));
                    });
                };
            });
})();


