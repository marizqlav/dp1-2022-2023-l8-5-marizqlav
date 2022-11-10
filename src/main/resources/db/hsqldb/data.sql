-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');
-- One owner user, Ale, with password patata
INSERT INTO users(username,password,enabled) VALUES ('Ale','patata',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'Ale','owner');
-- One owner user, ismherram, with password pass
INSERT INTO users(username,password,enabled) VALUES ('ismherram','pass',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'ismherram','owner');
-- One owner user, marizqlav, with password pass
INSERT INTO users(username,password,enabled) VALUES ('marizqlav','word',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'marizqlav','owner');
-- One owner user, marolmar1, with password pass
INSERT INTO users(username,password,enabled) VALUES ('marolmmar1','word',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'marolmmar1','owner');
-- One owner user, albdomrui, with password pass
INSERT INTO users(username,password,enabled) VALUES ('albdomrui','word',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'albdomrui','owner');
-- One owner user, pabparmen, with password pass
INSERT INTO users(username,password,enabled) VALUES ('pabparmen','word',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'pabparmen','owner');

INSERT INTO vets(id, first_name,last_name) VALUES (1, 'James', 'Carter');
INSERT INTO vets(id, first_name,last_name) VALUES (2, 'Helen', 'Leary');
INSERT INTO vets(id, first_name,last_name) VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets(id, first_name,last_name) VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets(id, first_name,last_name) VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets(id, first_name,last_name) VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');
INSERT INTO types VALUES (7, 'turtle');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
INSERT INTO owners VALUES (11, 'Ale', 'Merino', '0 Mi Casa', 'En donde vivo', '6060606060', 'Ale');
INSERT INTO owners VALUES (12, 'Ismael', 'Herrera', 'Marchena, 2', 'Sevilla', '6666666666', 'ismherram');
INSERT INTO owners VALUES (13, 'Mario', 'Izquierdo', 'Extremadura, Merida', 'Badajoz', '6666666661', 'marizqlav');
INSERT INTO owners VALUES (14, 'Marcos', 'Olmedo', 'Castilla la mancha, Ciudad-Real', 'Puertollano', '657253380', 'marolmmar1');
INSERT INTO owners VALUES (15, 'Alberto', 'Dominguez-Adame', 'Andalucia, Sevilla', 'Sevilla', '6666666662', 'albdomrui');
INSERT INTO owners VALUES (16, 'Pablo', 'Parra', 'Andalucia, Sevilla', 'Mairena', '6666666662', 'pabparmen');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Patato', '2013-04-13', 5, 11);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'Periko', '2018-06-10', 3, 12);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (16, 'Perik', '2019-06-10', 2, 13);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (17, 'Firulais', '2020-04-20', 3, 14);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (18, 'Pikachu', '2020-04-20', 3, 15);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (5, 15, '2022-03-07', 'clean');

INSERT INTO partida(id, faccion_ganadora, n_jugadores, fecha_creacion, fecha_inicio, fecha_fin)
        VALUES (1, 3, 6, '2022-09-18 10:34:04', '2022-09-18 10:35:02', '2022-09-18 10:49:31');

INSERT INTO ronda(id, partida)
                VALUES (1,1),
                        (2,1);

INSERT INTO turno(id,consul,predor,edil1,edil2,votos_Traidores,votos_Leales,votos_Neutrales,ronda_Id) 
                VALUES (1,'Jugador1','Jugador2','Jugador3','Jugador4',1,1,0,1),
                        (2,'Jugador2','Jugador3','Jugador4','Jugador5',2,0,0,1);			

				
INSERT INTO jugadores
				VALUES (1, 'admin1'),
				(2, 'owner1');	

INSERT INTO turno_jugador VALUES (1, 2);

INSERT INTO faccion(id, faccionposible1, faccionposible2, faccionselecionada, jugador_id) 
VALUES (1, 'Leal', 'Mercader', 'Leal', 1), 
(2, 'Traidor', 'Mercader', 'Mercader', 2);
						
 INSERT INTO mensaje(id,hora,jugador_id,texto) 
                 VALUES (1,'08:45:00',1,'Mensaje de prueba 1'),
                         (2,'10:23:09',2,'Mensaje de prueba 2');
                        
INSERT INTO achievement(id,name,description,threshold,badge_image) 
                VALUES (1,'Viciado','Si juegas <THRESHOLD> partidas o más, consideramos que ya estás enganchado.',10.0,'https://bit.ly/certifiedGamer'),
                        (2,'Triunfador','Si ganas <THRESHOLD> o  más partidas es que eres todo un triunfador.',20.0,'https://bit.ly/proGamer');
                        
                        
INSERT INTO jugador_achievement VALUES(1,1)
