'use strict';

angular.module('Feedback')
    .controller('FeedbackController', ['$http', '$scope', 'sendFeedbackService',
    function FeedbackController($http, $scope, sendFeedbackService) {
        var self = this;

        $scope.id = $("#TicketId").text();
        self.ticket_id = $scope.id;

        $http.get('/tickets/' + $scope.id).then(function(response) {
            self.ticket = response.data;
        });

        $scope.feedbackFlag = $("#Action").text();
        self.feedbackFlag = $scope.feedbackFlag;

        $scope.rates = [1, 2, 3, 4, 5];
        self.rates = $scope.rates;

        $scope.starFlag = 0;
        self.starFlag = $scope.starFlag;

        if ($scope.feedbackFlag === 'view') {
            $http.get('/tickets/' + $scope.id + '/feedbacks')
                .then(function (response) {
                    if (response.data === undefined) {
                        history.back();
                    } else {
                        self.feedback = response.data;
                    }
                });
        }

        $scope.times = 0;

        $scope.submitForm = function submitForm() {
            var thisButton = document.getElementById('submitButton');
            thisButton.disabled = true;

            setTimeout(100);

            if ($scope.times === 0) {
                $scope.times = 1;
            } else {
                return;
            }

            var feedbackForm = $('#feedbackForm');
            var feedbackData = feedbackForm.serialize();

            sendFeedbackService($http, $scope.id, feedbackData);
        };
        self.submitForm = $scope.submitForm;

        $scope.clickOnImg = function clickOnImg(rate) {
            if (self.starFlag !== rate) {
                self.starFlag = rate;
            } else {
                self.starFlag = 0;
            }
        };
        self.clickOnImg = $scope.clickOnImg;
    }]);