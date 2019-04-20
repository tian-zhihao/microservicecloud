package com.tzhtry.springcloud.service;

import com.tzhtry.springcloud.entities.Dept;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.awt.SunHints;

import java.util.List;

@FeignClient(value = "MICROSERVICECLOUD-DEPT")
public interface DeptClientService {

    @RequestMapping(value="/dept/add",method= RequestMethod.POST)
    public boolean add(Dept dept);

    @RequestMapping(value="/dept/get/{id}",method=RequestMethod.GET)
    public Dept    get(@PathVariable("id")Long id);

    @RequestMapping(value="/dept/list",method=RequestMethod.GET)
    public List<Dept> list();
    @RequestMapping(value="/dept/discovery",method=RequestMethod.GET)
    public Object discovery();

}
