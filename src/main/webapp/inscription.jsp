<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/templates/header.jsp" %>
	<title>Inscription</title>
</head>
<body>
	<%@ include file="/templates/navbar.jsp" %>
	<div id="inscription-container">
		<h1>Créer mon compte</h1>
		<form method="post" action="InscriptionUtilisateur">
			<div class="input-group">
				<label for="nom">nom</label>
				<input type="text" id="nom" name="nom" required>
			</div>
			<div class="input-group">
				<label for="prenom">prenom:</label>
				<input type="text" id="prenom" name="prenom" required>
			</div>
			<div class="input-group">
				<label for="password">password:</label>
				<input type="password" name="password" id="password" required>
			</div>
			<div class="input-group">
				<label for="email">email:</label>
				<input type="email" id="email" name="email" required>
			</div>
			<input class="button-a validationformulaire" type="submit" value="Créer mon compte"/>
		</form>
		<c:if test="${erreur != null}">
			<span class="error-message"><c:out value="${erreur}"/></span>
		</c:if>
		<a class="form-link" href="index.jsp">Déjà un compte ? cliquez ici</a>
	</div>
	<%@ include file="/templates/scripts.jsp" %>
</body>
</html>