INSERT INTO ploentuin_user (username, email, password, email_verified, role, email_verification_token, reset_token, banned)
VALUES
    ('defaultadmin', 'defaultadmin@totallyrealemail.com', '$2a$12$iKDBnRO27pw7Qf0yWRb51OraPrmbjsH8U6nL1.ji0AvSzMZJOC.RS', true, 'ADMIN', NULL, NULL, false),
    ('alice', 'alice@example.com', '$2a$12$iKDBnRO27pw7Qf0yWRb51OraPrmbjsH8U6nL1.ji0AvSzMZJOC.RS', true, 'USER', NULL, NULL, false),
    ('bob', 'bob@example.com', '$2a$12$iKDBnRO27pw7Qf0yWRb51OraPrmbjsH8U6nL1.ji0AvSzMZJOC.RS', true, 'MOD', NULL, NULL, false),
    ('charlie', 'charlie@example.com', '$2a$12$iKDBnRO27pw7Qf0yWRb51OraPrmbjsH8U6nL1.ji0AvSzMZJOC.RS', true, 'USER', NULL, NULL, false),
    ('diana', 'diana@example.com', '$2a$12$iKDBnRO27pw7Qf0yWRb51OraPrmbjsH8U6nL1.ji0AvSzMZJOC.RS', true, 'USER', NULL, NULL, false),
    ('edward', 'edward@example.com', '$2a$12$iKDBnRO27pw7Qf0yWRb51OraPrmbjsH8U6nL1.ji0AvSzMZJOC.RS', true, 'USER', NULL, NULL, false);

INSERT INTO user_profile (user_id, avatar_url, about)
VALUES
    (1, 'https://img.icons8.com/?size=100&id=14736&format=png&color=000000', 'Systeembeheerder van Ploentuin.'),
    (2, 'https://img.icons8.com/?size=100&id=14736&format=png&color=000000', 'Hoi, ik ben Alice!'),
    (3, 'https://img.icons8.com/?size=100&id=14736&format=png&color=000000', 'Bob hier, al 20 jaar vervent moestuinierder, ik weet nog hoe mijn opa.....'),
    (4, 'https://img.icons8.com/?size=100&id=14736&format=png&color=000000', 'Bier en groente, lalalalala.'),
    (5, 'https://img.icons8.com/?size=100&id=14736&format=png&color=000000', NULL),
    (6, 'https://img.icons8.com/?size=100&id=14736&format=png&color=000000', NULL);

INSERT INTO planner_item_catalog (name, colour, image_url, type)
VALUES
-- BUSHES
('Boxwood', '#228B22', 'https://getpotted.com/upload/resize_cache/iblock/3fa/256_256_1/xlwx52un3qi500wgauf3k976mka4dawwg.jpg.pagespeed.ic.Qswcf18gus.jpg', 'BUSHES'),
('Hydrangea Bush', '#FF69B4', 'https://www.theplantcompany.co.nz/file/getimage/shop/jpg/sm/hydrangea-alpen-gluten.webp', 'BUSHES'),
('Lilac Bush', '#9370DB', 'https://www.starkbros.com/images/dynamic/4182-256x256-fill.jpg', 'BUSHES'),

-- FLOWERS
('Rose', '#FF0000', 'https://lens-roses.com/web/image/product.template/17720/image_256/La Rose de Molinard®?unique=320b647', 'FLOWERS'),
('Tulip', '#FFA500', 'https://files.idyllic.app/files/static/278360?width=256&optimizer=image', 'FLOWERS'),
('Daisy', '#FFFF00', 'https://www.irishwildflowers.ie/images/folder1/53a1.jpg', 'FLOWERS'),

-- TREES
('Oak', '#8B4513', 'https://www.alanshelley.org/wp-content/uploads/2019/08/TheEnglishOak.jpg', 'TREES'),
('Pine', '#006400', 'https://media.tractorsupply.com/is/image/TractorSupplyCompany/1913349?$256$', 'TREES'),
('Maple', '#A52A2A', 'https://i.pinimg.com/564x/d3/a6/fd/d3a6fd5b732ae4bfd11c7cd7167b57cc.jpg', 'TREES'),

