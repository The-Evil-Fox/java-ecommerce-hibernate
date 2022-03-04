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
				<label for="categorie">Categorie:</label>
				<select name="categorie" id="categorie" required>
					<option value=""></option>
					<c:forEach items="${categories}" var="categorie" varStatus="status">
						<option value="<c:out value="${categorie.getIdentifiant()}" />"><c:out value="${categorie.getCategorie()}" /></option>
					</c:forEach>
				</select>
			</div>
			<div class="input-group">
				<label for="prix">Prix de vente unitaire:</label>
				<input type="text" name="prix" id="prix" required>
			</div>
			<div class="input-group">
				<label for="quantite">Quantite en stock:</label>
				<input type="text" name="quantite" id="quantite" required>
			</div>
			<div class="input-group-checkbox">
				<label for="miseenvente">Mettre en vente</label>
				<input type="checkbox" value="true" name="miseenvente" id="miseenvente">
			</div>
			<input class="button-a" type="submit" value="Valider"/>
		</form>
	</div>
	<%@include file="../../templates/scripts.jsp" %>
</body>
</html>