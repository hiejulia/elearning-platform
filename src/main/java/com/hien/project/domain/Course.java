package com.hien.project.domain;


import lombok.Data;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

//@Data
@Entity
@Table(name = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = -5850480222753274304L;

    // Course : id, title, summary, content (of the course ), User , createTime,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String title;


    @Size(min = 2, max = 300)
    @Column(nullable = false)
    private String summary;

    @Lob // MySQL - Long text
    @Basic(fetch = FetchType.LAZY)
    @Size(min = 2)
    @Column(nullable = false)
    private String content;

//    @Lob
//    @Basic(fetch = FetchType.LAZY)

//    @Size(min = 2)
//    @Column(nullable = false)
//    private String htmlContent;                             // md and html

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp createTime;

    @Column(name = "readSize")
    private Integer readSize = 0;

    @Column(name = "commentSize")
    private Integer commentSize = 0;

    @Column(name = "voteSize")
    private Integer voteSize = 0;

    @Column(name = "tags", length = 100)
    private String tags;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "course_comment", joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
    private List<Comment>     comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "course_vote", joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "vote_id", referencedColumnName = "id"))
    private List<Vote>        votes;

    @OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    protected Course() {

    }

    public Course(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
//        this.htmlContent = Processor.process(content);//将Markdown 内容转为 HTML 格式
    }

//    public String getHtmlContent() {
//        return htmlContent;
//    }

//    public void setHtmlContent(String htmlContent) {
//        this.htmlContent = htmlContent;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getReadSize() {
        return readSize;
    }

    public void setReadSize(Integer readSize) {
        this.readSize = readSize;
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    public Integer getVoteSize() {
        return voteSize;
    }

    public void setVoteSize(Integer voteSize) {
        this.voteSize = voteSize;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        this.commentSize = this.comments.size();
    }


    public void addComment(Comment comment) {
        this.comments.add(comment);
        this.commentSize = this.comments.size();
    }

    public void removeComment(Long commentId) {
        for (int index = 0; index < this.comments.size(); index++) {
            if (commentId.equals(comments.get(index).getId())) {
                this.comments.remove(index);
            }
        }
        this.commentSize = this.comments.size();
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
        this.voteSize = this.votes.size();
    }

//
//    public boolean addVote(Vote vote) {
//        boolean isExist = false;
//
//        for (int index = 0; index < this.votes.size(); index++) {
//            if (vote.getUser().getId().equals(this.votes.get(index).getUser().getId())) {
//                isExist = true;
//                break;
//            }
//        }
//        if (!isExist) {
//            this.votes.add(vote);
//            this.voteSize = this.votes.size();
//        }
//        return isExist;
//    }
//
//
//    public void removeVote(Long voteId) {
//        for (int index = 0; index < this.votes.size(); index++) {
//            if (voteId.equals(this.votes.get(index).getId())) {
//                this.votes.remove(index);
//                break;
//            }
//        }
//        this.voteSize = this.votes.size();
//    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}
