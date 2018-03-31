package com.hien.project.repository;



import com.hien.project.domain.Catalog;
import com.hien.project.domain.Course;
import com.hien.project.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Find course by User and Title like: User, String title
    Page<Course> findByUserAndTitleLike(User user, String title, Pageable pageable);

    // find Copurse by title like and User or tags liek and user order by create time desc : User - String title
    Page<Course> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(User user, String title,Pageable pageable);

    // find Course by Catalog : Catalog
    Page<Course> findByCatalog(Catalog catalog, Pageable pageable);
}