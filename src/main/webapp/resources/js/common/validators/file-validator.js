'use strict';

var fileTypes = [
    'image/jpeg',
    'image/png',
    'application/msword',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
    'application/pdf',
    'application/x-pdf'
];
function validFileType(file) {
    var flag = false;
    for (var i = 0; i < fileTypes.length; i++) {
        if (file.type === fileTypes[i]) {
            if (file.size >= 5*1024*1024) {
                alert('The size of attached file should not be ' +
                    'greater than 5 Mb. Please select another file.');
                return false;
            } else {
                flag = true;
            }
        }
    }
    if (flag == false) {
        alert('The selected file type is not allowed. Please select a file' +
            ' of one of the following types: pdf, png, doc, docx, jpg, jpeg.');
        return false;
    } else {
        return true;
    }
}