'use strict';

function nameValidation() {
    var regexp = /^[\w\d!-\/:-@[\]-`{-~]{0,100}$/m;
    var nameInput = document.getElementById("nameField");

    while (!regexp.test(nameInput.value)) {
        nameInput.value = nameInput.value.substr(0, nameInput.value.length-1);
    }
}