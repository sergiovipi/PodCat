/*	IOC 2022 S2
	DAW Desenvolupament d'Aplicacions Web
	M12B0 Projecte de desenvolupament d'aplicacions web
	Grup 1 Pied Piper: PodCat
	Script Zona ADMIN CANALS

Recursos
// fetch	https://jasonwatmore.com/post/2021/09/21/fetch-http-delete-request-examples
// PATCH	https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch
*/


// CANALS

// Botó veure podcasts d'un canal
function podcastsCanal(id){
	taula.innerHTML = "";
	titol.innerHTML = "";
	par.innerHTML = "";
	podcasts('canal',id);
}

function canals(tipus, id){
	var usTr = document.createElement("tr");
	usTr.innerHTML = "<th></th><th>Títol</th><th>Descripció</th><th>Imatge</th><th>Usuari</th><th></th><th></th><th></th>";
	taula.appendChild(usTr);

	let urlFetch = '/api/v1/canals';
	if(tipus == 'usuari'){
		// GET Usuaris / Agafem la info de l'usuari
		fetch('/api/v1/usuaris/'+id)
		.then(async response => {
			const isJson = response.headers.get('content-type')?.includes('application/json');
			const dades = isJson && await response.json();
			// Check for error response
			if (!response.ok) {
				missatge("error","No s'ha pogut rebre la petició HTTP");
			}else{
				// console.log(dades);
				titol.innerHTML = "USUARI > "+dades.username+" > CANALS";
				par.innerHTML = "<em>Tots els canals de l'usuari <span class='strong'>"+dades.username+"<span></em>";
			}
		})
		.catch((error) => {
			missatge('error', error);
		});
		urlFetch = '/api/v1/canals/usuari/'+id;
	}

	// GET Canals
	fetch(urlFetch)
    .then(async response => {
        const isJson = response.headers.get('content-type')?.includes('application/json');
        const dades = isJson && await response.json();
        // Check for error response
        if (!response.ok) {
            missatge("error","No s'ha pogut rebre la petició HTTP");
        }else{
			// console.log(dades);
			if(!dades.length){
				var usTr = document.createElement("tr");
				var usTdId = document.createElement("td");
					usTdId.setAttribute("colspan", 8);
					usTdId.innerHTML = "No hi ha canals";
					usTr.appendChild(usTdId);
				taula.appendChild(usTr);
			}else{
				var n = 1;
				for(const u in dades){
					var usTr = document.createElement("tr");
						usTr.setAttribute("idtr", dades[u].id);
						var usTdId = document.createElement("td");
							usTdId.innerHTML = n++ +".";
							usTr.appendChild(usTdId);
						var usTdTitol = document.createElement("td");
							usTdTitol.innerHTML = "<span class='strong'>"+dades[u].titol+'</span>';
							usTr.appendChild(usTdTitol);
						var usTdDesc = document.createElement("td");
							usTdDesc.innerHTML = dades[u].descripcio;
							usTr.appendChild(usTdDesc);
						var usTdImatge = document.createElement("td");
							usTdImatge.innerHTML = dades[u].imatge;
							usTr.appendChild(usTdImatge);
						var usTdUsuari = document.createElement("td");
							usTdUsuari.innerHTML = dades[u].usuari.username;
							usTr.appendChild(usTdUsuari);
						var usTdCanals = document.createElement("td");
							usTdCanals.innerHTML = "<button class='button button2' onclick='podcastsCanal("+dades[u].id+")'>Podcasts</button>";
							usTr.appendChild(usTdCanals);
						var usTdEdit = document.createElement("td");
							usTdEdit.innerHTML = "<button class='button button1 efecteButton' onclick='modificarCanal("+dades[u].id+")'><i class='fas fa-user-edit'></i></button>";
							usTr.appendChild(usTdEdit);
						var usTdDelete = document.createElement("td");
							usTdDelete.innerHTML = "<button class='button button1 efecteButton' onclick='eliminar("+dades[u].id+",\"canal\")'><i class='fa fa-trash'></i></button>";
							usTr.appendChild(usTdDelete);
					taula.appendChild(usTr);
				}
			}
		}
	})
	.catch((error) => {
		missatge('error', error);
	});
}


// Modificar Canal
function modificarCanal(id){
	// console.log("Modificar canal "+id);
	fetch('/api/v1/canals/'+id)
    .then(async response => {
        const isJson = response.headers.get('content-type')?.includes('application/json');
        const dades = isJson && await response.json();
        // Check for error response
        if (!response.ok) {
			// Control d'errors
            missatge("error","No s'ha pogut rebre la petició HTTP");
        }else{
			modCanal.classList.remove("ocult");
			modalFons.classList.remove("ocult");
			// console.log(dades);
			document.getElementById('modCaId').value = dades.id;
			document.getElementById('modCaTitol').value = dades.titol;
			document.getElementById('modCaDesc').value = dades.descripcio;
			document.getElementById('modCaImatge').value = dades.imatge;
			// document.getElementById('modCaUs').value = dades.usuari.id;
		}
	})
	.catch((error) => {
		missatge('error', error);
	});
}

// Modificar Canal / Enviar dades
modificarCa.onclick = function(){
	tancarModal();
	var idModCa = document.getElementById('modCaId').value;
	// PATCH: Modifiquem el canal
	fetch('/api/v1/canals/'+idModCa, {
		method: 'PATCH',
		body: JSON.stringify({
			titol: document.getElementById('modCaTitol').value,
			descripcio: document.getElementById('modCaDesc').value,
			imatge: document.getElementById('modCaImatge').value
		}),
		headers: {
			'Content-type': 'application/json; charset=UTF-8',
		},
	})
	.then(async response => {
		const isJson = response.headers.get('content-type')?.includes('application/json');
		const dades = isJson && await response.json();
		// Control d'errors
		if(!response.ok) {
			missatge("error","No s'ha pogut establir la petició HTTP");
		}else if(!dades){ // Si el back-end retorna false
			missatge("error","No s'han pogut modificar les dades");
		}else{
			// console.log(dades);
			// Actualitzem la fila del canal modificat
			var trs = document.getElementsByTagName("tr");
			for(let i=0; i<trs.length; i++){
				if(idModCa == trs[i].getAttribute('idtr')){
					trs[i].childNodes[1].innerHTML = "<span class='strong'>"+dades.titol+'</span>';
					trs[i].childNodes[2].innerHTML = dades.descripcio;
					trs[i].childNodes[3].innerHTML = dades.imatge;
				}
			}
			missatge("missatge", "S'ha modificat el canal");
		}
	})
	.catch((error) => {
		missatge('error', error);
	});
};
