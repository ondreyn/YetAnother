package com.enterprise.yetanother.dao.interfaces;

import com.enterprise.yetanother.entities.Attachment;
//import com.enterprise.yetanother.entities.Attachment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@author andrey
 */
@Repository
public interface AttachmentDao {

    void create(Attachment attachment);
    void createFromList(List<Attachment> attachments);
    List<Attachment> findByTicket(Long ticketId);
    void persist(Attachment attachment);
    void update(Attachment attachment);
    void saveOrUpdate(Attachment attachment);
    void save(Attachment attachment);
    void delete(Long attachmentId);
    Attachment findById(Long id);
}
