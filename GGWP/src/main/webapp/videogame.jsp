<%@page import="java.util.*, model.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
User user = (User) request.getSession().getAttribute("user");
String err=(String)request.getAttribute("error");
String un = null;
if (user != null)
	un = user.getNickname();

Videogame vg = (Videogame) request.getAttribute("videogame");
ArrayList<String> generi = (ArrayList<String>) request.getAttribute("generi");
boolean purchased = (boolean) request.getAttribute("purchased");

if (vg == null) {
	response.sendError(404);
	return;
}

ArrayList<Cart> cart = (ArrayList<Cart>) request.getSession().getAttribute("cart");
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="css/desktopview.css" rel="stylesheet" type="text/css">
<link href="css/viewmobile.css" rel="stylesheet" type="text/css">
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=0.86, maximum-scale=1.0, minimum-scale=0.86">
<link rel="icon" href="imgs/logo1.png" type="image/icon type">
<title>GGWP | <%=vg.getTitle()%></title>
<script src="js/jquery.js"></script>
<script src="js/js.js"></script>
</head>
<body>
	<div class="logo">
		<a href="<%=response.encodeRedirectURL("home.jsp")%>"><img
			src="imgs/logo1.png" alt=logo class="desktopLogo"></a> <a
			href="<%=response.encodeRedirectURL("home.jsp")%>"><img
			src="imgs/logo1 mobile.png" alt=logo class="mobileLogo"></a>
	</div>
	<div class="topnav" id="menu">
		<a href="<%=response.encodeRedirectURL("home.jsp")%>">Home</a> 
		<a href="<%=response.encodeRedirectURL("Catalogo")%>" class="active">Negozio</a>
		<%
		if (user == null) {
		%>
		<div class="navright" id="logEsignin">
			<a href="<%=response.encodeRedirectURL("login.jsp")%>">Accedi</a> <a
				href="<%=response.encodeRedirectURL("signup.jsp")%>">Iscriviti</a>
		</div>
		<%
		} else {
		%>
		<a href="<%=response.encodeRedirectURL("Libreria")%>">Libreria</a>
		<div class="navright" id="logout">
			<a id=wallet href=<%=response.encodeRedirectURL("carrello.jsp")%>><span
				id=cart><%=cart.size()%></span><%=String.format("%.2f", user.getWallet()).replace(",", ".")%>
				&euro;</a> <a href="<%=response.encodeRedirectURL("user.jsp")%>"><%=user.getNickname()%></a>
			<a
				href="javascript: if(confirm('Sicuro di voler effettuare il logout?')) document.location.href ='Invalidate' ">Logout</a>
		</div>
		<%
		}
		%>
		<a class="icon" onclick="topnav()"><i class="fa fa-bars"></i></a>
	</div>
	<br>

	<h1 id=titolo><%=vg.getTitle()%></h1>
	<div id="vg">
		<div class="videovg">
			<div id="iframediv">
				<iframe id="video" src="<%=vg.getVideo()%>"></iframe>
			</div>
			<section id=download>
				<%
				if (!purchased) {
				%>
				<h4>
					Acquista ora
					<%=vg.getTitle()%><br>
				</h4>
				<span id="acquista"><%if(vg.getPrice()==0.00){ %>Gratis<% } else {%><%=String.format("%.2f", vg.getPrice())%>&euro; <% }%> &nbsp;<a
					href='javascript: checkUser("<%if(user!=null){ %><%=user.getEmail()%><%}else{ %><%=user %><%} %>","<%=vg.getTitle().replaceAll("'", "%27")%>")'
					id="buybutton">Aggiungi al carrello</a></span>

				<%
				} else {
				%>
				<h4><%=vg.getTitle()%>
					&egrave; gi&agrave; nella tua libreria
				</h4>
				<a href=<%=vg.getImage()%> download=<%=vg.getTitle()%>
					id="scaricabutton">Scarica</a> <br>
			</section>
			<span id=contatta>Stai riscontrando problemi con il gioco?
				<a id="contattacibutton"
					href="Support?title=<%=vg.getTitle()%>">Chiedi un rimborso</a>
			</span>
			<%if(err!=null){ %>
				<span style="color: red">Spiacente. Non &egrave; stato possibile effettuare il rimborso <br>(Sono passati pi&uacute; di 14 giorni dalla data di acquisto).</span>
			<%} %>
			<%
			}
			%>
		</div>
		<br>
		<div class="infovg">
			<img src="./img_exe_games/<%=vg.getImage()%>" id=giocovg><br>
			<div id="infos">
				<h4>
					<span class="testodettagli">Sviluppatore:</span>
					<%=vg.getDeveloper()%></h4>
				<h4>
					<span class="testodettagli">Editore:</span>
					<%=vg.getPublisher()%></h4>
				<h4>
					<span class="testodettagli">Descrizione:</span>
					<%=vg.getDescription()%></h4>
				<h4>
					<span class="testodettagli">Data di rilascio:</span>
					<%=vg.getReleaseDate()%></h4>
			</div>	
			
			<div id=generiVg>
				<%
				for (String s : generi) {
				%>
				<span class=genere><%=s%></span>
				<%
				}
				%>
			</div>
		</div>
	</div>
	<br>
</body>
</html>