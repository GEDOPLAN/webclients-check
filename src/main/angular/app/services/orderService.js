(function () {
    "use strict";

    angular.module("GedoplanJAX.services.order", ['ngResource'])
            .service("orderService", function ($resource, restbaseurl) {

                var orderResource = $resource(restbaseurl + "order");
        
                this.getOrders=function(settings) {
                    return orderResource.get(settings);
                };
            });
})();


