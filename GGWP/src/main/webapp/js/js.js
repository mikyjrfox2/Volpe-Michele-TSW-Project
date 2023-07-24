function topnav() {
  var x = document.getElementById("menu");
  if (x.className === "topnav") {
    x.className += " responsive";
  } else {
    x.className = "topnav";
  }
}

function check(e) {
	if(e.checked == true)
		e.checked = false;
	else
		e.checked = true;
}

function cart(title){
	 $.getJSON("Carrello?title="+title, function (result){
        if(result.flag){
            $('#cart').text(result.numElements); //aggiorno il numero di elem nel carrello
			alert("Elemento aggiunto al carrello")
        }else alert("Elemento gia' presente nel carrello");
    })
}

function checkUser(user, vg){
		if(user!="null")
			cart(vg)
		 else 
			alert("Effettua il login per acquistare videogiochi")
}

function delCar(vg){
	document.location.href="Carrello?title="+vg+"&del=true"
}
