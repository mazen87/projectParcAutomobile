<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajouter Une Réservation</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/projet.css" />
  <!--  
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-ui-timepicker-addon/1.6.3/jquery-ui-timepicker-addon.min.css" />
  -->
</head>
<body>
<div id="header">
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
</div >
<h3 id="titleAjouter">AJOUTER UNE RESERVATION </h3>
<div class="container-sm">
<!-- afficher les messages à l'utilisateur  -->
<c:if test="${!empty selectToutesLesPersonnesException }" ><!-- message concernant l'affichage de la liste des personnes  -->
<p class="text-danger">${requestScope.selectToutesLesPersonnesException }</p>
</c:if>
<c:if test="${!empty selectToutesLesVehiculeException }" ><!-- message concernant l'affichage de la liste des véhicules   -->
<p class="text-danger">${requestScope.selectToutesLesVehiculeException }</p>
</c:if>
<c:if test="${!empty selectToutesLesDestinationException }" ><!-- message concernant l'affichage de la liste des destinations   -->
<p class="text-danger">${requestScope.selectToutesLesDestinationException }</p>
</c:if>
<c:if test="${!empty fmatDateException }" ><!-- message concernant le format de la date utilisé   -->
<p class="text-danger">${requestScope.fmatDateException }</p>
</c:if>
<c:if test="${!empty ajouterReservationException}" ><!-- message d'erreur  concernant  l'ajout d'une Réservation    -->
<p class="text-danger">${requestScope.ajouterReservationException }</p>
</c:if>

<c:if test="${empty ajouterReservationException}" ><!-- message de réussite concernant  l'ajout d'une Réservation    -->
<p class="text-success">${requestScope.messageReussiteAjouterReservation }</p>
</c:if>
<div class="shadow-lg p-3 mb-5 bg-white rounded">
<!-- formulaire ajoter une Réservation -->
<form action="<%=request.getContextPath()%>/ServletAjouterUneReservation" method="POST">
  <div class="form-group">
    <label for="exampleInputEmail1">Motif * : </label>
    <input type="text" class="form-control" name="motif"  placeHolder="Entrez le Motif ...." required>
    </div>
    <div class="form-group">
    <label for="exampleInputEmail1">Date Début * : </label>
    <input type="datetime-local" class="form-control" name="dateRes" placeholder="yyyy-MM-ddTHH:mm  veuillez faire bien attention au format de date et gardez le T entre la date et le temps!!!!"  required>
   <!--   <label for="exampleInputEmail1">Heure-Début * : </label>
    <input type="time"  class="form-control" name="heureRes" required/>-->
    </div>
    <div class="form-group">
    <label for="exampleInputEmail1">Date Fin * : </label>
     <input type="datetime-local" class="form-control" name="dateFin" placeholder="yyyy-MM-ddTHH:mm  veuillez faire bien attention au format de date et gardez le T entre la date et le temps!!!!"  required>
    <!--   <label for="exampleInputEmail1">Heure-Fin * : </label>
    <input type="time"  class="form-control" name="heureFin" required/>-->
    </div>
    <label> Destination *:</label>
    <select class="form-control" name="destination">
    <option value="0">Choisissez une Destination  </option>
    <c:forEach items="${requestScope.listeDestination }" var="destination">
    <option value ="${destination.idDes }">${destination.libelle } - ${destination.codeDes }</option>
    </c:forEach  >
    </select><br>
    <label> Personne *:</label>
     <select class="form-control" name="personne">
    <option value="0">Choisissez une Personne  </option>
    <c:forEach items="${requestScope.listePersonnes }" var="personne">
    <option value ="${personne.idPerso }">${personne.nom } ${personne.prenom }</option>
    </c:forEach  >
    </select><br>
    <label> Véhicule *:</label>
     <select class="form-control" name="vehicule">
    <!--  <option value="0"> Autre  </option>--> 
    <c:forEach items="${requestScope.listeVehicules }" var="vehicule">
    <c:choose>
    <c:when test="${vehicule.idVehic == 1028}">
    <option value ="${vehicule.idVehic }" selected>${vehicule.designation } </option>
    </c:when>
    <c:otherwise>
        <option value ="${vehicule.idVehic }">${vehicule.designation }-${vehicule.campus.libelle }</option>
    </c:otherwise>
    </c:choose>
    </c:forEach  >
    </select><br>
    <div>
     <button type="submit" class="btn btn-primary">Réserver</button>
     <a class="btn btn-primary" href="<%=request.getContextPath()%>/ServletGestionDesReservationsAccueil" role="button" >Retour</a>
    </div>  
</form>
</div>
</div>
  
 <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script> 
 <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
 <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
 
 <!--  
 <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-ui-timepicker-addon/1.6.3/jquery-ui-timepicker-addon.min.js"></script>
    
    <script>
    jQuery(function($) {
        $("#datepicker").datetimepicker();
    });
  </script>
<script>
    jQuery(function($) {
        $("#datepicker1").datetimepicker();
    });
  </script>
 
 -->
 <div id="separateurFormAjouter">
    
    </div>
<div id="footer">
<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</div>
</body>
</html>