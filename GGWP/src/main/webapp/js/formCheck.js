/**
 * Controllo dati inseriti nel form dall'utente per signup
 */

function validateNickname(nickname) {
	var nicknameformat = /^[A-Za-z0-9\._]+$/;
	if (nickname.value.match(nicknameformat)) {
		return true;
	}
	else {
		alert("Il Nickname puo' contenere solo caratteri alfanumerici, . e _");
		nickname.focus();
		return false;
	}
}

function validateEmail(email) {
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	if (email.value.match(mailformat)) {
		return true;
	}
	else {
		alert("Devi inserire un'email valida!");
		email.focus();
		return false;
	}
}

function validatePass(password) {
	var passwordformat = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;
	if (password.value.match(passwordformat)) {
		return true;
	}
	else {
		alert("Devi inserire una password contenete almeno 6 elementi tra cui almeno una cifra, una lettera maiuscola e una minuscola!");
		password.focus();
		return false;
	}
}

function validateRepeatPass(password, repeatPass) {
	if (password.value == repeatPass.value) {
		return true;
	} else {
		alert("La password inserita nel campo Ripeti Password non corrisponde con quella inserita nel campo Password!");
		return false;
	}
}

function validateName(name) {
	var letters = /^[A-Za-z\s]+$/;
	if (name.value.match(letters)) {
		return true;
	}
	else {
		alert("Il campo Nome puo' avere solo lettere!");
		name.focus();
		return false;
	}
}

function validate() {
	var valid = true;
	var nickname = document.getElementsByName("nickname")[0];

	if (!validateNickname(nickname)) {
		valid = false;
		nickname.classList.add("error");
	} else {
		nickname.classList.remove("error");
	}

	var email = document.getElementsByName("email")[0];
	if (!validateEmail(email)) {
		valid = false;
		email.classList.add("error");
	} else {
		email.classList.remove("error");
	}

	var password = document.getElementsByName("psw")[0];
	if (!validatePass(password)) {
		valid = false;
		password.classList.add("error");
	} else {
		password.classList.remove("error");
		var rpass = document.getElementsByName("psw-repeat")[0];
		if (!validateRepeatPass(password, rpass)) {
			valid = false;
			rpass.classList.add("error");
		} else {
			rpass.classList.remove("error");
		}
	}


	var name = document.getElementsByName("name")[0];
	if (!validateName(name)) {
		valid = false;
		name.classList.add("error");
	} else {
		name.classList.remove("error");
	}

	if (valid) 
		document.getElementById("form").submit();
}

function parcheck(){
	var email=document.getElementById("email").value;
	var pass=document.getElementById("password").value;
	if(email=="" || pass=="")
		alert("Inserire email/password")
	else
		document.getElementById("form").submit()
}
