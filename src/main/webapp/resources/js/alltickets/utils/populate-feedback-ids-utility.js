'use strict';

function populateFeedbackIds(feedbacks, tickets) {

    var feedbacksIds = [];

    for (var i = 0; i < feedbacks.length; i++) {
        for (var y = 0; y < tickets.length; y++) {
            if (tickets[y].id === feedbacks[i].ticket.id) {
                feedbacksIds.push(tickets[y].id);
                break;
            }
        }
    }

    return feedbacksIds;
}