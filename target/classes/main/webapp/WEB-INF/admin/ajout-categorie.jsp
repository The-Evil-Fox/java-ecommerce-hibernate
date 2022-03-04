<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../../templates/header.jsp" %>
	<title>Ajouter une catégorie</title>
</head>
<body>
	<%@include file="../../templates/navbarAuthentified.jsp" %>
	<div id="container-ajout-produit">
		<c:if test="${erreur != null}">
			<span class="error-message"><c:out value="${erreur}"/></span>
		</c:if>
		<h1>Ajouter une nouvelle catégorie</h1>
		<form method="post" action="AjouterCategorie" id="ajoutproduit">
			<div class="input-group">
				<label for="categorie">Nom de la catégorie:</label>
				<input type="text" name="categorie" id="categorie" required>
			</div>
			<input class="button-a" type="submit" value="Valider"/>
		</form>
	</div>
	<%@include file="../../templates/scripts.jsp" %>
</body>
</html>