insert into 
    genre (designation)
values 
    ('Homme'), ('Femme');


insert into 
    utilisateur(contact, date_creation, email, mot_de_passe, nom, photo_profil, prenom, role, date_naissance, id_genre)
values 
    ('0343434444', CURRENT_TIMESTAMP, 'huhu@gmail.com', 'huhu', 'Huhu', NULL, 'Haha', 0, '2000-01-01', 1),
    ('0323232232', CURRENT_TIMESTAMP, 'music@gmail.com', 'man', 'Music', NULL, 'Man', 1, '1987-01-01', 1);


insert into
    annonce (titre, description, date_annonce, prix, etat, id_utilisateur)
values 
    ('Mercedes Zavatra', 'Amidy maika', '2024-01-01', 1000000, 0, 1),
    ('Mitsubishi Zavatra', 'Amidy maika', '2024-01-02', 900000, 1, 2),
    ('Mercedes 2 Zavatra', 'Amidy maika 2', '2024-01-04', 1000000, 2, 1);

insert into 
    marque 
values 
    (default, 'Mitsubishi'),
    (default, 'Mercedes'),
    (default, 'Nissan');


insert into 
    modele(nom, id_marque)
values
    ('MMM', 1),
    ('MME', 2),
    ('NNN', 3);

insert into 
    boite_de_vitesse
values 
    (default, 'Automatique'),
    (default, 'Manuel'),
    (default, 'Semi-Automatique');

insert into 
    nombre_places 
values
    (default, '1'),
    (default, '2'),
    (default, '3'),
    (default, '4'),
    (default, '5+');


insert into 
    couleur_vehicule(couleur, code)
values 
    ('Rouge', '#ff0000'),
    ('Vert', '#00ff00'),
    ('Bleu', '#0000ff');

insert into
    carburant(nom)
values 
    ('Diesel'),
    ('Essence'),
    ('Electrique'),
    ('Hybride');

insert into 
    categorie_vehicule(nom)
values 
    ('SUV'), ('Pick-Up'), ('4x4');

insert into 
    etat_vehicule(nom)
values 
    ('Occasion'), ('Neuf');


--Sous anonces
insert into 
    sous_annonce(id_annee_modele, 
                id_annonce, 
                id_boite_de_vitesse, 
                id_carburant, 
                id_categorie_vehicule, 
                id_couleur_vehicule, 
                id_etat_vehicule, 
                id_marque, 
                id_modele, 
                id_nombre_places)
values
    (NULL, 1, 1, 1, 1, 1, 1, 1, 1, 1),
    (NULL, 2, 2, 2, 2, 2, 1, 2, 2, 2);