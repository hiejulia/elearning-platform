package com.hien.project.service;

import com.hien.project.domain.es.EsCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CourseService {

    Page<EsCourse> findByTags(String tagName, PageRequest pageRequest);
}
