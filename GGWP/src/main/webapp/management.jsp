<%@page import="javax.sql.DataSource"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*, model.*"%>

<%
User user = (User) request.getSession().getAttribute("user");
String un = null;
if (user != null){
	if (user.isAdmin())
		un = user.getNickname();
	else {
		response.sendError(403);
		return;
	}
}else{
	response.sendError(403);
	return;
}

String exc = (String) request.getAttribute("err");
if (exc != null) {
	System.out.println(exc);
	response.sendRedirect(response.encodeRedirectURL("login.jsp"));
	return;
}

ArrayList<Cart> cart = (ArrayList<Cart>) request.getSession().getAttribute("cart");
if(cart==null){
	response.sendRedirect(response.encodeRedirectURL("Carrello"));
	return;
}

DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
GenreDAO genre=new GenreDAO(ds);
ArrayList<Genre> generi=(ArrayList<Genre>) genre.doRetrieveAll("");
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="css/desktopview.css" rel="stylesheet" type="text/css">
<link href="css/viewmobile.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/js.js"></script>
<script type="text/javascript">
		<%String ok = (String) request.getSession().getAttribute("mess");
		if (ok != null) {%>
				alert("<%=ok%>")
				<%request.getSession().removeAttribute("mess");
		}%>
		
		function escapeHtml(unsafe) {
		    return unsafe.replace(/%20/g, " ").replace(/%27/g, "'");
		 }
		
		function elimina(id,par){
			var conf=confirm("Sei sicuro di eliminare "+escapeHtml(id)+" ?")
			if(conf)
				location.href="Delete?par="+par+"&id="+id	
		}
		
		function del(i){
			var gen=$("#g"+i).html().split("<b")
			$("#genSelect").append("<option value='"+gen[0]+"' id=g"+i+">"+gen[0]+"</option>")
			$("#g"+i).remove()
		}
		
		function compileform(){
			var generi=$("#genEdit").text()
			if(generi!=""){
				$("#hidden").val(generi)
				$("#editForm").submit()
			}else
				alert("Inserire almeno un genere")
		}

		function edit(id,par){
			var divpop=document.getElementById("divPopup")
			$("#addBtn").css("z-index","-1")
			$("#divAddGen").css("z-index","-1")
			divpop.style.display="block";
			$("#popup").empty()
			$("#popup").append("<span id=close>X</span>")
			var close=document.getElementById("close")
			close.onclick=function(){
				divpop.style.display="none";
				$("#addBtn").css("z-index","0")
				$("#divAddGen").css("z-index","0")
			}
			window.onclick = function(event) {
			    if (event.target == divpop) {
			        divpop.style.display = "none";
			        $("#addBtn").css("z-index","0")
			        $("#divAddGen").css("z-index","0")
			    }
			}
			$("#popup").append("<h1 style='color: black'>Modifica videogioco</h1>")
			$("#popup").append("<form id=editForm action=Edit method=post enctype=multipart/form-data><input type=hidden name=type value=vg></form>")
			
			$.getJSON("Retrieve?id="+id, function(data){	//modifica videogioco
				$("#editForm").append("<label for=titolo>Titolo</label><br>"+
						'<input type=text readonly name=titolo value="'+data.titolo+'"><br>'+
						"<label for=desc>Descrizione</label><br>"+
						'<textarea required style="resize: none" rows=3 name=desc>'+data.desc+'</textarea><br>'+
						"<label for=prezzo>Prezzo</label><br>"+
						"<input required type=number min=0 step=.01 name=prezzo value='"+data.prezzo+"'><br>"+
						"<label for=img>Immagine: "+data.img+"</label><br>"+
						"<input type=file style=color:black name=img accept='image/*''><br>"+
						"<label for=video>Video</label><br>"+
						"<input required type=text name=video value='"+data.video+"'><br>"+
						"<label for=genEdit>Generi</label><br>")
				var generi="<div name=genEdit id=genEdit>"
				var i=0
				data.generiVg.forEach(g => {
					g.replaceAll(" ","%20")
					generi+="<span id=g"+i+" class=gensEdit>"+g+"<button type=button class=del onClick=del("+i+")>-</button></span>"
					i++
				})
				generi+="</div><br>"
				generi+="Aggiungi: <select name=generi id=genSelect><option value=null selected></option>"
				i=0
				data.generiNonVg.forEach(g => {
					g.replaceAll(" ","%20")
					generi+="<option value='"+g+"' id=go"+i+">"+g+"</option>"
					i++
				})
				$("#editForm").append(generi+"</select><br><br><input type=hidden id=hidden name=gens><button type=button onclick=compileform() id=editBut>Conferma</button>")
				$("#genSelect").on("change", function(){//aggiunta generi dalla select
					$("#genEdit").append("<span id=g"+i+" class=gensEdit>"+this.value+"<button type=button class=del onClick=del("+i+")>-</button></span>")
					i++
					$("#genSelect option[value='"+this.value+"']").remove();
					$('#genSelect option[value=null]').attr('selected','selected');
				})
			})
		}
		
		function gestione(par){
			$("#divAddGen").remove();
			$("#tableMod").css("display","");
			$.getJSON("Management?manage="+par,function(data){	//tabella gestione
				switch(par){
				case "vg":
					$("#tableMod").empty();					
					$("#tableMod").removeClass("tableuser");
					$("#tableMod").addClass("tablevg");
					$("#tableMod").append("<thead><tr><th>Titolo</th><th>Sviluppatore</th><th>Editore</th><th>Prezzo</th></tr></thead><tbody>");
					$.each(data, function(index, field){	//ciclo i dati ritornati dal server
						$("#tableMod").append("<tr style='overflow: hidden'><td>"+escapeHtml(field.title)+"</td>"+
								"<td>"+field.dev+"</td>"+
								"<td>"+field.pub+"</td>"+
								"<td>"+field.price.toFixed(2)+"</td>"+
								"<td><img style='width: 20px;height: auto;cursor:pointer;' onclick=edit('"+field.title+"','"+par+"') src='imgs/edit.png'></td>"+
								"<td><img style='width: 24px;height: auto;cursor:pointer;' onclick=elimina('"+field.title+"','"+par+"') src='imgs/cestinoMng.png'></td></tr>");
					});
					$("#tableMod").append("</tbody>");
					
					
					$("#mng").append("<div id=divAddGen><button id=addBtn type=button>Aggiungi videogioco</button><br><br><button id=addGen>Aggiungi genere</button></div>")
					$("#addBtn").click(function(){	//bottone aggiunta videogioco
						var divpop=document.getElementById("divPopup")
						$("#addBtn").css("z-index","-1")
						$("#divAddGen").css("z-index","-1")
						divpop.style.display="block";
						$("#popup").empty()
						$("#popup").append("<span id=close>X</span>")
						var close=document.getElementById("close")
						close.onclick=function(){
							divpop.style.display="none";
							$("#addBtn").css("z-index","0")
							$("#divAddGen").css("z-index","0")
						}
						window.onclick = function(event) {
						    if (event.target == divpop) {
						        divpop.style.display = "none";
						        $("#addBtn").css("z-index","0")
						        $("#divAddGen").css("z-index","0")
						    }
						}
						$("#popup").append("<h1 style='color: black'>Aggiunta videogioco</h1>")	//aggiunta videogioco
						$("#popup").append("<form id=editForm action=Add method=post enctype=multipart/form-data><input id=hidden type=hidden name=gens><input type=hidden name=type value=vg></form>")
						$("#editForm").append("<label for=titolo>Titolo</label><br>"+
						"<input type=text required name=titolo><br>"+
						"<label for=dev>Sviluppatore</label><br>"+
						"<input type=text required name=dev><br>"+
						"<label for=pub>Editore</label><br>"+
						"<input type=text required name=pub><br>"+
						"<label for=desc>Descrizione</label><br>"+
						'<textarea required style="resize: none" rows=3 name=desc></textarea><br>'+
						"<label for=pubDate>Data di pubblicazione</label><br>"+
						"<input required type=date name=pubDate><br>"+
						"<label for=prezzo>Prezzo</label><br>"+
						"<input required type=number min=0 step=.01 name=prezzo><br>"+
						"<label for=img>Immagine</label><br>"+
						"<input required type=file style=color:black name=img accept='image/*'><br>"+
						"<label for=video>Video</label><br>"+
						"<input required type=text name=video><br>"+
						"<label for=genEdit>Generi</label><br><div name=genEdit id=genEdit></div>")
						var i=0
						$.getJSON("Management?manage=generi", function(data){	//recupero lista generi per aggiunta vg
							var generi="<br>Aggiungi: <select name=generi id=genSelect><option value=null selected></option>"							
							$.each(data, function(index, field){	//ciclo i dati ritornati dal server								
								generi+="<option id=g"+index+" value='"+field.tipo+"'>"+field.tipo+"</option>"
							});
							$("#editForm").append(generi+"</select><br><br><button id=editBut onclick=compileform() type=button>Conferma</button>")
							$("#genSelect").on("change", function(){//aggiunta generi dalla select
								$("#genEdit").append("<span id=g"+i+" class=gensEdit>"+this.value+"<button type=button class=del onClick=del("+i+")>-</button></span>")
								i++
								$("#genSelect option[value='"+this.value+"']").remove();
								$('#genSelect option[value=null]').attr('selected','selected');
							})
						})
					})
					$("#addGen").click(function(){	//bottone aggiunta genere
						var divpop=document.getElementById("divPopup")
						$("#addBtn").css("z-index","-1")
						$("#divAddGen").css("z-index","-1")
						divpop.style.display="block";
						$("#popup").empty()
						$("#popup").append("<span id=close>X</span>")
						var close=document.getElementById("close")
						close.onclick=function(){
							divpop.style.display="none";
							$("#addBtn").css("z-index","0")
							$("#divAddGen").css("z-index","0")
						}
						window.onclick = function(event) {
						    if (event.target == divpop) {
						        divpop.style.display = "none";
						        $("#addBtn").css("z-index","0")
						        $("#divAddGen").css("z-index","0")
						    }
						}
						$("#popup").append("<h1 style='color: black'>Aggiunta genere</h1>"+	//aggiunta genere
								"<form id=editForm method=post action=Add>"+
								"<label for=genere>Genere</label><br><input type=text name=genere><br><br>"+
								"<button id=editBut>Conferma</button></form>")
					})	
					break;
				case "user":
					$("#tableMod").empty();
					$("#tableMod").removeClass("tablevg");
					$("#tableMod").addClass("tableuser");
					$("#tableMod").append("<thead><tr><th>Email</th><th>Nickname</th><th>Nome</th><th>Biografia</th><th>Punti</th><th>Portafoglio</th></tr></thead><tbody>");
					$.each(data, function(index, field){	//ciclo i dati ritornati dal server
						$("#tableMod").append("<tr style='word-break: break-word'><td>"+field.email+"</td>"+
								"<td>"+field.nick+"</td>"+
								"<td>"+field.name+"</td>"+
								"<td>"+field.bio+"</td>"+
								"<td>"+field.points+"</td>"+
								"<td>"+field.wallet.toFixed(2)+"</td>"+
								"<td><img style='width: 24px;height: auto;cursor:pointer;' onclick=elimina('"+field.email+"','"+par+"') src='imgs/cestinoMng.png'></td></tr>");
					});
					$("#tableMod").append("</tbody>");
					break;
				}
			});
	}
