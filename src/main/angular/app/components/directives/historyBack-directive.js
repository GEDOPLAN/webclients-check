(function () {
    "use strict";
    angular.module("GedoplanWebClients.directives.historyBack", [])
            .directive('historyBack', ['$window', function ($window) {
                    return {
                        restrict: 'A',
                        link: function (scope, elem) {
                            elem.bind('click', function () {
                                $window.history.back();
                            });
                        }
                    };
                }]);
})();


