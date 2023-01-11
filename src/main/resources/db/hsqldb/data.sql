
INSERT INTO users(username, name, surname, photo, password,enabled) VALUES ('Ale','Alejandro','Merino','https://i.imgur.com/61kGbK6.png','word',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'Ale','player');

INSERT INTO users(username, name, surname, photo, password, enabled) VALUES ('ismherram','Ismael','Herrera Ramírez','https://i.imgur.com/wpbcwAh.jpeg','word',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'ismherram','player');

INSERT INTO users(username, name, surname, photo, password,enabled) VALUES ('marizqlav','Mario','Izquierdo Lavado','https://i.imgur.com/ij4XprK.jpeg','word',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'marizqlav','player');

INSERT INTO users(username,name,surname,photo,password,enabled) VALUES ('marolmmar1', 'Marcos', 'Olmedo','https://i.imgur.com/IO6NJMu.jpeg' ,'word',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'marolmmar1','player');

INSERT INTO users(username, name, surname, photo, email,password,enabled) VALUES ('albdomrui','Alberto','Domínguez-Adame','https://bit.ly/3WR8YkV','correo@correo.com','word',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'albdomrui','player');

INSERT INTO users(username, name, surname, photo, password, enabled) VALUES ('pabparmen','Pablo','Parra','https://i.imgur.com/GaheCcb.jpeg','word',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'pabparmen','player');

INSERT INTO users(username,password,enabled) VALUES ('admin','admin',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'admin','admin');

INSERT INTO jugadores(id, user) VALUES 
        (1, 'Ale'),
        (2, 'ismherram'),
        (3, 'marizqlav'),
        (4, 'marolmmar1'),
        (5, 'albdomrui'),
        (6, 'pabparmen');
        
INSERT INTO partida(id, faccion_ganadora, numero_jugadores, fecha_creacion, fecha_inicio, fecha_fin, jugador_id)
        VALUES (1, 'Leal', 6, '2022-09-18 10:34:04', '2022-09-18 10:35:02', '2022-09-18 10:49:31', 1),
		(2, 'Traidor', 5, '2023-01-01 11:05:04', '2023-01-01 11:06:02', null, 1);



INSERT INTO ronda(id, partida_id)
                VALUES (1 ,1);

INSERT INTO turno(id, consul_id, predor_id, edil1_id, edil2_id, votos_traidores, votos_leales, votos_neutrales, ronda_id) 
                VALUES (1, 1,2,3,4, 1,1,0, 1),
                        (2, 2,3,4,5, 2,0,0, 1);

INSERT INTO faccion(id, faccion_posible1, faccion_posible2, faccion_selecionada, partida_id, jugador_id) 
VALUES (1, 'Leal', 'Mercader', 'Leal', 1, 1), 
        (2, 'Traidor', 'Mercader', 'Mercader', 1, 2),
        (3, 'Leal', 'Traidor', 'Leal', 1, 3),
        (4, 'Leal', 'Traidor', 'Leal', 1, 4),
        (5, 'Leal', 'Traidor', 'Leal', 1, 5),
        (6, 'Leal', 'Traidor', 'Leal', 1, 6),
	  (7, 'Leal', 'Traidor', null, 2, 1),
	  (8, 'Mercader', 'Traidor', null, 2, 2),
	  (9, 'Leal', 'Traidor', null, 2, 3),
	  (10, 'Traidor', 'Leal', null, 2, 4),
	  (11, 'Leal', 'Traidor', null, 2, 5);
						
 INSERT INTO mensaje(id,hora,jugador_id,texto) 
                 VALUES (1,'08:45:00',1,'Mensaje de prueba 1'),
                         (2,'10:23:09',2,'Mensaje de prueba 2');
                        
INSERT INTO achievement(id,name,description,threshold,badge_image) 
                VALUES (1,'Viciado','Si juegas <THRESHOLD> partidas o más, consideramos que ya estás enganchado.',1.0,'https://bit.ly/certifiedGamer'),
                        (2,'Triunfador','Si ganas <THRESHOLD> o  más partidas es que eres todo un triunfador.',1.0,'https://bit.ly/proGamer'),
                        (3,'Et tu, Brute?','Si ganas <THRESHOLD> o  más partidas en la facción traidora, recibirás un pack de 23 puñales.',1.0,'https://bit.ly/proGamer'),
                        (4,'Sanco, dios de la honestidad','Gana <THRESHOLD> o  más partidas en la facción leal y demuestra tu lealtad al Caesar.',1.0,'https://bit.ly/proGamer'),
                        (5,'Collegia opificum','Gana <THRESHOLD> o  más partidas en la facción mercader y llena tus arcas de denarios.',1.0,'https://bit.ly/proGamer'),
                        (6,'Sabotaje','Gana <THRESHOLD> partida haciendo que la facción rival se pase de votos',1.0,'http://bitly.ws/xRBS');
                        
                        
INSERT INTO jugador_achievement VALUES(1,1);


INSERT INTO amigos VALUES(2,1), (1,2);



