(function () {
    "use strict";

    angular.module("GedoplanJAX.customer.detail", ['ui.router'])
            .controller("customer-detail", function (customer, growl, $translate, customerService) {
                var vm = this;
                vm.customer = customer;
                vm.custometDiscount=customerService.getCustomerDiscount(customer.customerID);
                
                vm.saveCustomer = function() {
                  vm.customer.$save().then(function() {
                      growl.info($translate.instant("save-success"));
                  });
                };
            });
})();


