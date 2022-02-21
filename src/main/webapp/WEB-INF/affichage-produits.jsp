<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ page import="Model.Produit" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../templates/header.jsp" %>
	<title>Produits</title>
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
	<div id="produits-liste">
		<c:set var="compteur" value="${1}" scope="page"/>
		<c:forEach items="${ produits }" var="produit" varStatus="status">
			<c:if test="${compteur == 1 }"  var="result" scope="page">
				<div class="ligne-articles">
			</c:if>
			<div class="produit-container">
				<img class="image-produit" src="<c:out value="${ produit.getCheminimage() }" />" alt="produit-image"/>
				<div class="produit-informations">
					<span class="libelle-produit"><c:out value="${ produit.getLibelle() }" /></span>
					<span class="prix-produit"><%-- <fmt:formatNumber var="prixarticle" type="number" minFractionDigits="2" value="${produit.getPrix()}"/> --%><c:out value="Prix: ${produit.getPrix()} euros"/></span>
					<c:url value="AjoutPanier" var="lienAjoutPanier">
						<c:param name="id" value="${ produit.getIdentifiant() }"/>
					</c:url>
					<c:url value="SupprimerProduit" var="lienSuppressionProduit">
						<c:param name="id" value="${ produit.getIdentifiant() }"/>
					</c:url>
					<a class="button-a" href="${lienAjoutPanier}">Ajouter au panier</a>
					<c:if test="${user.getPrivileges() == 1 }">
						<a class="button-a" href="${lienSuppressionProduit}">Supprimer l'article</a>
					</c:if>
				</div>
			</div>
			<c:set var="compteur" value="${compteur + 1}" scope="page"/>
			<c:if test="${compteur == 4 }"  var="result" scope="page">
				</div>
				<c:set var="compteur" value="${1}" scope="page"/>
			</c:if>
		</c:forEach>
	</div>
	<%@include file="../templates/scripts.jsp" %>
</body>
</html>