<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Immobiliser Un Véhicule</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/projet.css" />
</head>
<body>
<div id="header">
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
</div >
<h3 id="titleAjouter">IMMOBILISER UN VEHICULE </h3>
<div class="container-sm">
<!-- afficher les messages pour l'utilisateur  -->
<c:if test="${!empty selectVehiculeByIdException }" ><!-- message concernant l'affichage du véhicule à immobiliser   -->
<p class="text-danger">${requestScope.selectVehiculeByIdException }</p>
</c:if>
<c:if test="${!empty immobiliserVehiculeException }" ><!-- message concernant l'opération d'ajout d'un véhicule   -->
<p class="text-danger">${requestScope.immobiliserVehiculeException }</p>
</c:if>
<c:if test="${!empty fmatDateException }" ><!-- message concernant le format de date utilisé   -->
<p class="text-danger">${requestScope.fmatDateException }</p>
</c:if>
<c:if test="${empty immobiliserVehiculeException}" ><!-- message réussite de l'opération d'ajout d'une personne   -->
<p class="text-success">${requestScope.messageReussiteImmobiliserVehicule }</p>
</c:if>
<div class="shadow-lg p-3 mb-5 bg-white rounded">
<!-- formulaire ajoter une personnes -->
<form action="<%=request.getContextPath()%>/ServletImmobiliserUnVehicule" method="POST">
  <div class="form-group">
    <label for="exampleInputEmail1">Désignation  : </label><h5 >${vehicule.designation } </h5>
    </div>
    <div class="form-group">
    <label for="exampleInputEmail1">Immatriculation  : </label><h5>${vehicule.immatriculation } </h5>
    
    </div>
     <div class="form-group">
    <label for="exampleInputEmail1">Date Début * : </label>
    <input type="date" class="form-control" name="dateDebut"  placeholder="yyyy-MM-dd  faites-vous bien attention au format de date!!" required>
    </div>
    <div class="form-group">
    <label for="exampleInputEmail1">Date Fin * : </label>
    <input type="date" class="form-control" name="dateFin"  placeholder="yyyy-MM-dd faites-vous bien attention au format de date!!" required>
    </div>
   <div class="form-group">
    <label for="exampleInputEmail1">Motif * : </label>
    <input type="text" class="form-control" name="motif"  placeHolder="Exprimez ici le motif de l'indisponibilité ... " required>
    </div>
    <c:choose>
    <c:when test="${!empty immobiliserVehiculeException }">
    <a class="btn btn-primary" href="<%=request.getContextPath()%>/ServletGestionDesVehicules" role="button" >Retour</a>
    </c:when>
    <c:otherwise>
     <button type="submit" class="btn btn-primary">Immobiliser</button>
  <a class="btn btn-primary" href="<%=request.getContextPath()%>/ServletGestionDesVehicules" role="button" >Retour</a>
    </c:otherwise>
    </c:choose>
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