/**
 * Controllo dati inseriti dall'utente per la ricarica
 */

function validateCreditCardNumber(card) {
	var visaPattern = /^(?:4[0-9]{12}(?:[0-9]{3})?)$/;
	var mastPattern = /^(?:5[1-5][0-9]{14})$/;
	var amexPattern = /^(?:3[47][0-9]{13})$/;
	var discPattern = /^(?:6(?:011|5[0-9][0-9])[0-9]{12})$/; 
	if (card.value.match(visaPattern)) {
		return true;
	}else if(card.value.match(mastPattern)){
		return true;
	}else if(card.value.match(amexPattern)){
		return true;
	}else if(card.value.match(discPattern)){
		return true;
	}else {
		alert("Numero di carta di credito non valido!");
		card.focus();
		return false;
	}
}


function validateAmount(amount){
	var amountNumbers = /^\d+(\.\d{1,2})?$/
	if (amount.value.match(amountNumbers)) {
		return true;
	}
	else {
		alert("Importo non valido!");
		amount.focus();
		return false;
	}
}

function validateCard(){
	var valid = true;
	var cardNumber = document.getElementsByName("numcarta")[0];

	if (!validateCreditCardNumber(cardNumber)) {
		valid = false;
		cardNumber.classList.add("error");
	} else {
		cardNumber.classList.remove("error");
	}

	var amount = document.getElementsByName("importo")[0];
	if (!validateAmount(amount)) {
		valid = false;
		amount.classList.add("error");
	} else {
		amount.classList.remove("error");
	}


	if (valid) 
		document.getElementById("form").submit();
	
}

function validatePaypal(){
	var valid = true;

	var amount = document.getElementsByName("importo")[0];
	if (!validateAmount(amount)) {
		valid = false;
		amount.classList.add("error");
	} else {
		amount.classList.remove("error");
	}

	if (valid) 
		document.getElementById("form").submit();
	
}
