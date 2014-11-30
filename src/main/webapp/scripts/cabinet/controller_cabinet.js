'use strict';

mngcmsApp.controller('CabinetController', function ($scope, resolvedCabinet, Cabinet) {

        $scope.cabinets = resolvedCabinet;

        $scope.create = function () {
            Cabinet.save($scope.cabinet,
                function () {
                    $scope.cabinets = Cabinet.query();
                    $('#saveCabinetModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.cabinet = Cabinet.get({id: id});
            $('#saveCabinetModal').modal('show');
        };

        $scope.delete = function (id) {
            Cabinet.delete({id: id},
                function () {
                    $scope.cabinets = Cabinet.query();
                });
        };

        $scope.clear = function () {
            $scope.cabinet = {cabinet: null, owner: null, dateCreated: null, dateLastModified: null, id: null};
        };
    });
