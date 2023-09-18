<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,it.unisa.model.*, it.unisa.control.*, it.unisa.utils.*"%>
<%

Collection<?> products = (Collection<?>)request.getAttribute("product");
String err=(String)request.getAttribute("error");
//User user = (User) request.getSession().getAttribute("user");
String un = null;
un=(String) request.getSession().getAttribute("username");
boolean admin = false;
boolean test =false;
if(un!=null)
test = (boolean)request.getSession().getAttribute("isAdminUser");

if(un!=null)
	if(test)
		admin = true;
//if (user != null)
//	un = user.getUsername();


%><!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Zero Fantasy | Carrello personale</title>
    <link href="style.css" rel="stylesheet" type ="text/css">
<style>
    	/* Stili per il menu di navigazione in alto */
        .topnav {
            background-color: #333;
            overflow: hidden;
        }
        
		.transparent-button {
		    background-color: transparent;
		    border: none;
		    padding: 0;
		  }
        .topnav a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }

        .topnav a:hover {
            background-color: #ddd;
            color: black;
        }

        .topnav .active {
            background-color: #4CAF50;
            color: white;
        }

        .navright {
            float: right;
        }
        
        .car-form{
	        border: none;
		    background: none;
		    padding: 0;
		    margin: 0;
		 }
	    
        /* Stili per il form di login */
        form {
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f9f9f9;
        } 

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        button {
            background-color: #333;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #4CAF50;
        }
    </style>
</head>
<body>

<body style= background-color:#00A86B >
	<div class="topnav" id="menu">
		<a href="<%=response.encodeRedirectURL("home.jsp") %>">Home</a>
		<a href="SearchServlet" role="search">Negozio</a>
		<%
		if (un == null) {
		%>
		<div class="navright" id="logEsignin">
			<a href="<%=response.encodeRedirectURL("AdminLogin.jsp")%>">Accedi</a>
			<a href="<%=response.encodeRedirectURL("signup.jsp")%>">Iscriviti</a>
		</div>
		<%
		}else if (admin){
			%>
			<a href="<%=response.encodeRedirectURL("Libreria")%>">Libreria</a>
			<div class="navright" id="logout">
				<a href="<%=response.encodeRedirectURL("AdminLibreria")%>">Admin</a>
				<a href="CarrelloServlet"  class=active><%=un%></a>
				<a href="<%=response.encodeRedirectURL("AdminLogoutServlet")%>">Logout</a>
			</div>
			<%
		} else {
		%>
		<a href="<%=response.encodeRedirectURL("Libreria")%>">Libreria</a>
		<div class="navright" id="logout">
			<a href="CarrelloServlet"  class=active><%=un%></a>
			<a href="<%=response.encodeRedirectURL("AdminLogoutServlet")%>">Logout</a>
		</div>
		<%
		}
		%>
		<a class="icon" onclick="topnav()"><i class="fa fa-bars"></i></a>
	</div>

<br>
<div style="text-align: center;">
<form style="display: inline-block;">

<%if(err!=null) {%><h2 style="color:red"><%=err.toString() %></h2><%} %>
	<h2>Completa l'acquisto dei seguenti prodotti:</h2>
<%
        List<Carrello> searchResults = (List<Carrello>) request.getAttribute("searchResults");%>

		<table>
		
		<tr>
		    <th>Nome Prodotto</th>
		    <th>Prezzo</th>
		    <th>Acquista</th>
		    <th>Elimina</th>
		</tr>
	<%
        if (searchResults != null && !searchResults.isEmpty()) {
    %>
	    
	        <%
	                for (Carrello bean : searchResults) {
	        %>
	            <form></form>
	        <tr>
	            <td><%= bean.getName() %></td>
	            <td>€<%= bean.getPrice() %></td>
	            <td>
			        <form class="car-form" action="AcquistoDefinitivoServlet" method="post">
			      		<input type="hidden" name="name" value=<%= bean.getName()%>>
			            <input type="hidden" name="price" value= <%= bean.getPrice()%>>
			            
			            <button class="transparent-button" type="submit"><img src="./imgs/+2.png" width="50"></button>
			        </form>
     		   </td>
		        <td>
			        <form class="car-form" action="AcquistoCancellatoServlet" method="post">
			            <input type="hidden" name="name" value=<%= bean.getName()%>>
			            <button class="transparent-button" type="submit"><img src="./imgs/x2.png" width="50"></button>
			        </form>
		        </td>
	        </tr>
	        <%
	                }
	            } else if(products != null && products.size()>0){
				
				Iterator<?> it = products.iterator();
				while(it.hasNext()){
					Carrello bean = (Carrello)it.next();
				
		%>
			<tr>
				<td><%=bean.getName() %></td>
				<td>€<%=bean.getPrice() %></td>
				<td>
			        <form class="car-form"action="/AcquistoDefinitivoServlet" method="post">
			      		<input type="hidden" name="name" value=<%= bean.getName()%>>
			            <input type="hidden" name="price" value= <%= bean.getPrice()%>>
			            <button class="transparent-button" type="submit"><img src="./imgs/+2.png" width="50"></button>
			        </form>
     		   </td>
		        <td>
			        <form class="car-form"action="AcquistoCancellatoServlet" method="post">
			            <input type="hidden" name="name" value=<%= bean.getName()%>>
			            <button class="transparent-button" type="submit"><img src="./imgs/x2.png" width="50"></button>
			        </form>
		        </td>
			</tr>
		<%
				}
			} else {
		%>
			<tr>
				<td colspan="4">Carrello vuoto, visita il Negozio</td>
			</tr>
		<%
			}
		%>
	</table>
	</form></div>
	<br>

		<form action="RicaricaServlet" method="post"> 
	<h2>Il tuo saldo attuale:</h2>
	
	<a>€<%=request.getSession().getAttribute("wallet") %></a>
      <h3>Carica dei soldi:</h3>
        <label for="ricarica"></label>
        <input type="number" name="ricarica" required>
        <button type="submit">Ricarica</button>
      </form>
</body>
</html>