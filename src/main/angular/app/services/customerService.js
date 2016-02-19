(function () {
    "use strict";

    angular.module("GedoplanJAX.services.customer", ['ngResource'])
            .service("customerService", function ($resource, restbaseurl) {

                var customerResource = $resource(restbaseurl + "customer", null, {
                    details: {method: 'GET', url: restbaseurl + 'customer/detail/:id', params: {id: '@id'}},
                    discount: {method: 'GET', url: restbaseurl + 'customer/discount/:id', params: {id: '@id'}}
                });

                this.getCustomer = function (settings) {
                    return customerResource.get(settings);
                };
                
                this.getCustomerDiscount = function(id) {
                    return customerResource.discount({id:id});
                };
                
                this.getCustomerDetail = function(id) {
                    return customerResource.details({id:id});
                };
            });
})();


