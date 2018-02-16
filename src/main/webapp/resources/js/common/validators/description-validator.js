'use strict';

function descriptionValidation() {
    var regexp = /^[\w\d\s!-\/:-@[\]-`{-~]{0,500}$/m;
    var descriptionInput = document.getElementById("descriptionField");

    while (!regexp.test(descriptionInput.value)) {
        descriptionInput.value = descriptionInput.value.substr(0, descriptionInput.value.length-1);
    }
}