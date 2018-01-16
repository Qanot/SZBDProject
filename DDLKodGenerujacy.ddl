-- Generated by Oracle SQL Developer Data Modeler 4.2.0.932
--   at:        2018-01-16 20:43:55 CET
--   site:      Oracle Database 11g
--   type:      Oracle Database 11g



CREATE TABLE bilety (
    id                  INTEGER NOT NULL,
    rodzajebiletow_id   INTEGER NOT NULL,
    rezerwacje_id       INTEGER
);

CREATE UNIQUE INDEX bilety__idx ON
    bilety (
        id
    ASC,
        rodzajebiletow_id
    ASC,
        rezerwacje_id
    ASC );

ALTER TABLE bilety ADD CONSTRAINT bilety_pk PRIMARY KEY ( id );

CREATE TABLE filmy (
    id             INTEGER NOT NULL,
    tytul          VARCHAR2(30) NOT NULL,
    czas_trwania   INTEGER NOT NULL
);

ALTER TABLE filmy ADD CONSTRAINT filmy_pk PRIMARY KEY ( id );

ALTER TABLE filmy ADD CONSTRAINT filmy_tytul_un UNIQUE ( tytul );

CREATE TABLE klienci (
    id         INTEGER NOT NULL,
    imie       VARCHAR2(30) NOT NULL,
    nazwisko   VARCHAR2(30) NOT NULL,
    email      VARCHAR2(30) NOT NULL,
    login      VARCHAR2(30) NOT NULL,
    haslo      VARCHAR2(30) NOT NULL,
    telefon    VARCHAR2(9) NOT NULL
);

ALTER TABLE klienci ADD CONSTRAINT klienci_pk PRIMARY KEY ( id );

ALTER TABLE klienci ADD CONSTRAINT klienci_login_un UNIQUE ( login );

CREATE TABLE miejsca (
    id           INTEGER NOT NULL,
    rzad         CHAR(2) NOT NULL,
    nr_miejsca   INTEGER NOT NULL,
    sale_id      INTEGER NOT NULL
);

ALTER TABLE miejsca ADD CONSTRAINT miejsca_pk PRIMARY KEY ( id );

ALTER TABLE miejsca ADD CONSTRAINT miejsca__un UNIQUE ( nr_miejsca,rzad,sale_id );

CREATE TABLE miejscanaseansie (
    id              INTEGER NOT NULL,
    bilety_id       INTEGER,
    rezerwacje_id   INTEGER,
    miejsca_id      INTEGER NOT NULL,
    seanse_id       INTEGER NOT NULL
);

ALTER TABLE miejscanaseansie ADD CONSTRAINT arc_5 CHECK (
    (
        (
            bilety_id IS NOT NULL
        ) AND (
            rezerwacje_id IS NULL
        )
    ) OR (
        (
            rezerwacje_id IS NOT NULL
        ) AND (
            bilety_id IS NULL
        )
    )
);

CREATE UNIQUE INDEX miejscanaseansie__idx ON
    miejscanaseansie ( bilety_id ASC );

ALTER TABLE miejscanaseansie ADD CONSTRAINT miejscanaseansie_pk PRIMARY KEY ( id );

ALTER TABLE miejscanaseansie ADD CONSTRAINT miejscanaseansie__un UNIQUE ( miejsca_id,seanse_id );

CREATE TABLE paragony (
    id              INTEGER NOT NULL,
    data_godzina    DATE NOT NULL,
    pracownicy_id   INTEGER NOT NULL
);

ALTER TABLE paragony ADD CONSTRAINT paragony_pk PRIMARY KEY ( id );

CREATE TABLE pracownicy (
    id         INTEGER NOT NULL,
    imie       VARCHAR2(30) NOT NULL,
    nazwisko   VARCHAR2(30) NOT NULL,
    plec       CHAR(1) NOT NULL,
    pesel      VARCHAR2(11) NOT NULL
);

ALTER TABLE pracownicy ADD CONSTRAINT pracownicy_pk PRIMARY KEY ( id );

ALTER TABLE pracownicy ADD CONSTRAINT pracownicy_pesel_un UNIQUE ( pesel );

CREATE TABLE produkty (
    id               INTEGER NOT NULL,
    cena             NUMBER NOT NULL,
    nazwa            VARCHAR2(30) NOT NULL,
    rozmiar_porcji   VARCHAR2(1) NOT NULL
);

ALTER TABLE produkty ADD CONSTRAINT produkty_pk PRIMARY KEY ( id );

ALTER TABLE produkty ADD CONSTRAINT produkty_nazwa_un UNIQUE ( nazwa,rozmiar_porcji );

CREATE TABLE produktynaparagonie (
    id            INTEGER NOT NULL,
    paragony_id   INTEGER NOT NULL,
    produkty_id   INTEGER,
    bilety_id     INTEGER
);

ALTER TABLE produktynaparagonie ADD CONSTRAINT arc_4 CHECK (
    (
        (
            bilety_id IS NOT NULL
        ) AND (
            produkty_id IS NULL
        )
    ) OR (
        (
            produkty_id IS NOT NULL
        ) AND (
            bilety_id IS NULL
        )
    )
);