</script>
<meta charset="ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=0.86, maximum-scale=1.0, minimum-scale=0.86">
<link rel="icon" href="imgs/logo1.png" type="image/icon type">
<title>GGWP | Admin</title>
</head>
<body>
	<div class="logo">
		<a href="<%=response.encodeRedirectURL("home.jsp")%>"><img
			src="imgs/logo1.png" alt=logo class="desktopLogo"></a> <a
			href="<%=response.encodeRedirectURL("home.jsp")%>"><img
			src="imgs/logo1 mobile.png" alt=logo class="mobileLogo"></a>
	</div>
	<div class="topnav" id="menu">
		<a href="<%=response.encodeRedirectURL("home.jsp")%>">Home</a> <a
			href="<%=response.encodeRedirectURL("Catalogo")%>">Negozio</a> <a
			href="<%=response.encodeRedirectURL("Libreria")%>">Libreria</a>
		<div class="navright" id="logout">
			<a id=wallet href=<%=response.encodeRedirectURL("carrello.jsp")%>><span
				id=cart><%=cart.size()%></span><%=String.format("%.2f", user.getWallet()).replace(",", ".")%>
				&euro;</a> <a href="<%=response.encodeRedirectURL("user.jsp")%>"><%=un%></a>
			<a
				href="javascript: if(confirm('Sicuro di voler effettuare il logout?')) document.location.href ='Invalidate' ">Logout</a>
		</div>
		<a class="icon" onclick="topnav()"><i class="fa fa-bars"></i></a>
	</div>
	<br>

	<h1 class=header>GESTIONE DATABASE</h1>
	<br>
	<div class=gestionebuttons>
		<button onclick="gestione('user')" class="ManagementButton">
			<img src="imgs/ominoButton1.png">
		</button>
		<button onclick="gestione('vg')" class="ManagementButton">
			<img src="imgs/joypadButton.png">
		</button>
	</div>

	<div id=divPopup style="display: none; text-align: center;">
		<div id=popup></div>
	</div>
	<div id=mng style="display: flex; justify-content: center; align-items: baseline;"><div id=divtab><table id=tableMod style="display: none"></table></div></div>
	
	<a id=footer href=user.jsp>U</a>
</html>