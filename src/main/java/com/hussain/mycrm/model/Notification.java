package com.hussain.mycrm.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="notification")
public class Notification {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="notification_id")
    private long id;

    @Column(name="message")
    private String message;

    @OneToOne
    @JoinColumn(name="created_by")
    private User createdBy;

    @Column(name="created_on")
    private Date createdOn;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="type_id")
    private  NotificationType notificationType;

    @Column(name="start_time")
    private Date startTime;

    @Column(name="end_time")
    private String endTime;


    @OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name="attachments_id")
    private List<Attachments> attachments;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachments> attachments) {
        this.attachments = attachments;
    }
}