-- FRUIT_TREES
('Apple Tree', '#FF4500', 'https://images.stockcake.com/public/2/7/3/2734827a-bfbe-47eb-a130-6ff82b4ebdac_medium/bountiful-apple-tree-stockcake.jpg', 'FRUIT_TREES'),
('Cherry Tree', '#FF1493', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXlqagECV25ELPeADFK72R_cAA6F8Tlo6zCQ&s', 'FRUIT_TREES'),
('Pear Tree', '#9ACD32', 'https://www.starkbros.com/images/dynamic/7461-256x256-fill.jpg', 'FRUIT_TREES'),

-- HERBS
('Basil', '#32CD32', 'https://www.famflowerfarm.nl/web/image/product.template/1522/image_256', 'HERBS'),
('Thyme', '#556B2F', 'https://terrepromise.ca/web/image/product.template/1076/image_256/%5B345%5D%20Thyme%20%28Thymus%20vulgaris%29?unique=b7f0e73', 'HERBS'),
('Mint', '#00FA9A', 'https://images.stockcake.com/public/b/e/0/be0cb661-1082-459d-b8c4-44439a0a49b6_medium/fresh-mint-leaves-stockcake.jpg', 'HERBS'),

-- CLIMBERS
('Clematis', '#9400D3', 'https://www.naturalbulbs.nl/web/image/product.template/14630/image_256', 'CLIMBERS'),
('Wisteria', '#BA55D3', 'https://www.theplantcompany.co.nz/file/getimage/shop/jpg/sm/wisteria_sinensis_caroline4.webp', 'CLIMBERS'),
('Ivy', '#006400', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-aDvhlczIAGrPnG3IzHluoUDQSp_Dvuw8SA&s', 'CLIMBERS'),

-- GRASSES
('Ornamental Grass', '#7CFC00', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwfrWdNo1lc8reFQsxS19dbRJBDP0gd7iYWw&s', 'GRASSES'),
('Fescue', '#228B22', 'https://www.picturethisai.com/image-handle/website_cmsname/image/1080/153937503445843973.jpeg?x-oss-process=image/format,webp/resize,s_256&v=1.0', 'GRASSES'),
('Bamboo', '#32CD32', 'https://cdn.shopify.com/s/files/1/0955/5545/3192/files/Fargesia_robusta_campbell_close_up.jpg?v=1755944565&width=256', 'GRASSES'),

-- FRUITS
('Strawberry', '#FF6347', 'https://images.meesho.com/images/products/651454817/lieys_512.webp?width=256', 'FRUITS'),
('Blueberry', '#4169E1', 'https://www.starkbros.com/images/dynamic/4322-256x256-fill.jpg', 'FRUITS'),
('Raspberry', '#DC143C', 'https://www.starkbros.com/images/dynamic/6138-256x256-fill.jpg', 'FRUITS'),

-- VEGETABLES
('Tomato', '#FF4500', 'https://www.famflowerfarm.nl/web/image/product.template/1692/image_256', 'VEGETABLES'),
('Carrot', '#FFA500', 'https://www.famflowerfarm.nl/web/image/product.template/1689/image_256', 'VEGETABLES'),
('Iceberglettuce', '#7CFC00', 'https://dewijamesbutcherscouk-7700-static.symphonycommerce.io/images/2020/05/contain/256x256/555e245314955733241d513c77c5b570.jpg', 'VEGETABLES'),

-- AQUATICS
('Water Lily', '#FF69B4', 'https://images.stockcake.com/public/a/0/0/a00d6db2-e4de-4f68-acaa-d8db072afd9b_medium/serene-water-lily-stockcake.jpg', 'AQUATICS'),
('Lotus', '#FFD700', 'https://images.stockcake.com/public/9/6/5/96576ab6-70c4-427e-9f21-b119668f1b48_medium/serene-lotus-bloom-stockcake.jpg', 'AQUATICS'),
('Cattail', '#8B4513', 'https://www.picturethisai.com/image-handle/website_cmsname/image/1080/154046020089544724.jpeg?x-oss-process=image/format,webp/resize,s_256&v=1.0', 'AQUATICS'),

-- SUCCULENTS
('Aloe Vera', '#98FB98', 'https://plantsvszombies.wiki.gg/images/AloeVera.jpg?334afd', 'SUCCULENTS'),
('Echeveria', '#7FFFD4', 'https://static.wixstatic.com/media/2c3d2c_4e539e471ce642d3a62d0f7d6819f5a0~mv2.jpg/v1/fill/w_256,h_256,al_c,q_80,usm_0.66_1.00_0.01,enc_avif,quality_auto/2c3d2c_4e539e471ce642d3a62d0f7d6819f5a0~mv2.jpg', 'SUCCULENTS'),
('Sedum', '#32CD32', 'https://static.wixstatic.com/media/2c3d2c_bc1f6a76abf34545961a2bc422f584fb~mv2.jpg/v1/fill/w_256,h_256,al_c,q_80,usm_0.66_1.00_0.01,enc_avif,quality_auto/2c3d2c_bc1f6a76abf34545961a2bc422f584fb~mv2.jpg', 'SUCCULENTS'),

-- GROUNDS
('Sand', '#7CFC00', 'https://excluton.nl/img/products/thumbnails/6000112.jpg', 'GROUNDS'),
('Mulch', '#8B4513', 'https://www.mrmulch.com/web/image/product.template/5567/image_256', 'GROUNDS'),
('Gravel', '#D3D3D3', 'https://www.arroway-textures.ch/wp-content/uploads/2019/07/thumb_gravel-072_2-256x256.jpg', 'GROUNDS');

INSERT INTO planner (user_id, title, row_count, column_count, created_at, updated_at)
VALUES
    ((SELECT id FROM ploentuin_user WHERE username = 'alice'), 'Alice''s Voortuin', 6, 8, now(), now()),
    ((SELECT id FROM ploentuin_user WHERE username = 'bob'), 'Bob''s Achtertuin', 8, 10, now(), now()),
    ((SELECT id FROM ploentuin_user WHERE username = 'charlie'), 'Charlie''s Groentetuin', 5, 6, now(), now());

INSERT INTO planner_item_placement (planner_id, catalog_item_id, row_num, col_num)
VALUES
    ((SELECT id FROM planner WHERE title = 'Alice''s Voortuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Boxwood'), 0, 0),
    ((SELECT id FROM planner WHERE title = 'Alice''s Voortuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Boxwood'), 0, 1),
    ((SELECT id FROM planner WHERE title = 'Alice''s Voortuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Rose'), 2, 3),
    ((SELECT id FROM planner WHERE title = 'Alice''s Voortuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Tulip'), 3, 3),
    ((SELECT id FROM planner WHERE title = 'Alice''s Voortuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Daisy'), 3, 4),

    ((SELECT id FROM planner WHERE title = 'Bob''s Achtertuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Oak'), 1, 1),
    ((SELECT id FROM planner WHERE title = 'Bob''s Achtertuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Pine'), 1, 6),
    ((SELECT id FROM planner WHERE title = 'Bob''s Achtertuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Ornamental Grass'), 4, 2),
    ((SELECT id FROM planner WHERE title = 'Bob''s Achtertuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Fescue'), 4, 3),

    ((SELECT id FROM planner WHERE title = 'Charlie''s Groentetuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Tomato'), 1, 1),
    ((SELECT id FROM planner WHERE title = 'Charlie''s Groentetuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Carrot'), 1, 2),
    ((SELECT id FROM planner WHERE title = 'Charlie''s Groentetuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Iceberglettuce'), 2, 1),
    ((SELECT id FROM planner WHERE title = 'Charlie''s Groentetuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Basil'), 3, 1),
    ((SELECT id FROM planner WHERE title = 'Charlie''s Groentetuin'), (SELECT id FROM planner_item_catalog WHERE name = 'Mint'), 3, 2);

INSERT INTO info_category (category_name)
VALUES
    ('Tuin Tips'),
    ('Groenten'),
    ('Bloemen'),
    ('Struiken'),
    ('Bomen'),
    ('Heesters'),
    ('Kruiden & Specerijen'),
    ('Waterplanten'),
    ('Fruit');

INSERT INTO forum_category (category_name)
VALUES
    ('Algemeen tuin'),
    ('Groenten'),
    ('Bloemen'),
    ('Struiken'),
    ('Bomen'),
    ('Heesters'),
    ('Kruiden & Specerijen'),
    ('Waterplanten'),
    ('Fruit'),
    ('Fruitbomen'),
    ('Off-topic');

INSERT INTO info_page (
    category_id,
    title,
    tldr,
    section_one_title,
    section_one_content,
    section_two_title,
    section_two_content,
    section_three_title,
    section_three_content,
    section_four_title,
    section_four_content,
    created_at,
    updated_at
)
VALUES (
    (SELECT id FROM info_category WHERE category_name = 'Tuin Tips'),
    'Composteren basics',
    'Zorg voor juiste temperatuur, alles mag behalve vlees, vis, zuivel, brood, gekookt eten (trekt ongedierte aan), zieke plantenresten.' ||
    ' Zorg voor groen en bruin materiaal, keer elke 4-6 weken voor zuurstof, gebruik kleine stukjes. Is klaar als het ruikt naar bosgrond en zwart kleurt.',
    'Waarom composteren?',
    'Composteren vermindert afval en verrijkt de bodem met voedingsstoffen. Daarbij is het kostenbesparend (je hebt minder tot geen extra voedingsstoffen nodig)',
    'Benodigdheden',
    'Een compostbak, gemengd tuinafval zoals bladeren en keukenresten. Bijde groene (levende) delen en bruine (dode) delen zijn nodig in een verhouding van +- 70/30.',
    'Stappen om te starten',
    'Plaats de bak op een halfschaduwrijke plek. Voeg groen en bruin materiaal in lagen toe. Keer elke 4-6 weken om zuurstof te geven.',
    'Tips voor een succesvolle compost',
    'Gebruik kleine stukjes materiaal. Voeg geen vlees, vis, zuivel, brood of zieke planten toe. Het compost is klaar als het ruikt naar bosgrond en zwart kleurt.',
        now(),
        now()
       ),
    (
     (SELECT id FROM info_category WHERE category_name = 'Tuin Tips'),
     'Mulchen basics',
     'Bodembedekker. Minder onkruid, meer voedingsstoffen (langzaam).',
     'Wat is mulchen?',
     'Mulchen is niks meer dan het gebruik van planten resten om de grond te bedekken. Dit zorgt voor minder onkruid' ||
     '(minder tot geen zonlicht = minder onkruid) en doordat de mulch verwerkt word door insecten en microorganisme voed het gelijk de bodem.' ||
     'Het voeden van de bodem gaat wel langzaam (langzamer dan wanneer je voeding direct toevoegt aan planten). Het verbeterd ook gelijk de bodemstructuur' ||
     'en houd vocht vast (vooral handig met zand gronden).',
     'Hoe dan?',
     'Verspreid ongeveer 5 tot 10cm aan mulch materiaal over de tuin. Dit kan compost zijn of iets als dode bladeren/gras/plantresten (niet zieke plantresten).',
     NULL,
     NULL,
     NULL,
     NULL,
     now(),
     now()
    ),
    (
     (SELECT id FROM info_category WHERE category_name = 'Tuin Tips'),
     'Plannen',
     'Bijhouden van welke planten waar stonden in welk tuinseizoen/jaar, helpt bij de rotatie van planten,' ||
     'overzicht bewaren.',
     'Waarom?',
     'Een planning kan helpen met overzicht te bewaren en de rotatie van plantfamilies bij te houden. ' ||
     'Daarbij kan je het zo groot of klein maken als jezelf wil! Sommigen tekenen de planning op papier, anderen gebruiken iets als de planner op de website,' ||
     'sommigen houden ook bij hoeveel kilo ze van een specifieke plant hebben geoogst in welk jaar.',
     'Hoe?',
     'Een van de simpelste manieren om te beginning met een planning is het tekenen op papier. Teken je tuin,' ||
     'vul deze met woorden of tekeningen van planten en voila! Je kan het ook in excel bijhouden met de hoeveelheid voeding, kilo oogst,' ||
     'etc. Om die reden (dat sommigen naast de tekening ook dingen als oogst bijhouden) is de reden dat de planner op deze website te' ||
     'exporteren is als pdf, excel en word bestand!',
     NULL,
     NULL,
     NULL,
     NULL,
     now(),
     now()
       ),
    (
     (SELECT id FROM info_category WHERE category_name = 'Groenten'),
     'Tomaten',
     'Zaai in maart/april binnen, buiten planten half mei, dieven, bind/ondersteun, kalium/stikstof/magnesium,' ||
     'bewater +- om de dag (3x per week),',
     'Kweken',
     'Binnen (warm) voorzaaien, als de eerste echte blaadjes (niet de kiemblaadjes) verschijnen, verspeen de plantjes naar ' ||
     'een grotere pot (+- 9cm diameter). Halverwege mei (na ijsheilige/15 mei) mogen ze naar buiten of de (koude) kas in.',
     'Verzorging',
        'De tomaat is groot fan van Kalium, stikstof en magnesium. Deze stoffen heeft ze nodig voor het maken van de vruchten.' ||
     'Vergeet daarom vooral niet wat extra van deze stoffen te geven (of wat compost/zeewiermeel bij de geven). Kijk altijd naar' ||
     'de plant alvorens extra voeding/water bij te geven! ' ||
     'Kwa water heeft de plant +- om de dag water nodig (tomaten bestaan nou eenmaal uit veel water). Geef +- 5 tot 10L per plant per week. ' ||
     'uiteraard meer in de zomer dan in een ander seizoen',
     'Ziekten/ongedierten',
        'De tomaat is familie van de aardappel (klinkt raar, is toch echt zo) en heeft zo dus ook (mogelijk) last van de ziekten van die plant.' ||
     'Phytophthora (aardappelziekte) is daarbij de ernstigste. Als je tomatenplant bruin/zwarte plekken begint te krijgen is het al einde oefening. ' ||
     'Haal planten met phytophthora z.s.m weg bij de nog gezonde planten! Phytophthora is een zeer besmettelijke waterschimmel.' ||
     'Om deze waterschimmel te voorkomen zorg je voor voldoende beluchting. Zorg dat de planten droog staan.' ||
     '' ||
     'Neusrot is een ziekte die ervoor zorgt dat de vruchten langzaamaan wegrotten. Dit word veroorzaakt door een calciumgebrek.' ||
     'Vaak door onregelmatig water geven.' ||
     '' ||
     'Grauwe schimmel is een schimmel die zorgt voor grijsbruine schimmelpluis op de plant, vaak door beschadiging of een te hoge luchtvochtigheid.' ||
     '' ||
     'Bladvlekkenziekte zorgt voor bruine vlekken met concentrische ringen op de bladeren, die leiden tot afsterven van blad.' ||
     '' ||
     'Bladluizen. Deze heb je in vele soorten en maten maar veelal zijn het groene kleine insecten die van het sap van de plant leven. ' ||
     'Je kan ze van de plant afspoelen of iets als neemolie gebruiken. Consistent de beestjes eraf spoelen helpt echter al.' ||
     '' ||
     'Verwelkingsziekte. Verwelken, vergelen en afsterven van bladeren, vaak beginnend aan de basis van de plant en omhoog bewegend.
      Verkleuring van de interne stengel, vaak zichtbaar wanneer de stengel in de lengte wordt doorgesneden.
      Uiteindelijke instorting en dood van de plant. Word vooral verookzaakt door het gebruik van snoeischaren, houd deze dus schoon.' ||
     'Als je ziet dat je plant hieraan lijd, verwijder deze plant dan.',
     NULL,
     NULL,
     now(),
     now()
       ),
    (
       (SELECT id FROM info_category WHERE category_name = 'Groenten'),
    'Paprika',
    'Zaai in januari/februarie binnen, buiten/kas na 15 mei, zonliefhebber, ondersteun zware takken, kalium/stikstof,' ||
    'water met regelmaat, oogst groen of gerijpt.',
    'Kweken',
    'Paprika''s hebben warmte nodig om te kiemen (min. 20°C). Zaai binnenshuis in januarie of februarie. Verspeen naar ' ||
    'een grotere pot (+- 9cm diameter) zodra de eerste echte blaadjes verschijnen. Pas na IJsheiligen (15 mei) ' ||
    'mogen ze naar buiten of de kas in.',
    'Verzorging',
    'De paprika is een echte zonaanbidder. Geef de plant een beschutte, warme plek. Gebruik voeding met extra kalium ' ||
    'zodra de eerste vruchten zich vormen. Houd de grond vochtig maar niet kletsnat. Steun de plant met stokken ' ||
    'zodat de takken niet afbreken onder het gewicht van de paprika''s. Pluk de eerste vrucht terwijl deze nog ' ||
    'groen is om de groei van nieuwe vruchten te stimuleren.',
    'Ziekten/ongedierten',
    'Bladluizen zitten vaak in de jonge toppen. Je kan ze van de plant afspoelen of bestrijden met een mengsel van ' ||
    'water en groene zeep. Neemolie helpt ook eventueel.' ||
    '' ||
    'Neusrot geeft zwarte, ingezonken plekken onderaan de paprika. Dit wijst op een calciumgebrek, vaak door ' ||
    'onregelmatig water geven waardoor de plant voedingstoffen niet goed kan opnemen.' ||
    '' ||
    'Witte vlieg komt vaak voor in kassen. Het zijn kleine witte vliegjes die aan de onderkant van het blad zitten. ' ||
    'Zorg voor goede ventilatie of hang gele vangplaten op om de druk te verlagen.',
       NULL,
       NULL,
       now(),
       now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Groenten'),
    'Komkommer',
    'Snelgroeiend, zaaien april/mei, veel water (dagelijks), klimmend, oogst jong voor de beste smaak,' ||
    'vermijd bladbesproeiing tegen meeldauw.',
    'Kweken',
    'Zaai komkommers vanaf half april binnenshuis. Ze groeien zeer snel, dus zaai niet te vroeg. De wortels zijn ' ||
    'extreem kwetsbaar; verplant ze heel voorzichtig naar een grotere pot. Na 15 mei mogen de planten naar hun ' ||
    'definitieve plek in de zon, beschut tegen de wind.',
    'Verzorging',
    'Een komkommerplant bestaat voor het grootste deel uit water en heeft dus dagelijks water nodig, direct bij de ' ||
    'voet van de plant. Zorg voor een klimrek of gaas waar de ranken zich aan kunnen vastzetten. Verwijder de ' ||
    'eerste bloemen aan de onderste 30-50 cm van de stengel om de plant eerst sterk te laten worden. Hoe meer ' ||
    'je oogst, hoe meer de plant blijft produceren.',
    'Ziekten/ongedierten',
    'Meeldauw (wit poeder op het blad) is de meest voorkomende schimmel bij komkommers. Voorkom dit door ' ||
    'nooit op het blad te gieten en te zorgen voor voldoende afstand tussen planten.' ||
    '' ||
    'Spintmijt veroorzaakt kleine gele stipjes op het blad en fijne webjes. Dit gebeurt vaak bij droge lucht; ' ||
    'verhoog de luchtvochtigheid in de kas door de paden nat te maken.' ||
    '' ||
    'Vruchtvuur zorgt voor hoekige bruine vlekken op het blad die later gaten worden. Verwijder aangetaste bladeren ' ||
    'meteen om verdere verspreiding te voorkomen.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
     (SELECT id FROM info_category WHERE category_name = 'Bloemen'),
     'Oost-Indische Kers',
     'Zaaien april/mei, klimmend of kruipend, eetbare bloemen en blad, trekt luizen weg van groenten,' ||
     'houdt van zon en arme grond.',
     'Kweken',
     'Zaai de grote zaden vanaf april binnen of vanaf mei direct buiten. Ze kiemen snel. Plant ze op een plek ' ||
     'waar ze de ruimte hebben om te ranken. Gebruik geen bemeste grond, want dan krijg je veel blad en weinig bloemen.',
     'Verzorging',
     'Deze plant is zeer onderhoudsarm. Geef bij droogte water bij de wortels. De plant wordt vaak gebruikt als ' ||
     '"vanggewas" voor zwarte luis; de luizen gaan op deze plant zitten zodat je bonen of andere groenten gespaard blijven.',
     'Ziekten/ongedierten',
     'Zwarte bonenluis is de meest voorkomende gast. Je kunt de zwaarst getroffen bladeren verwijderen of de luizen ' ||
     'laten zitten als voedsel voor lieveheersbeestjes.' ||
     '' ||
     'Rupsen van het Groot Koolwitje zijn dol op het blad. Controleer de onderkant van de bladeren op gele eitjes ' ||
     'als je de plant liever intact houdt.',
     NULL,
     NULL,
     now(),
     now()
     ),
     (
 (SELECT id FROM info_category WHERE category_name = 'Bloemen'),
    'Zonnebloem',
    'Zaaien in april/mei, veel zon, stevige ondersteuning nodig, veel water en voeding, trekt bijen/vogels aan,' ||
    'hoogte varieert van 50cm tot 3 meter.',
    'Kweken',
    'Zaai vanaf april binnen in diepe potjes of vanaf mei direct in de volle grond. Zonnebloemen hebben een penwortel ' ||
    'die diep gaat, dus wees voorzichtig met verplanten. Kies de zonnigste plek in de tuin voor het beste resultaat.',
    'Verzorging',
    'Zonnebloemen zijn grote eters en drinkers. Geef ze regelmatig water en voeg wat extra organische mest toe. ' ||
    'Bind de hogere soorten vast aan een stevige stok om te voorkomen dat de stengel knakt bij harde wind.',
    'Ziekten/ongedierten',
    'Slakken zijn dol op de jonge kiemplantjes; bescherm deze met een barrière.' ||
    '' ||
    'Echte meeldauw kan aan het einde van het seizoen een wit poederachtig laagje op de bladeren vormen. ' ||
    'Zorg voor voldoende luchtcirculatie om dit te beperken.',
 NULL,
 NULL,
 now(),
 now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Bloemen'),
    'Goudsbloem (Calendula)',
    'Makkelijke groeier, zaaien maart-juni, volle zon/halfschaduw, eetbare bloemblaadjes,' ||
    'werkt goed als combinatieteelt tegen ongedierte.',
    'Kweken',
    'Zaai direct in de volle grond vanaf eind maart tot juni. Goudsbloemen kiemen zeer makkelijk en stellen weinig ' ||
    'eisen aan de grond. Ze zaaien zichzelf ook heel makkelijk uit voor het volgende jaar.',
    'Verzorging',
    'Verwijder regelmatig de uitgebloeide bloemen om de plant te stimuleren nieuwe knoppen aan te maken. ' ||
    'Hierdoor bloeit de plant door tot de eerste vorst. Geef alleen water bij extreme droogte.',
    'Ziekten/ongedierten',
    'Meeldauw kan optreden als de planten te dicht op elkaar staan of aan het einde van de zomer.' ||
    '' ||
    'Bladluizen kunnen soms de bloemknoppen aanvallen, maar dit wordt meestal natuurlijk opgelost door ' ||
    'natuurlijke vijanden die de plant aantrekt.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
     (SELECT id FROM info_category WHERE category_name = 'Struiken'),
     'Vlinderstruik (Buddleja)',
     'Snelgroeiend, bloeit juli-sept, volle zon, trekt massaal vlinders/bijen, ' ||
     'jaarlijks diep terugsnoeien in het voorjaar.',
     'Kweken',
     'De vlinderstruik plant je bij voorkeur in het najaar of vroege voorjaar. Hij stelt weinig eisen aan de grond, ' ||
     'mits deze goed doorlatend is. Zet hem op de zonnigste plek in de tuin; hoe meer zon, hoe meer nectar en bloemen.',
     'Verzorging',
     'In maart (na de strengste vorst) moet je de struik stevig terugsnoeien tot +- 30cm boven de grond. Dit houdt ' ||
     'de struik compact en zorgt voor een rijke bloei op de nieuwe takken. Tijdens de bloei uitgebloeide pluimen ' ||
     'verwijderen verlengt de bloeitijd aanzienlijk.',
     'Ziekten/ongedierten',
     'De vlinderstruik is zeer robuust en heeft zelden last van ziekten. ' ||
     '' ||
     'Soms kan spint optreden bij zeer droog en warm weer, herkenbaar aan fijne webjes onder het blad. ' ||
     '' ||
     'Wortelrot kan voorkomen als de struik in de winter te nat staat; zorg dus voor een plek waar water goed wegloopt.',
     NULL,
     NULL,
     now(),
     now()
    ),
    (
     (SELECT id FROM info_category WHERE category_name = 'Struiken'),
    'Hortensia (Hydrangea)',
    'Bloeit langdurig, halfschaduw, veel water nodig (vooral in de zomer), ' ||
    'kleur afhankelijk van zuurgraad bodem.',
    'Kweken',
    'Hortensia''s houden van een plek in de halfschaduw. Te veel felle middagzon kan de bladeren en bloemen verbranden. ' ||
    'Plant ze in humusrijke, zure tot neutrale grond. De kleur (blauw of roze) kun je beïnvloeden door de zuurgraad ' ||
    'en het toevoegen van aluminiumsulfaat.',
    'Verzorging',
    'De naam Hydrangea zegt het al: deze struik heeft veel water nodig. Geef in de zomer flink water bij de voet. ' ||
    'Snoeien hangt af van de soort; de bekende boerenhortensia bloeit op tweejarig hout, dus knip alleen de ' ||
    'oude bloemen weg in het voorjaar en laat de nieuwe knoppen zitten.',
    'Ziekten/ongedierten',
    'Meeldauw kan voorkomen als de plant te beschut staat met weinig luchtcirculatie. ' ||
    '' ||
    'De wollige dopluis is herkenbaar aan witte watachtige pluisjes op de takken; deze zuigen sap uit de plant ' ||
    'en kunnen de struik verzwakken.',
     NULL,
     NULL,
     now(),
     now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Struiken'),
    'Hulst (Ilex)',
    'Wintergroen, decoratieve bessen in het najaar, stekelig blad, ' ||
    'zeer winterhard en geschikt als heg.',
    'Kweken',
    'Hulst groeit langzaam maar gestaag en kan zowel in de zon als in de schaduw staan. Hij gedijt op bijna ' ||
    'elke grondsoort, mits niet te kalkrijk. Plant bij voorkeur in het voorjaar zodat de wortels goed kunnen ' ||
    'vestigen voor de winter.',
    'Verzorging',
    'Hulst heeft weinig onderhoud nodig. Als je hem als vormstruik of heg gebruikt, kun je hem in juni en ' ||
    'september snoeien. Let op: alleen vrouwelijke planten krijgen bessen, en daarvoor moet er vaak een ' ||
    'mannelijke plant in de buurt staan voor de bestuiving.',
    'Ziekten/ongedierten',
    'De hulstvlieg kan gaatjes of gangetjes (mijngangen) in de bladeren veroorzaken. Dit is meestal ' ||
    'esthetisch en niet dodelijk voor de plant. ' ||
    '' ||
    'Schildluis kan soms voorkomen op de onderkant van de bladeren, herkenbaar aan kleine bruine schildjes.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Bomen'),
    'Zomereik (Quercus robur)',
    'Langlevend, diepe wortels, waardplant voor honderden insecten, ' ||
    'verdraagt snoei goed, produceert eikels in het najaar.',
    'Kweken',
    'De eik groeit het best op diepe, vruchtbare en goed doorlatende grond. Hij heeft veel ruimte nodig, zowel ' ||
    'boven als onder de grond. Plant jonge bomen in het najaar zodat ze voor de zomer al goed geworteld zijn.',
    'Verzorging',
    'Een eik heeft als hij eenmaal staat weinig zorg nodig. Jonge bomen moeten in het begin gesnoeid worden ' ||
    'om een goede doorgaande stam te vormen. Let op dat je de boom niet te diep plant; de wortelhals moet ' ||
    'gelijk liggen met het maaiveld.',
    'Ziekten/ongedierten',
    'De eikenprocessierups is de bekendste plaag; de brandharen van deze rupsen zijn schadelijk voor mens en dier. ' ||
    '' ||
    'Meeldauw (wit poeder op het blad) komt veel voor bij jonge eiken of na een nat voorjaar, maar is ' ||
    'meestal niet dodelijk voor een gezonde boom.' ||
    '' ||
    'Eikengalwespen veroorzaken gallen (vergroeiingen) op de bladeren of eikels, wat meestal onschadelijk is.',
    NULL,
    NULL,
    now(),
    now()
     ),
     (
     (SELECT id FROM info_category WHERE category_name = 'Bomen'),
    'Berk (Betula)',
    'Snelgroeiend, karakteristieke witte stam, houdt van vochtige grond, ' ||
    'weinig snoei nodig, let op voor hooikoorts (pollen).',
    'Kweken',
    'De berk is een pioniersboom die bijna overal groeit, maar een voorkeur heeft voor zanderige, licht vochtige ' ||
    'grond. Plant hem op een plek waar hij de ruimte heeft, want de wortels groeien breed uit en ' ||
    'kunnen bestrating omhoog drukken.',
    'Verzorging',
    'Berken hebben een zeer vroege sapstroom. Snoei ze daarom NOOIT in het voorjaar, anders "bloedt" de boom ' ||
    'dood. De beste tijd om te snoeien is in de late herfst of vroege winter (november/december). ' ||
    'Veel verzorging heeft een volwassen berk verder niet nodig.',
    'Ziekten/ongedierten',
    'De berkenzwam is een parasitaire schimmel die op de stam kan groeien; dit duidt meestal op een ' ||
    'boom die van binnenuit al verzwakt of stervende is. ' ||
    '' ||
    'Berkenwantsen kunnen in grote getale voorkomen maar zijn onschadelijk voor de boom zelf.',
     NULL,
     NULL,
     now(),
     now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Bomen'),
    'Knotwilg (Salix alba)',
    'Snelgroeiend, elke 3-5 jaar knotten, houdt van natte voeten, ' ||
    'belangrijk voor biodiversiteit (insecten en vogels).',
    'Kweken',
    'Wilgen laten zich zeer makkelijk vermeerderen door simpelweg een tak (poot) in de vochtige grond te steken. ' ||
    'Ze groeien het best langs waterkanten of op drassige plekken waar andere bomen moeite hebben met ' ||
    'overleven.',
    'Verzorging',
    'Het belangrijkste onderhoud is het knotten. Knip of zaag alle takken weg tot op de "knot" (de verdikking bovenaan). ' ||
    'Doe dit in de wintermaanden. Doe dit bij voorkeur om de 3 jaar om te voorkomen dat de takken te zwaar ' ||
    'worden en de stam doen splijten.',
    'Ziekten/ongedierten',
    'Watermerkziekte is de meest ernstige bedreiging; takken sterven plotseling af en er komt vocht uit de stam. ' ||
    'Aangetaste bomen moeten vaak volledig verwijderd worden om verspreiding te voorkomen. ' ||
    '' ||
    'De wilgenhoutrups boort gangen in de stam, wat de boom mechanisch kan verzwakken.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Heesters'),
    'Sering (Syringa vulgaris)',
    'Geurende bloemen in mei/juni, volle zon, kalkrijke grond, ' ||
    'verwijder uitgebloeide bloemen voor nieuwe knopvorming.',
    'Kweken',
    'De sering staat het liefst op een zonnige plek in voedzame, kalkrijke grond. Plant ze in het najaar of vroege ' ||
    'voorjaar. Ze hebben ruimte nodig omdat ze zowel in de breedte als in de hoogte flink kunnen groeien.',
    'Verzorging',
    'Geef in het voorjaar een kalkgift en wat organische mest. Snoei direct na de bloei de uitgebloeide trossen ' ||
    'weg tot op een sterk paar knoppen. Oude struiken kun je verjongen door elk jaar 1/3 van de dikste takken ' ||
    'tot vlak boven de grond weg te snoeien.',
    'Ziekten/ongedierten',
    'Seringenziekte (bacterievuur) herken je aan zwarte vlekken op jonge scheuten; knip deze direct diep weg. ' ||
    '' ||
    'Meeldauw kan optreden in een vochtige zomer, vooral als de struik te weinig wind vangt.' ||
    '' ||
    'Schildluizen kunnen zich op de bast vestigen; controleer de takken op kleine grijze schildjes.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Heesters'),
    'Forsythia (Chinees klokje)',
    'Vroege bloeier (maart/april), felgele bloemen, zeer makkelijk, ' ||
    'snoeien direct na de bloei is essentieel.',
    'Kweken',
    'Forsythia groeit werkelijk overal, maar bloeit het rijkst in de volle zon. Het is een van de eerste tekenen ' ||
    'dat de lente begint. Je kunt ze planten als losse struik of als een informele, bloeiende heg.',
    'Verzorging',
    'Omdat de struik bloeit op het hout van vorig jaar, moet je direct na de bloei snoeien. Knip de takken die ' ||
    'gebloeid hebben flink terug. Zo krijgt de plant de hele zomer de tijd om nieuwe scheuten te maken ' ||
    'die volgend jaar weer vol bloemen zitten.',
    'Ziekten/ongedierten',
    'De plant is zeer sterk, maar kan soms last hebben van taksterfte. ' ||
    '' ||
    'Vogels (zoals mussen) vinden de bloemknoppen in de winter erg lekker en kunnen de struik soms ' ||
    'deels kaalvreten voordat deze bloeit.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Heesters'),
    'Japanse Esdoorn (Acer palmatum)',
    'Prachtige bladkleuren en vormen, langzame groeier, halfschaduw, ' ||
    'beschermen tegen harde wind en felle middagzon.',
    'Kweken',
    'Japanse esdoorns houden van een humusrijke, licht zure grond die goed water doorlaat. Ze zijn perfect ' ||
    'voor kleinere tuinen of potten vanwege hun langzame groei. Plant ze niet op een plek waar het veel ' ||
    'tocht of waait, want dan verdrogen de randen van de bladeren.',
    'Verzorging',
    'Snoeien is bij deze heester bijna niet nodig en vaak zelfs zonde van de natuurlijke vorm. Wil je toch ' ||
    'snoeien, doe dit dan in de nazomer of vroege herfst. Geef nooit te veel mest; een bescheiden laagje ' ||
    'compost in het voorjaar is voldoende.',
    'Ziekten/ongedierten',
    'Verwelkingsziekte (Verticillium) is het grootste gevaar; takken sterven plotseling af. Er is geen ' ||
    'genezing mogelijk, dus verwijder aangetaste delen direct. ' ||
    '' ||
    'Bladluizen kunnen in het voorjaar op de jonge scheuten zitten, maar de schade valt meestal mee.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Kruiden & Specerijen'),
    'Lavendel',
    'Zonnige plek, kalkrijke grond, snoeien na bloei en in maart, weinig water, ' ||
    'aromatisch blad en bloemen voor thee of geurzakjes.',
    'Kweken',
    'Lavendel houdt van "arme" grond en veel zon. Plant ze op een plek waar water goed wegloopt; ze haten natte voeten ' ||
    'in de winter. Zaaien is lastig, dus begin bij voorkeur met jonge planten in het voorjaar.',
    'Verzorging',
    'Snoei is essentieel: in maart knip je de plant terug tot boven het oude hout (laat altijd wat groen zitten). ' ||
    'Na de bloei in augustus knip je de uitgebloeide bloemstengels weg om de vorm compact te houden. ' ||
    'Geef liever te weinig dan te veel water.',
    'Ziekten/ongedierten',
    'Wortelrot door te natte grond is de meest voorkomende doodsoorzaak. ' ||
    '' ||
    'Spuugbeestjes (schuimcicade) kunnen witte schuimvlokken op de stengels achterlaten; dit is ' ||
    'onschadelijk en kun je er gewoon afspuiten.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Kruiden & Specerijen'),
    'Munt (Mentha)',
    'Zeer makkelijke groeier, halfschaduw/zon, woekert snel (gebruik een pot), ' ||
    'veel water nodig, oogst blad regelmatig.',
    'Kweken',
    'Munt groeit bijna overal zolang de grond maar vochtig is. Let op: munt verspreidt zich via ondergrondse ' ||
    'uitlopers en kan je hele tuin overnemen. Plant munt daarom bij voorkeur in een ruime pot of een ' ||
    'begrensde bak in de grond.',
    'Verzorging',
    'Houd de grond altijd licht vochtig. Om de plant mooi vol en bossig te houden, moet je regelmatig de ' ||
    'toppen eruit knippen voor gebruik. Als de plant in de zomer gaat bloeien, verliezen de bladeren ' ||
    'iets van hun aroma; knip de bloemstengels eventueel weg.',
    'Ziekten/ongedierten',
    'Muntroest is een schimmel die zorgt voor oranjebruine vlekken onderaan het blad. Verwijder ' ||
    'aangetaste delen direct en geef water op de grond, niet op het blad.' ||
    '' ||
    'Bladkevers (zoals de goudhaantjes) kunnen gaten in het blad vreten.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Kruiden & Specerijen'),
    'Bieslook (Allium schoenoprasum)',
    'Winterharde vaste plant, zon/halfschaduw, vochtige grond, ' ||
    'eetbare paarse bloemen, oogst door af te knippen.',
    'Kweken',
    'Bieslook is familie van de ui en groeit uit kleine bolletjes. Je kunt ze zaaien in het voorjaar of ' ||
    'een bestaande pol scheuren en herplanten. Ze doen het uitstekend in zowel de volle grond als in ' ||
    'potten op de vensterbank.',
    'Verzorging',
    'Geef regelmatig water, want bieslook houdt niet van uitdrogen. Knip de sprieten af tot ongeveer 2-3 cm ' ||
    'boven de grond; de plant groeit daarna gewoon weer aan. Verwijder de bloemstengels als je wilt dat ' ||
    'de plant al zijn energie in de bladgroei steekt.',
    'Ziekten/ongedierten',
    'Bieslookroest veroorzaakt kleine oranje stipjes op de stengels; knip de plant dan volledig kort ' ||
    'en gooi het aangetaste loof weg (niet op de composthoop). ' ||
    '' ||
    'Uienvlieg kan soms de wortels aantasten, maar bij bieslook komt dit minder vaak voor dan bij uien.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Waterplanten'),
    'Gele Waterlelie (Nuphar lutea)',
    'Drijvende bladeren, bloeit juni-augustus, diep water nodig (min. 40cm), ' ||
    'zuivert water en biedt schaduw tegen algen.',
    'Kweken',
    'Plant de wortelstok in een speciale vijvermand met vijveraarde, afgedekt met grind om woelen te voorkomen. ' ||
    'Plaats de mand op een diepte van 40 tot 100 cm. Ze houden van een zonnige plek; hoe meer zon, hoe meer bloemen.',
    'Verzorging',
    'Waterlelies hebben weinig onderhoud nodig. Verwijder in het najaar de afgestorven bladeren en bloemen om ' ||
    'slibvorming op de bodem te voorkomen. Als de plant te groot wordt voor de vijver, kun je de wortelstok ' ||
    'in het voorjaar delen en herplanten.',
    'Ziekten/ongedierten',
    'De waterleliekever kan gaten in de bladeren vreten; spoel de larven met een harde waterstraal van het blad af. ' ||
    '' ||
    'Bladluizen kunnen zich soms op de drijvende bladeren vestigen, maar vissen in de vijver eten deze vaak op.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Waterplanten'),
    'Glanzend Fonteinkruid (Potamogeton lucens)',
    'Belangrijke zuurstofplant, groeit volledig onder water, ' ||
    'houdt het vijverwater helder en gaat algen tegen.',
    'Kweken',
    'Dit is een echte zuurstofplant die voedingsstoffen direct uit het water opneemt. Plant de stengels in ' ||
    'vijvermandjes met een mengsel van zand en klei, en plaats deze op een diepte van 30 tot 60 cm. ' ||
    'Hij gedijt het beste in helder, kalkrijk water.',
    'Verzorging',
    'Snoeien is alleen nodig als de plant het wateroppervlak overwoekert; knip dan simpelweg de bovenste ' ||
    'delen van de stengels af. In de winter sterft de plant bovengronds af om in het voorjaar vanuit ' ||
    'de wortels weer razendsnel uit te lopen.',
    'Ziekten/ongedierten',
    'Fonteinkruid is zeer resistent tegen ziekten, maar kan overwoekerd worden door draadalgen als er ' ||
    'te veel voedingsstoffen (zoals vissenpoep) in het water zitten. ' ||
    '' ||
    'Watelslakken zijn nuttige bewoners die de algen van de bladeren afschrapen.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Waterplanten'),
    'Krabbenscheer (Stratiotes aloides)',
    'Drijvende plant, stijgt en daalt in het water, schuilplaats voor ' ||
    'libellenlarven, zeer effectieve waterzuiveraar.',
    'Kweken',
    'Krabbenscheer hoef je niet te planten; je gooit ze simpelweg in het water. In het voorjaar stijgen ze ' ||
    'naar het oppervlak om te bloeien, en in de winter zinken ze naar de bodem om te rusten. ' ||
    'Ze hebben een voorkeur voor stilstaand tot licht stromend water.',
    'Verzorging',
    'De plant regelt zijn eigen diepte, dus laat hem vooral met rust. Als de populatie te groot wordt en ' ||
    'het oppervlak volledig bedekt, kun je er in de zomer handmatig een aantal uit het water vissen. ' ||
    'Draai de planten niet om, ze moeten met de rozet naar boven drijven.',
    'Ziekten/ongedierten',
    'De plant is erg sterk, maar de zwarte glazenmaker (een libel) legt specifiek haar eitjes op deze plant. ' ||
    'Dit is geen plaag, maar een zeer nuttige vorm van biodiversiteit.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Fruit'),
    'Framboos (Rubus idaeus)',
    'Zon/halfschaduw, snoeien afhankelijk van zomer- of herfstsoort, ' ||
    'lekker vers van de struik, heeft ondersteuning nodig.',
    'Kweken',
    'Plant frambozen in humusrijke, licht zure grond. Ze houden van een zonnige plek voor de zoetste vruchten. ' ||
    'Omdat ze lange uitlopers maken, kun je ze het beste langs een draadwerk of tussen twee palen met ' ||
    'gespannen draden planten op ongeveer 40cm afstand van elkaar.',
    'Verzorging',
    'Houd de grond vochtig en mulch jaarlijks met compost. Snoei herfstframbozen in de winter volledig tot ' ||
    'de grond terug. Bij zomerframbozen knip je alleen de takken weg die vrucht hebben gedragen; de nieuwe ' ||
    'groene scheuten laat je staan voor de oogst van volgend jaar.',
    'Ziekten/ongedierten',
    'Frambozenkever zorgt voor gaatjes in de bloemen en wormpjes in de vruchten. ' ||
    '' ||
    'Vruchtboomkanker of stengelziekte kan bruine vlekken op de stengels geven; knip aangetaste takken ' ||
    'direct diep weg en voer ze af.' ||
    '' ||
    'Vogels zijn dol op de vruchten; span eventueel een net over de struiken zodra ze beginnen te kleuren.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Fruit'),
    'Aardbei (Fragaria x ananassa)',
    'Vaste plant, veel zon, bodembedekkend, elke 3 jaar vernieuwen, ' ||
    'geef water bij de voet om rot te voorkomen.',
    'Kweken',
    'Aardbeien plant je bij voorkeur in augustus of maart/april. Ze hebben een zonnige plek nodig en ' ||
    'vruchtbare grond. Plant ze niet te diep; de "kroon" (waar de bladeren uitkomen) moet net boven ' ||
    'de grond uitsteken om rotting tegen te gaan.',
    'Verzorging',
    'Leg stro onder de planten zodra de vruchten zich vormen om ze schoon en droog te houden. Geef extra ' ||
    'kaliumrijke mest in het voorjaar. Verwijder de uitlopers (stolonen) die de plant maakt, tenzij je ' ||
    'nieuwe plantjes wilt opkweken, zodat alle energie naar de vruchten gaat.',
    'Ziekten/ongedierten',
    'Grauwe schimmel (Botrytis) laat de vruchten rotten bij nat weer; stro en goede afstand helpen dit voorkomen. ' ||
    '' ||
    'Slakken vreten graag gaten in de rijpende aardbeien. ' ||
    '' ||
    'Meeldauw kan wit pluis op de bladeren geven bij warm, vochtig weer.',
    NULL,
    NULL,
    now(),
    now()
    ),
    (
    (SELECT id FROM info_category WHERE category_name = 'Fruit'),
    'Blauwe Bes (Vaccinium corymbosum)',
    'Houdt van zeer zure grond, geen kalk, decoratieve herfstkleur, ' ||
    'bevat veel antioxidanten.',
    'Kweken',
    'De blauwe bes stelt specifieke eisen: ze groeien alleen in zure grond (veen of bosgrond). Gebruik bij het ' ||
    'planten ruim turfstrooisel. Ze wortelen ondiep, dus plant ze niet te diep en zorg voor een ' ||
    'beschutte, zonnige tot halfschaduwrijke plek.',
    'Verzorging',
    'Geef nooit kraanwater (bevat vaak kalk), maar gebruik regenwater. Mulch elk jaar met een dikke laag ' ||
    'dennennaalden of turf om de zuurgraad op peil te houden. Snoei is de eerste jaren niet nodig; daarna ' ||
    'verwijder je alleen de oudste, grijze takken om de struik te verjongen.',
    'Ziekten/ongedierten',
    'Vogels (vooral duiven en merels) kunnen een hele struik in één dag leegplukken; een net is vaak noodzakelijk. ' ||
    '' ||
    'Mummybes is een schimmel waarbij de bessen verschrompelen en grijs worden; verwijder deze bessen ' ||
    'direct om verspreiding te voorkomen.',
    NULL,
    NULL,
    now(),
    now()
    );

