package com.enterprise.yetanother.dto.comment;

import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;
import com.enterprise.yetanother.entities.Ticket;
import com.enterprise.yetanother.entities.User;

import java.util.Date;
import java.util.Objects;

/**
 *@author andrey
 */
public class CommentDto {

    private Long id;
    private User user;
    private String text;
    private Date date;
    private Ticket ticket;

    public CommentDto() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

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
        CommentDto that = (CommentDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(text, that.text) &&
                Objects.equals(date, that.date) &&
                Objects.equals(ticket, that.ticket);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user, text, date, ticket);
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", user=" + user +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", ticket=" + ticket +
                '}';
    }
}
