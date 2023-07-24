<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, model.*"%>
<%
String err=(String)request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="css/desktopview.css" rel="stylesheet" type="text/css">
<link href="css/viewmobile.css" rel="stylesheet" type="text/css">
<link rel="icon" href="imgs/logo1.png" type="image/icon type">
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=0.86, maximum-scale=1.0, minimum-scale=0.86">
<script type="text/javascript" src="js/js.js"></script>
<script type="text/javascript" src="js/formCheck.js"></script>
<title>GGWP | Iscriviti</title>
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
			<a href="login.jsp">Accedi</a>
			<a href="signup.jsp" class="active">Iscriviti</a>
		</div>
		<a class="icon" onclick="topnav()"><i class="fa fa-bars"></i></a>
	</div>
	<br>

	<h1 class=header>ISCRIVITI</h1>

	<form action="Signup" id="form" method="post" class=datiSignup enctype="multipart/form-data">
		<%if(err!=null) {%><h2 style="color:red"><%=err %></h2><%} %>
		<label for="nickname">Nickname</label> 
		<input type="text" placeholder="Inserisci nickname" name="nickname" class=text required><br>

		<br> <label for="email">Email</label> 
		<input type="email" placeholder="Inserisci email" name="email" class=text required><br>

		<br> <label for="psw">Password</label> 
		<input type="password" placeholder="Inserisci password" name="psw" class=text required><br>

		<br> <label for="psw-repeat">Ripeti Password</label> 
		<input type="password" placeholder="Ripeti password" name="psw-repeat" class=text required><br> 
			
		<br> <label for="name">Nome</label>
		<input type="text" placeholder="Inserisci nome" name="name" class=text required><br>
		
		<br> <label for="profilepic">Immagine del profilo</label>
		<input type="file" name="profilepic" accept="image/*"><br>
		<br>
		<input type=button onclick=validate()  id="signupbtn" value=Iscriviti class=signin>
		
	</form>
	
</body>
</html>