function createNewCookie(username, asta_id) {
	//onde evitare problemi di formattazione
	
	
	document.cookie = username.replace(/(\r\n|\n|\r)/gm, "") + "=" + asta_id +";"+expires+ ";" + "path=/ ; Secure";
}

function getCookieValue(username) {
	//cerca acquisto o vendita dato un predefinito id;
	var username = username.replace(/(\r\n|\n|\r)/gm, "") + "=";
	var ca = document.cookie.split(';');


	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(username) == 0) {
			return c.substring(username.length, c.length);
		}
	}
	return "";
}

function updateOldCookie(username, lastAction) {
	const d = new Date();
	d.setTime(d.getTime() + (30 * 24 * 60 * 60 * 1000));
	let expires = "expires=" + d.toUTCString();
	//provo a creare la lista) mi faccio passare una lista e la formatto in cookie
	document.cookie = username.replace(/(\r\n|\n|\r)/gm, "") + "=" + lastAction +";"+expires+"; path=/ ; Secure";
}

function cookieExistence(username) {
	if (document.cookie.split(';').some((item) => item.trim().startsWith(username.replace(/(\r\n|\n|\r)/gm, "")))) {
		return true;
	}
	return false;
}

//carica gli id delle aste
function getIdFromCookieSet(username) {
	var cookieRecipe = getCookieValue(username);
	cookieComponents = cookieRecipe.split(',');
	const aste_set = new Set();

	for (const asta of cookieComponents) {
		aste_set.add(asta);
	}

	//nice, così funziona anche
	//aste_set.forEach(a => console.log(a));
	var lista_asteId = Array.from(aste_set).join(' ');
	lista_asteId.replace("%", ",");

	return lista_asteId;

}

function returnLastValueCookie(username) {
	//ritorna l'ultima istanza del campo value di un determinato cookie
	var rawCookies = getCookieValue(username);
	var rawSplitCookies = rawCookies.split(',');

	var arrayCookie = Array.from(rawSplitCookies);

	//getta l'ultimo elemento

	var lastNotEmptyValue = arrayCookie.length - 2;

	return lastNotEmptyValue;
}/**
 * 
 */