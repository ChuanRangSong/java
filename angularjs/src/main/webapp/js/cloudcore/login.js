var app = angular.module('myApp', []);
app.controller('checkLogin', function ($scope, $http) {
    $http({
        method: 'GET',
        url: '/user/checkLogin?username=username'
    }).then(function successCallback(response) {
        $scope.user = response.data;
    }, function errorCallback(response) {

    });
});
