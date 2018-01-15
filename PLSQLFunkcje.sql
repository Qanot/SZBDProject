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

create or replace PROCEDURE
    wstaw_pracownika (v_imie IN pracownicy.imie%TYPE, v_nazwisko IN pracownicy.nazwisko%TYPE,
                      v_plec IN pracownicy.plec%TYPE, v_id OUT pracownicy.id%TYPE) IS
BEGIN
    v_id := pracownik_id_seq.nextval;
    INSERT INTO PRACOWNICY(ID, IMIE, NAZWISKO, PLEC)
    VALUES(v_id, v_imie, v_nazwisko, v_plec);

END wstaw_pracownika;
/

create or replace FUNCTION wstaw_klienta(v_imie IN klienci.imie%TYPE, v_nazwisko IN klienci.nazwisko%TYPE,
                        v_email IN klienci.email%TYPE, v_login IN klienci.login%TYPE,
                        v_haslo IN klienci.haslo%TYPE, v_telefon IN klienci.telefon%TYPE) return number is
BEGIN
    INSERT INTO KLIENCI(IMIE, NAZWISKO, EMAIL, LOGIN, HASLO, TELEFON)
    VALUES(V_IMIE, V_NAZWISKO, V_EMAIL, V_LOGIN, V_HASLO, V_TELEFON);
    RETURN 1;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RETURN 0;
END wstaw_klienta;
/

create or replace FUNCTION wstaw_sale(v_nr_sali IN sale.nr_sali%TYPE) return number is
  BEGIN
    INSERT INTO SALE(nr_sali)
    VALUES(v_nr_sali);
    RETURN 1;
  EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
    RETURN 0;
  END wstaw_sale;
/

create or replace FUNCTION update_sala(v_nr_sali_stare IN sale.nr_sali%TYPE,
                v_nr_sali_nowe IN sale.nr_sali%TYPE) return number is
BEGIN
    UPDATE SALE SET NR_SALI = v_nr_sali_nowe
    WHERE NR_SALI = v_nr_sali_stare;
    RETURN 1;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RETURN 0;
END update_sala;

/
create or replace PROCEDURE
    wstaw_produkt (v_cena IN produkty.cena%TYPE, v_nazwa IN PRODUKTY.NAZWA%TYPE,
                      v_rozmiar_porcji IN PRODUKTY.ROZMIAR_PORCJI%TYPE, v_id OUT PRODUKTY.ID%TYPE) IS
BEGIN
    v_id := PRODUKT_ID_SEQ.NEXTVAL;
    INSERT INTO Produkty(ID, CENA, NAZWA, ROZMIAR_PORCJI)
    VALUES(v_id, v_cena, v_nazwa, v_rozmiar_porcji);
END wstaw_produkt;

/
create or replace PROCEDURE
    wstaw_paragon (v_data_godzina IN PARAGONY.DATA_GODZINA%TYPE, v_id OUT pracownicy.id%TYPE) IS
BEGIN
    v_id := paragon_id_seq.nextval;
    INSERT INTO PARAGONY(ID, DATA_GODZINA)
    VALUES(v_id, v_data_godzina);
END wstaw_paragon;
