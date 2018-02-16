'use strict';

function complexValidation() {

    var nameField = document.getElementById("nameField");
    var nameValue = nameField.value;

    var regexpName = /^[\w\d!-\/:-@[\]-`{-~]{1,100}$/m;
    if (!regexpName.test(nameValue)) {
        alert('Name has errors or empty');
        return false;
    }

    var regexp = /^[\w\d\s!-\/:-@[\]-`{-~]{0,500}$/m;
    var descriptionField = document.getElementById("descriptionField");
    var descriptionValue = descriptionField.value;

    if (!regexp.test(descriptionValue)) {
        alert('Description has errors');
        return false;
    }

    var dateField = document.getElementById("dateField");
    var dateValue = dateField.value;
    var now = new Date();
    var chosen = new Date(dateValue);

    if (chosen.getDate() < now.getDate()) {
        alert('Date has errors');
        return false;
    }

    var filesInput = document.getElementById("Files");
    var files = filesInput.files;

    if (files.length !== 0) {
        for (var i = 0; i < files.length; i++) {
            if (!validFileType(files[i])) {
                return false;
            }
        }
    }

    var commentField = document.getElementById("commentField");
    var commentValue = commentField.value;

    if (!regexp.test(commentValue)) {
        alert('Comment has errors');
        return false;
    }
    return true;
}