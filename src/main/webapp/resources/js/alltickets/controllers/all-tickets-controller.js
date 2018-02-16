'use strict';

angular.module('AllTickets')
    .controller('TicketsListController', [
        '$http', '$scope', 'urgencyComparator',
        'idComparator', 'nameComparator', 'desiredResolutionDateComparator',
        'stateComparator', 'editTicketStateService',
        function TicketsListController(
            $http, $scope, urgencyComparator, idComparator,
            nameComparator, desiredResolutionDateComparator,
            stateComparator, editTicketStateService
        ) {

        var self = this;
        self.sortPropertyName = 'urgency';
        self.orderDimention = false;


        $http.get('/tickets').then(function(response) {
            $scope.tickets = response.data;
            self.tickets = $scope.tickets;
        }).then(function (value) {

            $http.get('/tickets/feedbacks').then(function(response) {
                $scope.feedbacks = response.data;
                self.feedbacks = $scope.feedbacks;
            }).then(function (value) {
                $scope.feedbacksIds = populateFeedbackIds($scope.feedbacks,
                                                          $scope.tickets);
            }).then(function (value2) {
                $scope.checkFeedback = function (id) {
                    for (var i = 0; i < $scope.feedbacksIds.length; i++) {
                        if (id === $scope.feedbacksIds[i]) {
                            return true;
                        }
                    }
                    return false;
                };
                self.feedbacksIds = $scope.feedbacksIds;
                self.checkFeedback = $scope.checkFeedback;
            });
        });

        self.TicketsFilter = 'All Tickets';

        self.urgencyComparator = urgencyComparator;
        self.comparator = self.urgencyComparator;

        self.idComparator = idComparator;
        self.nameComparator = nameComparator;
        self.desiredResolutionDateComparator = desiredResolutionDateComparator;
        self.stateComparator = stateComparator;

        $scope.sortBy = function(propertyName, order) {

            self.sortPropertyName = propertyName;
            self.orderDimention = order;

            if (propertyName === 'state') {
                self.comparator = self.stateComparator;
                return;
            }
            if (propertyName === 'desiredResolutionDate') {
                self.comparator = self.desiredResolutionDateComparator;
                return;
            }
            if (propertyName === 'name') {
                self.comparator = self.nameComparator;
                return;
            }
            if (propertyName === 'id') {
                self.comparator = self.idComparator;
                return;
            }
            if (propertyName === 'urgency') {
                self.comparator = self.urgencyComparator;
                return;
            } else {
                self.comparator = null;
            }
        };

        ////submit buttons filling
        $scope.employeeButtons = [
            'Submit', 'Cancel'
        ];
        self.employeeButtons = $scope.employeeButtons;

        $scope.feedbackButtons = [
            'Leave Feedback', 'View Feedback'
        ];
        self.feedbackButtons = $scope.feedbackButtons;

        $scope.managerButtonsDR_DE = [
            'Submit', 'Cancel'
        ];
        self.managerButtonsDR_DE = $scope.managerButtonsDR_DE;

        $scope.managerButtonsNE = [
            'Approve', 'Decline', 'Cancel'
        ];
        self.managerButtonsNE = $scope.managerButtonsNE;

        $scope.engineerButtonsAP = [
            'Assign to Me', 'Cancel'
        ];
        self.engineerButtonsAP = $scope.engineerButtonsAP;

        $scope.engineerButtonsIN = [
            'Done'
        ];
        self.engineerButtonsIN = $scope.engineerButtonsIN;

        //User credentials
        $scope.currentRole = $(".userRole").text();
        $scope.loggedUser = $(".loggedUser").text();
        self.currentRole = $scope.currentRole;
        self.loggedUser = $scope.loggedUser;

        $scope.passedNumber = 0;

        $scope.editTicketState = function editTicketState(id) {
            var thisSelect = $(this);
            thisSelect.disabled = true;

            setTimeout(100);

            if ($scope.passedNumber !== id) {
                $scope.passedNumber = id;
            } else {
                return;
            }

            $scope.action = $("#action").val();
            $scope.state = $("#state").val();

            var data = [{
                'action': $scope.action, 'state': $scope.state
            }, {
                'email': $scope.loggedUser, 'role': $scope.currentRole
            }];

            editTicketStateService($http, id, data, thisSelect);
            thisSelect.disabled = true;
        };
        self.editTicketState = $scope.editTicketState;

        var allTicketsButton = document.getElementById('allTicketsButton');
        var myTicketsButton = document.getElementById('myTicketsButton');
        var TicketsFilter = 'All Tickets';
        self.TicketsFilter = TicketsFilter;
        allTicketsButton.disabled = true;
        myTicketsButton.disabled = false;

        $scope.allTicketsButton = function() {
            self.TicketsFilter = 'All Tickets';

            allTicketsButton.disabled = true;
            allTicketsButton.style.backgroundColor = 'dodgerblue';
            allTicketsButton.style.color = 'white';
            allTicketsButton.style.borderColor = 'dodgerblue';

            myTicketsButton.disabled = false;
            myTicketsButton.style.backgroundColor = 'white';
            myTicketsButton.style.color = 'black';
            myTicketsButton.style.borderColor = 'dodgerblue';
        };
        self.allTicketsButton = $scope.allTicketsButton;

        $scope.myTicketsButton = function() {
            self.TicketsFilter = 'My Tickets';

            allTicketsButton.disabled = false;
            allTicketsButton.style.backgroundColor = 'white';
            allTicketsButton.style.color = 'black';
            allTicketsButton.style.borderColor = 'dodgerblue';

            myTicketsButton.disabled = true;
            myTicketsButton.style.backgroundColor = 'dodgerblue';
            myTicketsButton.style.color = 'white';
            myTicketsButton.style.borderColor = 'dodgerblue';
        };
        self.myTicketsButton = $scope.myTicketsButton;

        $scope.initialSort = function() {
            self.sortPropertyName = 'urgency';
            self.orderDimention = false;
            self.comparator = self.urgencyComparator;
        };
        $scope.initialSort();

        $scope.getManagerTickets = function() {
            setTimeout(function () {
                try {
                    if (typeof self.tickets.length === 'number') {
                        self.managerTickets = managerFilterFunction(
                            self.tickets, $scope.currentRole, $scope.loggedUser);
                    }
                } catch (e) {
                    $scope.getManagerTickets();
                }
            }, 500);
        };

        $scope.getEngineerTickets = function() {
            setTimeout(function () {
                try {
                    if (typeof self.tickets.length === 'number') {
                        self.engineerTickets = engineerFilterFunction(
                            self.tickets, $scope.currentRole, $scope.loggedUser);
                    }
                } catch (e) {
                    $scope.getEngineerTickets();
                }
            }, 500);
        };

        $scope.getManagerTickets();
        $scope.getEngineerTickets();
    }]
);