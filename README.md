# lol
League of Legends - Project beleidsinformatica

TO-DO

* [x] GUI - teams en spelers invlullen
* [x] teams opdelen in poules
* [x] mogelijke matches genereren binnen een poule
* [x] GUI - match plannen op basis van deze matches
* [ ] GUI + methode change team
* [ ] methode delete team
* [ ] GUI + methode change match
* [ ] methode delete match
* [ ] Database kolommen genereren op basis van een Matchobject teruggegevn door de APIhandler
* [x] addTeam, generatePoules en generatePouleMatches moeten hun resultaten in de database kunnen steken via databasehandler
* [x] planMatch() methode, neemt datum en matchID aan en verandert datum van Matchobject van gegeven matchID in gegeven datum
* [x] storeMatch() methode, in de databasehandler een gegeven matchobject of lijst van objecten moet in de database gestored worden
* [x] GUI planMatch(), match selecteren uit matchlijst van nog niet gespeelde matches en datum kunnen ingeven.
* [ ] completeMatch(), das ne groten. Het matchobject moet op completed gezet worden, de data moet via de APIhandler opgehaald worden (getMatch()), in de database gestoken via databasehandler (storeMatch()) en alle statistieken moeten berekend worden en alle Speler, team en tournooistatistieken moeten hierdoor ook geüpdatet worden.
* [ ] punten moeten per poule bijgehouden worden en poules worden hierop gesorteerd (comparable team)
* [ ] 2 beste per poule worden in bracketobject gestoken + databasehandling
* [ ] wanneer bracketobject compleet is, winnaar doorgeven naar volgend object, als deze 2 teams heeft matches generen
* [ ] rapporteringsmethodes schrijven
* [ ] winnaar herkennen en toernooi eindigen
