package com.fineway.scaffolding;

import com.fineway.scaffolding.service.HelloService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MyTest extends BaseTest {

    @Autowired
    public HelloService helloService;

    @Test
    public void Test() {

    }
}
