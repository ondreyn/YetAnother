package com.enterprise.yetanother.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 *@author andrey
 */
@Entity
@Table(name = "ATTACHMENT")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @Lob
    @Column(length = 5*1024*1024)
    private byte[] blob;

    private String fileName;

    @ManyToOne
    @JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(
                                          name = "TICKET_ID_FK"))
    private Ticket ticket;

    public Attachment() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket tecket) {
        this.ticket = tecket;
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

        Attachment that = (Attachment) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (blob != null ? !blob.equals(that.blob) : that.blob != null)
            return false;
        return ticket != null ? ticket.equals(that.ticket) : that.ticket == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (blob != null ? blob.hashCode() : 0);
        result = 31 * result + (ticket != null ? ticket.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", ticket=" + ticket +
                ", fileName=" + fileName +
                '}';
    }
}
