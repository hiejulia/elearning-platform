package com.hien.project.repository.es;


import com.hien.project.domain.es.EsCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface EsCourseRepository extends ElasticsearchRepository<EsCourse, String> {

    // Find course by distinct es course by title contains or summary contain or content contain or tags contain
    // String title, String summary, String content, String tags
    Page<EsCourse> findDistinctEsCourseByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title,
                                                                                                           String summary,
                                                                                                           String content,
                                                                                                           String tags,
                                                                                                           Pageable pageable);

    // Find course by course id
    EsCourse findByCourseId(Long courseId);
    // Find course by tag name
    Page<EsCourse> findByTags(String name,Pageable pageable);
}