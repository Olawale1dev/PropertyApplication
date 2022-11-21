/*
package com.example.property.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResourceDispatcherServlet extends HttpServlet {

    public void init() throws ServletException {

    }

    public void doGet(HttpServletRequest req, HttpServletResponse rsp) throws ServletException, IOException {


        String servletPath = req.getServletPath();   // /resource
        String pathInfo = req.getPathInfo();         // /path/path/app.js

        String url = "/WEB-INF/views" + pathInfo;

        String lastPath = StringUtil.substringAfterLast(pathInfo, "/");
        String extension = StringUtil.substringAfterLast(lastPath, ".");

        try {
            RequestDispatcher dispatcher = null;
            if (!StringUtil.isEmpty(extension) && ("js".equals(extension) || "css".equals(extension))) {
                dispatcher = req.getRequestDispatcher(url);
            }

            if (dispatcher != null) {
                dispatcher.include(req, rsp);
            }
            else {
                rsp.sendError(404);
            }
        }
        catch (Exception e) {
            if (!rsp.isCommitted()) {
                rsp.sendError(500);
            }
        }
    }

*/
