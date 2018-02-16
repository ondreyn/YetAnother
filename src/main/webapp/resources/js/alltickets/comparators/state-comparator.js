'use strict';

angular.module('AllTickets')
    .service('stateComparator', function () {

        return function (v1, v2) {
            var a1, a2;

            a1 = v1.value.state;
            a2 = v2.value.state;

            if (typeof a1 == 'string' && typeof a2 == 'string') {
                return a1.localeCompare(a2);
            } else {
                return (v1.index < v2.index) ? -1 : 1;
            }
        }
    });