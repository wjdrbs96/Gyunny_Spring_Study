package com.example.demo.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * created by jg 2021/05/02
 */
public class HelloServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("init Servlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet !!");
    }

    @Override
    public void destroy() {
        System.out.println("destroy Servlet");
    }
}
