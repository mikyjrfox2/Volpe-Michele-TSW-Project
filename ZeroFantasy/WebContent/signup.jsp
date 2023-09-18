<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,it.unisa.model.*, it.unisa.control.*"%>
    <%
String err=(String)request.getAttribute("error");

%>
<!DOCTYPE html>
<html>
<head>
<script>
function validateUsername(username) {
    var alphanumericRegex = /^[a-zA-Z0-9]+$/;
    
    if (!alphanumericRegex.test(username)) {
        document.getElementById("username-error").textContent = "L'username deve contenere solo caratteri alfanumerici.";
        return;
    }
    document.getElementById("username-error").textContent = "";
    
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            var response = JSON.parse(xhttp.responseText);
            if (response.isValid) {
                // L'username è valido, nessun messaggio di errore
                document.getElementById("username-error").textContent = "";
            } else {
                // L'username non è valido, mostra un messaggio di errore
                document.getElementById("username-error").textContent = "L'username è già in uso.";
            }
        }
    };
    xhttp.open("GET", "UsernameValidationServlet?username=" + username, true);
    xhttp.send();
}
</script>

	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Zero Fantasy | Sign-up</title>
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
    
        /* Stili per il form di login */
        form {
            width: 400px;
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
		<div class="navright" id="logEsignin">
			<a href="<%=response.encodeRedirectURL("AdminLogin.jsp")%>">Accedi</a>
			<a href="<%=response.encodeRedirectURL("signup.jsp")%>"  class=active>Iscriviti</a>
		</div>	
	</div>
	
	<h1>Benvenuto</h1>
    <h1>Effettua la registrazione</h1>
	<form action="Signup" method="Post" >
	
		<%if(err!=null) {%><h2 style="color:red"><%=err %></h2><%} %>
		
		<span id="username-error" style="color: red;"></span>
		<label for="username">Username</label> 
		
		<input type="text" placeholder="Inserisci username univoco" name="username" class=text required onkeyup="validateUsername(this.value)"><br>

		<br> <label for="password">Password</label> 
		<input type="password" placeholder="Inserisci password" name="password" class=text required><br>

		<br> <label for="psw-repeat">Ripeti Password</label> 
		<input type="password" placeholder="Ripeti password" name="psw-repeat" class=text required><br> 
		
		<br>
		<input type="submit"  value=Iscriviti >
		
		<br>
	</form>
</body>
</html>