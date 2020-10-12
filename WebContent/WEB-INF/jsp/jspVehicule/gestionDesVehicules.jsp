<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Gestion Des Vehicule</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/projet.css" />
</head>
<body><!-- les ids pour tous les balise sont les même que la jsp gestion des personnes , l'affichage sera identique -->
<div id="header">
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
</div >
<div id="gestionCampus">
<h3 > GESTION DES VEHICULES</h3>
</div>
<!-- afficher les messages d'erreur si existent -->
<div class="container-lg">
<c:if test="${!empty selectTousLesVehiculeException }" ><!-- message erreur concernant lister des véhicules  -->
<p class="text-danger">${requestScope.selectTousLesVehiculeException }</p>
</c:if>
<c:if test="${!empty supprimerVehiculeException }" ><!-- message erreur concernant la suppression d'un véhicule  -->
<p class="text-danger">${requestScope.supprimerVehiculeException }</p>
</c:if>
<c:if test="${empty supprimerVehiculeException }" ><!-- message réussite concernant la suppression d'un véhicule  -->
<p class="text-success">${requestScope.messageReussiteSuppressionVehicule }</p>
</c:if>

<div class="d-flex justify-content-between">
<div id="listeCampus">
<h5  >Liste Des Véhicules</h3>
</div>
<div>
<a id="lienAjouterCampus" href="<%=request.getContextPath() %>/ServletGestionDesIndisponibilites" class="badge badge-success">Gestion Des Indisponibilités</a>
</div>
<div>
<a  id="lienAjouterCampus" class="badge badge-primary"  href="<%=request.getContextPath()%>/ServletAjouterUnVehicule">Ajouter un Véhicule</a>
</div>
</div>
<!-- afficher les véhicules -->
<div class="border" >
<div class="shadow-lg p-3 mb-5 bg-white rounded" >
<div class="overflow-auto" id ="scrolable">
<table class="table" id="table">
<thead class="thead-dark">
    <tr>
    <!-- l'en tête de tableau  -->
      <th scope="col"></th>
      <th scope="col"  class="align-middle">Désignation</th>
      <th scope="col"  class="align-middle">Immatriculation</th>
      <th scope="col"  class="align-middle">Date D'Achat</th>
      <th scope="col"  class="align-middle">Campus</th>
      <th scope="col"></th>
      <th scope="col"></th>
      <th scope="col"></th>
    </tr>
  </thead>
<!-- les véhicules -->

<c:forEach items="${requestScope.listeVehicules }" var="vehicule" varStatus="status">
<tbody >
<c:choose>
<c:when test="${vehicule.idVehic == 1028  }">

</c:when>
<c:otherwise>
 <tr >
      <th scope="row">${status.count}</th>
      <td>${vehicule.designation }</td>
      <td>${vehicule.immatriculation }</td>
      <td><fmt:formatDate value="${vehicule.dateAchat }" pattern="dd/MM/yyyy"/></td>
       <td>${vehicule.campus.libelle }</td>
      <td><a class="badge badge-dark" href="<%=request.getContextPath()%>/ServletModifierUnVehicule?id=${vehicule.idVehic}">Modifier</a></td>
      <td><a class="badge badge-warning" href="<%=request.getContextPath()%>/ServletImmobiliserUnVehicule?id=${vehicule.idVehic}">Immobiliser</a></td>
      <!--  <td><a class="badge badge-danger"  href="<%=request.getContextPath()%>/ServletSupprimerUnVehicule?id=${vehicule.idVehic}">Supprimer </a></td>-->
      <td><a class="badge badge-danger" href="#" data-toggle="modal" data-target="#exampleModal"  data-whatever="${vehicule.idVehic}">Supprimer </a></td>
      
   </tr>
</c:otherwise>
</c:choose>
  <!--    <tr >
      <th scope="row">${status.count}</th>
      <td>${vehicule.designation }</td>
      <td>${vehicule.immatriculation }</td>
      <td><fmt:formatDate value="${vehicule.dateAchat }" pattern="dd/MM/yyyy"/></td>
       <td>${vehicule.campus.libelle }</td>
      <td><a class="badge badge-dark" href="<%=request.getContextPath()%>/ServletModifierUnVehicule?id=${vehicule.idVehic}">Modifier</a></td>
      <td><a class="badge badge-warning" href="<%=request.getContextPath()%>/ServletImmobiliserUnVehicule?id=${vehicule.idVehic}">Immobiliser</a></td>
      <td><a class="badge badge-danger"  href="<%=request.getContextPath()%>/ServletSupprimerUnVehicule?id=${vehicule.idVehic}">Supprimer </a></td>
    -->
    <!-- Modal -->
 <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Suppression Véhicule</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>Confirmez vous la suppression de ce véhicule ?!.... </p>
      </div>
      <div class="modal-footer"> 
      <form action="<%=request.getContextPath()%>/ServletSupprimerUnVehicule"  method="POST">
        <input type="hidden" class="form-control" id="recipient-name" name="id">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
        <button type="submit" class="btn btn-primary">Supprimer </button>
        </form>  
      </div>
    </div>
  </div>
</div> 
   <!--   </tr>-->
      </tbody>
</c:forEach>
</div>
</table>
</div>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<div id="separateurGestion">
</div>
</div>
<div id="footer">
<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</div>

<script>
$('#exampleModal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget) // Button that triggered the modal
	  var recipient = button.data('whatever') // Extract info from data-* attributes
	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	  var modal = $(this)
	  modal.find('.modal-footer input').val(recipient)
	})
</script>
</body>
</html>