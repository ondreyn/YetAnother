package com.enterprise.yetanother.convertion.dtovalidation.implementations;

import com.enterprise.yetanother.dto.attachments.AttachmentDto;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.dto.attachments.AttachmentDto;
import com.enterprise.yetanother.convertion.dtovalidation.interfaces.AttachmentsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *@author andrey
 */
@Component
public class AttachmentsValidatorImpl implements AttachmentsValidator {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(AttachmentsValidatorImpl.class);

    @Override
    public boolean validate(MultipartFile file) {
        if (file.getSize() > Properties.FILE_SIZE_LIMIT) {
            LOGGER.warn("[AttachmentsValidatorImpl: The size of attached file" +
                        " should not be greater than " +
                        Properties.FILE_SIZE_LIMIT_MB + " Mb.]");
            return false;
        }
        for (String type: Properties.FILE_FORMATS) {
            if (file.getContentType().equalsIgnoreCase(type)) {
                return true;
            }
        }
        LOGGER.warn("[AttachmentsValidatorImpl: The selected file type is not" +
                    " allowed: " + file.getContentType() + "!]");
        return false;
    }

    @Override
    public boolean validate(AttachmentDto attachmentDto) {
        if (attachmentDto == null) {
            LOGGER.warn("[validate: attachmentDto is null!]");
            return false;
        }

        try {
            Long id = attachmentDto.getId();
            String fileName = attachmentDto.getFileName();
            String[] extensions = fileName.split("\\.");
            String extension = extensions[extensions.length - 1];

            if (id < 0) {
                LOGGER.warn("[validate: attachmentId non valid!]");
                return false;
            }
            for (String ext: Properties.FILE_EXTENSIONS) {
                if (extension.equalsIgnoreCase(ext)) {
                    return true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("[validate: Exception thrown!]", e);
            return false;
        }
        return false;
    }

    @Override
    public boolean validate(Long id, String fileName) {
        if (id == null) {
            LOGGER.warn("[validate: id is null!]");
            return false;
        }
        if (fileName == null || fileName.isEmpty()) {
            LOGGER.warn("[validate: fileName is null or empty!]");
            return false;
        }

        try {
            String[] extensions = fileName.split("\\.");
            String extension = extensions[extensions.length - 1];
            for (String ext: Properties.FILE_EXTENSIONS) {
                if (extension.equalsIgnoreCase(ext)) {
                    return true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("[validate: Exception thrown!]");
            return false;
        }

        return false;
    }
}
