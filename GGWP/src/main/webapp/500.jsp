<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, model.*" isErrorPage="true"%>
<%

User user=(User)request.getSession().getAttribute("user");
String un=null;
if(user!=null)
	un=user.getNickname();

ArrayList<Cart> cart=(ArrayList<Cart>)request.getSession().getAttribute("cart");
%>
<!DOCTYPE html>
<html style="background-image: url('imgs/500.png'); background-repeat: repeat-y; background-size: 100%;">
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="css/desktopview.css" rel="stylesheet" type="text/css">
<link href="css/viewmobile.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/js.js"></script>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=0.86, maximum-scale=1.0, minimum-scale=0.86">
<title>ERRORE 500</title>
</head>
<body id="error500">
	
	<div class="logo">
		<a href="<%=response.encodeRedirectURL("home.jsp")%>"><img src="imgs/logo1.png" alt=logo class="desktopLogo"></a>
		<a href="<%=response.encodeRedirectURL("home.jsp")%>"><img src="imgs/logo1 mobile.png" alt=logo class="mobileLogo"></a>
	</div>
	<div class="topnav" id="menu">
		<a href="<%=response.encodeRedirectURL("home.jsp") %>" class="active">Home</a>
		<a href="<%=response.encodeRedirectURL("Catalogo") %>">Negozio</a>
		<%
		if (user == null) {
		%>
		<div class="navright" id="logEsignin">
			<a href="<%=response.encodeRedirectURL("login.jsp")%>">Accedi</a>
			<a href="<%=response.encodeRedirectURL("signup.jsp")%>">Iscriviti</a>
		</div>
		<%
		}else {
		%>
		<a href="<%=response.encodeRedirectURL("Libreria")%>">Libreria</a>
		<div class="navright" id="logout">
			<a id=wallet href=<%=response.encodeRedirectURL("carrello.jsp")%>><span id=cart><%=cart.size() %></span><%=String.format("%.2f", user.getWallet()).replace(",",".") %> &euro;</a>
			<a href="<%=response.encodeRedirectURL("user.jsp")%>"><%=user.getNickname()%></a>
			<a href="javascript: if(confirm('Sicuro di voler effettuare il logout?')) document.location.href ='Invalidate' ">Logout</a>
		</div>
		<%
		}
		%>
		<a class="icon" onclick="topnav()"><i class="fa fa-bars"></i></a>
	</div>
	
	<h2 id=exc><%=exception %></h2>
</body>
</html>