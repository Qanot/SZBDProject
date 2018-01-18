create or replace FUNCTION
liczba_miejsc_w_sali (nr_sali IN INTEGER)RETURN NUMBER IS
ile NUMBER;
BEGIN
   --sprawdzenie czy jest sala o takim nr
  SELECT 0 into ile FROM miejsca where sala_nr_sali = nr_sali;
  SELECT count(*) INTO ile from miejsca WHERE sala_nr_sali = nr_sali;
  return ile;
END liczba_miejsc_w_sali;
/

create or replace FUNCTION
    wstaw_pracownika (v_imie IN pracownicy.imie%TYPE, v_nazwisko IN pracownicy.nazwisko%TYPE,
                      v_plec IN pracownicy.plec%TYPE, v_pesel IN pracownicy.pesel%TYPE,
                      v_id OUT pracownicy.id%TYPE) RETURN NUMBER IS
BEGIN
    v_id := pracownik_id_seq.nextval;
    INSERT INTO PRACOWNICY(ID, IMIE, NAZWISKO, PLEC, PESEL)
    VALUES(v_id, v_imie, v_nazwisko, v_plec, v_pesel);
    RETURN 1;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RETURN 0;
END wstaw_pracownika;
/

create or replace FUNCTION
    update_pracownika (v_id IN pracownicy.id%TYPE, v_imie IN pracownicy.imie%TYPE,
			v_nazwisko IN pracownicy.nazwisko%TYPE, v_plec IN pracownicy.plec%TYPE,
			v_pesel IN pracownicy.pesel%TYPE) RETURN NUMBER IS
BEGIN
    v_id := pracownik_id_seq.nextval;
    INSERT INTO PRACOWNICY(ID, IMIE, NAZWISKO, PLEC, PESEL)
    VALUES(v_id, v_imie, v_nazwisko, v_plec, v_pesel);
    RETURN 1;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RETURN 0;
END update_pracownika;
/


create or replace FUNCTION wstaw_klienta(v_imie IN klienci.imie%TYPE, v_nazwisko IN klienci.nazwisko%TYPE,
                        v_email IN klienci.email%TYPE, v_login IN klienci.login%TYPE,
                        v_haslo IN klienci.haslo%TYPE, v_telefon IN klienci.telefon%TYPE,
                        v_id OUT klienci.id%TYPE) return number is
BEGIN
    v_id := klient_id_seq.nextval;
    INSERT INTO KLIENCI(ID, IMIE, NAZWISKO, EMAIL, LOGIN, HASLO, TELEFON)
    VALUES(V_ID, V_IMIE, V_NAZWISKO, V_EMAIL, V_LOGIN, V_HASLO, V_TELEFON);
    RETURN 1;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RETURN 0;
END wstaw_klienta;
/

create or replace FUNCTION update_klienta(v_id IN klienci.id%TYPE, v_imie IN klienci.imie%TYPE,
                        v_nazwisko IN klienci.nazwisko%TYPE, v_email IN klienci.email%TYPE,
                        v_login IN klienci.login%TYPE, v_haslo IN klienci.haslo%TYPE,
                        v_telefon IN klienci.telefon%TYPE) return number is
BEGIN
    UPDATE KLIENCI
    SET IMIE = V_IMIE,
    NAZWISKO = V_NAZWISKO,
    EMAIL = V_EMAIL,
    LOGIN = V_LOGIN,
    HASLO = V_HASLO,
    TELEFON = V_TELEFON
    WHERE ID = V_ID;
    RETURN 1;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RETURN 0;
END update_klienta;
/

create or replace FUNCTION wstaw_sale(v_nr_sali IN sale.nr_sali%TYPE,
                                    v_id OUT sale.id%TYPE) return number is
  BEGIN
    v_id := sala_id_seq.nextval;
    INSERT INTO SALE(id, nr_sali)
    VALUES(v_id, v_nr_sali);
    RETURN 1;
  EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
    RETURN 0;
END wstaw_sale;
/

create or replace FUNCTION update_sala(v_id IN sale.id%type,
                v_nr_sali IN sale.nr_sali%TYPE) return number is
BEGIN
    UPDATE SALE SET NR_SALI = v_nr_sali
    WHERE id = v_id;
    RETURN 1;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RETURN 0;
END update_sala;
/

create or replace FUNCTION
    wstaw_produkt (v_cena IN produkty.cena%TYPE, v_nazwa IN PRODUKTY.NAZWA%TYPE,
                      v_rozmiar_porcji IN PRODUKTY.ROZMIAR_PORCJI%TYPE,
                      v_id OUT PRODUKTY.ID%TYPE) RETURN NUMBER IS
BEGIN
    v_id := PRODUKT_ID_SEQ.NEXTVAL;
    INSERT INTO Produkty(ID, CENA, NAZWA, ROZMIAR_PORCJI)
    VALUES(v_id, v_cena, v_nazwa, v_rozmiar_porcji);
    RETURN 1;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RETURN 0;
END wstaw_produkt;
/

create or replace FUNCTION
    update_produkt (v_id IN PRODUKTY.ID%TYPE, v_cena IN produkty.cena%TYPE,
                    v_nazwa IN PRODUKTY.NAZWA%TYPE,
                    v_rozmiar_porcji IN PRODUKTY.ROZMIAR_PORCJI%TYPE) RETURN NUMBER IS
BEGIN
    UPDATE PRODUKTY
    SET CENA = V_CENA,
    NAZWA = V_NAZWA,
    ROZMIAR_PORCJI = V_ROZMIAR_PORCJI
    WHERE ID = V_ID;
    RETURN 1;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RETURN 0;
END update_produkt;
/

create or replace PROCEDURE
    wstaw_paragon (v_data_godzina IN PARAGONY.DATA_GODZINA%TYPE, v_id_prac IN PARAGONY.PRACOWNICY_ID%TYPE,
                   v_id OUT pracownicy.id%TYPE) IS
BEGIN
    v_id := paragon_id_seq.nextval;
    INSERT INTO PARAGONY(ID, DATA_GODZINA, PRACOWNICY_ID)
    VALUES(v_id, v_data_godzina, v_id_prac);
END wstaw_paragon;


