<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
          <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Gestion Des Campus</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/projet.css" />
</head>
<body >
<div id="header">
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
</div >
<div id="gestionCampus">
<h3 > GESTION DES CAMPUS</h3>
</div>
<!-- afficher les messages d'erreur si existent -->
<div class="container-sm">

<c:if test="${!empty exception1 }" ><!-- message erreur concernant lister des campus  -->
<p class="text-danger">${requestScope.exception1 }</p>
</c:if>
<!-- afficher les message pour les utilisateur  -->
<c:if test="${! empty exceptionSupprimer }"><!-- message erreur concernant la suppression d'un campus -->
<p class="text-danger">${requestScope.exceptionSupprimer }</p>
</c:if>
<c:if test="${ empty  exceptionSupprimer }">
<p class="text-success">${requestScope.msgReussoteSuppression }</p><!-- message réussite concernant la suppression d'un campus -->
</c:if>
<div class="d-flex justify-content-between">
<div id="listeCampus">
<h5  >Liste Des Campus</h3>
</div>
<div>
<a  id="lienAjouterCampus" class="badge badge-primary"  href="<%=request.getContextPath()%>/ServletAjouterUnCampus">Ajouter Un Campus</a>
</div>
</div>
<!-- afficher les campus -->

<div class="border" >
<div class="shadow-lg p-3 mb-5 bg-white rounded" >
<div class="overflow-auto"  id ="scrolable">
<table class="table" id="table">

<thead class="thead-dark">
    <tr>
    <!-- l'en tête de tableau  -->
      <th scope="col"></th>
      <th scope="col"  class="align-middle">Campus</th>
      <th scope="col"></th>
      <th scope="col"></th>
    </tr>
  </thead>
<!-- les campus -->

<c:forEach items="${requestScope.listeCampus }" var="campus" varStatus="status">
<tbody>
    <tr>
      <th scope="row">${status.count}</th>
      <td>${campus.libelle }</td>
      <td><a class="badge badge-dark" href="<%=request.getContextPath()%>/ServletModifierUnCampus?libelle=${campus.libelle}">Modifier</a></td>
     <!-- <td><a class="badge badge-danger" href="<%=request.getContextPath()%>/ServletSupprimerUnCampus?id=${campus.idCampus}">Supprimer </a></td>-->
     <td><a class="badge badge-danger" href="#" data-toggle="modal" data-target="#exampleModal"  data-whatever="${campus.idCampus}">Supprimer </a></td>
    </tr>
    
     <!-- Modal -->
 <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Suppression Campus</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>Confirmez vous la suppression de ce Campus ?!.... </p>
      </div>
      <div class="modal-footer"> 
      <form action="<%=request.getContextPath()%>/ServletSupprimerUnCampus"  method="POST">
        <input type="hidden" class="form-control" id="recipient-name" name="id">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
        <button type="submit" class="btn btn-primary">Supprimer </button>
        </form>  
      </div>
    </div>
  </div>
</div> 
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
 <!--  <script type="text/javascript">
function confirmerSuppressionCampus()
{
	var resultat ; 
    resultat = confirm("ATTENTION !  Est-ce que vous êtes sûr de supprimer le Campus ! ....") ;
	if(resultat)
	{
		 window.location.href="http://localhost:8081/projectParcAutomobile/servletSupprimerUnCampus?id=${fbffbfb}";
	}
	}
</script>    --> 
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