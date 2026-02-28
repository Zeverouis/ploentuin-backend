Welkom op de backend van Ploentuin! Lees deze README
voordat je op de groene pijl klikt.

Ploentuin is dé web applicatie voor (moes)tuinders. Hierin is 
een database/bibliotheek (infopages), een forum en een tuinplanner
in verwerkt. 

In de bibliotheek kan men informatie over planten en aanverwante
zaken vinden en kunnen admins nieuwe pagina's en categorieën aanmaken.

Op het forum kan men tips en tricks delen (wel eerst even verifieëren), 
admin/mods posts/comments verwijderen en gebruikers verbannen.

Met de planner kan men de (moes)tuin inrichten, opslaan (mits ingelogd)
en exporteren als pdf/word/excel/png. 

Gebruikte technieken zijn Java 17, Spring Boot, Spring Security (JWT), 
Hibernate/JPA, PostgreSQL, Lombok, Maven, iText7, Jakarta Validation,
Apache POI (OOXML), Spring Mail, H2 Database en Spring Security Test.

De backend is opgedeeld in lagen om zo de onderhoudbaarheid te versterken.
Controllers - Services - Repositories - Models - DTO's (niet in 
volgorde uiteraard).

**Intellij:**
<br/>Java versie: 17  
<br/>Clone de repo.  
Laad maven in.  
Maak een postgresql database aan genaamd Ploentuin  
Vul je eigen username en password in bij die velden in de application.properties.  
Klik op de groene pijl.  
Mogelijk is het nodig om de net aangemaakte database te refreshen (in pgAdmin of iets dergelijks)  
<br/>spring.datasource.url=jdbc:postgresql://localhost:5432/Ploentuin 🡨 belangrijk!  
spring.datasource.username={username}  
spring.datasource.password={password}  
<br/>De tabellen worden automatisch aangemaakt (en ook ge-refreshed bij heropstart).

<br/><br/>**Postman:**

Pre-seeded usernames: alice, defaultadmin, bob (is mod), charlie, diana, edward  
Wachtwoord voor alle users: Password123  
<br/>Om aan bearertokens te komen log je in met een van deze accounts (waarbij defaultadmin uiteraard het default admin account is en bob een mod).  
<br/><br/><br/>**BaseUrl = <http://localhost:8080>**  
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>**User endpoints:**  
<br/>Base = /users  
<br/>

| **Endpoint**                | **Method** | **Auth** | **Beschrijving**                        | **Extra info**                                                                               |
|-----------------------------|------------|----------|-----------------------------------------|----------------------------------------------------------------------------------------------|
| /register                   | Post       | None     | Account creatie                         | Verify-email token word hiermee ook verstuurd die nodig is bij het 'token' van die endpoint. |
| /login                      | Post       | None     | Account login                           | Hiermee verkrijg je de nodige bearertoken voor andere endpoints.                             |
| /reset-password             | Post       | None     | Nieuw wachtwoord aanmaken               | Gebruik token verkregen van forgot-password                                                  |
| /forgot-password            | Post       | None     | Wachtwoord vergeten                     | Params value verander email                                                                  |
| /verify-email               | Post       | None     | E-mail verificatie                      | Nodig voor forum-endpoints.  <br>Token van de verificatie email gebruiken                    |
| /all                        | Get        | Bearer   | Alle e-mail geverifiërde users ophalen  | Bearertoken moet van admin zijn.                                                             |
| /public/{username}          | Get        | None     | Haal een user op, publiekelijk          | Dit haalt niet de volledige user op                                                          |
| /user/{username}            | Get        | Bearer   | Haal ingelogde user op.                 | Bearertoken moet van de ingelogde user (of admin) zijn.                                      |
| /me                         | Get        | Bearer   | User validatie                          | Bearertoken moet van ingelogde user zijn.                                                    |
| /profile/{username}         | Get        | None     | Haalt een user profiel op               |                                                                                              |
| /{username}/ban             | Patch      | Bearer   | Verbant een user van het forum          | Bearertoken moet van een admin zijn                                                          |
| /{username}/role            | Patch      | Bearer   | Verander userrole van een user          | Bearertoken moet van een admin zijn                                                          |
| /{username}/change-password | Patch      | Bearer   | Verander wachtwoord                     | Bearertoken moet van ingelogde user zijn                                                     |
| /email                      | Patch      | Bearer   | Verander emailadres                     | Bearertoken moet van ingelogde user zijn                                                     |
| /about                      | Patch      | Bearer   | Verander de 'about' in het user profiel | Bearertoken moet van ingelogde user zijn                                                     |
| /avatar                     | Patch      | Bearer   | Verander de avatar van user             | Bearertoken moet van ingelogde user zijn                                                     |

