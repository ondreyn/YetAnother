'use strict';

angular.module('AllTickets')
    .service('urgencyComparator', function () {

        return function (v1, v2) {
            var a1, a2;

            a1 = v1.value.urgency;
            a2 = v2.value.urgency;

            if (a1 == 'CRITICAL' && a2 != 'CRITICAL') {
                return -1;
            } else if (a1 == 'CRITICAL' && a2 == 'CRITICAL') {
                return 0;
            }

            if (a1 == 'LOW' && a2 != 'LOW') {
                return 1;
            } else if (a1 == 'LOW' && a2 == 'LOW') {
                return 0;
            }

            if (a1 == 'HIGH' && a2 == 'HIGH') {
                return 0;
            } else if (a1 == 'HIGH' && a2 == 'CRITICAL') {
                return 1;
            } else if (a1 == 'HIGH' && a2 != 'CRITICAL') {
                return -1;
            }

            if (a1 == 'AVERAGE' && a2 == 'AVERAGE') {
                return 0;
            } else if (a1 == 'AVERAGE' && a2 != 'LOW') {
                return 1;
            } else if (a1 == 'AVERAGE' && a2 == 'LOW') {
                return -1;
            }
        }
    });