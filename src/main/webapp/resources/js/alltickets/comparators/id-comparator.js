'use strict';

angular.module('AllTickets')
    .service('idComparator', function () {

    return function (v1, v2) {
        var a1, a2;

        a1 = v1.value.id;
        a2 = v2.value.id;

        if (typeof a1 === 'number' && typeof a2 === 'number') {

            if (a1 > a2) {
                return -1;
            } else if (a1 < a2) {
                return 1;
            } else if (a1 === a2) {
                return 0;
            }
        }
    }
});