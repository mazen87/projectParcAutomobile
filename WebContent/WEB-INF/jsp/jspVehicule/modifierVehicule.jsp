<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Modifier un Vehicule</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/projet.css" />
</head>
<body><!-- les ids des balises de cette jsp et la jsp ajouter un véhicule sont identique  -->
<div id="header">
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
</div >
<h3 id="titleAjouter">MODIFIER UN VEHICULE </h3>
<div class="container-sm">
<!-- afficher les messages pour l'utilisateur  -->
<c:if test="${!empty selectListeCampusException }" ><!-- message concernant l'affichage de la liste des campus  -->
<p class="text-danger">${requestScope.selectListeCampusException }</p>
</c:if>
<c:if test="${!empty fmatDateException }" ><!-- message concernant le format de date utilisé   -->
<p class="text-danger">${requestScope.fmatDateException }</p>
</c:if>
<c:if test="${!empty remplirDonneesVehiculeException }" ><!-- message concernant le remplissage les zones de saisies du véhicule   -->
<p class="text-danger">${requestScope.remplirDonneesVehiculeException }</p>
</c:if>
<c:if test="${!empty modifierVehiculeException }" ><!-- message concernant l'opération de modification d'un véhicule   -->
<p class="text-danger">${requestScope.modifierVehiculeException }</p>
</c:if>
<c:if test="${empty modifierVehiculeException}" ><!-- message réussite de l'opération de modification d'un véhicule    -->
<p class="text-success">${requestScope.messageReussiteModifier }</p>
</c:if>
<div class="shadow-lg p-3 mb-5 bg-white rounded">
<!-- formulaire modifier un véhicule -->
<form action="<%=request.getContextPath()%>/ServletModifierUnVehicule" method="POST">
  <div class="form-group">
    <label for="exampleInputEmail1">Désignation * : </label>
      <input type="text" class="form-control" name="designation" value="${ vehicule.designation}"  placeHolder="Entrez la Désignation ...." required>
    <!--  <label for="">${ vehicule.designation} </label>-->
    </div>
    <div class="form-group">
    <label for="exampleInputEmail1">Immatriculation * : </label>
      <input type="text" class="form-control" name="immatriculation"  value="${vehicule.immatriculation }"placeHolder="LL-NNN-LL ...." required>
    <!--  <label for=""> ${vehicule.immatriculation }</label>-->
    </div>
     <div class="form-group">
    <label for="exampleInputEmail1">Date D'Achat * : </label>
    <input type="date" class="form-control" name="dateAchat"  value="${vehicule.dateAchat}" placeHolder="LL-NNN-LL ...." required>
    </div>
    <label for="exampleInputEmail1">Campus * : </label>
    <select class="form-control" name="site">
    <c:forEach items="${requestScope.listeCampus }" var="campus1">
    <c:choose>
    <c:when test="${vehicule.campus.idCampus == campus1.idCampus }">
     <option value ="${campus1.idCampus }" selected >${campus1.libelle }</option>
    </c:when>
    <c:otherwise>
    <option value ="${campus1.idCampus }"  >${campus1.libelle }</option>
    </c:otherwise>
    </c:choose>
   
    </c:forEach  >
    </select><br>
    <c:choose>
    <c:when test="${!empty  modifierVehiculeException}">
      <a class="btn btn-primary" href="<%=request.getContextPath()%>/ServletGestionDesVehicules" role="button" >Retour</a>
    </c:when>
    <c:otherwise>
     <button type="submit" class="btn btn-primary">Modifier</button>
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