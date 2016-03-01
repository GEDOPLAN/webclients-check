(function () {
    "use strict";
    angular.module("GedoplanWebClients.order.list", ['GedoplanWebClients.services.order', 'GedoplanWebClients.services.tablehelper'])
            .controller("order-list", function (orderService, tableHelper, $filter, $translate) {
                var context = this;

                tableHelper.getTranslation().then(function (result) {
                    context.initTable(result.data);
                });

                this.initTable = function (translation) {
                    jQuery('#orderlist').DataTable({
                        ajax: function (data, callback, settings) {
                            var restSettings = tableHelper.parseRestSettings(data, settings);
                            orderService.getOrders(restSettings).$promise.then(function (response) {
                                callback({
                                    data: response.result,
                                    recordsTotal: response.resultCount,
                                    recordsFiltered: response.resultCount
                                });
                            });
                        },
                        language: translation,
                        serverSide: true,
                        searching: false,
                        columns: [
                            {title: $translate.instant("order.id"), data: "orderID"},
                            {title: $translate.instant("order.orderDate"), data: "orderDate", render: function (data) {
                                    return $filter('date')(new Date(data), "dd.MM.yyyy");
                                }},
                            {title: $translate.instant("order.freight"), data: "freight"},
                            {title: $translate.instant("order.name"), data: "shipName"},
                            {title: $translate.instant("order.street"), data: "shipAddress"},
                            {title: $translate.instant("order.zipcode"), data: "shipPostalCode"},
                            {title: $translate.instant("order.city"), data: "shipCity"}
                        ]
                    });
                };
            });
})();


