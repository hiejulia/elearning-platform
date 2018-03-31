package com.hien.project.controller;


import com.hien.project.domain.User;
import com.hien.project.domain.es.EsCourse;
import com.hien.project.service.es.EsCourseService;
import com.hien.project.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1/api/courses")
public class CourseController {
    
    @Autowired
    private EsCourseService esCourseService;

    @GetMapping
    public String listCourses(@RequestParam(value = "order", required = false, defaultValue = "new") String order,
                            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                            @RequestParam(value = "async", required = false) boolean async,
                            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                            Model model) {
        Page<EsCourse> page = null;
        List<EsCourse> list = null;
        boolean isEmpty = true;
        try {
            if(order.equals("hot")){//最热数据
                Sort sort=new Sort(Direction.DESC,"readSize","commentSize","voteSize","createTime");
                Pageable pageable=new PageRequest(pageIndex,pageSize,sort);
                page=esCourseService.listHotestEsCourses(keyword,pageable);
            }else if(order.equals("new")){//最新数据
                Sort sort=new Sort(Direction.DESC,"createTime");
                Pageable pageable = new PageRequest(pageIndex,pageSize,sort);
                page=esCourseService.listNewestEsCourses(keyword,pageable);
            }
            isEmpty=false;
        }catch (Exception e){
            Pageable pageable=new PageRequest(pageIndex,pageSize);
            page=esCourseService.listEsCourses(pageable);
        }
        list=page.getContent();//当前所在页面数据列表

        model.addAttribute("order",order);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        model.addAttribute("courseList",list);

        //首次访问才加载一下数据
        if(!async && !isEmpty){
            List<EsCourse> newest = esCourseService.listTop5NewestEsCourses();
            model.addAttribute("newest", newest);
            List<EsCourse> hotest = esCourseService.listTop5HotestEsCourses();
            model.addAttribute("hotest", hotest);
            List<TagVO> tags = esCourseService.listTop30Tags();
            model.addAttribute("tags", tags);
            List<User> users = esCourseService.listTop12Users();
            model.addAttribute("users", users);
        }
        return (async==true?"/index :: #mainContainerRepleace":"/index");
    }

    @GetMapping("/newest")
    public String listNewestEsCourses(Model model) {
        List<EsCourse> newest = esCourseService.listTop5NewestEsCourses();
        model.addAttribute("newest", newest);
        return "newest";
    }

    @GetMapping("/hotest")
    public String listHotestEsCourses(Model model) {
        List<EsCourse> hotest = esCourseService.listTop5HotestEsCourses();
        model.addAttribute("hotest", hotest);
        return "hotest";
    }

}