<br/><br/>

**Planner endpoints:**  
<br/>Base = /planner

| **Endpoint**                           | **Method** | **Auth** | **Beschrijving**                                                   | **Extra info**                                                                                            |
|----------------------------------------|------------|----------|--------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|
| None                                   | Post       | None     | Maak een nieuwe planner                                            | Vul row en col met cijfers                                                                                |
| /{plannerid}                           | Post       | None     | Plaats een item in de planner                                      | Kan geen massa requests doen in 1 keer (1 voor 1 dus), verander itemid, row en col na behoeven in params. |
| /catalog                               | Post       | Bearer   | Voeg een item toe aan de catalogus                                 | Admin bearertoken nodig.                                                                                  |
| /{plannerid}                           | Get        | None     | Haal een specifieke planner op                                     | Moet uiteraard wel in de database staan                                                                   |
| /catalog                               | Get        | None     | Haal alle catalog items op                                         |                                                                                                           |
| /catalog/type                          | Get        | None     | Haal alle catalog items op van 1 type                              | Vul een type in in de params (lijst staat in Post /catalog)                                               |
| /catalog/{itemId}                      | Get        | None     | Haal 1 item op uit de catalog                                      | ItemId in de URL                                                                                          |
| /{plannerid}/export/png                | Get        | None     | Maak van een gemaakte (of lege) planner een png                    |                                                                                                           |
|                                        |            |          |                                                                    |                                                                                                           |
| /{plannerid}/export/{pdf, word, excel} | Get        | None     | Maak van een gemaakte (of lege) planner een pdf/word/excel bestand |                                                                                                           |
| /{plannerId}                           | Patch      | Bearer   | Update bestaande planner m.b.t titel, row en col                   | Moet ingelogde user zijn                                                                                  |
| /{plannerId}                           | Delete     | Bearer   | Verwijder een planner                                              | Moet ingelogde user zijn                                                                                  |
| /{plannerId}/items/{placementId}       | Delete     | Bearer   | Verwijder een geplaatste item in een planner                       | Moet ingelogde user zijn                                                                                  |
| /catalog                               | Delete     | Bearer   | Verwijder een item uit de catalogus                                | Moet admin zijn (met token)                                                                               |

<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>**Infopages endpoints:**  
<br/>Base = /info  
<br/>

| **Endpoint**                 | **Method** | **Auth** | **Beschrijving**                                                                           | **Extra info**                                                                                                                                                    |
|------------------------------|------------|----------|--------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| /pages                       | Post       | Bearer   | Voeg een pagina toe aan de infopages                                                       | Moet admin zijn (bearertoken van admin)  <br>Tldr en title zijn NIET optioneel, de rest wel. Verwijder dan ook de keys mits niet gebruikt worden in de form-data. |
| /categories                  | Post       | Bearer   | Voeg een category toe                                                                      | Moet admin zijn (bearertoken)                                                                                                                                     |
| /categories                  | Get        | None     | Haal alle categorieën op                                                                   |                                                                                                                                                                   |
| /pages/latest                | Get        | None     | Haal de laatste 5 toegevoegde infopages op                                                 | Gebaseerd op tijd/datum (in de pre-seed komt alles tegerlijkertijd binnen)                                                                                        |
| /pages                       | Get        | None     | Haal alle infopages op                                                                     |                                                                                                                                                                   |
| /pages/{pageId}              | Get        | None     | Haal 1 specifieke infopage op                                                              |                                                                                                                                                                   |
| /pages/category/{categoryId} | Get        | None     | Haal alle infopages op uit 1 category                                                      |                                                                                                                                                                   |
| /pages/grouped               | Get        | None     | Haal alle infopages op gegroepeerd per category                                            |                                                                                                                                                                   |
| /pages/search/title          | Get        | None     | Zoek alle pagina titels met een woord. Haalt alle pagina's op met dat woord in de titel    | Gebruikt params.                                                                                                                                                  |
| /pages/search/content        | Get        | None     | Zoek alle pagina content met een woord. Haalt alle pagina's op met dat woord in de content | Gebruikt params                                                                                                                                                   |
| /categories/(categoryId)     | Put        | Bearer   | Update 1 specifieke category                                                               | Moet admin bearertoken zijn                                                                                                                                       |
| /pages/{pageId}              | Put        | Bearer   | Update 1 specifieke infopage                                                               | Moet admin bearertoken zijn. Alle velden zijn optioneel.                                                                                                          |
| /categories/(categoryId)     | Delete     | Bearer   | Verwijdert 1 specifieke category                                                           | Moet admin bearertoken zijn.                                                                                                                                      |
| /pages/{pageId}              | Delete     | Bearer   | Verwijdert 1 specifieke infopage                                                           | Moet admin bearertoken zijn.                                                                                                                                      |

