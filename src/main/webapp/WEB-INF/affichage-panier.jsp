<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ page import="Model.Produit" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../templates/header.jsp" %>
	<title>Mon panier</title>
</head>
<body>
	<%@include file="../templates/navbarAuthentified.jsp" %>
	<c:choose>
		<c:when test="${listepanier.getQuantiteTotale() == 0 }">
			<div id="error-message-container">
				<h1>Votre panier est vide !</h1>
				<a class="button-a" href="AfficherListe">Retourner sur le catalogue</a>
			</div>
		</c:when>
		<c:otherwise>
			<div id="panier-container">
				<div id="panier-liste">
					<c:forEach items="${ listepanier.getListe() }" var="panier">
						<div class="panier-produit-container">
							<img class="image-produit" src="<c:out value="${ panier.getProduit().getCheminimage() }" />" alt="produit-image"/>
							<div class="produit-informations">
								<span class="libelle-produit"><c:out value="${ panier.getProduit().getLibelle() }"/></span>
								<span class="prix-produit">Prix: <c:out value="${panier.getProduit().getPrix()} euros"/></span>
								<c:url value="ModifierQuantitePanier" var="incrementpanier">
									<c:param name="id" value="${ panier.getProduit().getIdentifiant() }"/>
									<c:param name="operation" value="increment"/>
								</c:url>
								<c:url value="ModifierQuantitePanier" var="decrementpanier">
									<c:param name="id" value="${ panier.getProduit().getIdentifiant() }"/>
									<c:param name="operation" value="decrement"/>
								</c:url>
								<span>Quantite: <a class="button-quantity" href="${decrementpanier}">-</a><c:out value="${ panier.getQuantite() }"/><a class="button-quantity" href="${incrementpanier}">+</a></span>
								<c:url value="SupprimerPanierFromPanierListe" var="supprimerdupanier">
									<c:param name="id" value="${ panier.getProduit().getIdentifiant() }"/>
								</c:url>
								<a class="button-a" href="${supprimerdupanier}">Supprimer du panier</a>
							</div>
						</div>
					</c:forEach>
				</div>
				<div id="total-panier">
					<div class="panier-details">
						<span>Total d'articles dans le panier: <c:out value="${ listepanier.getQuantiteTotale()}"/></span>
						<span>Total à payer: <c:out value="${listepanier.getMontantTotal()} euros"/></span>
					</div>
					<div id="buttons-container">
						<a class="button-a" href="">Finaliser la commande</a>
						<a class="button-a" href="ViderPanier">Vider le panier</a>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	<%@include file="../templates/scripts.jsp" %>
</body>
</html>