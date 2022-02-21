<div class="topnav" id="myTopnav">
   <a id="home-link" href="AfficherListe"><img id="logo-magasin" alt="logo-magasin" src="https://upload.wikimedia.org/wikipedia/fr/7/7a/Alien_Swarm_Logo.png"></a>
   <div class="dropdown">
     <button class="dropbtn">Compte
     </button>
     <div class="dropdown-content">
       <a href="AfficherProfil">Mon profil</a>
     </div>
   </div>
   <c:if test="${user.getPrivileges() == 1 }">
	   <div class="dropdown">
	     <button class="dropbtn">Administration
	     </button>
	     <div class="dropdown-content">
	       <a href="AjouterProduit">Ajout produit</a>
	     </div>
	   </div>
   </c:if>
   <a href="DeconnectUser">Déconnexion</a>
    <a href="AffichePanier" id="panier-navbar">
    	<img id="panier-logo" alt="panier-logo" title="afficher mon panier" src="https://www.innis-coiffure.com/images/pannier-grand.png">
	   	<c:choose>
			<c:when test="${listepanier.getQuantiteTotale() == 0 }"></c:when>
			<c:when test="${listepanier.getQuantiteTotale() == 1 }">
				<c:out value="(${listepanier.getQuantiteTotale() } article)" />
			</c:when>
			<c:when test="${listepanier.getQuantiteTotale() > 1 }">
				<c:out value="(${listepanier.getQuantiteTotale() } articles)" />
			</c:when>
		</c:choose>
	</a>
   <a href="javascript:void(0);" class="icon" onclick="myFunction()">&#9776;</a>
</div>