package com.tzhtry.springcloud.controller;


import com.tzhtry.springcloud.entities.Dept;
import com.tzhtry.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class DeptController_Consumer {
    @Autowired
    DeptClientService service;
    @RequestMapping(value="/consumer/dept/add")
    public Object add(Dept dept){
        return this.service.add(dept);
    }
    @RequestMapping(value="/consumer/dept/get/{id}",method=RequestMethod.GET)
    public Dept    get(@PathVariable("id")Long id){
        return this.service.get(id);
    }
    @RequestMapping(value="/consumer/dept/list",method=RequestMethod.GET)
    public List<Dept> list(){
        return this.service.list();
    }
        //消费端调用服务发现,为什么feign不行
       @RequestMapping(value = "/consumer/dept/discovery")
    public Object discovery(){
        return this.service.discovery();
    }

    //没有用feign的版本：
//    private static final String REST_URL_PREFIX = "http://MICROSERVICECLOUD-DEPT";
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @RequestMapping(value = "consumer/dept/add")
//    public boolean add(Dept dept){
//        return restTemplate.postForObject(REST_URL_PREFIX+"/dept/add",dept,Boolean.class);
//    }
//
//    @RequestMapping(value = "consumer/dept/get/{id}")
//    public Dept get(@PathVariable("id") Long id){
//        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/get/"+id,Dept.class);
//    }
//
//    @SuppressWarnings("unchecked")
//    @RequestMapping(value = "/consumer/dept/list")
//    public List<Dept> list(){
//        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/list",List.class);
//    }
//
//    //消费端调用服务发现
//    @RequestMapping(value = "/consumer/dept/discovery")
//    public Object discovery(){
//        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/discovery",Object.class);
//    }
}
