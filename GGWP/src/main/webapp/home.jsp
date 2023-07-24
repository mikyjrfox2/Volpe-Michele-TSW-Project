<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, model.*"%>
<%

ArrayList<Videogame> piucomprati=(ArrayList<Videogame>)request.getAttribute("comprati");
ArrayList<Videogame> recenti=(ArrayList<Videogame>)request.getAttribute("recenti");

if(piucomprati==null || recenti==null){
	response.sendRedirect(response.encodeRedirectURL("Home"));
	return;
}

User user = (User) request.getSession().getAttribute("user");
String un = null;
if (user != null)
	un = user.getNickname();

ArrayList<Cart> cart = (ArrayList<Cart>) request.getSession().getAttribute("cart");
%>
<!DOCTYPE html>
<html>
<head>


<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/flickity/2.2.2/flickity.min.css"
	integrity="sha512-BiFZ6oflftBIwm6lYCQtQ5DIRQ6tm02svznor2GYQOfAlT3pnVJ10xCrU3XuXnUrWQ4EG8GKxntXnYEdKY0Ugg=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link href="css/desktopview.css" rel="stylesheet" type="text/css">
<link href="css/viewmobile.css" rel="stylesheet" type="text/css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

<link rel="icon" href="imgs/logo1.png" type="image/icon type">
<meta charset="ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=0.86, maximum-scale=1.0, minimum-scale=0.86">
<script type="text/javascript" src="js/js.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/flickity/2.2.2/flickity.pkgd.min.js"
	integrity="sha512-cA8gcgtYJ+JYqUe+j2JXl6J3jbamcMQfPe0JOmQGDescd+zqXwwgneDzniOd3k8PcO7EtTW6jA7L4Bhx03SXoA=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<script type="text/javascript" src="js/slider.js"></script>


<title>GGWP | Home</title>
</head>
<body>
	<div class="logo">
		<a href="<%=response.encodeRedirectURL("home.jsp")%>"><img
			src="imgs/logo1.png" alt=logo class="desktopLogo"></a> <a
			href="<%=response.encodeRedirectURL("home.jsp")%>"><img
			src="imgs/logo1 mobile.png" alt=logo class="mobileLogo"></a>
	</div>
	<div class="topnav" id="menu">
		<a href="<%=response.encodeRedirectURL("home.jsp")%>" class="active">Home</a>
		<a href="<%=response.encodeRedirectURL("Catalogo")%>">Negozio</a>
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

	<div class=header>
		<ul id="listeffect">
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">G</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">L</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">O</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">B</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">E</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">&nbsp;</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">G</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">A</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">M</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">E</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">S</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">&nbsp;</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">W</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">O</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">R</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">K</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">S</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">H</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">O</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">P</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">&nbsp;</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">P</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">R</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">O</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">J</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">E</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">C</div></li>
			<li><input checked class=checkbox type="checkbox"
				onmouseover=check(this)>
				<div class="chareffect">T</div></li>
		</ul>
	</div>


		<h3 class="h3home">In evidenza</h3>
		<div id=evidDiv class="sliderdiv">
			<div id=evidSlider class="slider">
				<div class="slide active">
						<a href="Videogame?title=<%=piucomprati.get(0).getTitle() %>"><img src="./img_exe_games/<%=piucomprati.get(0).getImage() %>" alt="<%=piucomprati.get(0).getTitle() %>">
						<div class="info">
							<h2><%=piucomprati.get(0).getTitle() %></h2>
							<p><%=piucomprati.get(0).getDescription() %></p>
						</div>
						</a>
					</div>
				<%for(int i=1; i<piucomprati.size(); i++){ %>
				<div class="slide">
					<a href="Videogame?title=<%=piucomprati.get(i).getTitle() %>"><img src="./img_exe_games/<%=piucomprati.get(i).getImage() %>" alt="<%=piucomprati.get(i).getTitle() %>">					
					<div class="info">
						<h2><%=piucomprati.get(i).getTitle() %></h2>
						<p><%=piucomprati.get(i).getDescription() %></p>
					</div>
					</a>
				</div>
				<%} %>
				<div class="navigation">
					<i id=prev-btn1 class="fas fa-chevron-left prev-btn"></i> <i
						id=next-btn1 class="fas fa-chevron-right next-btn"></i>
				</div>
			</div>
		</div>
		
		<h3 class="h3home">Aggiunti di recente</h3>
		<div id=recDiv class="sliderdiv">
			<div id=recSlider class="slider">
				<div class="slide2 active">
						<a href="Videogame?title=<%=recenti.get(0).getTitle() %>"><img src="./img_exe_games/<%=recenti.get(0).getImage() %>" alt="<%=recenti.get(0).getTitle() %>">
						<div class="info">
							<h2><%=recenti.get(0).getTitle() %></h2>
							<p><%=recenti.get(0).getDescription() %></p>
						</div>
						</a>
					</div>
				<%for(int i=1; i<recenti.size(); i++){%>
				<div class="slide2">
					<a href="Videogame?title=<%=recenti.get(i).getTitle() %>"><img src="./img_exe_games/<%=recenti.get(i).getImage() %>" alt="<%=recenti.get(i).getTitle() %>">					
					<div class="info">
						<h2><%=recenti.get(i).getTitle() %></h2>
						<p><%=recenti.get(i).getDescription() %></p>
					</div>
					</a>
				</div>
				<%} %>
				<div class="navigation">
					<i id=prev-btn2 class="fas fa-chevron-left prev-btn"></i> <i
						id=next-btn2 class="fas fa-chevron-right next-btn"></i>
				</div>
			</div>
		</div>

</body>
</html>