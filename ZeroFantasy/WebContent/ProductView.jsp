<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,it.unisa.model.*, it.unisa.utils.*"%>
    
<%
	Collection<?> products = (Collection<?>)request.getAttribute("product");

	String error = (String)request.getAttribute("error");
	

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
//		un = user.getUsername();

%>
    
<!DOCTYPE html>
<html lang="en">
<head>

<script>
function mostraMessaggio() {
    alert("Non puoi acquistare se non effettui il login!");
}
</script>
	<meta charset="UTF-8">
	<meta name="author" content="Michele Volpe">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Zero Fantasy | Store</title>
	
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

    </style>
</head>
<body>

	<div class="topnav" id="menu">
		<a href="<%=response.encodeRedirectURL("home.jsp") %>">Home</a>
		<a href="SearchServlet" role="search" class=active>Negozio</a>
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
	
    
   
   
	<h1 class="display-1" id="tit" align="center">Negozio</h1>
    
    <%



%>
	<h2>Cerca il tuo prodotto</h2>
	
	
    <form action="SearchServlet" role="search" >
  	  <div class="search-container">
        <input type="text" id="searchInput" name="query" placeholder="Nome prodotto..."> 
        <a href= "#"><button type="submit">Search</button></a></div>
    </form>



		<h2>Prodotti disponibili:</h2>
<%
        List<ProductBean> searchResults = (List<ProductBean>) request.getAttribute("searchResults");%>

		<table>
		<tr>
		    <th>Codice</th>
		    <th>Nome</th>
		    <th>Descrizione</th>
		    <th>Prezzo</th>
		    <th>Aggiungi al carrello</th>
		</tr>
	<%
        if (searchResults != null && !searchResults.isEmpty()) {
    %>
	    
	        <%
	                for (ProductBean bean : searchResults) {
	        %>
	        <tr>
	            <td><%= bean.getCode() %></td>
	            <td><%= bean.getName() %></td>
	            <td><%= bean.getDescription() %></td>
	            <td>€<%= bean.getPrice() %></td>
	            <td>
			    <%if(un!=null){%>
			        <form class="car-form"action="AddCarrelloServlet" method="get">
			            <input type="hidden" name="name" value=<%= bean.getName()%>>
			            <input type="hidden" name="price" value= <%= bean.getPrice()%>>
			            <button class="transparent-button"type="submit"><img src="./imgs/carrello2.png" width="50"></button>
			        </form>
			    <%}else{ %> 
			            <button class="transparent-button"type="submit" onClick="mostraMessaggio()"><img src="./imgs/carrello2.png" width="50"></button></a>
			        <%} %>
     		   </td>
	        </tr>
	        <%
	                }
	            } else if(products != null && products.size()>0){
				
				Iterator<?> it = products.iterator();
				while(it.hasNext()){
					ProductBean bean = (ProductBean)it.next();
				
		%>
			<tr>
				<td><%=bean.getCode() %></td>
				<td><%=bean.getName() %></td>
				<td><%=bean.getDescription() %></td>
				<td>€<%=bean.getPrice() %></td>
				<td>
				<%if(un!=null){%>
			        <form action="AddCarrelloServlet" method="post">
			            <input type="hidden" name="name" value=<%= bean.getName()%>>
			            <input type="hidden" name="price" value= <%= bean.getPrice()%>>
			            <button class="transparent-button" type="submit"><img src="./imgs/carrello2.png" width="50"></button>
			        </form>
			    <%}else{ request.setAttribute("error", "Per accedere al carrello, effettua il login.");%>
			    	<a href="<%=response.encodeRedirectURL("AdminLogin.jsp")%>">
			            <button class="transparent-button" type="submit"><img src="./imgs/carrello2.png" width="50"></button></a>
			        <%} %>
     		   </td>
			</tr>
		<%
				}
			} else {
		%>
			<tr>
				<td colspan="4">No product available</td>
			</tr>
		<%
			}
		%>
	</table>
	
<br>
<br>
	
	

<!-- 												Sfondo																	 -->
<body style= background-color:#00A86B >

</html>