<br/><br/>

**Forum endpoints:**  
<br/>Base = /forums  
<br/>

| **Endpoint**                              | **Method** | **Auth** | **Beschrijving**                                           | **Extra info**                                                     |
|-------------------------------------------|------------|----------|------------------------------------------------------------|--------------------------------------------------------------------|
| /categories                               | Post       | Bearer   | Maak een nieuwe category aan                               | Moet admin bearertoken zijn                                        |
| /categories/{categoryId}/posts            | Post       | Bearer   | Maak een nieuwe post aan in een category                   | Moet een ingelogde gebruiker zijn waarbij de email geverifieerd is |
| /posts/{postId}/comments                  | Post       | Bearer   | Maak een nieuwe comment aan op een post                    | Moet ingelogde gebruiker zijn waar de email is geverifieerd.       |
| /categories                               | Get        | None     | Haal alle categorieën op.                                  |                                                                    |
| /categories/{categoryId}                  | Get        | None     | Haal 1 category op d.m.v Id                                |                                                                    |
| /categories/name/{name}                   | Get        | None     | Haal category op d.m.v category naam                       |                                                                    |
| /posts/{postId}                           | Get        | None     | Haal post op                                               |                                                                    |
| /users/{username}/posts                   | Get        | None     | Haal alle posts van een user op                            |                                                                    |
| /categories/{categoryId}/posts            | Get        | None     | Haal alle posts op van 1 specifieke category               |                                                                    |
| / categories/{categoryId}/posts/unordered | Get        | None     | Haal alle ongesorteerde posts op van 1 specifieke category |                                                                    |
| /search?query={word}                      | Get        | None     | Haal alle posts op met een bepaald woord in de titel       |                                                                    |
| /users/{username}/comments                | Get        | None     | Haal alle comments op van een specifieke user              |                                                                    |
| /posts/latest                             | Get        | None     | Haal de laatste 5 posts op                                 | Sorteert/filtert op basis van tijd/datum.                          |
| /comments/{commentId}                     | Patch      | Bearer   | Bewerk een comment                                         | Bearer moet van ingelogde user zijn                                |
| /posts/{postId}                           | Patch      | Bearer   | Bewerk een post                                            | Bearer moet van ingelogde user zijn                                |
| /comments/{commentId}                     | Delete     | Bearer   | Verwijder een comment                                      | Bearer moet van ingelogde user OF admin OF mod zijn                |
| /posts/{postId}                           | Delete     | Bearer   | Verwijder een post                                         | Bearer moet van ingelogde user OF admin OF mod zijn                |
| /categories/{categoryid}                  | Delete     | Bearer   | Verwijder 1 specifieke category                            | Bearer moet van admin zijn                                         |
| /users/{username}/comments                | Delete     | Bearer   | Verwijder alle comments van 1 specifieke user              | Bearer moet of ingelogde user OF admin OF mod zijn                 |

<br/><br/><br/>