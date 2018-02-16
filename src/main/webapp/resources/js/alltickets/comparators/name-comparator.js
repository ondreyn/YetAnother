'use strict';

angular.module('AllTickets')
    .service('nameComparator', function () {

        return function (v1, v2) {
            var a1, a2;

            a1 = v1.value.name;
            a2 = v2.value.name;

            if (a1 !== undefined && a2 !== undefined) {
                return a1.localeCompare(a2);
            } else {
                return 0;
            }
        }
    });