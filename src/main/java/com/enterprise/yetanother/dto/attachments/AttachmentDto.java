package com.enterprise.yetanother.dto.attachments;

import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.Ticket;

import java.util.Objects;

/**
 *@author andrey
 */
public class AttachmentDto {

    private Long id;
    private String fileName;
    private Ticket ticket;

    public AttachmentDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentDto that = (AttachmentDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(ticket, that.ticket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, ticket);
    }

    @Override
    public String toString() {
        return "AttachmentDto{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", ticket=" + ticket +
                '}';
    }
}