(function () {
    "use strict";
    var app = angular.module("GedoplanJAX", ['GedoplanJAX.home', 'GedoplanJAX.order.list', 'GedoplanJAX.customer.detail', 'GedoplanJAX.customer.list', 'GedoplanJAX.services.user', 'GedoplanJAX.services.customer', 'ui.router', 'pascalprecht.translate', 'ngResource', 'ngCookies', 'valdr', 'angular-growl', 'GedoplanJAX.directives.historyBack'])
            .config(function ($stateProvider, $urlRouterProvider, $translateProvider, $httpProvider, valdrProvider, valdrMessageProvider, growlProvider) {
//Navigation Rules
                $stateProvider
                        .state('login', {
                            url: '/login',
                            templateUrl: 'login.html',
                            controller: 'app',
                            controllerAs: 'vm'
                        })
                        .state('app', {
                            url: '/app',
                            templateUrl: 'template.html',
                            controller: 'app',
                            controllerAs: 'vm'
                        })
                        .state('app.home', {
                            url: '/home',
                            templateUrl: 'components/home/home.html',
                            controller: 'home',
                            controllerAs: 'vm'
                        })
                        .state('app.customer-detail', {
                            url: '/customer/detail/{id}',
                            templateUrl: 'components/customer/detail/customer-detail.html',
                            controller: 'customer-detail',
                            controllerAs: 'vm',
                            resolve: {
                                customer: function (customerService, $stateParams) {
                                    return customerService.getCustomerDetail($stateParams.id).$promise;
                                }
                            }
                        })
                        .state('app.customer-list', {
                            url: '/customer',
                            templateUrl: 'components/customer/list/customer-list.html',
                            controller: 'customer-list',
                            controllerAs: 'vm'
                        })
                        .state('app.order-list', {
                            url: '/order',
                            templateUrl: 'components/order/list/order-list.html',
                            controller: 'order-list',
                            controllerAs: 'vm'
                        })
                        ;

                $urlRouterProvider.otherwise("login");

// i18n
                $translateProvider.useSanitizeValueStrategy('escape');
                $translateProvider.registerAvailableLanguageKeys(['de', 'en'], {
                    'en_*': 'en',
                    'de_*': 'de'
                });
                $translateProvider.determinePreferredLanguage();
                $translateProvider.fallbackLanguage('de');
                $translateProvider.useStaticFilesLoader({
                    prefix: 'assets/i18n/',
                    suffix: '.json'
                });

                $httpProvider.defaults.withCredentials = true;
                $httpProvider.interceptors.push(function ($q, $injector, $cookies, growl) {
                    return {
                        responseError: function (rejection) {
                            if (rejection.status === 455) {
                                $cookies.remove("authToken");
                                $injector.get('$state').transitionTo('login');
                            } else if (rejection.status === 456) {
                                growl.error("Not allowed");
                            } else
                            {
                                growl.error("Unexpected HTTP Error");
                            }
                            return $q.reject(rejection);
                        },
                        requestError: function (rejection) {
                            growl.error("Unexpected HTTP Error");
                            return $q.reject(rejection);
                        },
                        request: function (config) {
                            var auth = $cookies.getObject("authToken");
                            if (auth) {
                                config.headers.Authorization = auth;
                            }
                            return config;
                        }
                    };
                });

// Bean Validation
                valdrProvider.setConstraintUrl('../bvrules');

                valdrMessageProvider.setTemplate('<span class="label label-danger valdrmsg">{{ violation.message | translate:violation }}</span>');

// Growl
                growlProvider.globalTimeToLive(5000);
            })

            .run(function ($window, $rootScope) {
                $rootScope.language = $window.navigator.language || $window.navigator.userLanguage;
            })

            .controller("app", function (userService, $state, $timeout, $translate) {
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

    app.constant("restbaseurl", "../webresources/");

})();




