package com.hussain.mycrm.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="notification_view_history")
public class NotificationViewHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="history_id")
    private long id;

    @Column(name="last_viewed")
    private Date lastViewed;

    @Column(name="user_id")
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getLastViewed() {
        return lastViewed;
    }

    public void setLastViewed(Date lastViewed) {
        this.lastViewed = lastViewed;
    }


}
