'use strict';

angular.module('TicketsOverview')
    .service('checkLeaveFeedbackService', function checkLeaveFeedback() {

        return function () {
            if (self.ticketState == 'DONE'
                && self.currentRole != '[ROLE_ENGINEER]'
                && self.ticket.owner.email == $scope.loggedUser
                && (typeof $scope.feedback === 'undefined')
            ) {
                return 0;
            }
        }
    });