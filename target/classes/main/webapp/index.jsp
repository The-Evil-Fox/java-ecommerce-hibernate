<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../templates/header.jsp" %>
	<title>Connexion</title>
</head>
<body>
	<%@include file="../templates/navbar.jsp" %>
	<c:set value="${erreur}" var="erreur" />
	<div id="connexion-container">
		<h1>Connexion</h1>
		<form method="post" action="ConnexionUtilisateur">
			<div class="input-group">
				<label for="email">email:</label>
				<input type="email" name="email" id="email" required>
			</div>
			<div class="input-group">
				<label for="password">password:</label>
				<input type="password" name="password" id="password" required>
				<a class="form-link" href="">Mot de passe oublié ?</a>
			</div>
			<input class="button-a validationformulaire" type="submit" value="Connexion"/>
		</form>
		<c:if test="${erreur != null}">
			<span class="error-message"><c:out value="${erreur}"/></span>
		</c:if>
		<a class="form-link" href="inscription.jsp">Pas encore de compte ? cliquez ici</a>
	</div>
	<%@include file="../templates/scripts.jsp" %>
</body>
</html>