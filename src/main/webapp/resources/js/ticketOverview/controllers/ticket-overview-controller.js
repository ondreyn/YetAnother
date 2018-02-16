'use strict';

var commentsLength = 5;
var historyLength = 5;

angular.module('TicketsOverview')
    .controller('TicketsOverviewController', [
        '$http', '$scope', 'deleteAttachment', 'downloadAttachment',
        'sendCommentService', 'dateComparatorService',
        'checkLeaveFeedbackService', 'checkViewFeedbackService',
    function TicketsOverviewController(
        $http, $scope, deleteAttachment, downloadAttachment,
        sendCommentService, dateComparatorService,
        checkLeaveFeedbackService, checkViewFeedbackService
    ) {
        var self = this;
        var id = $("#TicketId").text();
        self.ticket_id = id;

        var host = location.host;
        var url = 'http://' + host;
        var editButton = document.getElementById('EditButton');
        self.editButtonLink = '#';

        $scope.currentRole = $(".userRole").text();
        $scope.loggedUser = $(".loggedUser").text();
        self.currentRole = $scope.currentRole;
        self.loggedUser = $scope.loggedUser;

        self.leaveFeedbackLink = url + '/ticketFeedbackPage/' + id + '/new';
        self.viewFeedbackLink = url + '/ticketFeedbackPage/' + id + '/view';

        $http.get(url + '/tickets/' + id).then(function(response) {
            $scope.ticket = response.data;
            self.ticket = $scope.ticket;
        }).then(function (value) {
            self.ticketState = $scope.ticket.state;

            if (self.ticket.state !== 'DRAFT') {
                editButton.disabled = true;
            } else {
                self.editButtonLink = '/ticketEditPage/' + id;
            }

            $http.get(url + '/tickets/' + id + '/feedbacks')
                .then(function (value2) {
                    $scope.feedback = value2.data;
                }
            );
        });

        self.checkLeaveFeedback = checkLeaveFeedbackService;
        self.checkViewFeedback = checkViewFeedbackService;

        var historyButton = document.getElementById('HistoryButton');
        var commentsButton = document.getElementById('CommentsButton');
        var OverviewFilter = 'History';
        self.OverviewFilter = OverviewFilter;
        historyButton.disabled = true;
        commentsButton.disabled = false;

        $scope.historyButton = function() {
            self.OverviewFilter = 'History';
            historyButton.disabled = true;
            commentsButton.disabled = false;

            historyButton.disabled = true;
            historyButton.style.backgroundColor = 'dodgerblue';
            historyButton.style.color = 'white';
            historyButton.style.borderColor = 'dodgerblue';

            commentsButton.disabled = false;
            commentsButton.style.backgroundColor = 'white';
            commentsButton.style.color = 'black';
            commentsButton.style.borderColor = 'dodgerblue';
        };
        self.historyButton = $scope.historyButton;

        $scope.commentsButton = function() {
            self.OverviewFilter = 'Comments';
            historyButton.disabled = false;
            commentsButton.disabled = true;

            historyButton.disabled = false;
            historyButton.style.backgroundColor = 'white';
            historyButton.style.color = 'black';
            historyButton.style.borderColor = 'dodgerblue';

            commentsButton.disabled = true;
            commentsButton.style.backgroundColor = 'dodgerblue';
            commentsButton.style.color = 'white';
            commentsButton.style.borderColor = 'dodgerblue';
        };
        self.commentsButton = $scope.commentsButton;

        $scope.histories;
        $http.get(url + '/tickets/' + id + '/histories/' + historyLength)
            .then(function(response) {
            self.histories = response.data;
        });

        $scope.comments;
        $http.get(url + '/tickets/' + id + '/comments/' + commentsLength)
            .then(function(response) {
            self.comments = response.data;
        });

        $scope.attachments;
        $http.get(url + '/tickets/' + id + '/attachments')
            .then(function(response) {
                self.attachments = response.data;
            });

        self.deleteAttachment = deleteAttachment;
        self.downloadAttachment = downloadAttachment;
        self.dateComparator = dateComparatorService;

        $scope.showAllHistory = function () {
            $http.get(url + '/tickets/' + id + '/histories')
                .then(function(response) {
                    self.histories = response.data;
                });
        };
        self.showAllHistory = $scope.showAllHistory;

        $scope.showAllComments = function () {
            $http.get(url + '/tickets/' + id + '/comments')
                .then(function(response) {
                    self.comments = response.data;
                });
        };
        self.showAllComments = $scope.showAllComments;

        $scope.times = 0;
        $scope.onAddComment = function () {
            var thisButton = document.getElementById('addCommentButton');
            thisButton.disabled = true;

            setTimeout(100);

            if ($scope.times === 0) {
                $scope.times = 1;
            } else {
                return;
            }

            var commentForm = $('#commentForm');
            var commentData = commentForm.serialize();

            sendCommentService($http, id, commentData);
        };
        self.onAddComment = $scope.onAddComment;
    }]
);