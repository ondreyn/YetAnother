'use strict';

angular.module('LoginModule')
    .controller('LoginController', ['$scope',
    function LoginController($scope) {
        var self = this;

        $scope.loginRegexp = /^[^@.].*@.*\..*[^@.]$/;
        $scope.passwordRegexp = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!-\/:-@[\]-`{-~])(?!.*\s).*$/;

        $scope.username = '';
        $scope.password = '';

        $scope.pressEnter = function (keyEvent) {
            if (keyEvent.which == 13 || keyEvent.keyCode == 13) {
                $scope.checkInput();
            } else {
                return false;
            }
        };
        self.pressEnter = $scope.pressEnter;

        $scope.checkInput = function checkInput() {
            if ($scope.LoginForm.$valid === true) {
                $scope.pushForm();
            }
        };
        self.checkInput = $scope.checkInput;

        $scope.pushForm = function pushForm() {
            var LoginForm = document.getElementById('LoginForm');
            LoginForm.submit();
        };

        $scope.hintShow = function hintShow() {
            $("#Popup").show();
            $("#LoginDiv").hide();
            $("#Hint").hide();
        }
        self.hintShow = $scope.hintShow;

        $scope.hintHide = function hintHide() {
            $("#Popup").hide();
            $("#LoginDiv").show();
            $("#Hint").show();
        }
        self.hintHide = $scope.hintHide;
    }]);