ALTER TABLE PRACOWNICY
ADD CONSTRAINT PRAC_PLEC_K_M CHECK(PLEC IN ('M', 'K'));

ALTER TABLE PRODUKTY
ADD CONSTRAINT PRODUKTY_ROZM_PORC_CONSTR CHECK(ROZMIAR_PORCJI IN ('S', 'M', 'L'));

ALTER TABLE REZERWACJE
ADD CONSTRAINT REZERWACJE_OPLACONA_CONSTR CHECK(CZY_OPLACONA IN ('T', 'N'));