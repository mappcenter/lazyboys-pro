<%-- 
    Document   : newjsf
    Created on : Oct 4, 2012, 1:55:17 PM
    Author     : root
--%>
<%@page import="middleware.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>JSP Page</title>
        </head>
        <body>
            <script type="text/javascript">
                function aaa(){
                    alert("hello world");
                }
            </script>
            
            <h1>Hello world</h1>
            <a href="javascript:aaa()" >hello</a>
            <% 
            User user=new User();
            user.userID="chanhlt190290";
            out.println(user.userID);
            
            
%>


        </body>
    </html>
</f:view>
