<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../templates/header.jsp" %>
	<title>Validation commande</title>
</head>
<body>
	<c:choose>
		<c:when test="${user.getNom() != null }">
			<%@include file="../templates/navbarAuthentified.jsp" %>
		</c:when>
		<c:otherwise>
			<%@include file="../templates/navbar.jsp" %>
		</c:otherwise>		
	</c:choose>
	<div id="finalisation-commande-container">
		<h1>Votre commande a bien été finalisée !</h1>
	</div>
	<%@include file="../templates/scripts.jsp" %>
</body>
</html>