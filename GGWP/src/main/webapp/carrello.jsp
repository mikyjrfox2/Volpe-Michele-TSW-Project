<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, model.*"%>
<%

ArrayList<Cart> cart=(ArrayList<Cart>)request.getSession().getAttribute("cart");

if(cart==null){
	response.sendRedirect(response.encodeRedirectURL("Libreria"));
	return;
}

User user=(User)request.getSession().getAttribute("user");
String un=user.getNickname();

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
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/js.js"></script>
<script type="text/javascript">
<% 	String mess=(String)request.getSession().getAttribute("mess");
if(mess!=null){%>
	alert("<%=mess%>")
	<%request.getSession().removeAttribute("mess");
}%>

$(document).ready(function(){
	$("#puntiUtente").change(function(){
		if(this.checked) {
			$("#form").append("<h2 id='toglimi'>Punti EZ a disposizione: <%= user.getPoints() %> <br>"+
			"<span style=color:white;font-size:60%>Si ricorda che ogni punto vale 0.25 cent di sconto</span></h2>");
		} else {
			$("#toglimi").remove();
		}
	});
});

function checkCart(){
	if(<%= cart.size()%>==0)
		alert("Il carrello è vuoto")
}
</script>
<title><%=un %> | Carrello</title>
</head>
<body>
	<div class="logo">
		<a href="<%=response.encodeRedirectURL("home.jsp")%>"><img src="imgs/logo1.png" alt=logo class="desktopLogo"></a>
		<a href="<%=response.encodeRedirectURL("home.jsp")%>"><img src="imgs/logo1 mobile.png" alt=logo class="mobileLogo"></a>
	</div>
	<div class="topnav" id="menu">
		<a href="<%=response.encodeRedirectURL("home.jsp")%>">Home</a>
		<a href="<%=response.encodeRedirectURL("Catalogo")%>">Negozio</a>
		<a href="<%=response.encodeRedirectURL("Libreria")%>">Libreria</a>
		<div class="navright" id="logout">
			<a class='active' id=wallet href=<%=response.encodeRedirectURL("carrello.jsp")%>><span id=cart><%=cart.size() %></span><%=String.format("%.2f", user.getWallet()).replace(",",".") %> &euro;</a>
			<a href="<%=response.encodeRedirectURL("user.jsp")%>"><%=un%></a>
			<a href="javascript: if(confirm('Sicuro di voler effettuare il logout?')) document.location.href ='Invalidate' ">Logout</a>
		</div>
		<a class="icon" onclick="topnav()"><i class="fa fa-bars"></i></a>
	</div>
	<br>

	<h1 class=header>CARRELLO</h1>

	<div>
		<table id=libreria>
			<tr>
				<th colspan=2 class="thlibreria">Videogioco</th>
				<th class="thlibreria">Prezzo</th>
			</tr>
			<%
			float total=0.0f;
			if (cart.size()>0) {
				Iterator<?> it = cart.iterator();
				while (it.hasNext()) {
					Cart cr = (Cart) it.next();
					String t = cr.getVg();
					String titolo = t.replaceAll("'", "%27");
					titolo=titolo.replaceAll(" ", "%20");
					total+=cr.getCost();
			%>
			<tr class="trlibreria">
				<td onclick=document.location.href="Videogame?title=<%=titolo%>"><img src="./img_exe_games/<%=cr.getImg()%>" id="gioco"></td>
				<td onclick=document.location.href="Videogame?title=<%=titolo%>"><%=t%></td>
				<td onclick=document.location.href="Videogame?title=<%=titolo%>"><%=cr.getCost() %> &euro;</td>
				<td style="cursor: default"><button onclick='delCar("<%=titolo %>")' id="eliminabutton"><img src=imgs/cestino.png id="trashbinimage"></button>
			</tr>
			
			<%
			}
			} else {
			%>
			<tr class="trlibreria">
				<td style="cursor: default" colspan=4>Nessun prodotto nel carrello</td>
			</tr>
			<%
			}
			%>
	</table>
	</div>
	<br><br>
	<% String s = String.format("%.2f", total);
		String prezzo = s.replace(",", "."); %>
	<div id="gestionepag" style="display:flex; justify-content: center">
	<%if(cart.size()>0) {%>
		<div class="rectCheckout" id="punti">
			<form id='form' action='Acquista' method='post'>
				<label for="text" style="font-size:1.5em;"><b>Totale provvisorio: </b></label><input name="price" type='text' value="<%= prezzo %>&euro;" id="priceText" readonly><br>
				<input name="pts" type="checkbox" id="puntiUtente"><label for="checkbox" style="color:lightblue">Voglio utilizzare i punti EZ</label>
				<button onclick=checkCart() id="buynow">Procedi all'acquisto</button>
			</form>
		</div>
	<%} %>
		<div class="rectCheckout1">
			<h2>Aggiungi fondi al portafoglio</h2>
			<button id="ricarica" onclick="location.href='./ricarica.jsp';">Aggiungi</button>
		</div>
	</div>
</body>
</html>