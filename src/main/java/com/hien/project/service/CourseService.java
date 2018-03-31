package com.hien.project.service;


import com.hien.project.domain.Catalog;
import com.hien.project.domain.Course;
import com.hien.project.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hien.project.domain.es.EsCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CourseService {

    Page<EsCourse> findByTags(String tagName, PageRequest pageRequest);


    Course saveCourse(Course course);


    void removeCourse(Long id);


    Course getCourseById(Long id);

    Page<Course> listCoursesByTitleVote(User user, String title, Pageable pageable);

    Page<Course> listCoursesByTitleVoteAndSort(User user, String title, Pageable pageable);

    void readingIncrease(Long id);


    Course createComment(Long courseId,String commentContent);

    void removeComment(Long courseId,Long commentId);

    Course createVote(Long courseId);

    void removeVote(Long courseId,Long voteId);

    Page<Course> listCoursesByCatalog(Catalog catalog, Pageable pageable);
}
