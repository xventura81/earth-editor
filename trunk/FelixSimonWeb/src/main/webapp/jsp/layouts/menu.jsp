<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<ul class="menu_list">
	<li><a href="<s:url action="index.action"/>">Inicio</a></li>
	<li><a href="<s:url action="empresa.action"/>">Nuestra Empresa</a></li>
	<li><a href="<s:url action="calidad.action"/>">Calidad</a></li>
	<li><a href="<s:url action="productos.action"/>">Productos</a></li>
	<li><a href="<s:url action="imagenes.action"/>">Galería de imágenes</a></li>
	<li><a href="<s:url action="contacto.action"/>">Contacto</a></li>
</ul>

<script>
		$('li')[<tiles:getAsString name='index'/>].className = 'selected';		
</script>