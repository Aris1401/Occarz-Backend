insert into 
    genre (designation)
values 
    ('Homme'), ('Femme');


insert into 
    utilisateur(contact, date_creation, email, mot_de_passe, nom, photo_profil, prenom, role, date_naissance, id_genre)
values 
    ('0343434444', CURRENT_TIMESTAMP, 'admin@gmail.com', '$2a$10$E2ZS/N9g9pUkabTFG4/TvONj39AsNNrjqw4pgwOH649stflngy2lC', 'Admin', NULL, 'Admin', 0, '2000-01-01', 1),
    ('0323232232', CURRENT_TIMESTAMP, 'joe@gmail.com', '$2a$10$sKv7nV4AMu09KvsLADmTn.k91Z5zOKQboPmbEhH0.1z76hSxBjZUW', 'Joe', NULL, 'Chill', 1, '1987-01-01', 1);


insert into
    annonce (titre, description, date_annonce, prix, etat, id_utilisateur)
values 
    ('Mercedes Zavatra', 'Amidy maika', '2024-01-01', 1040000, 0, 1),
    ('Mercedes Zavatra 1', 'Amidy maika', '2024-01-01', 1200000, 0, 2),
    ('Mercedes Zavatra 2', 'Amidy maika', '2024-01-01', 1040000, 0, 2),
    ('Mercedes Zavatra 3', 'Amidy maika', '2024-01-01', 1060000, 2, 2),
    ('Mercedes Zavatra 4', 'Amidy maika', '2024-01-01', 10350000, 2, 2),
    ('Mitsubishi Zavatra', 'Amidy maika', '2024-01-02', 900500, 1, 2),
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
    (NULL, 2, 1, 1, 1, 1, 1, 1, 1, 1),
    (NULL, 3, 1, 1, 1, 1, 1, 2, 1, 1),
    (NULL, 4, 1, 1, 1, 1, 1, 2, 1, 1),
    (NULL, 5, 1, 1, 1, 1, 1, 3, 1, 1),
    (NULL, 2, 2, 2, 2, 2, 1, 2, 2, 2);

-- Mois de l'annee
INSERT into mois(nom) values ('Janvier'), ('Fevrier'), ('Mars'), ('Avril'), ('Mai'), ('Juin'), ('Juillet'), ('Aout'), ('Septembre'), ('Octobre'), ('Novembre'), ('Decembre');

--- Statistiques (TEST)
-- Insertion dans la table revenu_annonce
INSERT INTO revenu_annonce (commission, id_annonce, pourcentage_commission, date_commission)
SELECT
    floor(random() * 100000000) + 1000000,
    generate_series(1, 7) AS id_annonce,
    20,
    CURRENT_TIMESTAMP - (floor(random() * 365) + 1) * INTERVAL '1 day' * (floor(random() * 10) + 1)
FROM generate_series(1, 50);

INSERT INTO revenu_utilisateur (id_annonce, montant, date_revenu)
SELECT
    id_annonce,
    floor(random() * 100000000) + 1000000,
    CURRENT_TIMESTAMP - (floor(random() * 365) + 1) * INTERVAL '1 day' * (floor(random() * 10) + 1)
FROM generate_series(1, 50) as iteration
CROSS JOIN (SELECT unnest(ARRAY[1, 2, 3, 4, 5, 6, 7]) AS id_annonce) as annonces;


--- STATISTIQUEs
--- Commission total
create view v_annees as
select
    extract(YEAR from date_commission) as annee
from
    revenu_annonce
group by
    extract(YEAR from date_commission);

create view v_affichage_mois as
select
    id,
    nom
from
    mois;

create or replace view v_details_date as
select
    v_annees.annee,
    v_affichage_mois.id as id_mois,
    v_affichage_mois.nom as mois_nom,
    0 as montant
from v_affichage_mois, v_annees;

create view v_total_commission as
select
    extract(YEAR FROM date_commission) as annee,
    sum(commission) as commission_total
from revenu_annonce
group by
    extract(YEAR FROM date_commission);

-- Commission par mois
create view v_details_commission_calcul as
select
    extract(MONTH FROM date_commission) as mois,
    extract(YEAR FROM date_commission) as annee,
    SUM(commission) as commission_total
from
   revenu_annonce
