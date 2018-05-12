package com.hussain.mycrm.model;


import javax.persistence.*;

@Entity
@Table(name="notification_type")
public class NotificationType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="type_id")
    private long id;

    @Column(name="type_name")
    private String typeName;

    @Column(name="description")
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