CREATE UNIQUE INDEX produktynaparagonie__idx ON
    produktynaparagonie ( bilety_id ASC );

ALTER TABLE produktynaparagonie ADD CONSTRAINT produktynaparagonie_pk PRIMARY KEY ( id );

ALTER TABLE produktynaparagonie ADD CONSTRAINT produktynaparagonie__un UNIQUE ( bilety_id );

CREATE TABLE rezerwacje (
    id             INTEGER NOT NULL,
    data_godzina   DATE NOT NULL,
    czy_oplacona   CHAR(1) NOT NULL,
    klienci_id     INTEGER NOT NULL
);

ALTER TABLE rezerwacje ADD CONSTRAINT rezerwacje_pk PRIMARY KEY ( id );

CREATE TABLE rodzajebiletow (
    id      INTEGER NOT NULL,
    cena    NUMBER NOT NULL,
    nazwa   VARCHAR2(30) NOT NULL
);

ALTER TABLE rodzajebiletow ADD CONSTRAINT rodzajebiletow_pk PRIMARY KEY ( id );

ALTER TABLE rodzajebiletow ADD CONSTRAINT rodzajebiletow_nazwa_un UNIQUE ( nazwa );

CREATE TABLE sale (
    id        INTEGER NOT NULL,
    nr_sali   INTEGER NOT NULL
);

ALTER TABLE sale ADD CONSTRAINT sale_pk PRIMARY KEY ( id );

ALTER TABLE sale ADD CONSTRAINT sale_nr_sali_un UNIQUE ( nr_sali );

CREATE TABLE seanse (
    id             INTEGER NOT NULL,
    data_godzina   DATE NOT NULL,
    filmy_id       INTEGER NOT NULL,
    sala_id        INTEGER NOT NULL
);

ALTER TABLE seanse ADD CONSTRAINT seanse_pk PRIMARY KEY ( id );

ALTER TABLE seanse ADD CONSTRAINT seanse__un UNIQUE ( data_godzina,filmy_id,sala_id );

ALTER TABLE bilety ADD CONSTRAINT bilety_rezerwacje_fk FOREIGN KEY ( rezerwacje_id )
    REFERENCES rezerwacje ( id );

ALTER TABLE bilety ADD CONSTRAINT bilety_rodzajebiletow_fk FOREIGN KEY ( rodzajebiletow_id )
    REFERENCES rodzajebiletow ( id );

ALTER TABLE miejsca ADD CONSTRAINT miejsca_sale_fk FOREIGN KEY ( sale_id )
    REFERENCES sale ( id );

ALTER TABLE miejscanaseansie ADD CONSTRAINT miejscanaseansie_bilety_fk FOREIGN KEY ( bilety_id )
    REFERENCES bilety ( id );

ALTER TABLE miejscanaseansie ADD CONSTRAINT miejscanaseansie_miejsca_fk FOREIGN KEY ( miejsca_id )
    REFERENCES miejsca ( id );

ALTER TABLE miejscanaseansie ADD CONSTRAINT miejscanaseansie_rezerwacje_fk FOREIGN KEY ( rezerwacje_id )
    REFERENCES rezerwacje ( id );

ALTER TABLE miejscanaseansie ADD CONSTRAINT miejscanaseansie_seanse_fk FOREIGN KEY ( seanse_id )
    REFERENCES seanse ( id );

ALTER TABLE paragony ADD CONSTRAINT paragony_pracownicy_fk FOREIGN KEY ( pracownicy_id )
    REFERENCES pracownicy ( id );

ALTER TABLE produktynaparagonie ADD CONSTRAINT produktynapar_bilety_fk FOREIGN KEY ( bilety_id )
    REFERENCES bilety ( id );

ALTER TABLE produktynaparagonie ADD CONSTRAINT produktynapar_paragony_fk FOREIGN KEY ( paragony_id )
    REFERENCES paragony ( id );

ALTER TABLE produktynaparagonie ADD CONSTRAINT produktynapar_produkty_fk FOREIGN KEY ( produkty_id )
    REFERENCES produkty ( id );

ALTER TABLE rezerwacje ADD CONSTRAINT rezerwacje_klienci_fk FOREIGN KEY ( klienci_id )
    REFERENCES klienci ( id );

ALTER TABLE seanse ADD CONSTRAINT seanse_filmy_fk FOREIGN KEY ( filmy_id )
    REFERENCES filmy ( id );

ALTER TABLE seanse ADD CONSTRAINT seanse_sale_fk FOREIGN KEY ( sala_id )
    REFERENCES sale ( id );



-- Oracle SQL Developer Data Modeler Summary Report: 
-- 
-- CREATE TABLE                            13
-- CREATE INDEX                             3
-- ALTER TABLE                             39
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0


CREATE SEQUENCE paragon_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE produkt_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE bilet_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE film_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE pracownik_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE rezerwacja_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE klient_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE produktnaparagonie_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE seans_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE miejsce_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE sala_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE miejscenaseansie_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE rodzajbiletu_id_seq START WITH 1 NOCACHE ORDER;

