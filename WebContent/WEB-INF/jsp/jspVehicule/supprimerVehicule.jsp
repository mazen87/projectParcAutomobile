<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Supprimer un Vehicule</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/projet.css" />
</head>
<body>
<div id="header">
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
</div >
 <!-- les id de les balise sont les même pour tous les Id des formulaire ajouter ,modifier et supprimer , par ce que sont identique au niveau de désign -->
<h3 id="titleAjouter"> SUPPRIMER UN VEHICULE </h3> <br>
<h3 class="text-danger" id="messageAttentionSupprimerCampus"> Attention!! Est-ce que vous êtes sûr de supprimer Définitvement le Vehicule que vous avez Chiosi ?!!...</h3>


<div class="container-sm">
<!-- afficher les message pour les utilisateur  -->
<c:if test="${! empty supprimerVehiculeException }"><!-- message erreur concernant la suppression d'un véhicule -->
<p class="text-danger">${requestScope.supprimerVehiculeException }</p>
</c:if>
<c:if test="${ empty  supprimerVehiculeException }">
<p class="text-success">${requestScope.messageReussiteSuppressionVehicule }</p><!-- message réussite concernant la suppression d'un véhicule -->
</c:if>
<div class="shadow-lg p-3 mb-5 bg-white rounded">
<form  id="formAjouter" action="<%=request.getContextPath()%>/ServletSupprimerUnVehicule" method="POST">
    <div class="form-group">
    <div id="boutonsAjouterCampus">
    <button type="submit" class="btn btn-primary">Supprimer</button>
    <a class="btn btn-primary" href="<%=request.getContextPath()%>/ServletGestionDesVehicules" role="button" >Retour</a>
    </div>
    </div>
    </form>
    </div>
    </div>
    <!-- le css pour cette balise est différente que dans les autres formulaire pour bien adapter la page  -->
    <div id="separateurFormSupprimer">
    
    </div>
 
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<div id="footer">
<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</div>


</body>
</html>