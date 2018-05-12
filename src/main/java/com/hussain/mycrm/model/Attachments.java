package com.hussain.mycrm.model;

import javax.persistence.*;


@Entity
@Table(name="attachments")
public class Attachments {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="attachment_id")
    private long id;

    @Column(name="file_name")
    private String fileName;

    @Column(name="file")
    private Byte[] file;

    @Column(name="url")
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Byte[] getFile() {
        return file;
    }

    public void setFile(Byte[] file) {
        this.file = file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
