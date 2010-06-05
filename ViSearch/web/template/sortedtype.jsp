<%-- 
    Document   : sortedtype
    Created on : Jun 4, 2010, 3:59:49 PM
    Author     : VinhPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
      <label for="slSortedType">Sắp xếp kết quả theo:</label>
      <select name="slSortedType" id="slSortedType" onChange="Sort();">
      <option value="0">Theo độ liên quan</option>
      <option value="1">Theo thời gian giảm dần</option>
      </select>
    </body>
</html>
