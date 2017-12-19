var app = angular.module('myApp', []);
app.controller('checkLogin', function ($scope, $http) {
    $http({
        method: 'GET',
        url: '/user/checkLogin'
    }).then(function successCallback(response) {
        $scope.user = response.data;
    }, function errorCallback(response) {

    });
});
