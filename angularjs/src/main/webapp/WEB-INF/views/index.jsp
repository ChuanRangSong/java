<!doctype html>
<html ng-app>
<head>
    <meta content="text/html" charset="UTF-8">
    <title>AngularJS</title>
    <style>
        input.ng-invalid {
            background-color: lightblue;
        }
    </style>
</head>
<body>
<div>
    <form ng-app="" name="myForm">
        Email:
        <input type="email" name="myAddress" ng-model="text" required>
        <span ng-show="myForm.myAddress.$error.email">This is not a legal email!</span>
        <div>
            {{myForm.myAddress.$valid}}
            {{myForm.myAddress.$dirty}}
            {{myForm.myAddress.$touched}}
        </div>
    </form>
</div>
<script src="/js/angular-1.6.6/angular.js"></script>
<script src="/js/cloudcore/login.js"></script>
</body>
</html>
