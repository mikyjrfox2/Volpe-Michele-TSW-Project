<%@page import="java.util.*, model.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%

User user = (User) request.getSession().getAttribute("user");
String un=null;
if(user!=null)
	un=user.getNickname();
else{
	response.sendRedirect("login.jsp");
	return;
}

String exc = (String) request.getAttribute("err");
if (exc != null) {
	System.out.println(exc);
	response.sendRedirect(response.encodeRedirectURL("login.jsp"));
	return;
}

ArrayList<Cart> cart=(ArrayList<Cart>)request.getSession().getAttribute("cart");

%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="css/desktopview.css" rel="stylesheet" type="text/css">
<link href="css/viewmobile.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/js.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	<% 	String ok=(String)request.getSession().getAttribute("mess");
	if(ok!=null){%>
		alert("<%=ok%>")
		<%request.getSession().removeAttribute("mess");
	}%>
	
	function modificaProfilo(){
		var divpop=document.getElementById("divPopup")
		divpop.style.display="block";
		$("#popup").empty()
		$("#popup").append("<span id=close>X</span>")
		var close=document.getElementById("close")
		close.onclick=function(){
			divpop.style.display="none";
		}
		window.onclick = function(event) {
		    if (event.target == divpop) {
		        divpop.style.display = "none";
		    }
		}
		$("#popup").append("<h1 style='color: black'>Modifica profilo</h1>")
		$("#popup").append("<form id=editForm action=Edit method=post enctype=multipart/form-data><input type=hidden name=type value=user></form>")
		
		$("#editForm").append("<label for=email>Email</label><br>"+
				'<input type=text readonly name=email value=<%= user.getEmail()%>><br>'+
				"<label for=nick>Nickname</label><br>"+
				"<input type=text required name=nick value=<%= user.getNickname()%>>"+
				"<label for=pass>Password</label><br>"+
				"<input type=password required name=pass value=<%= user.getPassword()%>>"+
				"<label for=nome>Nome</label><br>"+
				"<input type=text required name=nome value=<%= user.getName()%>>"+
				"<label for=bio>Biografia</label><br>"+
				"<textarea style=resize:none name=bio><%= user.getBio()%></textarea>"+
				"<label for=immagine>Immagine: <%= user.getProfilePic()%></label><br>"+
				"<input type=file style=color:black name=immagine accept='image/*'><br><br>"+
				"<button id=editBut>Conferma</button>")
	}
</script>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=0.86, maximum-scale=1.0, minimum-scale=0.86">
<link rel="icon" href="imgs/logo1.png" type="image/icon type">
<title>GGWP | <%=user.getNickname()%></title>
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
			<a id=wallet href=<%=response.encodeRedirectURL("carrello.jsp")%>><span id=cart><%=cart.size() %></span><%=String.format("%.2f", user.getWallet()).replace(",",".") %> &euro;</a>
			<a class='active' href="<%=response.encodeRedirectURL("user.jsp")%>"><%=un%></a>
			<a href="javascript: if(confirm('Sicuro di voler effettuare il logout?')) document.location.href ='Invalidate' ">Logout</a>
		</div>
		<a class="icon" onclick="topnav()"><i class="fa fa-bars"></i></a>
	</div>
	<br>
	<div id="dati">
		<div class="imguser">
		  <%if(user.getProfilePic()!=null){ %>
		 <img id=profilepic alt=<%=user.getNickname()%>	src="profilepics\<%=user.getProfilePic()%>"><br> 
			<%}else{ %>
				<img id=profilepic src="profilepics\user.png">
			<%}%>
		</div>
		<div id="infoutente">
			<h1><%=user.getNickname()%></h1>
			<h3><%=user.getName()%></h3>
			<h3><%=user.getBio()%></h3>
			<h3>Punti EZ accumulati: <%=user.getPoints() %></h3>
			<button id="modificaprofbutton" onclick=modificaProfilo()>Modifica profilo</button>
		</div>
		<br>
	</div>
	<div id=divPopup style="display: none; text-align: center;">
		<div id=popup></div>
	</div>
	<%
	if (user.isAdmin()) {
	%>
	<a id=footer href=management.jsp >A</a>
	<%
	}
	%>
</body>
</html>