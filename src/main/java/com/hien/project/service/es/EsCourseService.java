package com.hien.project.service.es;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface EsCourseService {


    void removeEsCourse(String id);


    EsCourse updateEsCourse(EsCourse esCourse);


    EsCourse getEsCourseByCourseId(Long courseId);


    Page<EsCourse> listNewestEsCourses(String keyword, Pageable pageable);


    Page<EsCourse> listHotestEsCourses(String keyword,Pageable pageable);


    Page<EsCourse> listEsCourses(Pageable pageable);


    List<EsCourse> listTop5NewestEsCourses();


    List<EsCourse> listTop5HotestEsCourses();


    List<TagVO> listTop30Tags();


    List<User> listTop12Users();
}