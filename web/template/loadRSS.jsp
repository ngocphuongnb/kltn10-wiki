<%-- 
    Document   : loadRSS
    Created on : May 22, 2010, 6:36:32 PM
    Author     : tuandom, vinhpham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="import javax.xml.parsers.DocumentBuilder"%>
<%@page import="import javax.xml.parsers.DocumentBuilderFactory"%>
<%@page import="import org.w3c.dom.*"%>
<%@page import="import java.io.File"%>



<%
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = (Document) builder.parse(new File("http://vnexpress.net/rss/gl/the-gioi.rss"));
            Element root = doc.getDocumentElement();
            NodeList list = root.getChildNodes(); // Node goc

            for (int i = 0; i < 5; ++i) { // Danh sach cac Channel
                Node node = (Node) list.item(i);

                 if (node instanceof Element) {
                Element element = (Element) node;

                ciDTO.setId(element.getAttribute("Id"));
                ciDTO.setName(element.getAttribute("Name"));
                if (element.getAttribute("Id").equals(IdCinema) == true) {// Chi lay Rap Id

                    NodeList list2 = element.getChildNodes();
                    for (int j = 0; j < list2.getLength(); ++j) { // Danh sach cac Phim
            }
%>