group by
    extract(YEAR FROM date_commission), extract(MONTH FROM date_commission);

-- Commissions par mois
create view v_details_commission as
select
    v_details_date.annee,
    v_details_date.id_mois,
    v_details_date.mois_nom,
    GREATEST(v_details_commission_calcul.commission_total, v_details_date.montant) as montant
from v_details_date
left join v_details_commission_calcul
    on v_details_date.annee = v_details_commission_calcul.annee
    and v_details_date.id_mois = v_details_commission_calcul.mois;

-- Ventes en annee
create view v_total_vente as
select
    extract(year from a.date_annonce) as annee,
    count(a.*) as nombre
from
    revenu_annonce as ra
join annonce as a
    on a.id = ra.id_annonce
where a.etat = 2
group by extract(year from a.date_annonce);

-- Details par jours
CREATE OR REPLACE VIEW v_details_date_jour AS
SELECT
    v_annees.annee,
    v_affichage_mois.id AS id_mois,
    v_affichage_mois.nom AS mois_nom,
    extract(DAY FROM  generate_series(
        DATE_TRUNC('MONTH', CAST(v_annees.annee || '-' || v_affichage_mois.id || '-01' AS DATE)),
        DATE_TRUNC('MONTH', CAST(v_annees.annee || '-' || v_affichage_mois.id || '-01' AS DATE)) + INTERVAL '1 MONTH - 1 DAY',
        INTERVAL '1 DAY'
    )::DATE) AS jour,
    0 AS montant
FROM
    v_affichage_mois
CROSS JOIN
    v_annees;


-- Vente en jours
create view v_details_vente_calcul as
select
    extract(year from ra.date_commission) as annee,
    extract(month from ra.date_commission) as mois,
    extract(day from ra.date_commission) as jour,
    count(a.*) as nombre
from
    revenu_annonce as ra
join annonce as a
    on a.id = ra.id_annonce
where a.etat = 2
group by
    extract(year from ra.date_commission),
    extract(month from ra.date_commission),
    extract(day from ra.date_commission);

create or replace view v_details_vente as
select
    v_details_date_jour.annee,
    id_mois,
    mois_nom,
    v_details_date_jour.jour,
    GREATEST(nombre, montant) as nombre
from
    v_details_date_jour
left join
    v_details_vente_calcul
    on v_details_vente_calcul.annee = v_details_date_jour.annee
    and v_details_vente_calcul.mois = v_details_date_jour.id_mois
    and v_details_vente_calcul.jour = v_details_date_jour.jour;

-- Vente en mois
create view v_details_vente_mois as
select
    annee,
    id_mois,
    mois_nom,
    sum(nombre)
from
    v_details_vente
group by annee, id_mois, mois_nom;

-- Vente marque
create view v_details_date_marque as
select
    annee,
    id_mois,
    mois_nom,
    jour,
    marque.id as id_marque,
    0 as nombre
from v_details_date_jour, marque;

create view v_details_vente_marque_calcul as
select
    extract(year from ra.date_commission) as annee,
    extract(month from ra.date_commission) as mois,
    extract(day from ra.date_commission) as jour,
    sa.id_marque as marque,
    count(a.*) as nombre
from
    revenu_annonce as ra
join annonce as a
    on a.id = ra.id_annonce
left join sous_annonce as sa
    on sa.id_annonce = a.id
where a.etat = 2
group by
    sa.id_marque,
    extract(year from ra.date_commission),
    extract(month from ra.date_commission),
    extract(day from ra.date_commission);

create or replace view v_details_vente_marque as
select
    v_details_date_marque.annee,
    id_mois,
    mois_nom,
    v_details_date_marque.jour,
    v_details_date_marque.id_marque,
    greatest(v_details_date_marque.nombre, v_details_vente_marque_calcul.nombre) as nombre
from
    v_details_date_marque
left join
    v_details_vente_marque_calcul on
    v_details_date_marque.annee = v_details_vente_marque_calcul.annee
    and v_details_date_marque.id_mois = v_details_vente_marque_calcul.mois
    and v_details_date_marque.jour = v_details_vente_marque_calcul.jour
    and v_details_date_marque.id_marque = v_details_vente_marque_calcul.marque;

insert into configuration(commission) values (20);