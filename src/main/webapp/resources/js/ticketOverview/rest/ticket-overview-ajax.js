'use strict';

angular.module('TicketsOverview')
    .service('sendCommentService', ['$http', function sendComment() {

        return function ($http, id, commentData) {
            $http({
                method: 'POST',
                url: '/tickets/' + id + '/comments',
                data: commentData,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(function (value) {
                location.reload();
            });
        }
    }]);