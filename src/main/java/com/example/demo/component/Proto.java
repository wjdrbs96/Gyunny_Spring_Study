package com.example.demo.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * created by jg 2021/04/24
 */
@Component @Scope("prototype")
public class Proto {

    @Autowired
    private Single single;
}