INSERT INTO forum_post (
    category_id,
    user_id,
    created_at,
    updated_at,
    content,
    title
)
VALUES
    ((SELECT id FROM forum_category WHERE category_name = 'Algemeen tuin'), 1, now(), now(), 'Dit is de plek voor algemene tuinvragen. Stel hier al je vragen!', 'Welkom bij Algemeen tuin'),
    ((SELECT id FROM forum_category WHERE category_name = 'Groenten'), 2, now(), now(), 'Ik heb een kleine tuin en wil tomaten kweken. Wie heeft tips?', 'Tips voor tomaten kweken'),
    ((SELECT id FROM forum_category WHERE category_name = 'Bloemen'), 3, now(), now(), 'Wanneer is het beste moment om bloemen te bemesten?', 'Bloemen bemesten'),
    ((SELECT id FROM forum_category WHERE category_name = 'Struiken'), 4, now(), now(), 'Hoe en wanneer moet ik mijn struiken snoeien?', 'Struiken snoeien'),
    ((SELECT id FROM forum_category WHERE category_name = 'Bomen'), 5, now(), now(), 'Welke bomen zijn geschikt voor een kleine achtertuin?', 'Bomen planten'),
    ((SELECT id FROM forum_category WHERE category_name = 'Heesters'), 6, now(), now(), 'Tips om mijn heesters mooi in bloei te houden.', 'Heesters in bloei houden'),
    ((SELECT id FROM forum_category WHERE category_name = 'Kruiden & Specerijen'), 1, now(), now(), 'Welke kruiden groeien het beste in potten?', 'Kruiden & Specerijen tips'),
    ((SELECT id FROM forum_category WHERE category_name = 'Waterplanten'), 2, now(), now(), 'Hoe houd ik mijn vijver waterplanten gezond?', 'Waterplanten onderhouden'),
    ((SELECT id FROM forum_category WHERE category_name = 'Fruit'), 3, now(), now(), 'Wanneer is de beste tijd om appels te oogsten?', 'Fruit oogsten'),
    ((SELECT id FROM forum_category WHERE category_name = 'Fruitbomen'), 4, now(), now(), 'Hoe snoei ik mijn fruitbomen zonder ze te beschadigen?', 'Fruitbomen snoeien'),
    ((SELECT id FROM forum_category WHERE category_name = 'Off-topic'), 1, now(), now(), 'Plaats hier alles wat niet over tuinieren gaat!', 'Offtopic chill');

