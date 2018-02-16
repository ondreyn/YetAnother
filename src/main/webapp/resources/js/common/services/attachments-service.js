'use strict';

angular.module('attachmentsService', [])
    .service('deleteAttachment', ['$http', 
        function deleteAttachmentService($http) {
            var ticket_id = $("#TicketId").text();

            return function deleteAttachment() {                
                var attachmentsSelect = $('#AttachmentsSelect');
                var attachmentsIds = attachmentsSelect.val();                

                for (var i = 0; i < attachmentsIds.length - 1; i++) {
                    $http({
                        method: 'DELETE',                        
                        url: '/tickets/' + ticket_id + '/attachments/' + attachmentsIds[i],                        
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    });
                }

                $http({
                    method: 'DELETE',
                    url: '/tickets/' + ticket_id + '/attachments/' + attachmentsIds[attachmentsIds.length - 1],
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(function (value) {
                    location.reload();
                });
            };
        }])
    
    .service('downloadAttachment', ['$http', 
        function downloadAttachmentService($http) { 
            var ticket_id = $("#TicketId").text();

            return function downloadAttachment() {

                var selected = $("#AttachmentsSelect option:selected").text();                
                var filenames;
                var attachmentsIds = $("#AttachmentsSelect").val();
                
                if (attachmentsIds.length > 1) {
                    filenames = selected.split(' ', attachmentsIds.length);
                } else {
                    filenames = selected.split(' ', 1);
                }

                for (var i = 0; i < attachmentsIds.length; i++) {
                    var extension = filenames[i].split('.')[1];                    

                    if (extension === filenames[i]) {
                        extension = 'binary';
                    }
                    
                    var type = 'application/octet-stream';                    

                    if (extension === 'pdf') {                        
                        type = 'application/pdf';
                    }
                    if (extension === 'png') {                        
                        type = 'image/png';
                    }
                    if (extension === 'doc') {                        
                        type = 'application/mswordf';
                    }
                    if (extension === 'docx') {                     
                        type = 'application/vnd.openxmlformats-officedocument.' +
                             'wordprocessingml.document';
                    }
                    if (extension === 'jpg') {                        
                        type = 'image/jpeg';
                    }
                    if (extension === 'jpeg') {                        
                        type = 'image/jpeg';
                    }

                    const TYPE = type;
                    const FILENAME = filenames[i];                  

                    $http({
                        url: '/tickets/' + ticket_id + '/attachments/' + attachmentsIds[i],                   
                        method: 'GET',                        
                        responseType: 'arraybuffer'
                    }).then(function (response) {
                        var file = new Blob([response.data], {
                            type: TYPE                           
                        });

                        var fileURL = window.URL.createObjectURL(file);
                        var a = document.createElement('a');
                        a.href = fileURL;
                        a.target = '_self';                        
                        a.download = FILENAME;                       
                        a.click();
                        window.URL.revokeObjectURL(fileURL);                        
                    });
                }
            };
        }]);