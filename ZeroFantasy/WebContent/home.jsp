<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.*, it.unisa.model.*, it.unisa.control.*, it.unisa.utils.*"%>
    
    <%

//User user = (User) request.getSession().getAttribute("user");
String un = null;
un=(String) request.getSession().getAttribute("username");
boolean admin= false;
boolean test =false;
if(un!=null)
test = (boolean)request.getSession().getAttribute("isAdminUser");

if(un!=null)
	if(test)
		admin = true;
//if (user != null)
//	un = user.getUsername();


%>
<!DOCTYPE html>
<html>
<head>
	
	<meta charset="UTF-8">
	<meta name="author" content="Michele Volpe">
	<title>Zero Fantasy</title>
	<link href="style.css" rel="stylesheet" type ="text/css">
	
	<style>
		  #logo {
		    display: block;
		    margin: 0 auto;
		    width: 20vw;
  	 	 	height: auto;
		  }
	
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

    </style>
</head>
<body>
<body style= background-color:#00A86B >
	<div class="topnav" id="menu">
		<a href="<%=response.encodeRedirectURL("home.jsp") %>"  class=active>Home</a>
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

<h1 class="display-1" id="tit" align="center">Zero Fantasy</h1>
<img id="logo" src="./imgs/logo2.png" alt="logo">
</body>
</html>