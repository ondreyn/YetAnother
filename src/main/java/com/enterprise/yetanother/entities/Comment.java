package com.enterprise.yetanother.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *@author andrey
 */
@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(
                                        name = "COMMENT_USER_ID_FK"))
    private User user;

    private String text;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(
                                          name = "COMMENT_TICKET_ID_FK"))
    private Ticket ticket;

    public Comment() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (!id.equals(comment.id)) return false;
        if (!user.equals(comment.user)) return false;
        if (!text.equals(comment.text)) return false;
        if (!date.equals(comment.date)) return false;
        return ticket.equals(comment.ticket);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + ticket.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", ticket=" + ticket +
                '}';
    }
}
