package com.enterprise.yetanother.convertion.dtoconverters.interfaces;

import com.enterprise.yetanother.dto.attachments.AttachmentDto;
import com.enterprise.yetanother.entities.Attachment;
/*import com.enterprise.yetanother.dto.attachments.AttachmentDto;
import com.enterprise.yetanother.entities.Attachment;*/
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentDtoConverter {

    Attachment dtoToEntity(AttachmentDto attachmentDto);
    AttachmentDto entityToDto(Attachment attachment);
    List<Attachment> dtosToEntities(List<AttachmentDto> attachmentDtos);
    List<AttachmentDto> entitiesToDtos(List<Attachment> attachments);
    List<Attachment> transform(MultipartFile[] files);
}
