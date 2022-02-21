<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ page import="Model.Produit" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../templates/header.jsp" %>
	<title>Profil</title>
</head>
<body>
	<%@include file="../templates/navbarAuthentified.jsp" %>
	<div id="container-profil">
		<h1>Mon profil <c:choose><c:when test="${user.getPrivileges() == 0 }">client</c:when><c:otherwise>administrateur</c:otherwise></c:choose>:</h1>
		<div id="profil-infos-container">
			<h3>Nom: <c:out value="${user.getNom() }"/></h3>
			<h3>Prenom: <c:out value="${user.getPrenom() }"/></h3>
			<h3>Adresse e-mail: <c:out value="${user.getEmail() }"/></h3>
		</div>
	</div>
	<%@include file="../templates/scripts.jsp" %>
</body>
</html>