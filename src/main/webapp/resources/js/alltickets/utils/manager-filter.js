'use strict';

//managerFilterFunction
function managerFilterFunction(tickets, currentRole, loggedUser) {

    var filtered = [];

    if (typeof tickets !== 'undefined') {
        for (var i = 0; i < tickets.length; i++) {
            var ticket = tickets[i];

            if (ticket.owner != null
                    && ticket.owner.email === loggedUser
                    || (ticket.state === 'APPROVED'
                    && ticket.approver.email === loggedUser)) {
                filtered.push(ticket);
            }
        }
        return filtered;
    } else {
        return null;
    }
}
