package com.enterprise.yetanother.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.enterprise.yetanother.enums.State;
import com.enterprise.yetanother.enums.Urgency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *@author andrey
 */
@Entity
@Table(name = "TICKET")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    private String name;
    private String description;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "desired_resolution_date")
    private Date desiredResolutionDate;

    @ManyToOne
    @JoinColumn(name = "assignee_id", foreignKey = @ForeignKey(
                                            name = "ASSIGNEE_ID_FK"))
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(
                                         name = "OWNER_ID_FK"))
    private User owner;

    @Column(name = "state_id")
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "urgency_id")
    @Enumerated(EnumType.STRING)
    private Urgency urgency;

    @ManyToOne
    @JoinColumn(name = "approver_id", foreignKey = @ForeignKey(
                                            name = "APPROVER_ID_FK"))
    private User approver;

    @JsonIgnore
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL,
                                    orphanRemoval = true)
    private List<Attachment> attachments;

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(
                                            name = "CATEGORY_ID_FK"))
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL,
                                    orphanRemoval = true)
    private List<History> histories;

    @JsonIgnore
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL,
                                    orphanRemoval = true)
    private List<Comment> comments;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL,
                                   orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonBackReference
    private Feedback feedback;

    public Ticket() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getDesiredResolutionDate() {
        return desiredResolutionDate;
    }

    public void setDesiredResolutionDate(Date desiredResolutionDate) {
        this.desiredResolutionDate = desiredResolutionDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        if (this.attachments != null) {
            this.attachments.addAll(attachments);
        } else {
            this.attachments = new ArrayList<>();
            this.attachments.addAll(attachments);
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getApprover() {
        return approver;
    }

    public void setApprover(User approver) {
        this.approver = approver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (!id.equals(ticket.id)) return false;
        if (!name.equals(ticket.name)) return false;
        if (!description.equals(ticket.description)) return false;
        if (!createdOn.equals(ticket.createdOn)) return false;
        if (!desiredResolutionDate.equals(ticket.desiredResolutionDate))
            return false;
        if (!assignee.equals(ticket.assignee)) return false;
        if (!owner.equals(ticket.owner)) return false;
        if (!state.equals(ticket.state)) return false;
        if (!category.equals(ticket.category)) return false;
        if (!urgency.equals(ticket.urgency)) return false;
        return approver.equals(ticket.approver);
    }

    @Override
    public int hashCode() {
        int result = 1;
        if (id != null) {
            result = 31 * result + id.hashCode();
        }
        if (name != null) {
            result = 31 * result + name.hashCode();
        }
        if (description != null) {
            result = 31 * result + description.hashCode();
        }
        if (createdOn != null) {
            result = 31 * result + createdOn.hashCode();
        }
        if (desiredResolutionDate != null) {
            result = 31 * result + desiredResolutionDate.hashCode();
        }
        if (assignee != null) {
            result = 31 * result + assignee.hashCode();
        }
        if (owner != null) {
            result = 31 * result + owner.hashCode();
        }
        if (state != null) {
            result = 31 * result + state.hashCode();
        }
        if (category != null) {
            result = 31 * result + category.hashCode();
        }
        if (urgency != null) {
            result = 31 * result + urgency.hashCode();
        }
        if (approver != null) {
            result = 31 * result + approver.hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdOn=" + createdOn +
                ", desiredResolutionDate=" + desiredResolutionDate +
                ", assignee=" + assignee +
                ", owner=" + owner +
                ", state=" + state +
                ", category=" + category +
                ", urgency=" + urgency +
                ", approver=" + approver +
                '}';
    }
}
