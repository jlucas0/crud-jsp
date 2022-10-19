/**
 * 
 */
 var abierto = false;
 botonBurger.onclick = function(){
	if(abierto){
		abierto = false;
		botonBurger.classList.remove("abierto");
		navMovil.classList.remove("abierto");
	}
	else{
		abierto = true;
		botonBurger.classList.add("abierto");
		navMovil.classList.add("abierto");
	}
} 