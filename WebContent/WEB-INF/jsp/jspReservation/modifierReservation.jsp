<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
             <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Modifier une Réservation</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/projet.css" />
</head>
<body>
<div id="header">
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
</div >
<h3 id="titleAjouter">MODIFIER UNE RESERVATION </h3>
<div class="container-sm">
<!-- afficher les messages pour l'utilisateur  -->
<c:if test="${!empty selectToutesLesPersonnesException }" ><!-- message concernant l'affichage de la liste des personnes  -->
<p class="text-danger">${requestScope.selectToutesLesPersonnesException }</p>
</c:if>

<c:if test="${!empty selectToutesLesVehiculeException }" ><!-- message concernant l'affichage de la liste des véhicules   -->
<p class="text-danger">${requestScope.selectToutesLesVehiculeException }</p>
</c:if>
<c:if test="${!empty selectToutesLesDestinationException }" ><!-- message concernant l'affichage de la liste des destinations   -->
<p class="text-danger">${requestScope.selectToutesLesDestinationException }</p>
</c:if>
<c:if test="${!empty fmatDateException }" ><!-- message concernant le format de date utilisé   -->
<p class="text-danger">${requestScope.fmatDateException }</p>
</c:if>
<c:if test="${!empty selectReservationByIdException }" ><!-- message concernant la récupération de la Réservation à modifier    -->
<p class="text-danger">${requestScope.selectReservationByIdException }</p>
</c:if>
<c:if test="${!empty modifierReservationException}" ><!-- message erreur  concernant  la modification d'une Réservation    -->
<p class="text-danger">${requestScope.modifierReservationException }</p>
</c:if>
<c:if test="${empty modifierReservationException}" ><!-- message réussite  concernant  li modification d'une Réservation    -->
<p class="text-success">${requestScope.messageReussiteModifierReservation }</p>
</c:if>
<div class="shadow-lg p-3 mb-5 bg-white rounded">
<!-- formulaire ajoter une personnes -->
<form action="<%=request.getContextPath()%>/ServletModifierUneReservation" method="POST">
  <div class="form-group">
    <label for="exampleInputEmail1">Motif * : </label>
    <input type="text" class="form-control" name="motif"   value="${reservation.motif }" placeHolder="Entrez le Motif ...." required>
    </div>
    <div class="form-group">
    <label for="exampleInputEmail1">Date Début * : </label>
    <input type="datetime-local" class="form-control" name="dateRes"   value="${reservation.dateHeureRes1 }" required>
    </div>
    <div class="form-group">
    <label for="exampleInputEmail1">Date Fin * : </label>
     <input type="datetime-local" class="form-control" name="dateFin" value="${reservation.dateHeureFin1 }"  required>
    </div>
    <label> Destination *:</label>
    <select class="form-control" name="destination">
    <c:forEach items="${requestScope.listeDestination }" var="destination">
    <c:choose>
    <c:when test="${destination.idDes == reservation.destination.idDes }">
    <option value ="${destination.idDes }" selected>${destination.libelle } - ${destination.codeDes }</option>
    </c:when>
    <c:otherwise>
     <option value ="${destination.idDes }">${destination.libelle } - ${destination.codeDes }</option>
    </c:otherwise>
    </c:choose>
    </c:forEach  >
    </select><br>
    <label> Personne *:</label>
    <select class="form-control" name="personne">
    <c:forEach items="${requestScope.listePersonnes }" var="personne">
    <c:choose>
    <c:when test="${personne.idPerso == reservation.personne.idPerso }">
        <option value ="${personne.idPerso }" selected>${personne.nom } ${personne.prenom }</option>
    </c:when>
    <c:otherwise>
        <option value ="${personne.idPerso }">${personne.nom } ${personne.prenom }</option>
    </c:otherwise>
    </c:choose>
    </c:forEach  >
    </select><br>
    <label> Véhicule *:</label>
     <select class="form-control" name="vehicule">
    <c:forEach items="${requestScope.listeVehicules }" var="vehicule">
    <c:choose>
    <c:when test="${vehicule.idVehic == reservation.vehicule.idVehic}">
    <c:choose>
    <c:when test="${vehicule.idVehic == 1028 }">
    <option value ="${vehicule.idVehic }" selected>${vehicule.designation } </option>
    </c:when>
    <c:otherwise>
    <option value ="${vehicule.idVehic }" selected>${vehicule.designation }-${vehicule.campus.libelle }</option>
    </c:otherwise>
    </c:choose>
    </c:when>
    <c:otherwise>
    <c:choose>
    <c:when test="${vehicule.idVehic == 1028 }">
    <option value ="${vehicule.idVehic }" >${vehicule.designation } </option>
    </c:when>
    <c:otherwise>
    <option value ="${vehicule.idVehic }" >${vehicule.designation }-${vehicule.campus.libelle }</option>
    </c:otherwise>
    </c:choose>
    </c:otherwise>
    </c:choose>
    </c:forEach  >
    </select><br>
    <div>
    <c:choose>
    <c:when test="${!empty modifierReservationException }">
         <a class="btn btn-primary" href="<%=request.getContextPath()%>/ServletGestionDesReservationsAccueil" role="button" >Retour</a>
    </c:when>
    <c:otherwise>
     <button type="submit" class="btn btn-primary">Modifier</button>
     <a class="btn btn-primary" href="<%=request.getContextPath()%>/ServletGestionDesReservationsAccueil" role="button" >Retour</a>
    </c:otherwise>
    </c:choose>
    </div>  
</form>
</div>
</div>
  
 <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script> 
 <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
 <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
 
 
 <div id="separateurFormAjouter">
    
    </div>
<div id="footer">
<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</div>
</body>
</html>