INSERT INTO comment (
    post_id,
    user_id,
    content,
    created_at,
    updated_at)
VALUES

    ((SELECT id FROM forum_post WHERE title = 'Tips voor tomaten kweken'), 3, 'Vergeet ze niet te dieven, dat scheelt de helft!', now(), now()),
    ((SELECT id FROM forum_post WHERE title = 'Tips voor tomaten kweken'), 4, 'Ik gebruik altijd vloeibare voeding, werkt top.', now(), now()),

    ((SELECT id FROM forum_post WHERE title = 'Bloemen bemesten'), 1, 'Ik doe het meestal net voor een regenbui, trekt het lekker in.', now(), now()),

    ((SELECT id FROM forum_post WHERE title = 'Offtopic chill'), 2, 'Iemand nog tips voor een goede serie over tuinieren?', now(), now());

INSERT INTO images (
    parent_id,
    user_id,
    parent_type,
    image_url,
    caption,
    data,
    created_at,
    updated_at
)
VALUES
 --Tuin tips
    ((SELECT id FROM info_page WHERE title = 'Composteren basics'),1 , 'INFOPAGE', 'https://shop.action.com/_next/image?url=https%3A%2F%2Fshop.action.com%2Fstatic%2Fimages%2F800%2F5709386720421_817b848e-0a54-4209-9509-4eb27cdcb8df.jpg&w=256&q=75', 'Compostbak', NULL, now(), now()),
    ((SELECT id FROM info_page WHERE title = 'Composteren basics'), 1, 'INFOPAGE', 'https://static.wixstatic.com/media/1b8a25_74834ba16e9c426fba3485081a3e214a~mv2.jpg', 'Rijpe compost', NULL, now(), now()),
    ((SELECT id FROM info_page WHERE title = 'Mulchen basics'), 1, 'INFOPAGE', 'https://sargentsgardens.com/wp-content/uploads/2022/05/BDP-06-Red-Mulch.jpg', 'Mulchen met organisch materiaal', NULL, now(), now()),
    ((SELECT id FROM info_page WHERE title = 'Plannen'), 1, 'INFOPAGE', 'https://mergenmetz.nl/wp-content/uploads/2014/02/Plattegrond-moestuin.jpg', 'Voorbeeld van een tuinplanning', NULL, now(), now()),

 --Groenten
    ((SELECT id FROM info_page WHERE title = 'Tomaten'), 1, 'INFOPAGE', 'https://www.koppert.nl/content/_processed_/b/9/csm_phytophtora_blight_phytophtora_infestans_damage_2_koppert_335b9ed155.jpg', 'Tomaat met phytophthora', NULL, now(), now()),
    ((SELECT id FROM info_page WHERE title = 'Tomaten'), 1, 'INFOPAGE', 'https://www.bolster.nl/media/content/tomatendieven.jpg', 'Het dieven van tomatenplanten', NULL, now(), now()),
    ((SELECT id FROM info_page WHERE title = 'Paprika'), 1, 'INFOPAGE', 'https://www.rootsum.nl/img/fqnTMZWWhDcdi6h8q-aMc8ge7lpaGOfvha0V4vLEy4Y/resize:fit:0:0/aHR0cHM6Ly93d3cucm9vdHN1bS5ubC9tZWRpYS93eXNpd3lnL0Jpb2dyb2VpLXR1aW4vcGFwcmlrYS9Hcm9lbnRlbnBsYW50ZW4tcGFwcmlrYS1iaW9ncm9laS00MDBweC1taW4uanBn.jpg', 'Paprika plant in de kas', NULL, now(), now()),
    ((SELECT id FROM info_page WHERE title = 'Komkommer'), 1, 'INFOPAGE', 'https://cdn.shopify.com/s/files/1/0833/3924/7942/files/komkommer2-640x427.jpg', 'Komkommer voorbeeld', NULL, now(), now()),

 --Bloemen
   ((SELECT id FROM info_page WHERE title = 'Oost-Indische Kers'), 1, 'INFOPAGE', 'https://sprinklr.co/cdn/shop/articles/oost-indische-kers-tropaeolum_520x500_520x500_6d71fb6f-170d-4c05-8044-9707facc1efa.jpg?v=1754650482', 'Eetbare bloemen van de Oost-Indische kers', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Zonnebloem'), 1, 'INFOPAGE', 'https://img.static-rmg.be/a/view/q75/w940/h528/2443967/zonnebloemen-jpg.jpg', 'Zonnebloem in volle bloei', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Goudsbloem (Calendula)'), 1, 'INFOPAGE', 'https://www.bolster.nl/media/images/tof_7806_800x80057.jpg?1615391081', 'Goudsbloemen in de border', NULL, now(), now()),

 --Struiken
   ((SELECT id FROM info_page WHERE title = 'Vlinderstruik (Buddleja)'), 1, 'INFOPAGE', 'https://groeneparadijs.nl/media/mf_webp/jpg/media/catalog/product/cache/4749daa9d7edd9f9838910af1a0d84eb/b/u/buddleja_pink_delight_-_vlinderstruik_pink_delight__1.webp', 'Vlinderstruik', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Hortensia (Hydrangea)'), 1, 'INFOPAGE', 'https://www.tuincentrumnieuwhanenburg.nl/files/images/news/hortensia-tuinplant-van-dit-moment-1588578706-1588580120_n.jpg', 'Hortensia in de schaduw', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Hulst (Ilex)'), 1, 'INFOPAGE', 'https://buitenleven.nl/wp-content/uploads/Hulst-of-Ilex-met-rode-bessen.jpg', 'Hulst met rode bessen', NULL, now(), now()),

 --Bomen
   ((SELECT id FROM info_page WHERE title = 'Zomereik (Quercus robur)'), 1, 'INFOPAGE', 'https://ecopedia.s3.eu-central-1.amazonaws.com/styles/fiche_detail_large/s3/bomenwijzer/2/volwassen.jpg?itok=WW5g9Q53', 'De imposante kruin van een zomereik', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Berk (Betula)'), 1, 'INFOPAGE', 'https://www.directplant.nl/media/catalog/product/b/i/birch-vertical-3.jpg?optimize=medium&fit=crop&height=600&width=600', 'De karakteristieke witte stam van een berk', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Knotwilg (Salix alba)'), 1, 'INFOPAGE', 'https://www.schoutenbomen.nl/wp-content/uploads/2023/03/10-2.jpg', 'Geknotte wilgen langs de waterkant', NULL, now(), now()),

 --Heesters
   ((SELECT id FROM info_page WHERE title = 'Sering (Syringa vulgaris)'), 1, 'INFOPAGE', 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Lilac_%282%29.jpg/330px-Lilac_%282%29.jpg', 'Geurende seringenbloesem', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Forsythia (Chinees klokje)'), 1, 'INFOPAGE', 'https://www.tuinflora.com/media/catalog/product/cache/3a7af0a8e0e317723249dc9098669163/f/d/fd11042-0-wh.jpg', 'Vroegbloeiende Forsythia', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Japanse Esdoorn (Acer palmatum)'), 1, 'INFOPAGE', 'https://www.intratuin.nl/media/Plantengids/Esdoorn/Esdoorn-acer3.jpg', 'Japanse esdoorn', NULL, now(), now()),

 --Kruiden & Specerijen
   ((SELECT id FROM info_page WHERE title = 'Lavendel'), 1, 'INFOPAGE', 'https://groenebuurten.nl/wp-content/uploads/2015/05/Lavandula-Lavendel-Zon.jpg', 'Bloeiende lavendelvelden', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Munt (Mentha)'), 1, 'INFOPAGE', 'https://www.directplant.nl/media/catalog/product/m/e/mentha_spicata.jpg?optimize=medium&fit=crop&height=600&width=600', 'Verse munt', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Bieslook (Allium schoenoprasum)'), 1, 'INFOPAGE', 'https://www.versvancees.nl/wp-content/uploads/2014/02/Gewone-Bieslook.jpg', 'Bieslook met paarse bloemen', NULL, now(), now()),

 --Waterplanten
   ((SELECT id FROM info_page WHERE title = 'Gele Waterlelie (Nuphar lutea)'), 1, 'INFOPAGE', 'https://www.vijverwinkel.com/wp-content/uploads/2023/05/Nymphaea-American-Star-Z-e1684846801988.jpg', 'Drijvende bladeren van de waterlelie', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Glanzend Fonteinkruid (Potamogeton lucens)'), 1, 'INFOPAGE', 'https://upload.wikimedia.org/wikipedia/commons/2/29/Potamogeton.jpg', 'Fonteinkruid onder water', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Krabbenscheer (Stratiotes aloides)'), 1, 'INFOPAGE', 'https://www.floravannederland.nl/upload/foto/full/d01106c3593e45b6a62e296666f28b8e.jpg', 'Krabbenscheer aan het oppervlak', NULL, now(), now()),

 --Fruit
   ((SELECT id FROM info_page WHERE title = 'Framboos (Rubus idaeus)'), 1, 'INFOPAGE', 'https://cdn.webshopapp.com/shops/276354/files/255626702/500x500x2/image.jpg', 'Rijpe frambozen aan de struik', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Aardbei (Fragaria x ananassa)'), 1, 'INFOPAGE', 'https://www.tuinflora.be/media/catalog/category/aardbeienplant.jpg', 'Verse aardbeien op stro', NULL, now(), now()),
   ((SELECT id FROM info_page WHERE title = 'Blauwe Bes (Vaccinium corymbosum)'), 1, 'INFOPAGE', 'https://betuwebomen.nl/cdn/shop/files/blauwe_bosbes_1.jpg?v=1755096685', 'Trossen blauwe bessen', NULL, now(), now());