<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="s" uri="/struts-tags"%>

	<script type="text/javascript" src="<s:url value="/scripts/fancybox/jquery.mousewheel-3.0.4.pack.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/scripts/fancybox/jquery.fancybox-1.3.4.pack.js"/>"></script>
	<link rel="stylesheet" type="text/css" href="<s:url value="/styles/fancybox/jquery.fancybox-1.3.4.css"/>" media="screen" />

<div id="container_banner"><img style="width: 100%;"
	src="<s:url value="/images/banner.jpg"/>" /></div>

<div id="info">
	<div class="info_box">
		<p class="info_box_title" style="color: #333333">
			<s:text name="inicio.text1.title" />
		</p>
		
		<p><s:text name="inicio.text1" /></p>
	</div>
	<div class="info_box">
		<p class="info_box_title" style="color: #333333">
			<s:text name="inicio.text2.title" />
		</p>
		<p>
			<s:text name="inicio.text2" />
			<a href="mailto:info@felixsimon.com"><s:text name="info.email" /></a>
		</p>
		<p style="margin-top: 15px;">
			<a href="http://bit.ly/jtuHQf" target="blank"> 
				<img id="map_button" border="0" alt="" src="<s:url value="/images/map-button.png"/>" 
				height="30" width="120" />
			</a>
		</p>
	</div>
	<div class="info_box">
		<p class="info_box_title" style="color: #333333">
			<s:text name="inicio.text3.title" />
		</p>
		<p>
			<s:text name="inicio.text3" />
		</p>
	</div>
	<div id="imagesPreview" style="float:left;width:100%;margin-top:2%;">
		<p class="info_box_title" style="color: #2D0E45;width:100%">
			<s:text name="galeria_imagenes" />
		</p>
		<div style="float:left;width:100%;padding-left:3%;padding-right:3%">
			<a rel="image_group" href="<s:url value="/images/productos/Producto1.jpg"/>">
				<img src="<s:url value="/images/productos/Producto1Mini.jpg"/>" />
			</a>
			<a rel="image_group" href="<s:url value="/images/productos/Producto2.jpg"/>">
				<img src="<s:url value="/images/productos/Producto2Mini.jpg"/>" />
			</a>
			<a rel="image_group" href="<s:url value="/images/productos/Producto3.jpg"/>">
				<img src="<s:url value="/images/productos/Producto3Mini.jpg"/>" />
			</a>
			<a rel="image_group" href="<s:url value="/images/productos/Producto4.jpg"/>">
				<img src="<s:url value="/images/productos/Producto4Mini.jpg"/>" />
			</a>
			<a rel="image_group" href="<s:url value="/images/productos/Producto5.jpg"/>">
				<img src="<s:url value="/images/productos/Producto5Mini.jpg"/>" />
			</a>
			<a rel="image_group" href="<s:url value="/images/productos/Producto6.jpg"/>">
				<img src="<s:url value="/images/productos/Producto6Mini.jpg"/>" />
			</a>
		</div>
	</div>
</div>

	<script type="text/javascript">
	$(document).ready(function() {
		
		$("#imagesPreview a[rel=image_group]").fancybox();

		$(function() {
		    $('#map_button').hover(function() {
		        var currentImg = $(this).attr('src');
		        $(this).attr('src', '<s:url value="/images/map-button-hover.png"/>');
		    }, function() {
		        var currentImg = $(this).attr('src');
		        $(this).attr('src', '<s:url value="/images/map-button.png"/>');
		    });
		});
		

	});
	</script>