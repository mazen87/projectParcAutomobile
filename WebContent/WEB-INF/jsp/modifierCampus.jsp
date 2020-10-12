<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Modifier un Campus</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/projet.css" />
</head>
<body>

<div id="header">
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
</div >
  <!-- les id de les balise sont les même pour les deux formulaire ajouter et modifier , par ce que sont identique au niveau de désign -->
<h3 id="titleAjouter"> MODIFIER UN CAMPUS </h3>
<!-- formulaire pour modifier un campus  -->
<div class="container-sm">
<!-- afficher les message pour les utilisateur  -->
<c:if test="${! empty exceptionModifier }"><!-- message erreur concernant la modification d'un campus -->
<p class="text-danger">${requestScope.exceptionModifier }</p>
</c:if>
<c:if test="${ empty  exceptionModifier }">
<p class="text-success">${requestScope.msgReussiteModifier }</p><!-- message réussite concernant la modification d'un campus -->
</c:if>
<c:if test="${! empty selectCampudByIdException }"><!-- message erreur concernant la modification d'un campus -->
<p class="text-danger">${requestScope.selectCampudByIdException }</p>
</c:if>
<div class="shadow-lg p-3 mb-5 bg-white rounded">
<form  id="formAjouter" action="<%=request.getContextPath()%>/ServletModifierUnCampus" method="POST">
    <div class="form-group">
    <label for="exampleInputEmail1">Libellé * :</label>
    <input type="text" class="form-control" id="exampleInputEmail1" name="libelle"  value="${requestScope.libelle }"  required /> <br>
    <div id="boutonsAjouterCampus">
    <c:choose>
    <c:when test="${!empty exceptionModifier }">
        <a class="btn btn-primary" href="<%=request.getContextPath()%>/ServletGestionDesCampus" role="button" >Retour</a>
    </c:when>
    <c:otherwise>
      <button type="submit" class="btn btn-primary">Modifier</button>
    <a class="btn btn-primary" href="<%=request.getContextPath()%>/ServletGestionDesCampus" role="button" >Retour</a>
    </c:otherwise>
    </c:choose>
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