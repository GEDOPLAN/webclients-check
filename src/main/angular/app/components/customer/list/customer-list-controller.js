(function () {
    "use strict";

    angular.module("GedoplanJAX.customer.list", ['GedoplanJAX.services.customer'])
            .controller("customer-list", function (customerService, tableHelper, $translate, $rootScope, $state) {

                jQuery('#customerlist').DataTable({
                    ajax: function (data, callback, settings) {
                        var restSettings = tableHelper.parseRestSettings(data, settings);
                        customerService.getCustomer(restSettings).$promise.then(function (response) {
                            callback({
                                data: response.result,
                                recordsTotal: response.resultCount,
                                recordsFiltered: response.resultCount
                            });
                        });
                    },
                    serverSide: true,
                    searching: false,
                    language: {url: "assets/i18n/" + $rootScope.language + "_datatable.json"
                    },
                    columns: [
                        {title: $translate.instant("customer.id"), type: 'html', data: "customerID",
                            render: function (data) {
                                return '<a>' + data + '</a>';
                            },
                            createdCell: function (td, cellData, rowData) {
                                jQuery(td).find("a").on("click", function () {
                                    $state.transitionTo('app.customer-detail', {id: rowData.customerID});
                                });
                            }
                        },
                        {title: $translate.instant("customer.name"), data: "companyName"},
                        {title: $translate.instant("customer.contact"), data: "contactName"},
                        {title: $translate.instant("customer.street"), data: "address"},
                        {title: $translate.instant("customer.zipcode"), data: "postalCode"},
                        {title: $translate.instant("customer.city"), data: "city"}
                    ]
                });
            });
})();


