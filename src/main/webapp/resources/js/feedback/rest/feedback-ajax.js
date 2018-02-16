'use strict';

angular.module('Feedback')
    .service('sendFeedbackService', ['$http', function sendFeedback() {

        return function ($http, id, feedbackData) {
            var URL = '/tickets/' + id + '/feedbacks';

            if (self.starFlag !== 0) {

                $http({
                    method: 'POST',
                    url: URL,
                    data: feedbackData,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                });
                history.back();
            }
        }
    }]);