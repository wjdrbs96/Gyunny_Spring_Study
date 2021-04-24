package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by jg 2021/04/24
 */
public class ScopeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class);
        ProtoTypeBean proto1 = ac.getBean(ProtoTypeBean.class);
        System.out.println("find prototypeBean1" + proto1);
        ProtoTypeBean proto2 = ac.getBean(ProtoTypeBean.class);
        System.out.println("find prototypeBean2" + proto2);
        assertThat(proto1).isSameAs(proto2);
    }

    @Scope("prototype")
    static class ProtoTypeBean {
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init ");
        }

        @PreDestroy
        public void destory() {
            System.out.println("SingletonBean.destory");
        }
    }
}
