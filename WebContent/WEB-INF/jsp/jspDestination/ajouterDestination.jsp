<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajouter une Destination </title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/projet.css" />
</head>
<body> <!-- les ids des balise sont identiques dans cette jsp et la jsp ajouter un campus  -->
<div id="header">
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
</div >
<!-- formulaire pour ajouter une DEstination  -->
<div class="container-sm">
<h3 id="titleAjouter"> AJOUTER UNE DESTINATION </h3>
<!-- afficher les messages pour l'utilisateur  -->
<c:if test="${empty exceptionAjouterDestination }" ><!-- message réussite concernant l'ajout d'une destination  -->
<p class="text-success">${requestScope.messageReussiteAjouterDEstination }</p>
</c:if>
<c:if test ="${!empty exceptionAjouterDestination  }"> <!-- message erreur concernant l'ajout d'une destination  -->
<p class="text-danger">${requestScope.exceptionAjouterDestination } </p>
</c:if>
<div class="shadow-lg p-3 mb-5 bg-white rounded">
<form  id="formAjouter" action="<%=request.getContextPath()%>/ServletAjouterUneDestination" method="POST">

    <div class="form-group">
    <label for="exampleInputEmail1">Libellé * :</label>
    <input type="text" class="form-control" id="exampleInputEmail1" name="libelle"  placeHolder ="Entrez le libellé de la destination .." required /> <br>
    </div>
    <div class="form-group">
    <label for="exampleInputEmail1">Code Destination * :</label>
    <input type="text" class="form-control" id="exampleInputEmail1" name="codeDes"  placeHolder ="Entrez le code destination .." required /> <br>
    </div>
     <div>
    <button type="submit" class="btn btn-primary">Ajouter</button>
    <a class="btn btn-primary" href="<%=request.getContextPath()%>/ServletGestionDesDestinations" role="button" >Retour</a>
    </div>
    </form>
    </div>
    </div>
    <div id="separateurFormAjouter">
    </div>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<div id="footer">
<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</div>
</body>
</html>