'use strict';

angular.module('EditTicket')
    .controller('EditTicketsController', ['$http', '$scope', 'editTicketService',
    function TicketsOverviewController($http, $scope, editTicketService) {
        var self = this;

        $scope.id = $("#TicketId").text();
        self.ticket_id = $scope.id;
        $scope.ownerId = $("#OwnerId").text();

        $scope.currentRole = $(".userRole").text();
        $scope.loggedUser = $(".loggedUser").text();
        self.currentRole = $scope.currentRole;
        self.loggedUser = $scope.loggedUser;

        self.ticketListButtonLink = '/ticketOverviewPage/' + $scope.id;

        $scope.ticketState = '';
        self.ticketState = $scope.ticketState;
        $scope.submitDraftFlag = 0;
        $scope.submitNewFlag = 0;

        $scope.buttonDraft = function () {
            var thisButton = document.getElementById('buttonDraft');
            thisButton.disabled = true;

            setTimeout(100);

            $scope.ticketState = 'DRAFT';
            $scope.submitDraftFlag = 'DRAFT';

            if (complexValidation()) {
                $scope.submitForms($scope.ticketState);
            } else {
                thisButton.disabled = false;
            }
        };
        self.buttonDraft = $scope.buttonDraft;

        $scope.buttonNew = function () {
            var thisButton = document.getElementById('buttonNew');
            thisButton.disabled = true;

            setTimeout(100);

            $scope.ticketState = 'NEW';
            $scope.submitNewFlag = 'NEW';

            if (complexValidation()) {
                $scope.submitForms($scope.ticketState);
            } else {
                thisButton.disabled = false;
            }
        };
        self.buttonNew = $scope.buttonNew;

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
                             + '&' + commentForm.serialize() + '&OwnerId='
                             + $scope.ownerId;

            editTicketService($http, $scope.id, ticketData, attachmentsData);
        };
        self.submitForms = $scope.submitForms;
    }]
);