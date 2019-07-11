package com.fineway.scaffolding.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fineway.scaffolding.annotation.Log;
import com.fineway.scaffolding.entity.Hello;
import com.fineway.scaffolding.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/{apiVersion}")
public class HelloController extends BaseController {


    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/hello/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    @Log(body = true,slowRequestMillis = 3000)
    public ResponseEntity<String> getHelloByName(@PathVariable String name, @PathVariable String apiVersion, HttpServletRequest request, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Hello> helloList = helloService.getHelloList(name, pageable);
        return response(helloList);
    }

    @RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Log
    public ResponseEntity<String> getHelloById(@PathVariable long id, @PathVariable String apiVersion, HttpServletRequest request) {
        Hello hello = helloService.getHello(id);
        return response("hello", hello);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    @ResponseBody
    @Log
    public ResponseEntity<String> getHello(@PathVariable String apiVersion, @RequestBody String body, HttpServletRequest request) {
        JSONObject jsonObject = JSON.parseObject(body);
        String name = jsonObject.getString("name");
        Hello hello = helloService.saveHello(name);
        return response("hello ", hello);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    @Log
    public ResponseEntity<String> getHello(@PathVariable String apiVersion, HttpServletRequest request) {
        Map<String, String> resp = new HashMap<>();
        resp.put("Hello", "world");
        return response(resp);
    }

    @RequestMapping(value = "/hello/error", method = RequestMethod.GET)
    @ResponseBody
    @Log
    public ResponseEntity<String> getHelloError(@PathVariable String apiVersion, HttpServletRequest request) {
        return responseError();
    }

    @RequestMapping(value = "/hello/ok", method = RequestMethod.GET)
    @ResponseBody
    @Log
    public ResponseEntity<String> getHelloOk(@PathVariable String apiVersion, HttpServletRequest request) {
        return responseOk();
    }
}
