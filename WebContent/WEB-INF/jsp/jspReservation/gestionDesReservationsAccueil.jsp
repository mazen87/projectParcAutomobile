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
<title>Accueil</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/projet.css" />

<!-- month year picker jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
 <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<script>
  $(function ()
  {
	$("#getdate").datepicker(
	{
	      
		 changeMonth :true ,
		  changeYear : true,
		  showButtonPanel: true,
		  onClose: function(dateText, inst) {
	            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
	            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
	            $(this).val($.datepicker.formatDate('yy-mm', new Date(year, month, 1)));
	        }
	}
	);
  });
</script>
 <style>
    .ui-datepicker-calendar {
        display: none;
        }
    </style>

</head>
<body>
<fmt:setLocale value="FR"/>

<div id="header">
<jsp:include page="/WEB-INF/jsp/headerConnecte.jsp"></jsp:include>
</div >

<div id="gestionReservation">
<h3 id="title" > ACCUEIL</h3>
</div>
<!-- afficher les messages d'erreur si existent -->
<div class="container-xxlg">

<c:if test="${!empty connexionReussite }" ><!-- message réussite concernant la connexion  -->
<p class="text-success">${requestScope.connexionReussite }</p>
</c:if>

<c:if test="${ ! empty selectReservationsParMoisActuelException}">
<p class="text-danger">${requestScope.selectReservationsParMoisActuelException }</p>
</c:if>

<c:if test="${ ! empty supprimerReservationException}">
<p class="text-danger">${requestScope.supprimerReservationException }</p>
</c:if>
<c:if test="${  empty supprimerReservationException}">
<p class="text-success">${requestScope.messageReussiteSupprimerReservation }</p>
</c:if>
<div class="d-flex justify-content-between" style="margin-left:20px;margin-right:20px;" > 	
<div id="listeCampus">
<h5 >Liste Des Réservations : <span class="text-danger"><fmt:formatDate  value="${dateDeJour }" pattern=" MMMM-Y"/></span></h5>
</div>
<div>
<a  id="lienAjouterCampus" class="badge badge-primary"  href="<%=request.getContextPath()%>/ServletAjouterUneReservation">Ajouter Une Réservation</a>
</div>
</div>
<!-- afficher les réservation -->

<div class="border"  style="margin-left:20px;margin-right:20px;">
<div class="shadow-lg p-3 mb-5 bg-white rounded" >
<div class="overflow-auto" id ="scrolable" >
<table class="table" id="table">
<thead class="thead-dark">
    <tr>
    <!-- l'en tête de tableau  -->
      <th scope="col"></th>
      <th scope="col"  class="align-middle">Véhicule</th>
      <th scope="col"  class="align-middle">Immatriculation</th>
      <th scope="col"  class="align-middle">Date-Heure Réservation</th>
      <th scope="col"  class="align-middle">Date-Heure Fin</th>
      <th scope="col"  class="align-middle">Utilisateur</th>
      <th scope="col"  class="align-middle">Destination</th>
      <th scope="col"  class="align-middle">Motif</th>
      <th scope="col"></th>
      <th scope="col"></th>
    </tr>
  </thead>
<!-- les campus -->

<c:forEach items="${requestScope.listeReservations }" var="reservation" varStatus="status">
<tbody>
    <tr>
      <th scope="row">${status.count}</th>
      <td>${reservation.vehicule.designation }</td>
      <c:choose>
      <c:when test="${reservation.vehicule.idVehic == 1028 }">
         <td></td>
      </c:when>
      <c:otherwise>
       <td>${reservation.vehicule.immatriculation }</td>
      </c:otherwise>
      </c:choose>
      <td>
          <fmt:parseDate value="${ reservation.dateHeureRes1 }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" /> 
          <fmt:formatDate value="${parsedDateTime }" pattern="E dd/MM/yyyy HH:mm"/>
      </td>
      <td>
          <fmt:parseDate value="${ reservation.dateHeureFin1 }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" /> 
          <fmt:formatDate value="${parsedDateTime }" pattern="E dd/MM/yyyy HH:mm"/>
      </td>
      <td> ${reservation.personne.nom }  ${reservation.personne.prenom }</td>
      <td>${reservation.destination.libelle }</td>
      <td>${reservation.motif }</td>
      <td><a class="badge badge-dark" href="<%=request.getContextPath()%>/ServletModifierUneReservation?id=${reservation.idRes}">Modifier</a></td>
      <!--   <td><a class="badge badge-danger" href="<%=request.getContextPath()%>/ServletSupprimerUneReservation?id=${reservation.idRes}">Supprimer </a></td>-->
        <td><a class="badge badge-danger" href="#" data-toggle="modal" data-target="#exampleModal"  data-whatever="${reservation.idRes}">Supprimer </a></td>

    </tr>
      </tbody>
</c:forEach>
</table>
</div>
</div>
</div>
</div>

<div><!-- modal supprimer -->
    <!-- Button trigger modal -->
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Supprimer Réservation </h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
           Confirmez la Suppression de cette Réservation ?!!   
    </div>
      <div class="modal-footer">
      <form action="<%=request.getContextPath()%>/ServletSupprimerUneReservation" method="post">
      	<input type="hidden" class="form-control" id="recipient-name" name="id">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
        <button type="submit" class="btn btn-primary">Supprimer</button>
     </form>
      </div>
    </div>	
  </div>
</div>
    </div>
 <div class="container">
 <form action="<%=request.getContextPath()%>/ServletAfficherReservationsParMois" method="POST">
 
    <div class="d-flex justify-content-center">
    <div class="form-group col-md-4" >
    <label for="getdate" >  </label>
     <input type="text" class="form-control" name="dateMois"  required  id="getdate"  placeholder="Afficher Reservations Par Mois....cliquez ici..! ">
    </div>
    <div>
   <button type="submit" class="btn btn-dark" style= "margin-top:8px;" >Afficher</button>
    </div>
    </div>
    </form>
    </div>
 
 <div class="container"><!-- form pour chercher des Réservation par nom et prénom  -->
    <form action="<%=request.getContextPath()%>/ServletAfficherReservationsParNomPrenomPersonne" method="POST">
    <div class="d-flex justify-content-center">
    <div class="form-group col-md-4" >
    <div class="d-flex justify-content-center">
    <div>
    <label for="nom" >  </label>
     <input type="text" class="form-control" name="nom"  required  id="nom"  placeholder="Nom.... ">
    </div>
    <div>
    <label for="prenom" >  </label>
     <input type="text" class="form-control" name="prenom"  required  id="prenom"  placeholder=" Prénom... ">
    </div>
    </div>
    </div>
    <div>
   <button type="submit" class="btn btn-dark" style= "margin-top:8px;" >Afficher</button>
    </div>
    </div>
    </form>
   </div>
    <div class="container text-center">
</div>
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
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

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