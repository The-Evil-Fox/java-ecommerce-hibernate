<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../../templates/header.jsp" %>
	<title>Ajouter un produit</title>
</head>
<body>
	<%@include file="../../templates/navbarAuthentified.jsp" %>
	<div id="container-ajout-produit">
		<c:if test="${erreur != null}">
			<span class="error-message"><c:out value="${erreur}"/></span>
		</c:if>
		<h1>Ajouter un nouveau produit</h1>
		<form method="post" action="AjoutProduit" id="ajoutproduit">
			<div class="input-group">
				<label for="libelle">Libelle du produit:</label>
				<input type="text" name="libelle" id="libelle" required>
			</div>
			<div class="input-group">
				<label for="cheminimage">Chemin de l'image:</label>
				<input type="text" name="cheminimage" id="cheminimage" required>
			</div>
			<div class="input-group">
				<label for="cheminimage">Prix de vente:</label>
				<input type="text" name="prix" id="prix" required>
			</div>
			<input class="button-a" type="submit" value="Valider"/>
		</form>
	</div>
	<%@include file="../../templates/scripts.jsp" %>
</body>
</html>