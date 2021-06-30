//BUG: 
//quando chiude asta aperta non refresha


(function() {

	var listaAsteAperte,
		pageOrchestrator = new PageOrchestrator();

	window.addEventListener("load", () => {
		//se la sessione è vuota rimanda al login
		if (sessionStorage.getItem("username") == null) {
			window.location.href = "index.html";
		} else {
			pageOrchestrator.start();
			if (cookieExistence(sessionStorage.getItem("username"))) {
				// il cookie esiste
				Manager.showVendita();
				Manager.resetAcquisto();
				Manager.resetdettagliPage();
			}
			else {
				//il cookie non esiste
				Manager.showAcquisto();
				Manager.resetdettagliPage();
				Manager.resetVendita();
			}
			/**
			gestione dei cookie */
			// initialize the components
			pageOrchestrator.refresh();
		} // display initial content
	}, false);

	function PersonalMessage(_username, messagecontainer) {
		this.username = _username;
		this.show = function() {
			messagecontainer.textContent = this.username;
		}
	}



	//GET DELLE ASTE APERTE

	function listaAsteAperte(_alert, _listcontainer, _listcontainerbody) {
		this.alert = _alert;
		this.listcontainer = _listcontainer;
		this.listcontainerbody = _listcontainerbody;

		this.reset = function() {
			this.listcontainer.style.visibility = "hidden";
		}

		this.show = function() {
			var self = this;
			makeCall("GET", "GetListaAsteAperte", null,
				function(req) {
					//fa il parse della risposta della servlet e chiama l'update
					if (req.readyState == 4) {
						var message = req.responseText;
						if (req.status == 200) {
							var asteToShow = JSON.parse(req.responseText);
							if (asteToShow.length == 0) {
								self.alert.textContent = "Non sono presenti aste!";
								return;
							}
							self.update(asteToShow); // self visible by closure
						}

					} else {
						self.alert.textContent = message;
					}

				}
			);
		};


		this.update = function(arrayAste) {
			var row, destcell, datecell, linkcell, anchor;
			this.listcontainerbody.innerHTML = ""; // empty the table body
			// build updated list
			var self = this;
			arrayAste.forEach(function(asta) { // self visible here, not this
				row = document.createElement("tr");
				destcell = document.createElement("td");
				destcell.textContent = asta.asta_id;
				row.appendChild(destcell);
				datecell = document.createElement("td");
				datecell.textContent = asta.titolo;
				row.appendChild(datecell);
				datecell = document.createElement("td");
				datecell.textContent = asta.descrizione;
				row.appendChild(datecell);
				datecell = document.createElement("td");
				datecell.textContent = asta.max_offerta;
				row.appendChild(datecell);
				linkcell = document.createElement("td");
				anchor = document.createElement("a");
				linkcell.appendChild(anchor);
				linkText = document.createTextNode("Show");
				anchor.appendChild(linkText);
				// make list item clickable
				anchor.setAttribute('asta_id', asta.asta_id); // set a custom HTML attribute
				anchor.addEventListener("click", (e) => {
					astaApertaDetails.show(e.target.getAttribute("asta_id")); // the list must know the details container
					listaOfferteAsteAperte.reset();
					listaOfferteAsteAperte.show(e.target.getAttribute("asta_id")); // the list must know the details container
				}, false);
				anchor.href = "#";
				row.appendChild(linkcell);
				self.listcontainerbody.appendChild(row);
				linkcell = document.createElement("td");
				anchor = document.createElement("a");
				linkcell.appendChild(anchor);
				linkText = document.createTextNode("Close");
				anchor.appendChild(linkText);
				// make list item clickable
				anchor.setAttribute('asta_id', asta.asta_id);
				anchor.addEventListener("click", (e) => {
					// quando clicco chiudo e aggiorno
					listaAsteChiuse.reset();
					listaAsteAperte.reset();
					listaAsteAperte.close(e.target.getAttribute("asta_id"));
					
				}, false);
				anchor.href = "#";
				row.appendChild(linkcell);
				self.listcontainerbody.appendChild(row);
			});
			this.listcontainer.style.visibility = "visible";

		}


		//quando chiudo se va a buon fine riaggiorno chiamando l'update
		this.close = function(asta_id) {
			var self = this;
			makeCall("POST", "CloseAstaAperta?asta_id=" + asta_id, null,
				function(req) {
					if (req.readyState == 4) {
						var message = req.responseText;
						if (req.status == 200) {
							self.show();
							listaAsteChiuse.show();
						}
						else {
							self.alert.textContent = message;
						}
					}
				}
			);

		};
	}




	// GET DELLE ASTE CHIUSE
	function listaAsteChiuse(_alert, _listcontainer, _listcontainerbody) {
		this.alert = _alert;
		this.listcontainer = _listcontainer;
		this.listcontainerbody = _listcontainerbody;

		this.reset = function() {
			this.listcontainer.style.visibility = "hidden";
		}

		this.show = function() {
			//cera next nella function(next)
			var self = this;
			makeCall("GET", "GetListaAsteChiuse", null,
				function(req) {
					if (req.readyState == 4) {
						var message = req.responseText;
						if (req.status == 200) {
							var asteToShow = JSON.parse(req.responseText);
							if (asteToShow.length == 0) {
								self.alert.textContent = "Non sono presenti aste!";
								return;
							}
							//chiama l'update per creare l'htm	l
							self.update(asteToShow);
						}

					} else {
						self.alert.textContent = message;
					}
				}
			);
		};


		this.update = function(arrayAste) {
			var elem, i, row, destcell, datecell, linkcell, anchor;
			this.listcontainerbody.innerHTML = ""; // empty the table body
			// build updated list
			var self = this;
			arrayAste.forEach(function(asta) { // self visible here, not this
				row = document.createElement("tr");
				destcell = document.createElement("td");
				destcell.textContent = asta.asta_id;
				row.appendChild(destcell);
				datecell = document.createElement("td");
				datecell.textContent = asta.titolo;
				row.appendChild(datecell);
				datecell = document.createElement("td");
				datecell.textContent = asta.descrizione;
				row.appendChild(datecell);
				datecell = document.createElement("td");
				datecell.textContent = asta.prezzo_vendita;
				row.appendChild(datecell);
				linkcell = document.createElement("td");
				anchor = document.createElement("a");
				linkcell.appendChild(anchor);
				linkText = document.createTextNode("Show");
				anchor.appendChild(linkText);
				anchor.setAttribute('asta_id', asta.asta_id);
				anchor.addEventListener("click", (e) => {
					//mostra i dettagli di asta chiusa chiamando la show
					astaChiusaDetails.show(e.target.getAttribute("asta_id")); // the list must know the details container
				}, false);
				anchor.href = "#";
				row.appendChild(linkcell);
				self.listcontainerbody.appendChild(row);
			});
			this.listcontainer.style.visibility = "visible";

		}



	}



	//DETTAGLI DELLE ASTE CHIUSE
	//TUTTO OK ma non funziona importo 
	function AstaChiusaDetails(options) {
		this.alert = options['alert'];
		this.detailcontainer = options['detailcontainerChiusa'];

		this.descrizione = options['descrizione'];
		this.prezzo = options['prezzo'];
		this.spedizione = options['spedizione'];
		this.data_apertura = options['data_apertura'];





		//crea la get per la mission details dell'asta specifica con asta_id
		//sistemare la visualizzazioni di eventi
		this.show = function(asta_id) {
			var self = this;
			makeCall("GET", "GetAstaChiusaDetails?asta_id=" + asta_id, null,
				function(req) {
					if (req.readyState == 4) {
						var message = req.responseText;
						if (req.status == 200) {
							var aste = JSON.parse(req.responseText);
							//se tutto ok chiamo l'update e mostro i dettagli
							self.update(aste);
							self.detailcontainer.style.visibility = "visible";
						}
						if (req.status == 403) {
							window.location.href = req.getResponseHeader("Location");
							window.sessionStorage.removeItem('username');
						}
						else if (req.status != 200) {
							self.alert.textContent = message;
						}
					}
				}
			);
		};




		this.reset = function() {
			this.detailcontainer.style.visibility = "hidden";

		}

		//passo tutte le info da mostrare 
		this.update = function(m) {
			this.descrizione.textContent = m.descrizione;
			this.prezzo.textContent = m.prezzo_vendita;
			this.spedizione.textContent = m.spedizione
				;
			this.data_apertura.textContent = m.data_apertura;

		}
	}






	//GESTIONE BOTTONE CREA ASTE E FORM DELLA CREAZIONE ASTE
	function Wizard(wizardId, alert) {
		this.wizard = wizardId;
		this.alert = alert;



		this.registerEvents = function(orchestrator) {
			//seleziona il bottone "button" e il fildset più vicino
			this.wizard.querySelector("input[type='button'].submit").addEventListener('click', (e) => {
				var eventfieldset = e.target.closest("fieldset"),
					valid = true;
				for (i = 0; i < eventfieldset.elements.length; i++) {
					//controlla la validita dei data
					if (!eventfieldset.elements[i].checkValidity()) {
						eventfieldset.elements[i].reportValidity();
						valid = false;
						break;
					}
				}
				//se valido chiama la creaAstaAperta
				if (valid) {
					var self = this;
					makeCall("POST", 'CreaAstaAperta', e.target.closest("form"),
						function(req) {
							if (req.readyState == XMLHttpRequest.DONE) {
								var message = req.responseText; // error message or mission id
								if (req.status == 200) {
									orchestrator.refresh(message);
								}
								if (req.status == 403) {
									window.location.href = req.getResponseHeader("Location");
									window.sessionStorage.removeItem('username');
								}
								else if (req.status != 200) {
									self.alert.textContent = message;
									self.reset();
								}
							}
						}
					);
				}
			});

			this.reset = function() {
				var fieldsets = document.querySelectorAll("#" + this.wizard.id + " fieldset");
				fieldsets[0].hidden = false;
				//fieldsets[1].hidden = true;
				//fieldsets[2].hidden = true;
			}


		}
	}





	//DETTAGLI DELLE ASTE aperte
	//TUTTO OK
	function AstaApertaDetails(options) {
		this.alert = options['alert'];
		this.detailcontainer = options['detailcontainer'];
		this.descrizione = options['descrizione'];
		this.minimo_rialzo = options['minimo_rialzo'];
		this.prezzo_iniziale = options['prezzo_iniziale'];
		this.image = options['image'];
		this.titolo = options['titolo'];
		this.asta_id = options['asta_id'];
		this.max_offerta = options['max_offerta'];
		this.data_chiusura = options['data_chiusura'];
		this.data_apertura = options['data_apertura'];




		//crea la get per la mission details dell'asta specifica con asta_id
		this.show = function(asta_id) {
			var self = this;
			makeCall("GET", "GetAstaApertaDetails?asta_id=" + asta_id, null,
				function(req) {
					if (req.readyState == 4) {
						var message = req.responseText;
						if (req.status == 200) {
							var asta = JSON.parse(req.responseText);
							self.update(asta);
							self.detailcontainer.style.visibility = "visible";
						}
						if (req.status == 403) {
							window.location.href = req.getResponseHeader("Location");
							window.sessionStorage.removeItem('username');
						}
						else if (req.status != 200) {
							self.alert.textContent = message;

						}
					}
				}
			);
		}; 1

		this.reset = function() {
			this.detailcontainer.style.visibility = "hidden";

		}

		this.update = function(m) {

			this.titolo.textContent = m.titolo;
			this.asta_id.textContent = m.asta_id;
			this.max_offerta.textContent = m.max_offerta;
			this.data_chiusura.textContent = m.scadenza;
			this.data_apertura.textContent = m.dataApertura;
			this.descrizione.textContent = m.descrizione;
			this.minimo_rialzo.textContent = m.minimo_rialzo;
			this.prezzo_iniziale.textContent = m.prezzo_iniziale;
			this.image.src = "data:image/png;base64," + m.img;


		}
	}



	//GESTIONE CLICK DI ASTE APERTE E DI VISUALIZZAZZIONE DI TUTTE LE OFFERTE


	function listaOfferteAsteAperte(_alert, _listcontainer, _listcontainerbody, _listcontainerbodyAsteRicercate, _listcontainerOfferteRicercate) {
		this.alert = _alert;
		this.listcontainer = _listcontainer;
		this.listcontainerbody = _listcontainerbody;
		this.listcontainerbodyAsteRicercate = _listcontainerbodyAsteRicercate;
		this.listcontainerOfferteRicercate = _listcontainerOfferteRicercate;

		this.reset = function() {
			this.listcontainer.style.visibility = "hidden";
				/**this.listcontainerbodyAsteRicercate.style.visibility = "hidden";
				this.listcontainerOfferteRicercate.style.visibility = "hidden";
				this.listcontainerbody.style.visibility = "hidden";
			*/}

		this.show = function(asta_id) {
			var self = this;
			makeCall("GET", "GetAstaApertaOfferts?asta_id=" + asta_id, null,
				function(req) {

					if (req.readyState == 4) {
						var message = req.responseText;
						if (req.status == 200) {
							var OfferteasteToShow = JSON.parse(req.responseText);
							if (OfferteasteToShow.length == 0) {
								self.alert.textContent = "Non sono presenti offerte!";
								return;
							}
							self.update(OfferteasteToShow);
						}
						if (req.status == 403) {
							window.location.href = req.getResponseHeader("Location");
							window.sessionStorage.removeItem('username');
						}

					} else if (req.status != 200) {
						self.alert.textContent = message;
					}

				}
			);
		};

		this.showOfferte = function(asta_id) {
			var self = this;
			makeCall("GET", "GetAstaApertaOfferts?asta_id=" + asta_id, null,
				function(req) {

					if (req.readyState == 4) {
						var message = req.responseText;
						if (req.status == 200) {
							var OfferteasteToShow = JSON.parse(req.responseText);
							if (OfferteasteToShow.length == 0) {
								self.alert.textContent = "Non sono presenti offerte!";
								return;
							}
							self.updateSearchedOffert(OfferteasteToShow);
						}
						if (req.status == 403) {
							window.location.href = req.getResponseHeader("Location");
							window.sessionStorage.removeItem('username');
						}
					
					}

				}
			);
		};




		this.update = function(arrayAsteOfferte) {
			var elem, i, row, destcell, datecell, linkcell, anchor;
			this.listcontainerbody.innerHTML = ""; // empty the table body
			// build updated list
			var self = this;
			arrayAsteOfferte.forEach(function(offerta) { // self visible here, not this
				row = document.createElement("tr");
				destcell = document.createElement("td");
				destcell.textContent = offerta.offerente;
				row.appendChild(destcell);

				datecell = document.createElement("td");
				datecell.textContent = offerta.importo;
				row.appendChild(datecell);

				datecell = document.createElement("td");
				datecell.textContent = offerta.data_offerta;
				row.appendChild(datecell);

				self.listcontainerbody.appendChild(row);
			});
			this.listcontainer.style.visibility = "visible";

		}

		this.updateSearchedOffert = function(arrayAsteOfferte) {
			var elem, i, row, destcell, datecell, linkcell, anchor;
			this.listcontainerbodyAsteRicercate.innerHTML = ""; // empty the table body
			// build updated list
			var self = this;
			arrayAsteOfferte.forEach(function(offerta) { // self visible here, not this
				row = document.createElement("tr");
				destcell = document.createElement("td");
				destcell.textContent = offerta.offerente;
				row.appendChild(destcell);

				datecell = document.createElement("td");
				datecell.textContent = offerta.importo;
				row.appendChild(datecell);

				datecell = document.createElement("td");
				datecell.textContent = offerta.data_offerta;
				row.appendChild(datecell);

				self.listcontainerbodyAsteRicercate.appendChild(row);
			});
			this.listcontainerOfferteRicercate.style.visibility = "visible";

		}





	}


	//uso questa funzione per nascondere e mostrare vari pezzi del codice quando clicco acquista e vendita
	function Manager(options) {
		this.tableVendita = options['tableVendita'];
		this.tableAcquista = options['tableAcquista'];
		this.dettagliPage = options['dettagliPage'];



		// è il posto giusto?? bah chissa mannaggaia al cane maledetto di 
		this.showAcquisto = function() {
			this.tableAcquista.style.display = "block";
			//document.getElementById('iframe1').contentDocument.location.reload(true);
			//updateOldCookie(sessionStorage.getItem("username"), "acquisto");



		};

		this.showVendita = function() {
			this.tableVendita.style.display = "block";
			//faccio un append al cookie con il valore vendo
			var oldcookiepart = getCookieValue(sessionStorage.getItem("username"));
			updateOldCookie(sessionStorage.getItem("username"), oldcookiepart + "vendo" + ",");

		};

		this.showdettagliPage = function() {
			this.dettagliPage.style.display = "block";
		};

		this.resetAcquisto = function() {

			this.tableAcquista.style.display = "none";

		};

		this.resetVendita = function() {

			this.tableVendita.style.display = "none";

		};

		this.resetdettagliPage = function() {
			this.dettagliPage.style.display = "none";
		};



	}


	//NELLO START SI COLLEGANO I VARI ELEMENTI MENTRE NEL REFRESH SI GESTISCE LA VISIONE

	function PageOrchestrator() {
		var alertContainer = document.getElementById("id_alert");

		this.start = function() {

			personalMessage = new PersonalMessage(sessionStorage.getItem('username'),
				document.getElementById("id_username"));
			personalMessage.show();


			listaAsteAperte = new listaAsteAperte(
				alertContainer,
				document.getElementById("id_listcontainerAstaAperta"),
				document.getElementById("id_listcontainerbodyAstaAperta"));


			listaAsteChiuse = new listaAsteChiuse(
				alertContainer,
				document.getElementById("id_listcontainerAstaChiusa"),
				document.getElementById("id_listcontainerbodyastachiusa"));


			listaOfferteAsteAperte = new listaOfferteAsteAperte(
				alertContainer,
				document.getElementById("id_listcontainerOfferteAstaAperta"),
				document.getElementById("id_listcontainerbodyOfferteAstaAperta"),
				document.getElementById("id_listcontainerbodyOfferteRicercate"),
				document.getElementById("id_listcontainerOfferteRicercate")
			);
			//listaOfferteAsteAperte.reset();



			Manager = new Manager({
				alert: alertContainer,
				tableVendita: document.getElementById("id_vendo"),
				tableAcquista: document.getElementById("id_acquisto"),
				dettagliPage: document.getElementById("id_dettagli_page")

			});





			astaChiusaDetails = new AstaChiusaDetails({
				alert: alertContainer,
				detailcontainerChiusa: document.getElementById("id_detailcontainerAstaChiusa"),
				descrizione: document.getElementById("id_descrizioneChiusa"),
				prezzo: document.getElementById("id_prezzo"),
				spedizione: document.getElementById("id_spedizione"),
				data_apertura: document.getElementById("id_data_apertura")

			});



			astaApertaDetails = new AstaApertaDetails({
				alert: alertContainer,
				detailcontainer: document.getElementById("id_detailcontainerAstaAperta"),
				descrizione: document.getElementById("id_descrizioneAperta"),
				minimo_rialzo: document.getElementById("id_minimo_rialzo"),
				prezzo_iniziale: document.getElementById("id_prezzo_iniziale"),
				image: document.getElementById("id_image"),
				titolo: document.getElementById("id_titolo"),
				asta_id: document.getElementById("id_asta_id"),
				max_offerta: document.getElementById("id_offerta_massima"),
				data_apertura: document.getElementById("data_apertura"),
				data_chiusura: document.getElementById("data_chiusura")
			});


			wizard = new Wizard(document.getElementById("id_createastaform"), alertContainer);
			wizard.registerEvents(this);


			//quando faccio logout
			document.querySelector("a[href='Logout']").addEventListener('click', () => {
				window.sessionStorage.removeItem('id_utente');
			})



			listaByKeyword = new ListAsteByKeyword(
				document.getElementById("id_listcontainerAsteRicercate"),
				alertContainer,
				document.getElementById("id_listcontainerbodyAsteRicercate")
			);

			//form per offerta:
			formForOfferta = new FormForOfferta(
				alertContainer,
				document.getElementById("id_faiOfferta")
			);
			//formForOfferta.reset();

			astaKeywordForm = new AstaByKeywordForm(document.getElementById("id_search"), alertContainer);
			astaKeywordForm.registerEvents(this);

			//quando clicco vendita
			document.getElementById("id_gotoVendita").addEventListener("click", (e) => {
				Manager.showVendita();
				Manager.resetAcquisto();
				Manager.resetdettagliPage();

			}, false);



			//quando clicco acquista
			document.getElementById("id_gotoAcquisto").addEventListener("click", (e) => {
				Manager.showAcquisto();
				Manager.resetdettagliPage();
				Manager.resetVendita();

			}, false);

			astaApertaDetailsForOfferta = new AstaApertaDetailsForOfferta({
				alert: alertContainer,
				detailcontainer: document.getElementById("id_detailcontainerAstaAperta2"),
				descrizione: document.getElementById("id_descrizioneAperta2"),
				minimo_rialzo: document.getElementById("id_minimo_rialzo2"),
				prezzo_iniziale: document.getElementById("id_prezzo_iniziale2"),
				image: document.getElementById("id_image2"),
				titolo: document.getElementById("id_titolo2"),
				asta_id: document.getElementById("id_asta_id2"),
				offerente: document.getElementById("id_offerente2"),
				max_offerta: document.getElementById("id_offerta_massima2"),
				data_apertura: document.getElementById("id_data_apertura2"),
				data_chiusura: document.getElementById("id_data_chiusura2")
			});

			astaCookie = new AstaCookie(
				document.getElementById("id_listcontainerbodyAstaApertaPrev"),
				document.getElementById("id_listcontainerAstaApertaPrev")
			);


		};


		this.refresh = function() {
			alertContainer.textContent = "";
			//lista offerte show è fatto quando viene cliccato details stessa cosa per i details
			listaOfferteAsteAperte.reset();
			listaAsteAperte.reset();
			listaAsteChiuse.reset();
			listaAsteAperte.show();
			listaAsteChiuse.show();
			astaChiusaDetails.reset();
			astaApertaDetails.reset();
			wizard.reset();
			astaCookie.show();
			astaCookie.reset();
			listaByKeyword.reset();


		};
	}

	//new 
	function AstaByKeywordForm(astabyKeywordFormId, alertContainer) {
		this.astaByKeywordFormId = astabyKeywordFormId;
		this.alert = alertContainer;
		this.keyword = null;
		// implementare check validity
		this.registerEvents = function(orchestrator) {
			this.astaByKeywordFormId.querySelector("input[type='button'].submit").addEventListener('click', (e) => {
				var self = this;
				//salva la keyword nella variabile "keyword"
				this.keyword = document.getElementById("id_search").elements['keyword'].value;
				console.log("keyword pricipe "+this.keyword);
				makeCall("POST", "GetAstaApertaByKeyword", e.target.closest("form"),
					function(req) {
						if (req.readyState == XMLHttpRequest.DONE) {
							var message = req.responseText; // error message or mission id
							if (req.status == 200) {
								//salvo la keyword
								//this.keyword = document.getElementById("keyword").value;
								var asteKeyword = JSON.parse(req.responseText);
								//resetta le possibili offerte gia stampate a schermo
								orchestrator.refresh(message);
								listaByKeyword.update(asteKeyword);
							}
							else {
								self.alert.textContent = message;
								
							}
						}
					}
				);
			});
		};

		this.updateOfferteAfterOffertaCorrect = function() {
			console.log(this.keyword);	 
			makeCall("POST", "GetAstaApertaByKeyword?keyword=" + this.keyword, null,
				function(req) {
					if (req.readyState == XMLHttpRequest.DONE) {
						var message = req.responseText; // error message or mission id
						if (req.status == 200) {
							var asteKeyword = JSON.parse(req.responseText);
							//resetta le possibili offerte gia stampate a schermo
							listaByKeyword.update(asteKeyword);
						}
						else {
							self.alert.textContent = message;
						}
					}
				}
			);

		}
	}

	//
	function ListAsteByKeyword(listaAsteKeyword, alert, listKeywordContainerBody) {
		this.alert = alert;
		this.listaAsteKeyword = listaAsteKeyword;
		this.listKeywordContainerBody = listKeywordContainerBody;

		this.reset = function() {
			this.listaAsteKeyword.style.visibility = "hidden";
		}
		//
		this.update = function(arrayAsteKeyword) {
			var row, destcell, datecell, linkcell, anchor;
			this.listKeywordContainerBody.innerHTML = ""; // empty the table body
			// build updated list
			var self = this;
			arrayAsteKeyword.forEach(function(asta) { // self visible here, not this
				row = document.createElement("tr");
				destcell = document.createElement("td");
				destcell.textContent = asta.asta_id;
				row.appendChild(destcell);
				datecell = document.createElement("td");
				datecell.textContent = asta.titolo;
				row.appendChild(datecell);
				datecell = document.createElement("td");
				datecell.textContent = asta.descrizione;
				row.appendChild(datecell);
				datecell = document.createElement("td");
				datecell.textContent = asta.max_offerta;
				row.appendChild(datecell);
				//new

				datecell = document.createElement("td");
				datecell.textContent = asta.scadenza;
				row.appendChild(datecell);
				//gestione immagini	

				//new


				linkcell = document.createElement("td");
				anchor = document.createElement("a");
				linkcell.appendChild(anchor);
				linkText = document.createTextNode("dettagli asta");
				anchor.appendChild(linkText);
				// make list item clickable
				anchor.setAttribute('asta_id', asta.asta_id); // set a custom HTML attribute
				anchor.addEventListener("click", (e) => {
					var a = e.target.getAttribute("asta_id");
					Manager.resetAcquisto();
					Manager.resetVendita();
					Manager.showdettagliPage();
					listaOfferteAsteAperte.reset();
					listaOfferteAsteAperte.showOfferte(e.target.getAttribute("asta_id")); // the list must know the details container
					//mosta form offerta
					formForOfferta.show();
					formForOfferta.processForm(a);
					//mostrare dettagli dell'asta'
					astaApertaDetailsForOfferta.reset();
					astaApertaDetailsForOfferta.show(a);

					//aggiunge l'id ai cookies'
					var oldcookiepart = getCookieValue(sessionStorage.getItem("username"));
					updateOldCookie(sessionStorage.getItem("username"), oldcookiepart + a + ",");

					getIdFromCookieSet(sessionStorage.getItem("username"));

					/**
					chiamata alla festione dei cookies
					 */


				}, false);
				anchor.href = "#";
				row.appendChild(linkcell);
				self.listKeywordContainerBody.appendChild(row);

			});
			this.listaAsteKeyword.style.visibility = "visible";
		}

	}
	function FormForOfferta(alert, formIdFaiOfferta) {
		this.alert = alert;
		this.formIdFaiOfferta = formIdFaiOfferta;

		this.show = function() {
			this.formIdFaiOfferta.style.visibility = "visible";
			formIdFaiOfferta.reset();
		}
		this.reset = function() {
			this.formIdFaiOfferta.style.visibility = "hidden";

		}

		this.processForm = function(asta_id) {
			this.formIdFaiOfferta.querySelector("input[type='button'].submit").addEventListener('click', (e) => {
				var self = this;
				//check validity controlla che la form sia ben formata(niente valori nulli o merdate simili)
				var eventfieldset = e.target.closest("fieldset"),
					valid = true;
				for (i = 0; i < eventfieldset.elements.length; i++) {
					//controlla la validita dei data
					if (!eventfieldset.elements[i].checkValidity()) {
						eventfieldset.elements[i].reportValidity();
						valid = false;
						break;
					}
				}
				//se valido chiama la creaAstaAperta
				if (valid) {
					makeCall("POST", "CreateOfferta?asta_id=" + asta_id, e.target.closest("form"),
						function(req) {
							if (req.readyState == XMLHttpRequest.DONE) {
								var message = req.responseText; // error message or mission id
								if (req.status == 200) {
									listaOfferteAsteAperte.showOfferte(asta_id); // the list must know the details container
									astaKeywordForm.updateOfferteAfterOffertaCorrect();
									astaApertaDetailsForOfferta.show(asta_id);
								}
								else {
									self.alert.textContent = message;
									self.reset();
								}
							}
						}
					);
				}
			});
		}
	}
	/**
	cookie ha la forma key=value nel nostro caso key e lo username e value è l'id dell'asta
	 */



	function AstaApertaDetailsForOfferta(options) {
		this.alert = options['alert'];
		this.detailcontainer = options['detailcontainer'];
		this.descrizione = options['descrizione'];
		this.minimo_rialzo = options['minimo_rialzo'];
		this.prezzo_iniziale = options['prezzo_iniziale'];
		this.image = options['image'];
		this.titolo = options['titolo'];
		this.asta_id = options['asta_id'];
		this.offerente = options['offerente'];
		this.max_offerta = options['max_offerta'];
		this.data_chiusura = options['data_chiusura'];
		this.data_apertura = options['data_apertura'];


		//crea la get per la mission details dell'asta specifica con asta_id
		this.show = function(asta_id) {
			var self = this;
			makeCall("GET", "GetAstaApertaDetails?asta_id=" + asta_id, null,
				function(req) {
					if (req.readyState == 4) {
						var message = req.responseText;
						if (req.status == 200) {
							var asta = JSON.parse(req.responseText);
							self.update(asta);
							self.detailcontainer.style.visibility = "visible";

						}
						else {
							self.alert.textContent = message;

						}
					}
				}
			);
		};

		this.reset = function() {
			this.detailcontainer.style.visibility = "hidden";

		}

		this.update = function(m) {

			this.titolo.textContent = m.titolo;
			this.asta_id.textContent = m.asta_id;
			this.max_offerta.textContent = m.max_offerta;
			this.data_chiusura.textContent = m.scadenza;
			this.data_apertura.textContent = m.dataApertura;
			this.descrizione.textContent = m.descrizione;
			this.minimo_rialzo.textContent = m.minimo_rialzo;
			this.prezzo_iniziale.textContent = m.prezzo_iniziale;
			this.image.src = "data:image/png;base64," + m.img;

		}
	}

	function AstaCookie(id_listcontainerbodyAstaApertaPrev, id_listcontainerAstaApertaPrev) {
		this.listcontainerAstaApertaPrev = id_listcontainerAstaApertaPrev;
		this.body = id_listcontainerbodyAstaApertaPrev;

		this.reset = function() {
			this.listcontainerAstaApertaPrev.style.visibility = "hidden";
		}

		this.show = function() {
			var self = this;
			var rawCookies = getIdFromCookieSet(sessionStorage.getItem("username"));
			if (cookieExistence(sessionStorage.getItem("username"))) {
				makeCall("GET", "GetAsteAperteFromCookies?cookieAstaIdList=" + rawCookies, null,
					function(req) {
						if (req.readyState == 4) {
							var asteCookie = JSON.parse(req.responseText);
							if (req.status == 200) {
								self.update(asteCookie);
								self.listcontainerAstaApertaPrev.style.visibility = "visible";
							}
						}
					}
				);
			};
		}

		this.update = function(arrayAstaCookies) {
			var row, destcell, datecell, linkcell, anchor;
			this.body.innerHTML = ""; // empty the table body
			// build updated list
			var self = this;
			arrayAstaCookies.forEach(function(asta) { // self visible here, not this
				row = document.createElement("tr");
				destcell = document.createElement("td");
				destcell.textContent = asta.asta_id;
				row.appendChild(destcell);
				datecell = document.createElement("td");
				datecell.textContent = asta.titolo;
				row.appendChild(datecell);
				//new
				//new
				linkcell = document.createElement("td");
				anchor = document.createElement("a");
				linkcell.appendChild(anchor);
				linkText = document.createTextNode("dettagli asta");
				anchor.appendChild(linkText);
				// make list item clickable
				anchor.setAttribute('asta_id', asta.asta_id); // set a custom HTML attribute
				anchor.addEventListener("click", (e) => {
					var a = e.target.getAttribute("asta_id");
					Manager.resetAcquisto();
					Manager.resetVendita();
					Manager.showdettagliPage();
					listaOfferteAsteAperte.reset();
					listaOfferteAsteAperte.showOfferte(e.target.getAttribute("asta_id")); // the list must know the details container
					//mosta form offerta
					formForOfferta.show();
					formForOfferta.processForm(a);
					//mostrare dettagli dell'asta'
					astaApertaDetailsForOfferta.reset();
					astaApertaDetailsForOfferta.show(a);

					//aggiunge l'id ai cookies'

					/**
					chiamata alla festione dei cookies
					 */
					//astaCookie.show(sessionStorage.getItem("username"));

				}, false);
				anchor.href = "#";
				row.appendChild(linkcell);
				self.body.appendChild(row);

			});
			this.listcontainerAstaApertaPrev.style.visibility = "visible";
		}
	}


})();