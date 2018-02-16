'use strict';

angular.module('CreateTicket')
    .controller('CreateTicketController', ['$http', '$scope', 'createTicketService',
    function ($http, $scope, createTicketService) {
        var self = this;

        //User credentials
        $scope.currentRole = $(".userRole").text();
        $scope.loggedUser = $(".loggedUser").text();
        self.currentRole = $scope.currentRole;
        self.loggedUser = $scope.loggedUser;

        $scope.states = [
            'DRAFT', 'NEW'
        ];
        self.states = $scope.states;


        $scope.submitDraftFlag = 0;
        $scope.submitNewFlag = 0;

        $scope.submitForms = function submitForms(state) {
            if (($scope.submitDraftFlag === 0
                && $scope.submitNewFlag !== 0)
                || ($scope.submitDraftFlag !== 0
                    && $scope.submitNewFlag === 0)) {

            } else {
                return;
            }

            var stateValue = $("input[name=state]:hidden");
            stateValue.val(state);

            var userForm = $('#userForm');
            var ticketForm = $('#ticketForm');
            var commentForm = $('#commentForm');

            var attachmentsData = new FormData();
            var files = document.getElementById('Files').files;

            angular.forEach(files, function (file) {
                attachmentsData.append('file', file);
            });

            var ticketData = userForm.serialize() + '&' + ticketForm.serialize()
                             + '&' + commentForm.serialize();

            createTicketService($http, ticketData, attachmentsData);
        };
        self.submitForms = $scope.submitForms;

        $scope.buttonDraft = function buttonDraft() {
            var thisButton = document.getElementById('buttonDraft');
            thisButton.disabled = true;

            setTimeout(100);

            $scope.submitDraftFlag = $scope.states[0];

            if (complexValidation()) {
                $scope.submitForms($scope.submitDraftFlag);
            } else {
                thisButton.disabled = false;
            }
        };
        self.buttonDraft = $scope.buttonDraft;

        $scope.buttonNew = function buttonNew() {
            var thisButton = document.getElementById('buttonNew');
            thisButton.disabled = true;

            setTimeout(100);

            $scope.submitNewFlag = $scope.states[1];

            if (complexValidation()) {
                $scope.submitForms($scope.submitNewFlag);
            } else {
                thisButton.disabled = false;
            }
        };
        self.buttonNew = $scope.buttonNew;
    }]);