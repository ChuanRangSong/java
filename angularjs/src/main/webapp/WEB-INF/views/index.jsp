<!doctype html>
<html ng-app="myApp">
<head>
    <meta content="text/html" charset="UTF-8">
    <title>AngularJS</title>
</head>
<body>
<div ng-controller="myCtrl">
   <accordion>
       <expander ng-repeat="expander in expanders" expander-title="expander.title">{{expander.text}}</expander>
   </accordion>
</div>
</body>
<script src="/js/angular-1.6.6/angular.js"></script>
<script type="text/javascript">
    var myApp = angular.module("myApp", []);
    myApp.controller("myCtrl", function ($scope) {
        $scope.expanders = [{
            title:'AAAAAA',
            text:'aaaaaa'
        },{
            title:'BBBBBB',
            text:'bbbbbb'
        }];
    });
    myApp.directive("accordion", function () {
        return {
            restrict:'AE',
            replace:true,
            transclude:true,
            template:'<div ng-transclude=""></div>',
            controller:function () {
                var expanders = [];
                this.closeOther = function (selectExpander) {
                    angular.forEach(expanders, function (expander) {
                        if (expander != selectExpander) {
                            expander.show = false;
                        }
                    });
                };
                this.addExpander = function (expander) {
                    expanders.push(expander);
                }
            }
        }
    });
    myApp.directive("expander", function () {
        return {
            restrict:'AE',
            replace:true,
            transclude:true,
            require:'^accordion',
            scope:{
                title:'=expanderTitle'
            },
            template:'<div>' +
            '<div ng-click="toggle()">{{title}}</div>' +
            '<div ng-show="show" ng-transclude=""></div>' +
            '</div>',
            link:function (scope, element, attrs, accordionCtrl) {
                scope.show = false;
                accordionCtrl.addExpander(scope);
                scope.toggle = function () {
                    accordionCtrl.closeOther(scope);
                    scope.show = !scope.show;
                }
            }
        }
    })

</script>
</html>
