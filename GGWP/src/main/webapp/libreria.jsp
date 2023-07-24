<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, model.*"%>
<%
User user=(User)request.getSession().getAttribute("user");
if(user==null){
	response.sendError(403);
	return;
}

ArrayList<Cart> libr=(ArrayList<Cart>)request.getSession().getAttribute("libr");

if(libr==null){
	response.sendRedirect("Libreria");
	return;
}

String un=user.getNickname();

ArrayList<Cart> cart=(ArrayList<Cart>)request.getSession().getAttribute("cart");

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
<script type="text/javascript">
	<% 	String ok=(String)request.getSession().getAttribute("mess");
	if(ok!=null){%>
		alert("<%=ok%>")
		<%request.getSession().removeAttribute("mess");
	}%>
</script>
<title><%=un %> | Libreria</title>
</head>
<body>
	<div class="logo">
		<a href="<%=response.encodeRedirectURL("home.jsp")%>"><img src="imgs/logo1.png" alt=logo class="desktopLogo"></a>
		<a href="<%=response.encodeRedirectURL("home.jsp")%>"><img src="imgs/logo1 mobile.png" alt=logo class="mobileLogo"></a>
	</div>
	<div class="topnav" id="menu">
		<a href="<%=response.encodeRedirectURL("home.jsp")%>">Home</a>
		<a href="<%=response.encodeRedirectURL("Catalogo")%>">Negozio</a>
		<a href="<%=response.encodeRedirectURL("Libreria")%>" class='active'>Libreria</a>
		<div class="navright" id="logout">
			<a id=wallet href=<%=response.encodeRedirectURL("carrello.jsp")%>><span id=cart><%=cart.size() %></span><%=String.format("%.2f", user.getWallet()).replace(",",".") %> &euro;</a>
			<a href="<%=response.encodeRedirectURL("user.jsp")%>"><%=un%></a>
			<a href="javascript: if(confirm('Sicuro di voler effettuare il logout?')) document.location.href ='Invalidate' ">Logout</a>
		</div>
		<a class="icon" onclick="topnav()"><i class="fa fa-bars"></i></a>
	</div>
	<br>

	<h1 class=header>LIBRERIA</h1>

	<div id="libreriadiv">
		<table id=libreria>
			<tr>
				<th colspan=2 class="thlibreria">Videogioco</th>
				<th class="thlibreria">Data di acquisto</th>
			</tr>
			<%
			if (libr.size()>0) {
				Iterator<?> it = libr.iterator();
				while (it.hasNext()) {
					Cart cr = (Cart) it.next();
					String t = cr.getVg();
					String titolo = t.replaceAll("'", "%27");
					titolo=titolo.replaceAll(" ", "%20");
			%>
			<tr class="trlibreria">
				<td onclick=document.location.href="Videogame?title=<%=titolo%>"><img src="./img_exe_games/<%=cr.getImg() %>" id="gioco"></td>
				<td onclick=document.location.href="Videogame?title=<%=titolo%>"><%=t%></td>
				<td onclick=document.location.href="Videogame?title=<%=titolo%>"><%=cr.getPurchaseDate() %></td>
				<td style="cursor: default"><a href=<%=cr.getImg() %> download="<%=cr.getVg() %>" class="playbutton">Avvia Ora</a>
			</tr>
			<%
			}
			} else {
			%>
			<tr class="trlibreria">
				<td colspan=4>Nessun prodotto disponibile</td>
			</tr>
			<%
			}
			%>
	</table>
	</div>
</body>
</html>