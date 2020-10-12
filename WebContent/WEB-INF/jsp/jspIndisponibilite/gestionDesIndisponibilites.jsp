<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Gestion Des  Indisponibilites</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/projet.css" />
</head>
<body><!-- les ids pour tous les balise sont les même que la jsp gestion des Véhicules , l'affichage sera identique -->
<div id="header">
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
</div >
<div id="gestionCampus">
<h3 > GESTION DES INDISPONIBILITES</h3>
</div>
<!-- afficher les messages d'erreur si existent -->
<div class="container-xl">

<c:if test="${!empty selectToutesLesIndisponibilitesException }" ><!-- message erreur concernant lister des indisponibilités  -->
<p class="text-danger">${requestScope.selectToutesLesIndisponibilitesException }</p>
</c:if>
<!-- afficher les message pour les utilisateur  -->
<c:if test="${! empty supprimerIndisponibiliteException }"><!-- message erreur concernant la suppression d'une indisponibilité -->
<p class="text-danger">${requestScope.supprimerIndisponibiliteException }</p>
</c:if>
<c:if test="${ empty  supprimerIndisponibiliteException }">
<p class="text-success">${requestScope.messageReussiteSupprimerIndisponibilite }</p><!-- message réussite concernant la suppression d'une indisponibilité -->
</c:if>
<div class="d-flex justify-content-between">
<div id="listeCampus">
<h5  >Liste Des Indisponibilités</h3>
</div>
<div>
<a  id="lienAjouterCampus"  href="<%=request.getContextPath() %>/ServletGestionDesVehicules" class="badge badge-success">Gestion Des Vehicules</a>
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
      <th scope="col"  class="align-middle">Campus</th>
      <th scope="col"  class="align-middle">Date Début</th>
      <th scope="col"  class="align-middle">Date Fin</th>
      <th scope="col"  class="align-middle">Motif</th>
      <th scope="col"></th>
      <th scope="col"></th>
      <th scope="col"></th>
    </tr>
  </thead>
<!-- les indisponibilités -->

<c:forEach items="${requestScope.listeIndisponibilites }" var="indisponibilite" varStatus="status">
<tbody>
    <tr>
      <th scope="row">${status.count}</th>
      <td>${indisponibilite.vehicule.designation }</td>
      <td>${indisponibilite.vehicule.immatriculation }</td>
      <td>${indisponibilite.vehicule.campus.libelle }</td>
      <td><fmt:formatDate value="${indisponibilite.dateDebut }" pattern="dd/MM/yyyy"/></td>
      <td><fmt:formatDate value="${indisponibilite.dateFin }" pattern="dd/MM/yyyy"/></td> 
      <td>${indisponibilite.motifIndisponibilite }</td>
      <td><a class="badge badge-dark" href="<%=request.getContextPath()%>/ServletModifierUneIndisponibilite?id=${indisponibilite.idIndis}">Modifier</a></td>
      <!--  <td><a class="badge badge-danger"  href="<%=request.getContextPath()%>/ServletSupprimerUneIndisponibilite?id=${indisponibilite.idIndis}">Supprimer </a></td>-->
      <td><a class="badge badge-danger" href="#" data-toggle="modal" data-target="#exampleModal"  data-whatever="${indisponibilite.idIndis}">Supprimer </a></td>
      
    
    <!-- Modal -->
 <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Suppression Indisponibilité</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>Confirmez vous la suppression de cette Indisponibilité ?!.... </p>
      </div>
      <div class="modal-footer">
      <form action="<%=request.getContextPath()%>/ServletSupprimerUneIndisponibilite" method="POST">
        <input type="hidden" class="form-control" id="recipient-name" name="id">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
        <button type="submit" class="btn btn-primary">Supprimer </button>
        </form>  
      </div>
    </div>
  </div>
</div>
 
    </tr>
      </tbody>
</c:forEach>
</table>
</div>
</div>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<div id="separateurGestion">
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