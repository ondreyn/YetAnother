'use strict';

function textValidation() {
    var regexp = /^[\w\d!-\/:-@[\]-`{-~]*$/m;
    var input = document.getElementById("searchFilter");

    while (!regexp.test(input.value)) {
        input.value = input.value.substr(0, input.value.length-1);
    }
}