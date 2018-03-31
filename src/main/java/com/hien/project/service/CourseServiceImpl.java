package com.hien.project.service;

import com.hien.project.domain.*;
import com.hien.project.domain.es.EsCourse;
import com.hien.project.repository.CourseRepository;
import com.hien.project.service.es.EsCourseService;
import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EsCourseService esCourseService;

    @Transactional
    @Override
    public Course saveCourse(Course course) {
        boolean isNew = (course.getId()==null);
        EsCourse esCourse = null;
        Course returnCourse=courseRepository.save(course);
        if(isNew){
            esCourse=new EsCourse(returnCourse);
        }else{
            esCourse=esCourseService.getEsCourseByCourseId(course.getId());
            esCourse.update(returnCourse);
        }
        esCourseService.updateEsCourse(esCourse);
        return returnCourse;
    }

    @Transactional
    @Override
    public void removeCourse(Long id) {
        courseRepository.delete(id);
        EsCourse esCourse=esCourseService.getEsCourseByCourseId(id);//es中nosql数据库中的博客数据也进行删除
        esCourseService.removeEsCourse(esCourse.getId());
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findOne(id);
    }

    @Override
    public Page<Course> listCoursesByTitleVote(User user, String title, Pageable pageable) {

        title = "%" + title + "%";
        Page<Course> courses = courseRepository.findByUserAndTitleLike(user, title, pageable);
        return courses;
    }

    @Override
    public Page<Course> listCoursesByTitleVoteAndSort(User user, String title, Pageable pageable) {
        title = "%" + title + "%";
        String tags = title;
        Page<Course> courses = courseRepository
                .findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(user, tags, pageable);
        return courses;
    }

    @Override
    public void readingIncrease(Long id) {
        Course course = courseRepository.findOne(id);
        course.setReadSize(course.getReadSize() + 1);
        this.saveCourse(course);
    }

    @Override
    public Course createComment(Long courseId, String commentContent) {
        Course originalCourse = courseRepository.findOne(courseId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//获取当前用户信息
        Comment comment = new Comment(user, commentContent);
        originalCourse.addComment(comment);
        return this.saveCourse(originalCourse);
    }

    @Override
    public void removeComment(Long courseId, Long commentId) {
        Course originalCourse = courseRepository.findOne(courseId);
        originalCourse.removeComment(commentId);
        this.saveCourse(originalCourse);
    }

    @Override
    public Course createVote(Long courseId) {
        Course originalCourse = courseRepository.findOne(courseId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vote vote = new Vote(user);
        boolean isExist = originalCourse.addVote(vote);
        if(isExist){
            throw new IllegalArgumentException("该用户已经点过赞了");
        }
        return this.saveCourse(originalCourse);
    }


    @Override
    public void removeVote(Long courseId, Long voteId) {
        Course originalCourse = courseRepository.findOne(courseId);
        originalCourse.removeVote(voteId);
        this.saveCourse(originalCourse);
    }


    @Override
    public Page<Course> listCoursesByCatalog(Catalog catalog, Pageable pageable) {
        Page<Course> courses=courseRepository.findByCatalog(catalog,pageable);
        return courses;
    }
}