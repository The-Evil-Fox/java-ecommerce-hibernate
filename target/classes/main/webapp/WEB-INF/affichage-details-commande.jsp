<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../templates/header.jsp" %>
	<title>Mes commandes</title>
</head>
<body>
	<%@include file="../templates/navbarAuthentified.jsp" %>
	<!-- Conteneur affichage produit -->
	<div id="produits-liste">
		<h1 id="commande-details-title">Détails de votre commande:</h1>
		<!-- Création d'une variable compteur -->
		<c:set var="compteur" value="${1}" scope="page"/>
		<c:forEach items="${ produits }" var="produit" varStatus="count">
			<!-- Création d'une div qui contiendra maximum 3 produits alignés sur la page -->
			<c:if test="${compteur == 1 }"  var="result" scope="page">
				<div class="ligne-articles">
			</c:if>
			<!-- Container qui contient un unique produit -->
			<div class="produit-container">
				<img class="image-produit" src="<c:out value="${ produit.getCheminimage() }" />" alt="produit-image"/>
				<div class="produit-informations">
					<span class="libelle-produit"><c:out value="${ produit.getLibelle() }" /></span>
					<span class="prix-produit"><%-- <fmt:formatNumber var="prixarticle" type="number" minFractionDigits="2" value="${produit.getPrix()}"/> --%><c:out value="Prix unitaire: ${produit.prix} euros"/></span>
						<span>Exemplaire(s) commandé(s): <c:out value="${articles[count.index].quantite}" /></span>
						<span>Prix total: <c:out value="${articles[count.index].prixtotal}" /> euros</span>
				</div>
			</div>
			<!-- Incrémentation du compteur après l'insertion d'un produit -->
			<c:set var="compteur" value="${compteur + 1}" scope="page"/>
			<!-- Fermeture de la div d'alignement si le compteur arrive à 4-->
			<c:if test="${compteur == 4 }"  var="result" scope="page">
				</div>
				<!-- Réinitialisation du compteur à 1 -->
				<c:set var="compteur" value="${1}" scope="page"/>
			</c:if>
		</c:forEach>
	</div>
	<%@include file="../templates/scripts.jsp" %>
</body>
</html>