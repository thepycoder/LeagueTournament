# lol
League of Legends - Project beleidsinformatica

TO-DO

* [x] GUI - teams en spelers invlullen
* [x] teams opdelen in poules
* [x] mogelijke matches genereren binnen een poule
* [x] GUI - match plannen op basis van deze matches
* [ ] Database kolommen genereren op basis van een Matchobject teruggegevn door de APIhandler
* [ ] addTeam, generatePoules en generatePouleMatches moeten hun resultaten in de database kunnen steken via databasehandler
* [ ] planMatch() methode, neemt datum en matchID aan en verandert datum van Matchobject van gegeven matchID in gegeven datum
* [ ] storeMatch() methode, in de databasehandler een gegeven matchobject of lijst van objecten moet in de database gestored worden
* [ ] GUI planMatch(), match selecteren uit matchlijst van nog niet gespeelde matches en datum kunnen ingeven. Als match al een datum had, geef weer en maak duidelijk
* [ ] completeMatch(), das ne groten. Het matchobject moet op completed gezet worden, de data moet via de APIhandler opgehaald worden (getMatch()), in de database gestoken via databasehandler (storeMatch()) en alle statistieken moeten berekend worden en alle Speler, team en tournooistatistieken moeten hierdoor ook geüpdatet worden.
* [ ] editMatch() zowel in de databasehandler (SQL UDDATE) als in de tornooi-klasse voor een match in de arraylist
