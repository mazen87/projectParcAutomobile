<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajouter Une Personne</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/projet.css" />
</head>
<body>
<div id="header">
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
</div >
<h3 id="titleAjouter">AJOUTER UNE PERSONNE </h3>
<div class="container-sm">
<!-- afficher les messages pour l'utilisateur  -->
<c:if test="${!empty emailUniqueException }" ><!-- message concernant l'email utilisé ou pas   -->
<p class="text-danger">${requestScope.emailUniqueException }</p>
</c:if>
<c:if test="${!empty AjouterPersonneException }" ><!-- message concernant l'opération d'ajout d'une personne   -->
<p class="text-danger">${requestScope.AjouterPersonneException }</p>
</c:if>
<c:if test="${empty AjouterPersonneException and empty emailUniqueException  }" ><!-- message réussite de l'opération d'ajout d'une personne   -->
<p class="text-success">${requestScope.messageReussiteAjouterPersonne }</p>
</c:if>
<div class="shadow-lg p-3 mb-5 bg-white rounded">
<!-- formulaire ajoter une personnes -->
<form action="<%=request.getContextPath()%>/ServletAjouterUnePersonne" method="POST">
  <div class="form-group">
    <label for="exampleInputEmail1">Nom * : </label>
    <input type="text" class="form-control" name="nom"  placeHolder="Entrez le nom de la personne ...." required>
    </div>
    <div class="form-group">
    <label for="exampleInputEmail1">Prénom * : </label>
    <input type="text" class="form-control" name="prenom"  placeHolder="Entrez le prénom de la personne ...." required>
    </div>
     <div class="form-group">
    <label for="exampleInputEmail1">Nom Stucture  : </label>
    <input type="text" class="form-control" name="nomStructure"  placeHolder="Entrez le nom de la structure ....">
    </div>
    <div class="form-group">
    <label for="exampleInputEmail1">Email * : </label>
    <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="email"  placeHolder="Entrez l'email de la personne ...." required>
    </div>
  <div class="form-group">
    <label for="exampleInputPassword1">Password : </label>
    <input type="password" class="form-control" id="exampleInputPassword1" name="motDePasse"  placeHolder="Entrez le mot de passe  de la personne ....">
  </div>
  <div class="form-group form-check">
    <input type="checkbox" class="form-check-input" id="exampleCheck1" name="administrateur">
    <label class="form-check-label" for="exampleCheck1">Administrateur</label>
  </div>
  <div>
  <button type="submit" class="btn btn-primary">Ajouter</button>
  <a class="btn btn-primary" href="<%=request.getContextPath()%>/ServletGestionDesPersonnes" role="button" >Retour</a>
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