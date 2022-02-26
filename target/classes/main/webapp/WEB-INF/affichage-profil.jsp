<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../templates/header.jsp" %>
	<title>Profil</title>
</head>
<body>
	<%@include file="../templates/navbarAuthentified.jsp" %>
	<div id="container-profil">
		<h2>Mon profil <c:choose><c:when test="${user.getPrivileges() == 0 }">client</c:when><c:otherwise>administrateur</c:otherwise></c:choose>:</h2>
		<div id="profil-infos-container">
			<h3>Nom: <c:out value="${user.getNom() }"/> <button class="button-a" onclick="showForm('modif-nom')">Modifier</button></h3>
			<form id="modif-nom" action="ModifierNomUtilisateur" method="POST">
				<div class="input-group">
					<label for="nom">Nouveau nom:</label>
					<input type="text" name="nom" id="nom" required>
				</div>
				<input class="button-a" type="submit" value="Confirmer"/>
			</form>
			<h3>Prenom: <c:out value="${user.getPrenom() }"/> <button class="button-a" onclick="showForm('modif-prenom')">Modifier</button></h3>
			<form id="modif-prenom" action="ModifierPrenomUtilisateur" method="POST">
				<div class="input-group">
					<label for="prenom">Nouveau prénom:</label>
					<input type="text" name="prenom" id="prenom" required>
				</div>
				<input class="button-a" type="submit" value="Confirmer"/>
			</form>
			<h3>Adresse e-mail: <c:out value="${user.getEmail() }"/> <button class="button-a" onclick="showForm('modif-email')">Modifier</button></h3>
			<form id="modif-email" action="ModifierEmailUtilisateur" method="POST">
				<div class="input-group">
					<label for="email">Nouvelle adresse e-mail:</label>
					<input type="text" name="email" id="email" required>
				</div>
				<input class="button-a" type="submit" value="Confirmer"/>
			</form>
			<c:choose>
				<c:when test="${user.getAdresses().isEmpty()}">
					<h3 class="error-message">Vous n'avez pas encore ajouté d'adresse de livraison !</h3>
				</c:when>
				<c:otherwise>
					<c:forEach items="${user.getAdresses()}" var="adresse" varStatus="status">
						<c:set var="found" value="${false}" scope="session"/>
						<c:if test="${adresse.isAdresselivraison() == true}">
							<h3>Adresse de livraison: 
								<c:out value="${adresse.getRue()}" /> <c:out value="${adresse.getCodePostal()}" /> <c:out value="${adresse.getVille()}" />
							</h3>
							<c:set var="found" value="${true}"/>
						</c:if>
					</c:forEach>
					<c:if test="${found == false }">
						<h3 class="error-message">Vous n'avez pas encore défini d'adresse de livraison !</h3>
					</c:if>
				</c:otherwise>
			</c:choose>
			<button class="button-a button-adresse" onclick="showForm('ajout-adresse')">Ajouter une adresse de livraison</button>
			<form id="ajout-adresse" action="AjoutAdresse" method="POST">
				<div class="input-group">
					<label for="rue">Rue:</label>
					<input type="text" name="rue" id="rue" required>
				</div>
				<div class="input-group">
					<label for="cp">Code postal:</label>
					<input type="text" name="cp" id="cp" required>
				</div>
				<div class="input-group">
					<label for="ville">Ville:</label>
					<input type="text" name="ville" id="ville" required>
				</div>
				<div class="input-group-checkbox">
					<label for="livraisonpardefaut">Définir comme adresse de livraison par défaut:</label>
					<input type="checkbox" value="true" name="livraisonpardefaut" id="livraisonpardefaut">
				</div>
				<div class="input-group">
					<input class="button-a" type="submit" value="Confirmer"/>
				</div>
			</form>
			<c:if test="${!user.getAdresses().isEmpty() && user.getAdresses().size() >= 1}">
				<button class="button-a button-adresse" onclick="showForm('modif-adresselivraison')">Définir une adresse de livraison</button>
				<form id="modif-adresselivraison" action="ModifierAdresse" method="POST">
					<input type="hidden" name="action" id="action" value="setAdresseLivraison">
					<div class="input-group">
						<label for="rue">Adresse:</label>
						<select id="adresse" name="adresse">
							<option value="" selected></option>
							<c:forEach  items="${user.getAdresses()}" var="adresse" varStatus="status">
									<c:if test="${adresse.isAdresselivraison() == false}">
										<option value="<c:out value="${adresse.getRue()}" />"><c:out value="${adresse.getRue()}" /> <c:out value="${adresse.getCodePostal()}" /> <c:out value="${adresse.getVille()}" /></option>
										</c:if>
							</c:forEach>
						</select>
					</div>
					<input class="button-a" type="submit" value="Confirmer"/>
				</form>
				<button class="button-a button-adresse" onclick="showForm('supp-adresselivraison')">Supprimer une adresse de livraison</button>
				<form id="supp-adresselivraison" action="ModifierAdresse" method="POST">
					<input type="hidden" name="action" id="action" value="delete">
					<div class="input-group">
						<label for="rue">Adresse:</label>
						<select id="adresse" name="adresse">
							<option value="" selected></option>
							<c:forEach  items="${user.getAdresses()}" var="adresse" varStatus="status">
									<option value="<c:out value="${adresse.getRue()}" />"><c:out value="${adresse.getRue()}" /> <c:out value="${adresse.getCodePostal()}" /> <c:out value="${adresse.getVille()}" /></option>
							</c:forEach>
						</select>
					</div>
					<input class="button-a" type="submit" value="Confirmer"/>
				</form>
			</c:if>
		</div>
	</div>
	<script type="text/javascript">

		var previous = null;

		function showForm(formTarget) {

			var formulaire = document.getElementById(formTarget).style;


			if(formulaire.display == "block") {

				formulaire.display = "none";

			} else {

				formulaire.display = "block";

			}

			if(previous != null) {

				if(previous != formTarget) {

					if(document.getElementById(previous).style.display == "block") {
	
						document.getElementById(previous).style.display = "none";		
									
					}

				}

			}

			previous = formTarget;

		}
	
	</script>
	<%@include file="../templates/scripts.jsp" %>
</body>
</html>