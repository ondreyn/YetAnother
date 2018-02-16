'use strict';

angular.module('AllTickets')
    .service('editTicketStateService', ['$http',
        function editTicketState() {

        return function ($http, id, data, thisSelect) {
            $http({
                method: 'PATCH',
                url: '/tickets/' + id,
                data: JSON.stringify(data),
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                }
            }).then(function (value) {
                thisSelect.disabled = true;
                location.reload();
            });
        }
    }]);