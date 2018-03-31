package com.hien.project.domain.es;


import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

// Index Elastic Search : with Course search
@Data
@Document(indexName = "course", type = "course") //es nosql
public class EsCourse implements Serializable {

    private static final long serialVersionUID = 7121512017969174834L;

    // Course ES : id, courseId, title, summary, content , username, avatar, createTime, readSize, commentSize, voteSize, tags

    @Id
    private String id;

//    @Field(index = FieldIndex.not_analyzed)
    @Field
    private Long courseId;  // Course : id

    private String title;

    private String summary;

    private String content;

    @Field
    private String username;

    @Field
    private String avatar;

    @Field
    private Timestamp createTime;

    @Field
    private Integer readSize = 0;

    @Field
    private Integer commentSize = 0;

    @Field
    private Integer voteSize = 0;

    private String tags;

    protected EsCourse() {//JPA protected

    }
//
//    public EsCourse(Long courseId, String title, String summary, String content, String username,
//                  String avatar, Timestamp createTime, Integer readSize, Integer commentSize,
//                  Integer voteSize, String tags) {
//        this.Id = courseId;
//        this.title = title;
//        this.summary = summary;
//        this.content = content;
//        this.username = username;
//        this.avatar = avatar;
//        this.createTime = createTime;
//        this.readSize = readSize;
//        this.commentSize = commentSize;
//        this.voteSize = voteSize;
//        this.tags = tags;
//    }

//    public EsCourse(Course course) {
//        this.courseId = course.getId();
//        this.title = course.getTitle();
//        this.summary = course.getSummary();
//        this.content = course.getContent();
//        this.username = course.getUser().getUsername();
//        this.avatar = course.getUser().getAvatar();
//        this.createTime = course.getCreateTime();
//        this.readSize = course.getReadSize();
//        this.commentSize = course.getCommentSize();
//        this.voteSize = course.getVoteSize();
//        this.tags = course.getTags();
//    }
//
//    public void update(Course course){
//        this.courseId = course.getId();
//        this.title = course.getTitle();
//        this.summary = course.getSummary();
//        this.content = course.getContent();
//        this.username = course.getUser().getUsername();
//        this.avatar = course.getUser().getAvatar();
//        this.createTime = course.getCreateTime();
//        this.readSize = course.getReadSize();
//        this.commentSize = course.getCommentSize();
//        this.voteSize = course.getVoteSize();
//        this.tags = course.getTags();
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public Long getCourseId() {
//        return courseId;
//    }
//
//    public void setCourseId(Long courseId) {
//        this.courseId = courseId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getSummary() {
//        return summary;
//    }
//
//    public void setSummary(String summary) {
//        this.summary = summary;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(String avatar) {
//        this.avatar = avatar;
//    }
//
//    public Timestamp getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Timestamp createTime) {
//        this.createTime = createTime;
//    }
//
//    public Integer getReadSize() {
//        return readSize;
//    }
//
//    public void setReadSize(Integer readSize) {
//        this.readSize = readSize;
//    }
//
//    public Integer getCommentSize() {
//        return commentSize;
//    }
//
//    public void setCommentSize(Integer commentSize) {
//        this.commentSize = commentSize;
//    }
//
//    public Integer getVoteSize() {
//        return voteSize;
//    }
//
//    public void setVoteSize(Integer voteSize) {
//        this.voteSize = voteSize;
//    }
//
//    public String getTags() {
//        return tags;
//    }
//
//    public void setTags(String tags) {
//        this.tags = tags;
//    }
//
//    @Override
//    public String toString() {
//        return String.format(
//                "User[id=%d, title='%s', content='%s']",
//                courseId, title, content);
//    }
}