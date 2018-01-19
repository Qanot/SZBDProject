SELECT ID,
    TO_CHAR(DATA_GODZINA,'YYYY:MM:DD:HH24:MI:SS'),
    FILMY_ID, SALE_ID
FROM SEANSE;


INSERT INTO SEANSE(ID, DATA_GODZINA, FILMY_ID, SALE_ID)
    VALUES(seans_id_seq.nextval, TO_DATE('2018:10:10:23:20:00','YYYY:MM:DD:HH24:MI:SS'), 7, 13);
    
    
select * from filmy;
select * from sale;
select * from seanse;