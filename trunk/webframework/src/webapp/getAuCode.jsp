<%-- 
    Document   : getAuCode
    Created on : Oct 3, 2012, 9:47:26 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%      
        String authorizationCode="";
        if (request.getParameter("code") == null) {
            out.println("Please enter your name.");
            %>
            <h1>PLEASE LOGIN ZING ME!</h1>
        <%} else {
            //out.println("Hello <b>"+request.getParameter(i)+"</b>!");
            authorizationCode=request.getParameter("code");
            String url = "http://fresher2012.dev/?code="+authorizationCode+ "&isRedirect=false";               
            response.sendRedirect(url);
        }                   
%>

