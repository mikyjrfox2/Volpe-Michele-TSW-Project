<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,it.unisa.model.*"%>
<%
Collection<?> products = (Collection<?>)request.getAttribute("product");


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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Zero Fantasy | Admin Dashboard</title>
	<link href="style.css" rel="stylesheet" type ="text/css">
	 <style>
    	/* Stili per il menu di navigazione in alto */
        .topnav {
            background-color: #333;
            overflow: hidden;
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
				<a href="<%=response.encodeRedirectURL("AdminLibreria")%>"  class=active>Admin</a>
				<a href="CarrelloServlet"><%=un%></a>
				<a href="<%=response.encodeRedirectURL("AdminLogoutServlet")%>">Logout</a>
			</div>
			<%
		} else {
		%>
		<a href="<%=response.encodeRedirectURL("Libreria")%>">Libreria</a>
		<div class="navright" id="logout">
			<a href="CarrelloServlet"><%=un%></a>
			<a href="<%=response.encodeRedirectURL("AdminLogoutServlet")%>">Logout</a>
		</div>
		<%
		}
		%>
		<a class="icon" onclick="topnav()"><i class="fa fa-bars"></i></a>
	</div>
	<br>
    <h1>Admin Dashboard</h1>
    <div style="text-align: center;">
<form style="display: inline-block;">
	<h2>Ecco i prodotti acquistati dagli utenti:</h2>
<%
        List<Carrello> searchResults = (List<Carrello>) request.getAttribute("searchResults");%>

		<table>
		<tr>
		    <th>Nome Prodotto</th>
		    <th>Username dell'acquirente</th>
		    <th>Prezzo all'acquisto</th>
		    <th>Data Acquisto</th>
		    <th></th>
		</tr>
	<%
        if (searchResults != null && !searchResults.isEmpty()) {
    %>
	    
	        <%
	                for (Carrello bean : searchResults) {
	        %>
	        <tr>
	            <td><%= bean.getName() %></td>
	            <td><%= bean.getUsername() %></td>
	            <td>€<%= bean.getPrice() %></td>
	            <td><%= bean.getData() %></td>
	            
	            <td></td>
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
	            <td><%= bean.getUsername() %></td>
				<td>€<%=bean.getPrice() %></td>
	            <td><%= bean.getData() %></td>
	            
	            <td></td>
			</tr>
		<%
				}
			} else {
		%>
			<tr>
				<td colspan="4">Nessun acquisto effettuato</td>
			</tr>
		<%
			}
		%>
	</table></form></div>
<br>
    <div >
      <form action="AddProductServlet" method="Post">
      
      <h2>Aggiungi un nuovo prodotto:</h2>
        <label for="code">Codice:</label>
        <input type="text" name="code" required><br>

        <label for="name">Nome:</label>
        <input type="text" name="name" required><br>

        <label for="description">Descrizione:</label>
        <input type="text" name="description" required><br>
        
        
        <label for="quantity">Prezzo (€):</label>
        <input type="number" name="price" required><br>

        <button type="submit">Aggiungi Prodotto</button>
      </form>
<br>
      <form action="RemoveProductServlet" method="Post">
      
      <h2>Rimuovi un prodotto:</h2>
        <label for="productCode">Codice Prodotto:</label>
        <input type="number" name="productCode" required>
        <button type="submit">Rimuovi Prodotto</button>
      </form>
<br>

	<form action="UpdateProductServlet" method="Post" >
	
	<h2>Aggiorna un prodotto già esistente:</h2>
        <label for="code">Codice:</label>
        <input type="text" name="code" required><br>

        <label for="name">Nome:</label>
        <input type="text" name="name" required><br>

        <label for="description">Descrizione:</label>
        <input type="text" name="description" required><br>
        
        
        <label for="quantity">Prezzo (€):</label>
        <input type="number" name="price" required><br>

        <button type="submit">Modifica Prodotto</button>
      </form>
      
    </div>
<br>
<br>

	 
</body>
</html>
