'use strict';

angular.module('CreateTicket')
    .service('createTicketService', ['$http', function createTicket() {
        var url = 'http://' + location.host;
    return function ($http, ticketData, attachmentsData) {

        var ticketURL = '/tickets';
        var attachmentsURL = '/tickets/attachments';

        $http({
            method: 'POST',
            url: ticketURL,
            data: ticketData,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });

        $http({
            method: 'POST',
            url: attachmentsURL,
            data: attachmentsData,
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }).then(function (value) {
            location.assign(url + '/ticketsPage');
        });
    }
}]);