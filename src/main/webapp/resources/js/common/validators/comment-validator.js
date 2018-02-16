'use strict';

function commentValidation() {
    var regexp = /^[\w\d\s!-\/:-@[\]-`{-~]{0,500}$/m;
    var commentInput = document.getElementById("commentField");

    while (!regexp.test(commentInput.value)) {
        commentInput.value = commentInput.value.substr(0, commentInput.value.length-1);
    }
}