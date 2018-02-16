'use strict';

angular.module('EditTicket')
    .service('editTicketService', ['$http', function editTicket() {
        var host = location.host;
        var url = 'http://' + host;
        return function ($http, id, ticketData, attachmentsData) {

            var ticketURL = '/tickets/' + id;
            var attachmentsURL = '/tickets/' + id + '/attachments';

            $http({
                method: 'PUT',
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