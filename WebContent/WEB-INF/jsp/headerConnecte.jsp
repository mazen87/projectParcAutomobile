<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <script>
     
     function openWindow()
     {
        window.open("ServletConnexion","_self");
     }
     </script>
<header>
<header  id="innerhead">
 <nav class="navbar navbar-dark bg-black">
  <a class="navbar-brand" href="#"></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavDropdown">
    <ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="#"><span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/ServletGestionDesCampus">Gestion Des Campus</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/ServletGestionDesDestinations">Gestion Des Destinations</a>
      </li>
      <c:choose>
      <c:when test="${sessionScope.personne.administrateur }">
       <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/ServletGestionDesPersonnes">Gestion Des Personnes</a>
      </li>
      </c:when>
      <c:otherwise>
      </c:otherwise>
      </c:choose>
     
         <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/ServletGestionDesVehicules">Gestion Des Véhicules</a>
      </li>
        <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/ServletGestionDesIndisponibilites">Gestion Des Indisponibilités</a>
      </li>
        <li class="nav-item">
        <!--  <a class="nav-link" href="<%=request.getContextPath()%>/ServletConnexion">Se Déconnecter</a>-->
        <a class="nav-link" href="#" onClick="openWindow()" >Se Déconnecter</a>
      </li>
      
      </ul>
  </div>
</nav>
 <div class="container text-center text-white">
  <div class="rounded-sm">
  <div >
	<img src="img/Logo ENI GD_web.jpg" class="rounded float-left" id="logo" alt="logo">
  </div id="parc">
  <div>
    <h1>Parc <span><img src="img/car_logo.png" class="rounded float-middle" id="car" alt="logo"></span>Automobile</h1>
  </div>
  </div>
  </div>
  </div>
  </header>
</header>