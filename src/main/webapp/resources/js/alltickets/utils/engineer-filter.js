'use strict';

//engineerFilterFunction
function engineerFilterFunction(tickets, currentRole, loggedUser) {

    var filtered = [];

    if (typeof tickets !== 'undefined') {
        for (var i = 0; i < tickets.length; i++) {
            var ticket = tickets[i];

            if (ticket.assignee != null 
                && ticket.assignee.email === loggedUser) {
                filtered.push(ticket);
            }
        }
        return filtered;
    } else {
        return null;
    }
}