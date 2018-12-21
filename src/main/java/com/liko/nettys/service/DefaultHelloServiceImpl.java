package com.liko.nettys.service;

import org.springframework.stereotype.Service;

/**
 * Created by likoguan on 5/12/18.
 */
@Service("helloService")
public class DefaultHelloServiceImpl implements HelloService {
    @Override
    public void hello() {
        System.out.println("hello");
    }
}
