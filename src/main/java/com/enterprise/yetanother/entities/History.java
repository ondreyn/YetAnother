package com.enterprise.yetanother.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *@author andrey
 */
@Entity
@Table(name = "HISTORY")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(
                                          name = "HISTORY_TICKET_ID_FK"))
    private Ticket ticket;

    private Date date;
    private String action;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(
                                        name = "HISTORY_USER_ID_FK"))
    private User user;

    public History() {}

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class HistoryBuilder {

        private Ticket ticket;
        private Date date;
        private String action;
        private String description;
        private User user;

        public HistoryBuilder ticket(Ticket ticket) {
            this.ticket = ticket;
            return this;
        }

        public HistoryBuilder date(Date date) {
            this.date = date;
            return this;
        }

        public HistoryBuilder action(String action) {
            this.action = action;
            return this;
        }

        public HistoryBuilder description(String description) {
            this.description = description;
            return this;
        }

        public HistoryBuilder user(User user) {
            this.user = user;
            return this;
        }

        public History build() {
            return new History(this);
        }
    }

    private History(HistoryBuilder historyBuilder ) {
        ticket = historyBuilder.ticket;
        date = historyBuilder.date;
        action = historyBuilder.action;
        description = historyBuilder.description;
        user = historyBuilder.user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        History history = (History) o;

        if (!id.equals(history.id)) return false;
        if (!ticket.equals(history.ticket)) return false;
        if (!date.equals(history.date)) return false;
        if (!action.equals(history.action)) return false;
        return user.equals(history.user);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + ticket.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + action.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", ticket=" + ticket +
                ", date=" + date +
                ", action='" + action + '\'' +
                ", user=" + user +
                ", description=" + description +
                '}';
    }
}
