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
		<c:if test="${erreur != null}">
			<span class="error-message"><c:out value="${erreur}"/></span>
		</c:if>
		<h1>Créer mon compte</h1>
		<form method="post" action="InscriptionUtilisateur">
			<div class="input-group">
				<label for="nom">Nom</label>
				<input type="text" id="nom" name="nom" required>
			</div>
			<div class="input-group">
				<label for="prenom">Prenom:</label>
				<input type="text" id="prenom" name="prenom" required>
			</div>
			<div class="input-group">
				<label for="email">Email:</label>
				<input type="email" id="email" name="email" required>
			</div>
			<h2>Adresse de livraison principale: (optionnel)</h2>
			<div class="input-group">
				<label for="rue">N° et Rue</label>
				<input type="text" name="rue" id="rue">
			</div>
			<div class="input-group">
				<label for="cp">Code Postal</label>
				<input type="text" name="cp" id="cp">
			</div>
			<div class="input-group">
				<label for="ville">Ville</label>
				<input type="text" name="ville" id="ville">
			</div>
			<h2>Sécurité</h2>
			<div class="input-group">
				<label for="password">Mot de passe:</label>
				<input type="password" name="password" id="password" required>
			</div>
			<div class="input-group">
				<label for="password2">Confirmez le mot de passe:</label>
				<input type="password" name="password2" id="password2" required>
			</div>
			<input class="button-a" type="submit" value="Créer mon compte"/>
		</form>
		<a class="form-link" href="index.jsp">Déjà un compte ? cliquez ici</a>
	</div>
	<%@ include file="/templates/scripts.jsp" %>
</body>
</html>