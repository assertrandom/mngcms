'use strict';

mngcmsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/cabinet', {
                    templateUrl: 'views/cabinets.html',
                    controller: 'CabinetController',
                    resolve:{
                        resolvedCabinet: ['Cabinet', function (Cabinet) {
                            return Cabinet.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
