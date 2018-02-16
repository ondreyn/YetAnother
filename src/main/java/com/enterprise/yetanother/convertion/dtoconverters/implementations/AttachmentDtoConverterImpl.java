package com.enterprise.yetanother.convertion.dtoconverters.implementations;

import com.enterprise.yetanother.convertion.dtovalidation.interfaces.AttachmentsValidator;
import com.enterprise.yetanother.dto.attachments.AttachmentDto;
import com.enterprise.yetanother.entities.Attachment;
import com.enterprise.yetanother.services.interfaces.AttachmentsService;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.AttachmentDtoConverter;
import com.enterprise.yetanother.dto.attachments.AttachmentDto;
import com.enterprise.yetanother.entities.Attachment;
import com.enterprise.yetanother.services.interfaces.AttachmentsService;
import com.enterprise.yetanother.convertion.dtovalidation.interfaces.AttachmentsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 *@author andrey
 */
@Component
public class AttachmentDtoConverterImpl implements AttachmentDtoConverter {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(AttachmentDtoConverterImpl.class);

    @Autowired
    private AttachmentsValidator attachmentsValidator;

    @Autowired
    private AttachmentsService attachmentsService;

    @Override
    public Attachment dtoToEntity(AttachmentDto attachmentDto) {
        if (attachmentsValidator.validate(attachmentDto)) {
            try {
                Long id = attachmentDto.getId();
                String fileName = attachmentDto.getFileName();
                Attachment attachment = attachmentsService.get(id);

                if (attachment.getFileName().equalsIgnoreCase(fileName)) {
                    return attachment;
                } else {
                    LOGGER.error("[dtoToEntity: filenames' mismatch!]");
                }
            } catch (Exception e) {
                LOGGER.error("[dtoToEntity: Exception thrown!]");
            }
        } else {
            LOGGER.warn("[dtoToEntity: dtovalidation failed!]");
        }
        return null;
    }

    @Override
    public AttachmentDto entityToDto(Attachment attachment) {
        if (attachment != null) {
            try {
                Long id = attachment.getId();
                String fileName = attachment.getFileName();
                if (attachmentsValidator.validate(id, fileName)) {
                    AttachmentDto attachmentDto = new AttachmentDto();
                    attachmentDto.setId(id);
                    attachmentDto.setFileName(fileName);
                    attachmentDto.setTicket(attachment.getTicket());
                    return attachmentDto;
                } else {
                    LOGGER.warn("[entityToDto: dtovalidation failed!]");
                }
            } catch (Exception e) {
                LOGGER.error("[entityToDto: Exception thrown!]", e);
            }
        } else {
            LOGGER.warn("[entityToDto: attachment is null!]");
        }
        return null;
    }

    @Override
    public List<Attachment> dtosToEntities(
            List<AttachmentDto> attachmentDtos
    ) {
        if (attachmentDtos != null) {
            try {
                List<Attachment> attachments = new ArrayList<>();
                for (AttachmentDto attachmentDto : attachmentDtos) {
                    attachments.add(dtoToEntity(attachmentDto));
                }
                if (!attachments.isEmpty()) {
                    return attachments;
                }
            } catch (Exception e) {
                LOGGER.error("[dtosToEntities: Exception thrown!]", e);
            }
        } else {
            LOGGER.warn("[dtosToEntities: attachmentDtos are null or empty]");
        }
        return null;
    }

    @Override
    public List<AttachmentDto> entitiesToDtos(List<Attachment> attachments) {
        if (attachments != null) {
            try {
                List<AttachmentDto> attachmentDtos = new ArrayList<>();
                for (Attachment attachment : attachments) {
                    attachmentDtos.add(entityToDto(attachment));
                }
                if (!attachmentDtos.isEmpty()) {
                    return attachmentDtos;
                }
            } catch (Exception e) {
                LOGGER.error("[entitiesToDtos: Exception thrown!]", e);
            }
        } else {
            LOGGER.warn("[entitiesToDtos: attachments are null or empty]");
        }
        return null;
    }

    @Override
    public List<Attachment> transform(MultipartFile[] files) {

        List<Attachment> attachments = new ArrayList<>();
        try {
             if (files != null && files.length != 0) {
                 for (MultipartFile file : files) {
                     if (attachmentsValidator.validate(file)) {
                         Attachment attachment = new Attachment();
                         attachment.setBlob(file.getBytes());
                         attachment.setFileName(file.getOriginalFilename());
                         attachments.add(attachment);
                     }
                 }
                 LOGGER.info("[transform: attachments " + attachments + "]");
                 return attachments;
             } else {
                 LOGGER.warn("[transform: there is no attachments]");
                 return null;
             }
        } catch (Exception e) {
            LOGGER.error("[transform: Exception]", e);
            return null;
        }
    }
}
