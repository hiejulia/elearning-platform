package com.hien.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;


@RestController
@RequestMapping("/v1/api/votes")
public class VoteController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private VoteService voteService;

    /**
     * 发表点赞
     * @param courseId
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")//指定角色权限才能操作方法
    public ResponseEntity<Response> createVote(Long courseId){
        try{
            courseService.createVote(courseId);
        }catch (ConstraintViolationException e){
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true,"处理成功",null));
    }


    /**
     * 取消点赞
     * @param id
     * @param courseId
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")//指定角色权限才能操作方法
    public ResponseEntity<Response> delete(@PathVariable("id") Long id,Long courseId){
        boolean isOwner = false;
        User user = voteService.getVoteById(id).getUser();

        //判断操作用户是否是点赞的所有者
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
        if(!isOwner){
            return ResponseEntity.ok().body(new Response(false,"没有操作权限"));
        }
        try{
            courseService.removeVote(courseId,id);
            voteService.removeVote(id);
        }catch (ConstraintViolationException e){
            return ResponseEntity.ok().body(new Response(false,ConstraintViolationExceptionHandler.getMessage(e)));
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true,"取消点赞",null));
    }
}