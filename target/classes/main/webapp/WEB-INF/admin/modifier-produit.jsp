<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ page import="Model.Produit" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../../templates/header.jsp" %>
	<title>Modification du produit</title>
</head>
<body>
	<c:set var="produit" value="${produitToUpdate}"/>
	<%@include file="../../templates/navbarAuthentified.jsp" %>
	<div id="container-modification-produit">
		<h1>Modification du produit</h1>
		<form method="post" action="ModificationProduit" id="modificationProduit">
			<input type="hidden" id="id" name="id" value="${produit.getIdentifiant() }">
			<div class="input-group">
				<label for="libelle">Libelle du produit:</label>
				<input type="text" name="libelle" id="libelle" value="${ produit.getLibelle() }" required>
			</div>
			<div class="input-group">
				<label for="cheminimage">Chemin de l'image:</label>
				<input type="text" name="cheminimage" id="cheminimage" value="${ produit.getCheminimage() }" required>
			</div>
			<div class="input-group">
				<label for="cheminimage">Prix de vente:</label>
				<input type="text" name="prix" id="prix" value="${ produit.getPrix() }" required>
			</div>
			<input class="button-a validationformulaire" type="submit" value="Valider"/>
		</form>
	</div>
	<%@include file="../../templates/scripts.jsp" %>
</body>
</html>