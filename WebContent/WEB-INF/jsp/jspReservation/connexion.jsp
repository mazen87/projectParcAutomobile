<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript" type="text/javascript">
function openedwindowClose() {
	{
		parent.close();
	}
}
</script>
<meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Connexion</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/projet.css" />
</head>
<body>
<div id="header">
<jsp:include page="/WEB-INF/jsp/headerConnexion.jsp"></jsp:include>
</div >
  <!-- les id de les balise sont les même pour les deux formulaire ajouter et modifier , par ce que sont identique au niveau de désign -->
<h3 id="titleAjouter"> CONNEXION </h3>
<!-- formulaire pour se connecter  -->
<div class="container-sm">
<!-- afficher les message pour les utilisateur  -->
<c:if test="${! empty connexioneErrone }"><!-- message erreur concernant la connexion -->
<p class="text-danger">${requestScope.connexioneErrone }</p>
</c:if>
<div class="shadow-lg p-3 mb-5 bg-white rounded">
<form  id="formAjouter" action="<%=request.getContextPath()%>/ServletConnexion" method="POST">
    <div class="form-group">
    <label for="exampleInputEmail1">Email * :</label>
    <input type="email" class="form-control" id="exampleInputEmail1" name="email" placeHolder ="Entrez votre Email ...." required /> <br>
   
    <div class="form-group">
    <label for="exampleInputEmail1">Mot De Passe * :</label>
    <input type="password" class="form-control" id="exampleInputEmail1" name="motDePasse" placeHolder ="Entrez votre Mot De Passe ...."   required /> <br>
   
   
    <div id="boutonsAjouterCampus">
    <button type="submit" class="btn btn-primary">Se Connecter</button>
     <!--  
       <a class="btn btn-primary" href="javascript:window.close()" role="button" >Fermer</a>
       <input type="button"  class="btn btn-primary"  value="Fermer" onclick="javascript:window.close();">
       -->
    </div>
    </div>
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