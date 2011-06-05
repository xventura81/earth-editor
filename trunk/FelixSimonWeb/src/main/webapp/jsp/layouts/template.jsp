<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<html>
<head>
	
	<!-- No cache -->
	<%   
    response.setHeader("Cache-Control","no-cache"); //HTTP 1.1   
    response.setHeader("Pragma","no-cache"); //HTTP 1.0   
    response.setDateHeader ("Expires", 0); //prevent caching at the proxy server   
    %>
	
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="-1" />
	<link href="<s:url value="/styles/style.css"/>" rel="stylesheet" type="text/css"/>	
	<script type="text/javascript" src="<s:url value="/scripts/jquery-1.6.1.min.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/scripts/jquery-ui-1.8.13.custom.min.js"/>"></script>
	<link href="<s:url value="/styles/jquery-ui-1.8.13.custom.css"/>" rel="stylesheet" type="text/css"/>	
	<link rel="icon" type="image/png" href="<s:url value='/images/logoFS.png'/>"/>     
	<title><tiles:getAsString name='title'/></title>		
</head>  
<body>
<div id="page">
	<div id="page_wrap">
		<div id="header">
			<div id="logo">
				<div id="logo_image">
					<img alt="FELIX SIMON, S.L." src="<s:url value="/images/logoFS.png"/>"/>
				</div>	
				<div id="logo_title">
					<h2 style="display: inline; color:white; font-size: xx-large; 
					font-weight: bold; padding-left: 2%;">
						FELIX SIMON, S.L.
					</h2>
				</div>				
				
			</div>
		</div>
		<div id="menu">
			<tiles:insertAttribute name="menu"/>
		</div>
		<div id="container">
			<tiles:insertAttribute name="body" />				
		</div>
		<div id="footer">
			<tiles:insertAttribute name="footer" />		
		</div>
	</div>
</div>

</body>
</html>