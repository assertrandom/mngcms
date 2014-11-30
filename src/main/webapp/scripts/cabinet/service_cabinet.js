'use strict';

mngcmsApp.factory('Cabinet', function ($resource) {
        return $resource('app/rest/cabinets/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
