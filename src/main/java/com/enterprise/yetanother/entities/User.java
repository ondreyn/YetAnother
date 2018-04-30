package com.enterprise.yetanother.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.enterprise.yetanother.enums.Roles;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 *@author andrey
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    private String position;
    private String phone;
    private String userPassword;

    @Column(name = "ROLE_ID")
    @Enumerated(EnumType.STRING)
    private Roles role;

    private String email;
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL,
                                      orphanRemoval = true)
    private List<Ticket> assigneeTickets;

    @JsonIgnore
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL,
                                   orphanRemoval = true)
    private List<Ticket> ownerTickets;

    @JsonIgnore
    @OneToMany(mappedBy = "approver", cascade = CascadeType.ALL,
                                      orphanRemoval = true)
    private List<Ticket> approverTickets;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
                                  orphanRemoval = true)
    private List<History> histories;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
                                  orphanRemoval = true)
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
                                  orphanRemoval = true)
    private List<Feedback> feedbacks;

    public User() {}

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Ticket> getAssigneeTickets() {
        return assigneeTickets;
    }

    public void setAssigneeTickets(List<Ticket> tickets) {
        this.assigneeTickets = tickets;
    }

    public List<Ticket> getOwnerTickets() {
        return ownerTickets;
    }

    public void setOwnerTickets(List<Ticket> ownerTickets) {
        this.ownerTickets = ownerTickets;
    }

    public List<Ticket> getApproverTickets() {
        return approverTickets;
    }

    public void setApproverTickets(List<Ticket> approverTickets) {
        this.approverTickets = approverTickets;
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

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null && !id.equals(user.id)) {             
            return false;
        }
        if (firstName != null && !firstName.equals(user.firstName)) {             
            return false;
        }
        if (lastName != null && !lastName.equals(user.lastName)) {             
            return false;
        }
        if (position != null && !position.equals(user.position)) {             
            return false;
        }
        if (phone != null && !phone.equals(user.phone)) {             
            return false;
        }
        if (role != null && !role.equals(user.role)) {             
            return false;
        }
        if (email != null && !email.equals(user.email)) {             
            return false;
        }        
        return address != null && address.equals(user.address);
    }

    @Override
    public int hashCode() {
        int result = 1;
        if (id != null) {
            result = 31 * result + id.hashCode();
        }
        if (firstName != null) {
            result = 31 * result + firstName.hashCode();
        }
        if (lastName != null) {
            result = 31 * result + lastName.hashCode();
        }
        if (position != null) {
            result = 31 * result + position.hashCode();
        }
        if (phone != null) {
            result = 31 * result + phone.hashCode();
        }
        if (role != null) {
            result = 31 * result + role.hashCode();
        }
        if (email != null) {
            result = 31 * result + email.hashCode();
        }
        if (address != null) {
            result = 31 * result + address.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
