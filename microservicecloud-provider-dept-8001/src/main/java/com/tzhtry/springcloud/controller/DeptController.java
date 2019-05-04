package com.tzhtry.springcloud.controller;

import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.ServiceInstance;


import com.tzhtry.springcloud.entities.Dept;
import com.tzhtry.springcloud.service.DeptService;

@RestController
public class DeptController
{
    @Autowired
    private DeptService service;

    //服务的自动发现,eureka
    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value="/dept/add",method=RequestMethod.POST)
    public boolean add(@RequestBody Dept dept)
    {
        return service.add(dept);
    }

    @RequestMapping(value="/dept/get/{id}",method=RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "processHystrix_Get")//熔断器（参考spring异常通知），报错熔断后执行processHystrix_Get函数
    public Dept get(@PathVariable("id") Long id)
    {
        Dept dept = service.get(id);
        if(dept==null){
            throw new RuntimeException("id:"+id+" 没有对应信息");
        }
        return dept;
    }

    @RequestMapping(value="/dept/list",method=RequestMethod.GET)
    public List<Dept> list()
    {
        return service.list();
    }

    @RequestMapping(value = "/dept/discovery",method = RequestMethod.GET)
    public Object discovery(){
        List<String> list = client.getServices();
        System.out.println("**********"+list);

        List<ServiceInstance> srvList = client.getInstances("MICROSERVICECLOUD-DEPT");
        for (ServiceInstance element : srvList){
            System.out.println(element.getServiceId()+"\t"+element.getHost()+"\t"+element.getPort()+"\t"+element.getUri());
        }
        return  this.client;
    }
    public Dept processHystrix_Get(@PathVariable("id") Long id){
        Dept erroObj = new Dept();
        erroObj.setDeptno(id);
        erroObj.setDb_source("no this datasource");
        erroObj.setDname("ID:"+id+" 没有对应信息，null --@HystrixCommand");
        return erroObj;
    }

}

