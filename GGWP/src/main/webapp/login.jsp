<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, model.*"%>
<%

boolean reg=false;
if((request.getAttribute("reg"))!=null)
	reg=true;
 
String err=(String)request.getAttribute("error");
%>


<!DOCTYPE html>
<html>
<head>
<script>
	
</script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="css/viewmobile.css" rel="stylesheet" type="text/css">
<link href="css/desktopview.css" rel="stylesheet" type="text/css">
<link rel="icon" href="imgs/logo1.png" type="image/icon type">
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=0.86, maximum-scale=1.0, minimum-scale=0.86">
<script type="text/javascript" src="js/js.js"></script>
<script type="text/javascript" src="js/formCheck.js"></script>
<title>GGWP | Accedi</title>
</head>
<body>
	<div class="logo">
		<a href="home.jsp"><img src="imgs/logo1.png" alt=logo class="desktopLogo"></a>
		<a href="home.jsp"><img src="imgs/logo1 mobile.png" alt=logo class="mobileLogo"></a>
	</div>
	<div class="topnav" id="menu">
		<a href="home.jsp">Home</a>
		<a href="Catalogo">Negozio</a>
		<div class="navright" id="logEsignin">
			<a href="login.jsp" class="active">Accedi</a>
			<a href="signup.jsp">Iscriviti</a>
		</div>
		<a class="icon" onclick="topnav()"><i class="fa fa-bars"></i></a>
	</div>
	<br>
	<h1 class="header mobileheader" >ACCEDI</h1>
		<form id="form" action="Login" method="post" class=dati>
			<h2 style="color: red">
			<%
			if (reg) {
			%>Benvenuto in GGWP! Si prega di inserire le proprie credenziali.
			<%
			}
			if(err!=null){%>
				<%=err %>
			<%
			}%>
			</h2>
			<label for="email">Email</label>
			<input type="email" id="email" name="email"
				placeholder="mariorossi@email.com" class="text" required><br> <br><br>
			<label for="password">Password</label> <input type="password"
				id="password" name="password" class="text" required><br> <br>
				<input type="button" onclick="parcheck()" value="Accedi" class="signin"> 
		</form>
	
</body>
</html>