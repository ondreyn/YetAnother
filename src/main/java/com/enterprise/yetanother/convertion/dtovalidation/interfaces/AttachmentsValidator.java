package com.enterprise.yetanother.convertion.dtovalidation.interfaces;

import com.enterprise.yetanother.dto.attachments.AttachmentDto;
import com.enterprise.yetanother.dto.attachments.AttachmentDto;
import org.springframework.web.multipart.MultipartFile;

/**
 *@author andrey
 */
public interface AttachmentsValidator {

    boolean validate(MultipartFile file);
    boolean validate(AttachmentDto attachmentDto);
    boolean validate(Long id, String fileName);
}
