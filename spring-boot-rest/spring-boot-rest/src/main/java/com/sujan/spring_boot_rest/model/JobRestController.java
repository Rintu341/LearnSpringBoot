package com.sujan.spring_boot_rest.model;

import com.sujan.spring_boot_rest.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RestController /*It's make whole controller Rest like it only return responseBody
// then you do not need to add @ResponseBody for each request*/
public class JobRestController {

    @Autowired
    private JobService service;

    @GetMapping(path = "/jobPosts" , produces = {"application/json"})
//    @ResponseBody /*By default, @Controller return a view but if you want to return a body add @ResponseBody*/
    public List<JobPost> getAllJobs(){
        return service.getAllJobs();
    }


    @GetMapping("/jobPost/{postId}")
//    @ResponseBody
    public JobPost getJob(@PathVariable int postId){
        return service.getJob(postId);
    }


    @PostMapping(path = "/jobPost", consumes  ={"application/xml"})
//    @ResponseBody
    public JobPost addJob(@RequestBody JobPost jobPost){
         service.addJob(jobPost);
         return service.getJob(jobPost.getPostId());
    }

    @PutMapping("/jobPost" )
    public JobPost updatePost(@RequestBody JobPost jobPost){
        service.updatePost(jobPost);
        return service.getJob(jobPost.getPostId());
    }

    @DeleteMapping("jobPost/{postId}")
    public String deletePost(@PathVariable int postId){
        service.deletePost(postId);
        return "Delete Successfully";
    }
    @GetMapping("load")
    public String getAllPost(){
        service.load();

        return "success";
    }

    @GetMapping("jobPosts/keyword/{keyword}")
    public List<JobPost> search(@PathVariable String keyword){
        return service.search(keyword);
    }

    /*
    * Consumes and Produce is for content negotiation to restrike user with media format
    *
    * */

}
