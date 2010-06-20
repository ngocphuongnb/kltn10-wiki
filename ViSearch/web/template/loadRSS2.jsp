<%-- 
    Document   : loadRSS2
    Created on : May 22, 2010, 7:00:38 PM
    Author     : tuandom, vinhpham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@page import="org.w3c.dom.*"%>
<%@page import="java.io.File"%>
<%@page import="javax.xml.xpath.XPathFactory"%>
<%@page import="javax.xml.xpath.XPath.*"%>
<%@page import="javax.xml.xpath.XPath"%>
<%@page import="javax.xml.xpath.XPathExpression"%>
<%@page import="javax.xml.xpath.XPathExpressionException"%>
<%@page import="javax.xml.xpath.XPathConstants"%>

<%

            DocumentBuilderFactory domfactory = DocumentBuilderFactory.newInstance();
            domfactory.setNamespaceAware(true);
            DocumentBuilder builder = domfactory.newDocumentBuilder();
            Document doc = (Document) builder.parse(new File("C:/16.rss.xml"));

            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expre = null;
            try {
                expre = xpath.compile("//");
            } catch (XPathExpressionException ex) {
                out.print(ex.toString());
            }
            Object result = expre.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            for (int i = 0; i < nodes.getLength(); i++) {
                out.print(i);
                out.print(nodes.item(i).getNodeValue());
            }


%>
