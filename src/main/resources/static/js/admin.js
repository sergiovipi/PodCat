/*	IOC 2022 S2
	DAW Desenvolupament d'Aplicacions Web
	M12B0 Projecte de desenvolupament d'aplicacions web
	Grup 1 Pied Piper: PodCat
	Script Zona ADMIN
*/

// Elements Generals
var menu = document.getElementsByClassName('menu');
var taula = document.getElementById('taula');
var titol = document.getElementById('titol');
var par = document.getElementById('paragraf');
// var checkburg = document.getElementsByClassName('checkburg')[0];
var modalFons = document.getElementsByClassName('modalFons')[0];
var cancellar = document.getElementsByClassName('cancellar');

// Elements Usuaris
var modUsuari = document.getElementsByClassName('modalModUsuari')[0];
var modificarUs = document.getElementById('modificarUs');

// Elements Canals
var modCanal = document.getElementsByClassName('modalModCanal')[0];
var modificarCa = document.getElementById('modificarCa');

// Elements Podcast
var modPodcast = document.getElementsByClassName('modalModPodcast')[0];
var modificarPo = document.getElementById('modificarPo');

// MENÚ 
for(let i=0; i<menu.length; i++){
	menu[i].addEventListener('click', function(e){
		taula.innerHTML = "";
		// checkburg.checked = false;
		
		// Menú actiu
		for(let j=0; j<menu.length; j++){
			menu[j].parentNode.classList.remove('actiu');
		}
		menu[i].parentNode.classList.add('actiu');
		
		var seccio = this.getAttribute('seccio');
		if(seccio == 'usuaris'){
			titol.innerHTML = "USUARIS";
			par.innerHTML = "Tots els usuaris registrats a la plataforma";
			usuaris();
		}else if(seccio == 'canals'){
			titol.innerHTML = "CANALS";
			par.innerHTML = "Tots els canals de podcasts";
			canals();
		}else if(seccio == 'podcasts'){
			titol.innerHTML = "PODCASTS";
			par.innerHTML = "Tots els podcasts publicats";
			podcasts();
		}
	});
}


// Mostrar missatge
function missatge(tipus, m){
	// Creem el popup d'error
	var capaError = document.createElement("div");
	capaError.classList.add("popup");
	
	let textInici = "";
	if(tipus == 'missatge'){
		capaError.classList.add("popupMissatge");
	}else if(tipus == 'alerta'){
		textInici = "<span class='warning'>&#9888;</span> Alerta!";
		capaError.classList.add("popupAlerta");
	}else if(tipus == 'error'){
		textInici = "<span class='warning'>&#9888;</span> Error!";
		capaError.classList.add("popupError");
	}
	capaError.innerHTML = textInici+" "+m;
	document.body.appendChild(capaError);
	// Eliminar la capa al cap de 5 segons
	setTimeout(function(){ capaError.remove(); }, 5000);
}


// Eliminar Usuari/Canal/Podcast
function eliminar(id, tipus){
	if(tipus == 'usuari'){
		var url = '/api/v1/usuaris/'+id;
		var mok = "S'ha eliminat l'usuari correctament";
		var mal = "No s'ha pogut eliminar l'usuari<br>perquè té un o més canals associats";
	}else if(tipus == 'canal'){
		var url = '/api/v1/canals/'+id;
		var mok = "S'ha eliminat el canal correctament";
		var mal = "No s'ha pogut eliminar el canal<br>perquè té un o més podcasts associats";
	}else if(tipus == 'podcast'){
		var url = '/api/v1/podcasts/'+id;
		var mok = "S'ha eliminat el podcast correctament";
		var mal = "No s'ha pogut eliminar el podcast<br>per un error desconegut";
	}
	
	if(confirm("Estàs segur que vols eliminar aquest "+tipus+"?")){
		fetch(url, { method: 'DELETE' })
		.then(async response => {
			const isJson = response.headers.get('content-type')?.includes('application/json');
			const correcte = isJson && await response.json();
			console.log(correcte);

			if (!response.ok) {
				// Control d'errors
				missatge('error', "No s'ha pogut fer la petició HTTP");
			}else{
				if(correcte){
					// Eliminem la fila de la taula de l'element que eliminem
					var trs = document.getElementsByTagName("tr");
					for(let i=1; i<trs.length; i++){
						if(id == trs[i].getAttribute('idtr')){
							trs[i].remove();
							i--;
						}
					}
					missatge("missatge", mok);
				}else{
					missatge("alerta", mal);
				}
			}
		})
		.catch(error => {
			missatge('error', error);
		});
	}
}


// Button Cancel·lar
for(let i=0; i<cancellar.length; i++){
	cancellar[i].onclick = function(){
		tancarModal();
	};
}

// Tancar modal
function tancarModal(){
	if(!modUsuari.classList.contains("ocult")){
		modUsuari.classList.add("ocult");
	}
	if(!modCanal.classList.contains("ocult")){
		modCanal.classList.add("ocult");
	}
	if(!modPodcast.classList.contains("ocult")){
		modPodcast.classList.add("ocult");
	}
	modalFons.classList.add("ocult");
}


/* OBSOLET
// Creació de l'objecte en AJAX 
function conObj(){
	var httpRequest;
	// Processament de respostes com a JSON
	if(window.XMLHttpRequest) { // Mozilla, Safari, IE7+
		httpRequest = new XMLHttpRequest();
	}else if(window.ActiveXObject) { // IE 6 i anteriors
		httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
	}else{
		console.error("Error! Aquest navegador no suporta AJAX");
	}
	return httpRequest;
}
*/
