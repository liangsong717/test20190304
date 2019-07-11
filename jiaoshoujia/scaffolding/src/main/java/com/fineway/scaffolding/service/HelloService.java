package com.fineway.scaffolding.service;

import com.fineway.scaffolding.dao.HelloDao;
import com.fineway.scaffolding.entity.Hello;
import com.fineway.scaffolding.exception.BaseException;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HelloService {

    @Autowired
    private HelloDao helloDao;

    public Page<Hello> getHelloList(String name, Pageable pageable) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(pageable);
        return helloDao.findByName(name, pageable);
    }

    public Hello saveHello(String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return helloDao.save(hello);
    }

    public Hello getHello(long id) {
        Optional<Hello> hello = helloDao.findById(id);
        return hello.orElseThrow(() -> new BaseException("404", "not found"));
    }
}
