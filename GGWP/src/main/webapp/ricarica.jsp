<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, model.*"%>
<%
String w = (String) request.getAttribute("wallet");
String error = (String) request.getAttribute("error");
if(error!=null){
	response.sendError(500);
	return;
}

ArrayList<Cart> cart=(ArrayList<Cart>)request.getSession().getAttribute("cart");

if(cart==null){
	response.sendRedirect("Libreria");
	return;
}

User user=(User)request.getSession().getAttribute("user");
if(user==null){
	response.sendError(403);
	return;
}
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
<script type="text/javascript" src="js/reloadCheck.js"></script>
<script type="text/javascript" src="js/js.js"></script>
<script type="text/javascript">
<% 	String mess=(String)request.getSession().getAttribute("mess");
if(mess!=null){%>
	alert("<%=mess%>")
	<%request.getSession().removeAttribute("mess");
}%>
	var bool1 = false;
	var bool2 = false;
	$(document).ready(function(){
		$("#form").change(function(){
			if($("#carta").is(":checked") && !bool1) {
				$("#cartadiv").append("<br><label for='numcarta' style='color: #002038; font-size: 20px; font-weight: bold'>Num. Carta:<input type='text' name='numcarta' size='16' class='text'></label><br>"+
				"<label for='importo' style='color: #002038; font-size: 20px; font-weight: bold'>Importo:<input type='text' name='importo' size='10' class='text'></label><br>"+
				"<br><button type=button id='inviabutton' onclick='validateCard()'>Invia</button>");
				bool1= true;
			}else if(!$("#carta").is(":checked")){
				$("#cartadiv").empty();
				bool1= false;
			}
			
			if($("#paypal").is(":checked") && !bool2) {
				$("#paypaldiv").append("<br><label for='importo' style='color: #002038; font-size: 20px; font-weight: bold'>Importo:<input type='text' name='importo' size='10' class='text'></label>"+
				"<br><button type=button id='inviabutton' onclick='validatePaypal()'>Invia</button>");
				bool2= true;
			}else if(!$("#paypal").is(":checked")){
				$("#paypaldiv").empty();
				bool2= false;
			}
		}).change();
	});
</script>
<title><%=un %> | Ricarica Account</title>
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

	<h1 class=header>RICARICA ACCOUNT</h1>
	<br>
		<div class="dati" style="color: #002038">
			<% if(w!=null) { %>
				<h3 style='color:red'><%= w %></h3>
			<% } %>
			<h2>Modalità di ricarica: </h2>
			<form id='form' action='Wallet' method='post'>
				<input type='radio' name='pay' id='carta' checked><label style='font-size: 20px'><b> Carta di credito</b></label><br>
				<input type='radio' name='pay' id='paypal' ><label style='font-size: 20px'><b> Paypal</b></label><br>
				<div id='cartadiv'>
				</div>
				<div id='paypaldiv'>
				</div>
			</form>	
		</div>
</body>
</html>