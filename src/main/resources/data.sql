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

--- Statistiques (TEST)
-- Insertion dans la table revenu_annonce
INSERT INTO revenu_annonce (commission, id_annonce, pourcentage_commission, date_commission)
SELECT
    floor(random() * 100000000) + 1000000,
    generate_series(1, 3) AS id_annonce, -- Alternance des identifiants d'annonce de 0 à 4
    20,
    CURRENT_TIMESTAMP; -- Date actuelle pour toutes les entrées

-- Insertion dans la table revenu_utilisateur avec des montants aléatoires
INSERT INTO revenu_utilisateur (id_annonce, montant, date_revenu)
SELECT
    id_annonce,
    floor(random() * 100000000) + 1000000, -- Montant aléatoire entre 1,000,000 et 100,000,000
    CURRENT_TIMESTAMP -- Date actuelle pour toutes les entrées
FROM
    generate_series(1, 3) AS id_annonce; -- Alternance des identifiants d'annonce de 0 à 4

--- STATISTIQUEs
--- Commission total
create view v_commission as
select
    sum(commission) as commission_total
from revenu_annonce;

-- Commission par mois
create view v_commission_mois_annee as
select
    extract(MONTH FROM date_commission),
    extract(YEAR FROM date_commission),
    SUM(commission) as commission_total
from
   revenu_annonce
group by
    extract(YEAR FROM date_commission), extract(MONTH FROM date_commission);

-- Commission par annee
create view v_commission_annee as
select
    extract(YEAR FROM date_commission),
    SUM(commission) as nombre
from
   revenu_annonce
group by
    extract(YEAR FROM date_commission);

-- Ventes en annee
create view v_total_vente_annee as
select
    extract(year from a.date_annonce),
    count(a.*) as nombre
from
    revenu_annonce as ra
join annonce as a
    on a.id = ra.id_annonce
where a.etat = 2
group by extract(year from a.date_annonce);

-- Vente en mois
create view v_total_vente_mois_annee as
select
    extract(year from a.date_annonce),
    extract(month from a.date_annonce),
    count(a.*) as nombre
from
    revenu_annonce as ra
join annonce as a
    on a.id = ra.id_annonce
where a.etat = 2
group by extract(mois from a.date_annonce), extract(year from a.date_annonce);

-- Vente en jours
create view v_total_vente_jours_mois_annee as
select
    extract(year from a.date_annonce),
    extract(month from a.date_annonce),
    extract(day from a.date_annonce),
    count(a.*) as nombre
from
    revenu_annonce as ra
join annonce as a
    on a.id = ra.id_annonce
where a.etat = 2
group by
    extract(year from a.date_annonce),
    extract(month from a.date_annonce),
    extract(day from a.date_annonce);

-- Vente marque
create view v_vente_marque_init as
select
    extract(year from a.date_annonce),
    extract(month from a.date_annonce),
    extract(day from a.date_annonce),
    m.id,
    0
from
    revenu_annonce as ra
join annonce as a
    on a.id = ra.id_annonce
cross join marque as m
where a.etat = 2
group by
    m.id,
    extract(year from a.date_annonce),
    extract(month from a.date_annonce),
    extract(day from a.date_annonce);

create view v_total_vente_marque_jours_mois_annee as
select
    extract(year from a.date_annonce),
    extract(month from a.date_annonce),
    extract(day from a.date_annonce),
    sa.id_marque,
    COALESCE(count(a.*), 0) as nombre
from
    revenu_annonce as ra
join annonce as a
    on a.id = ra.id_annonce
left join sous_annonce as sa
    on sa.id_annonce = a.id
where a.etat = 2
group by
    sa.id_marque,
    extract(year from a.date_annonce),
    extract(month from a.date_annonce),
    extract(day from a.date_annonce);