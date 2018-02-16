'use strict';

angular.module('TicketsOverview')
    .service('checkViewFeedbackService', function checkViewFeedback() {

        return function () {
            if (self.ticketState == 'DONE'
                && (typeof $scope.feedback !== 'undefined')
            ) {
                return 1;
            }
        }
    });