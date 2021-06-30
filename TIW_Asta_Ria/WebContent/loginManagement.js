/**
 * Login management
 */


(function() { // avoid variables ending up in the global scope
  document.getElementById("loginbutton").addEventListener('click', (e) => {
    var form = e.target.closest("form");

    if (form.checkValidity()) {
      makeCall("POST", 'LoginCheck', e.target.closest("form"),
        function(req) {
          if (req.readyState == XMLHttpRequest.DONE) {
            var message = req.responseText;
			//controlla il messaggio passato dalla servlet tramite la funzione di call back
            switch (req.status) {
              case 200:
            	sessionStorage.setItem('username', message);
                window.location.href = "Home.html";
                break;
              case 400: // bad request
            	document.getElementById("id_alert").textContent = "Bad request";
                break;
              case 401: // unauthorized
                  document.getElementById("id_alert").textContent = "Utente non autorizzato";
                  break;
			case 403: // unauthorized
                  document.getElementById("id_alert").textContent = "utente non autorizzato";
                  break;
              case 500: // server error
            	document.getElementById("id_alert").textContent = "Errore interno del server";

                break;
            }
          }
        }
      );
    } else {
    	 form.reportValidity();
    }
  });

})();