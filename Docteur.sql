CREATE DATABASE Docteur;

\c docteur

CREATE TABLE patient (
    id serial primary key,
    nom varchar(50),
    Age integer
);

INSERT into patient values
(default,'Tsiry',20),
(default,'Fetra',12);

CREATE TABLE symptome(
    id VARCHAR(20) primary key,
    nom varchar(50)
);

INSERT into symptome values
('S001','loha'),
('S002','tenda'),
('S003','kibo'),
('S004','Sery'),
('S005','koaka'),
('S007','reraka'),
('S006','vavony');

CREATE TABLE symptome_patient(
    id serial primary key,
    idPatient int references patient(id),
    idSymptome varchar(20) references symptome(id),
    etat integer
);

INSERT into symptome_patient values
(default,1,'S001',6),
(default,1,'S002',5),
(default,1,'S005',4);


CREATE TABLE maladie(
    id serial primary key,
    nom varchar(40)
);

INSERT into maladie values
(default,'grippe'),
(default,'Rhume'),
(default,'Reflux gastro-œsophagien (RGO)');

CREATE TABLE soinMedical(
    id serial primary key,
    idMaladie int references maladie(id),
    idSymptome varchar(20) references symptome(id),
    entre integer,
    et integer
);

INSERT into soinMedical values
(default,1,'S001',4,8),
(default,1,'S004',4,8),
(default,1,'S005',4,7);


CREATE TABLE medicament(
    id serial primary key,
    nom varchar(50),
    type integer
);

-- type 1 = comprimer , 0 effervescence
INSERT into medicament values
(default,'doliprane',0),
(default,'paracetamol',1),
(default,'Ca C-100',0),
(default,'Antitussif',2),
(default,'Anti-acides ',1);
(default,'Lysorynx ',0);

CREATE TABLE detail_medicament_symp(
    id serial primary key,
    idSymptome varchar(20) references symptome(id),
    idMedicament int references medicament(id),
    entre integer,
    et integer,
    prix double precision
);

UPDATE detail_medicament_symp set et =6 WHERE id = 6;

INSERT INTO detail_medicament_symp VALUES
(default,'S001',1,6,10,10000),
(default,'S001',2,1,5,3000),
(default,'S003',5,3,8,8000),
(default,'S005',2,2,6,3000);

INSERT INTO detail_medicament_symp VALUES
(default,'S002',6,2,6,15000);
-- view 
CREATE or replace view v_getPatient_Symptome as 
SELECT sp.id, sp.idPatient,sp.idSymptome, sp.etat, p.nom as nom_patient, 
s.nom as nom_symptome  
FROM symptome_patient as sp JOIN patient as p ON sp.idPatient = p.id
JOIN symptome as s on sp.idSymptome = s.id;

CREATE or replace view v_getMaladie_Symptome as
SELECT sm.id, sm.idMaladie, sm.idSymptome, sm.entre, sm.et, m.nom as nom_maladie, s.nom as nom_symptome
FROM  soinMedical as sm JOIN maladie as m on sm.idMaladie = m.id
JOIN symptome as s on sm.idSymptome = s.id;

CREATE or replace view v_getMedicament_Symptome as 
SELECT dms.id, dms.idSymptome, dms.idMedicament, dms.entre, dms.et,dms.prix,
m.nom as nom_medicament, m.type, s.nom as nom_symptome 
FROM detail_medicament_symp as dms JOIN medicament as m on dms.idMedicament = m.id
JOIN symptome as s on dms.idSymptome = s.id;


CREATE or replace view v_getPrix as
SELECT DISTINCT ms.id, ms.idSymptome, ms.idMedicament, ms.entre, ms.et,ms.prix,
ms.nom_medicament, ms.type, ms.nom_symptome,
ps.nom_patient, ps.etat 
FROM v_getMedicament_Symptome as ms JOIN v_getPatient_Symptome as ps
on ms.idSymptome = ps.idSymptome;

SELECT 
    ms.id, 
    ms.idSymptome, 
    ms.idMedicament, 
    ms.entre, 
    ms.et,
    ms.prix,
    ms.nom_medicament, 
    ms.type, 
    ms.nom_symptome,
    ps.nom_patient, 
    ps.etat,
    ps.idPatient 
FROM 
    v_getMedicament_Symptome as ms 
JOIN 
    v_getPatient_Symptome as ps on ms.idSymptome = ps.idSymptome

WHERE (ps.etat BETWEEN ms.entre AND ms.et) AND ms.idMedicament IS NOT NULL;


CREATE or replace view v_getPrix as
WITH RankedData AS (
    SELECT 
        ms.id, 
        ms.idSymptome, 
        ms.idMedicament, 
        ms.entre, 
        ms.et,
        ms.prix,
        ms.nom_medicament, 
        ms.type, 
        ms.nom_symptome,
        ps.idPatient,
        ps.nom_patient, 
        ps.etat,
        ROW_NUMBER() OVER (PARTITION BY ms.idSymptome ORDER BY ms.prix ASC) as RowNum
    FROM 
        v_getMedicament_Symptome as ms 
    JOIN 
        v_getPatient_Symptome as ps on ms.idSymptome = ps.idSymptome
    WHERE 
        (ps.etat BETWEEN ms.entre AND ms.et) AND ms.idMedicament IS NOT NULL
)
SELECT 
    id, 
    idSymptome, 
    idMedicament, 
    entre, 
    et,
    prix,
    nom_medicament, 
    type, 
    nom_symptome,
    idPatient,
    nom_patient, 
    etat
FROM 
    RankedData
WHERE 
    RowNum = 1;







