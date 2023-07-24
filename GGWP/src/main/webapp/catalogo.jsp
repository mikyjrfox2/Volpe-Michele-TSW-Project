<%@page import="java.util.*, model.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%
ArrayList<Videogame> games = (ArrayList<Videogame>) request.getAttribute("videogames");
ArrayList<Genre> generi = (ArrayList<Genre>) request.getAttribute("generi");

User user = (User) request.getSession().getAttribute("user");
String un = null;
if (user != null)
	un = user.getNickname();

String error = (String) request.getAttribute("error");
if (games == null && error == null) {
	response.sendRedirect("Catalogo");
	return;
}
if (error != null) {
	response.sendError(500);
	return;
}

ArrayList<Cart> cart = (ArrayList<Cart>) request.getSession().getAttribute("cart");
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="css/desktopview.css" rel="stylesheet" type="text/css">
<link href="css/viewmobile.css" rel="stylesheet" type="text/css">
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=0.86, maximum-scale=1.0, minimum-scale=0.86">
<link rel="icon" href="imgs/logo1.png" type="image/icon type">
<title>GGWP | Negozio</title>
<script type="text/javascript" src="js/jquery.js"></script>
<script src="js/js.js"></script>
<script type="text/javascript">
	function suggest(){
		var search=$("#bar").val();
		var xhr=new XMLHttpRequest();
		xhr.onreadystatechange=function(){
			if(xhr.readyState==4 && xhr.status==200){
				var vg=JSON.parse(xhr.responseText);
				$("#suggest").empty();
				if(search.length>0){
					if(vg.length>5)
						for(var i=0; i<6 ; i++){
							var vgame=vg[i].replaceAll(" ","%20");
							$("#suggest").append("<tr>"+
												 "<td><a href=Videogame?title="+vgame+"><div style='height:100%;width:100%'>"+vg[i]+"</div></a></td>"+
												 "</tr>")
						}
					else
						for(var i=0; i<vg.length ; i++){
							var vgame=vg[i].replaceAll(" ","%20");
							$("#suggest").append("<tr>"+
									 			 "<td><a href=Videogame?title="+vgame+"><div style='height:100%;width:100%'>"+vg[i]+"</div></a></td>"+
									 			 "</tr>")
						}
				}else
					$("#suggest").empty();
			}
		}
		xhr.open("POST","Search?search="+search,true);
		xhr.send();
	}
	
</script>
</head>
<body>
	<div class="logo">
		<a href="<%=response.encodeRedirectURL("home.jsp")%>"><img src="imgs/logo1.png" alt=logo class="desktopLogo"></a>
		<a href="<%=response.encodeRedirectURL("home.jsp")%>"><img src="imgs/logo1 mobile.png" alt=logo class="mobileLogo"></a>
	</div>
	<div class="topnav" id="menu">
		<a href="<%=response.encodeRedirectURL("home.jsp") %>">Home</a>
		<a href="<%=response.encodeRedirectURL("Catalogo") %>" class=active>Negozio</a>
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
	<br>
	
	<div id="searchdiv">
		<form action="Catalogo" method="get" id=searchBar>
			<input oninput=suggest() id=bar type="search" name="search">
			<button id=submitbtn value="submit">
				<i class="fa fa-search"></i>
			</button>
			<table id=suggest>
			</table>
		</form>
	</div>
	

	<h1 class=header>NEGOZIO</h1>
	<div id=tabelle>
		<div class=generi>
			<table id=generi>
				<tr>
					<th style="color: silver">Sfoglia per generi</th>
				</tr>
				<%
				if (generi.size() > 0) {
					Iterator<?> it = generi.iterator();
					while (it.hasNext()) {
						Genre g = (Genre) it.next();
				%>
				<tr>
					<td class="gen"><a href="Catalogo?gen=<%=g.getType()%>"><%=g.getType()%></a></td>
				</tr>
				<%
				}
				}
				%>
			</table>
		</div>
		<div class=catalogo>
			<table cellpadding=10% id=catalogo>
				<%
				if (games.size() > 0) {
					Iterator<?> it = games.iterator();
					while (it.hasNext()) {
						Videogame bean = (Videogame) it.next();
						String t = bean.getTitle();
						String titolo = t.replaceAll("'", "%27");
						titolo=titolo.replaceAll(" ", "%20");
						String href=response.encodeRedirectURL("Videogame?title="+titolo);
				%>
				<tr class=vgCat onclick=document.location.href="<%=href%>">
					<td><img id=gioco src="./img_exe_games/<%=bean.getImage()%>" alt=<%=t%>></td>
					<td class="catelements"><%=t%></td>
					<td class="catelements"><%=bean.getReleaseDate()%></td>
					<td class="catelements"><% if(bean.getPrice()==0.00){ %>Gratis<% } else {%><%=String.format("%.2f", bean.getPrice())%>&euro; <% }%></td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td id=notFound style="text-align: center" colspan="4">Nessun prodotto disponibile</td>
				</tr>
				<%
				}
				%>
			</table>
		</div>
	</div>
</body>
</html>