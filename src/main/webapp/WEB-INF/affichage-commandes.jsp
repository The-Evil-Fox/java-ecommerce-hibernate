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
	<c:if test="${commandes.isEmpty() }">
		<div id="error-message-container">
			<h1>Vous n'avez pas encore finaliser de commande !</h1>
		</div>
	</c:if>
	<c:forEach items="${ commandes }" var="commande" varStatus="status">
		<div class="commande-container">
			<h1>Commande n°<c:out value="${status.count}"/></h1>
			<div class="infos-commande">
				<span>Prix total: ${commande.prixTotal} euros</span>
				<span>Nombre d'articles total: ${commande.quantite}</span>
				<c:url value="AfficherDetailsCommande" var="liendetailcommande">
					<c:param name="idcommande" value="${ commande.identifiant }"/>
				</c:url>
				<a class="button-a" href="${liendetailcommande}">Afficher le détail de la commande</a>
			</div>
		</div>
	</c:forEach>
	<%@include file="../templates/scripts.jsp" %>
</body>
</html>