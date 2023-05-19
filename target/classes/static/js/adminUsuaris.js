/*	IOC 2022 S2
	DAW Desenvolupament d'Aplicacions Web
	M12B0 Projecte de desenvolupament d'aplicacions web
	Grup 1 Pied Piper: PodCat
	Script Zona ADMIN USUARIS

Recursos
// fetch	https://jasonwatmore.com/post/2021/09/21/fetch-http-delete-request-examples
// PATCH	https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch
*/


// USUARIS

// Veure podcasts d'un usuari
function podcastsUsuari(id){
	taula.innerHTML = "";
	titol.innerHTML = "";
	par.innerHTML = "";
	podcasts('usuari',id);
}

// Veure canals d'un usuari
function canalsUsuari(id){
	taula.innerHTML = "";
	titol.innerHTML = "";
	par.innerHTML = "";
	canals('usuari',id);
}


// Llistar USURAIS
function usuaris(){
	var usTr = document.createElement("tr");
	usTr.innerHTML = "<th></th><th>Usuari</th><th>Rol</th><th>Nom</th><th>Cognom</th><th>Email</th><th></th><th></th><th></th><th></th>";
	taula.appendChild(usTr);
	
	// GET Usuaris
	fetch('/api/v1/usuaris')
    .then(async response => {
        const isJson = response.headers.get('content-type')?.includes('application/json');
        const dades = isJson && await response.json();
        // Check for error response
        if (!response.ok) {
            missatge("error","No s'ha pogut rebre la petició HTTP");
        }else{
			// console.log(dades);
			var n = 1;
			for(const u in dades){
				var usTr = document.createElement("tr");
					usTr.setAttribute("idtr", dades[u].id);
					var usTdId = document.createElement("td");
						usTdId.innerHTML = n++ +".";
						usTr.appendChild(usTdId);
					var usTdUsername = document.createElement("td");
						usTdUsername.innerHTML = "<span class='strong'>"+dades[u].username+"</span>";
						usTr.appendChild(usTdUsername);
					var usTdRol = document.createElement("td");
						usTdRol.innerHTML = dades[u].rol;
						usTr.appendChild(usTdRol);
					var usTdNom = document.createElement("td");
						usTdNom.innerHTML = dades[u].nom;
						usTr.appendChild(usTdNom);
					var usTdCognom = document.createElement("td");
						usTdCognom.innerHTML = dades[u].cognom;
						usTr.appendChild(usTdCognom);
					var usTdEmail = document.createElement("td");
						usTdEmail.innerHTML = dades[u].email;
						usTr.appendChild(usTdEmail);
					var usTdCanals = document.createElement("td");
						usTdCanals.innerHTML = "<button class='button button2' onclick='podcastsUsuari("+dades[u].id+")'>Podcasts</button>";
						usTr.appendChild(usTdCanals);
					var usTdPodcasts = document.createElement("td");
						usTdPodcasts.innerHTML = "<button class='button button2' onclick='canalsUsuari("+dades[u].id+")'>Canals</button>";
						usTr.appendChild(usTdPodcasts);
					var usTdEdit = document.createElement("td");
						usTdEdit.innerHTML = "<button class='button button1 efecteButton' onclick='modificarUsuari("+dades[u].id+")'><i class='fas fa-user-edit'></i></button>";
						usTr.appendChild(usTdEdit);
					var usTdDelete = document.createElement("td");
						usTdDelete.innerHTML = "<button class='button button1 efecteButton' onclick='eliminar("+dades[u].id+",\"usuari\")'><i class='fa fa-trash'></i></button>";
						usTr.appendChild(usTdDelete);
				taula.appendChild(usTr);
			}
		}
	})
	.catch((error) => {
		missatge('error', error);
	});
}
	

// Modificar Usuari
function modificarUsuari(id){
	// console.log("Modificar usuari "+id);
	// GET Usuari: el posem al formulari com a valor inicial
	fetch('/api/v1/usuaris/'+id)
    .then(async response => {
        const isJson = response.headers.get('content-type')?.includes('application/json');
        const dades = isJson && await response.json();
        // check for error response
        if (!response.ok) {
			// Control d'errors
            missatge("error","No s'ha pogut rebre la petició HTTP");
        }else{
			modUsuari.classList.remove("ocult");
			modalFons.classList.remove("ocult");
			// console.log(dades);
			document.getElementById('modId').value = dades.id;
			document.getElementById('modUsername').value = dades.username;
			document.getElementById('modNom').value = dades.nom;
			document.getElementById('modCognom').value = dades.cognom;
			document.getElementById('modEmail').value = dades.email;
		}
	})
	.catch((error) => {
		missatge('error', error);
	});
}

// Modificar Usuari / Enviar dades
modificarUs.onclick = function(){
	tancarModal();
	var idMod = document.getElementById('modId').value;
	// PATCH: Modifiquem l'usuari
	fetch('/api/v1/usuaris/'+idMod, {
		method: 'PATCH',
		body: JSON.stringify({
			username: document.getElementById('modUsername').value,
			nom: document.getElementById('modNom').value,
			cognom: document.getElementById('modCognom').value,
			email: document.getElementById('modEmail').value
		}),
		headers: {
			'Content-type': 'application/json; charset=UTF-8',
		},
	})
	.then(async response => {
		const isJson = response.headers.get('content-type')?.includes('application/json');
		const dades = isJson && await response.json();
		// Control d'errors
		if (!response.ok) {
			missatge("error","No s'ha pogut establir la petició HTTP");
		}else if(!dades){ // Si el back-end retorna false
			missatge("error","No s'han pogut modificar les dades");
		}else{
			// console.log(dades);
			// Actualitzem la fila de l'usuari modificat
			var trs = document.getElementsByTagName("tr");
			for(let i=0; i<trs.length; i++){
				if(idMod == trs[i].getAttribute('idtr')){
					trs[i].childNodes[1].innerHTML = "<span class='strong'>"+dades.username+'</span>';
					trs[i].childNodes[3].innerHTML = dades.nom;
					trs[i].childNodes[4].innerHTML = dades.cognom;
					trs[i].childNodes[5].innerHTML = dades.email;
				}
			}
			missatge("missatge", "S'ha modificat l'usuari");
		}
	})
	.catch((error) => {
		missatge('error', error);
	});
};


