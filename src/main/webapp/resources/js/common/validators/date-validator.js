'use strict';

function dateValidation() {
    var dateField = document.getElementById("dateField");
    var dateValue = dateField.value;
    var now = new Date();
    var chosen = new Date(dateValue);
    
    if (chosen.getDate() < now.getDate()) {
        document.getElementById("dateField").value = null;
    }
}