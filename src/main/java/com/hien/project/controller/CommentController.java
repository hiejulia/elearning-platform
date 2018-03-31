package com.hien.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;


@RestController
@RequestMapping("/v1/api/comments")
public class CommentController {

    @Autowired
    private CourseService    courseService;

    @Autowired
    private CommentService commentService;

    /**
     * 获取评论列表
     * @param courseId
     * @param model
     * @return
     */
    @GetMapping
    public String listComments(@RequestParam(value = "courseId", required = true) Long courseId,
                               Model model) {
        Course course = courseService.getCourseById(courseId);
        List<Comment> comments = course.getComments();

        //判断操作用户是否是评论的所有者
        String commentOwner = "";
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
                .equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            if (principal != null) {
                commentOwner = principal.getUsername();
            }
        }
        model.addAttribute("commentOwner", commentOwner);
        model.addAttribute("comments", comments);
        return "/userspace/course :: #mainContainerRepleace";
    }

    /**
     * 发表评论
     * @param courseId
     * @param commentContent
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')") //指定角色权限才能操作方法
    public ResponseEntity<Response> createComment(Long courseId, String commentContent) {
        try {
            courseService.createComment(courseId, commentContent);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok()
                    .body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')") //指定角色权限才能操作
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Long courseId) {
        boolean isOwner = false;
        User user = commentService.getCommentById(id).getUser();

        //判断操作用户是否是评论的所有者
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
                .equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            if (principal != null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }
        }
        if (!isOwner) {
            return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
        }
        try {
            courseService.removeComment(courseId, id);
            commentService.removeComment(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok()
                    .body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }
}