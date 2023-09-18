<!DOCTYPE html>
<html lang="it">
<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8" import="java.util.*,it.unisa.model.*"%>
<title>Cards Market</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1 shrink-to-fit=no">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="css/styleHome.css">
<link rel="stylesheet" href="css/styleMedia.css">
<link rel="stylesheet" href="css/styleNav.css">

<script src="https://kit.fontawesome.com/a076d05399.js"></script> <!-- per l'icona e aprire e chiudere -->

<script>//barra di navigazione fissa
$(document).ready(function() {
var stickyNavTop = $('nav').offset().top;

var stickyNav = function(){
var scrollTop = $(window).scrollTop();

if (scrollTop > stickyNavTop) {
$('nav').addClass('sticky');
} else {
$('nav').removeClass('sticky');
}
};

stickyNav();

$(window).scroll(function() {
stickyNav();
});
});
</script>

<%
Collection<?> prB = (Collection<?>)request.getAttribute("prB");
%>
<script type="text/javascript">
		function play(i){
			<%
			if(prB != null && prB.size()>0){
				Iterator<?> it = prB.iterator();
				while(it.hasNext()){
					ProductBean bean = (ProductBean)it.next();}
			%><%}%>
</script>

<script>
	function avviso(){
	$('#exampleModal').modal({backdrop: 'static',keyboard: true, show:true});
	}; 
	
</script>

<script>
	function avvisoc(){
	$('#ModalCar').modal({backdrop: 'static',keyboard: true, show:true});
	}; 
	
</script>
</head>

<!-- 												Sfondo																	 -->
<body background=./imgs/sfondo6.jpg >

<!-- 											Barra di navigazione														 -->

<div>
<nav class="navbar navbar-expand-sm bg-dark justify-content-center navbar-dark" >
  
  <input type="checkbox" id="check">
  <label for="check" class="checkbtn">
   	<i class="fas fa-bars" id="btn"></i>
   	<i class="fas fa-times" id="cancel"></i>
  </label>
 
   <ul class="menu" id="nav" >
    <li class="nav-item active">
      <a class="nav-link" href="login.jsp">Login</a>
    </li>
    <li class="nav-item active">
      <a class="nav-link" href="ServletIndex">Home</a>
    </li>
    
  </ul>
  
  <!-- 									Barra di Ricerca													 -->
  
  <form action="ServletCerca" name="ricerca" class="navbar-form navbar-left" role="search">
  <div id="cerca">
 <input type="text" id="cerca" name="parola" placeholder="Ricerca"> 
  </div>
  <a href=""><button type="submit" value="Cerca" id="cerca">Cerca</button></a>
</form>
<a><button id="carrello" onclick="avvisoc()"><img src="./imgs/carrello.png" height="45px" alt="Foto carrello"></button></a> 
   </nav>
 </div>

<!-- 											Titolo																		 -->

<div class="container-fluid" >
	<div class="titolo">
	<h1 class="display-1" id="tit" >Cards Market</h1>
</div>
</html>