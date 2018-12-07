CREATE USER filmApp_admin
  IDENTIFIED BY q1
  DEFAULT TABLESPACE USERS
  TEMPORARY TABLESPACE TEMP
  QUOTA 20M on USERS;

GRANT create session TO filmApp_admin;
GRANT create table TO filmApp_admin;
GRANT create view TO filmApp_admin;
GRANT create any trigger TO filmApp_admin;
GRANT create any procedure TO filmApp_admin;
GRANT create sequence TO filmApp_admin;
GRANT create synonym TO filmApp_admin;


------------------------------------


CREATE TABLE USER_(
	ID NUMBER,
	USERNAME VARCHAR2(1000),
	PASSWORD VARCHAR2(1000),
	CONSTRAINT USER_PK PRIMARY KEY(ID)
);

CREATE TABLE ROLE(
	ID NUMBER,
	NAME VARCHAR2(1000),
	CONSTRAINT ROLE_PK PRIMARY KEY(ID)
);

CREATE TABLE USER_ROLE(
	USER_ID NUMBER,
	ROLE_ID NUMBER,
	CONSTRAINT USER_ROLE_PK PRIMARY KEY(USER_ID, ROLE_ID),
	CONSTRAINT USER_ROLE_FK
		FOREIGN KEY (USER_ID)
		REFERENCES USER_(ID),
	CONSTRAINT ROLE_USER_FK
		FOREIGN KEY (ROLE_ID)
		REFERENCES ROLE(ID)
);

CREATE TABLE PERSON(
	ID NUMBER,
	FULL_NAME VARCHAR2(1000),
	STAGE_NAME VARCHAR2(1000),
	DATE_OF_BIRTH DATE,
	DATE_OF_DEATH DATE,
	PLACE_OF_BIRTH VARCHAR2(1000),
	BIOGRAPHY VARCHAR2(4000),
	HEIGHT NUMBER(3),
	CONSTRAINT PERSON_PK PRIMARY KEY(ID)
);

CREATE TABLE FILM(
	ID NUMBER,
	ORIGINAL_TITLE VARCHAR2(1000),
	POLISH_TITLE VARCHAR2(1000),
	WORLDWIDE_RELEASE_DATE DATE,
	POLISH_RELEASE_DATE DATE,
	BUDGET NUMBER,
	BOX_OFFICE NUMBER,
	RUNNING_TIME NUMBER,
	STORYLINE VARCHAR2(4000),
	RATINGS_SUM NUMBER(11) DEFAULT 0,
    NUMBER_OF_VOTES NUMBER(10) DEFAULT 0,
    AVERAGE_RATING NUMBER(3, 1),
	CONSTRAINT FILM_PK PRIMARY KEY(ID)
);

CREATE TABLE ACTOR(
	PERSON_ID NUMBER,
	FILM_ID NUMBER,
	CHARACTER VARCHAR2(500),
	CONSTRAINT ACTOR_PK PRIMARY KEY(PERSON_ID, FILM_ID),
	CONSTRAINT ACTOR_PERSON_FK
		FOREIGN KEY (PERSON_ID)
		REFERENCES PERSON(ID),
	CONSTRAINT ACTOR_FILM_FK
		FOREIGN KEY (FILM_ID)
		REFERENCES FILM(ID)
);

CREATE TABLE DIRECTOR(
	PERSON_ID NUMBER,
	FILM_ID NUMBER,
	CONSTRAINT DIRECTOR_PK PRIMARY KEY(PERSON_ID, FILM_ID),
	CONSTRAINT DIRECTOR_PERSON_FK
		FOREIGN KEY (PERSON_ID)
		REFERENCES PERSON(ID),
	CONSTRAINT DIRECTOR_FILM_FK
		FOREIGN KEY (FILM_ID)
		REFERENCES FILM(ID)
);

CREATE TABLE ORIGINAL_MATERIALS(
	PERSON_ID NUMBER,
	FILM_ID NUMBER,
	CONSTRAINT ORIGINAL_MATERIALS_PK PRIMARY KEY(PERSON_ID, FILM_ID),
	CONSTRAINT ORIGINAL_MATERIALS_PERSON_FK
		FOREIGN KEY (PERSON_ID)
		REFERENCES PERSON(ID),
	CONSTRAINT ORIGINAL_MATERIALS_FILM_FK
		FOREIGN KEY (FILM_ID)
		REFERENCES FILM(ID)
);

CREATE TABLE PRODUCER(
	PERSON_ID NUMBER,
	FILM_ID NUMBER,
	CONSTRAINT PRODUCER_PK PRIMARY KEY(PERSON_ID, FILM_ID),
	CONSTRAINT PRODUCER_PERSON_FK
		FOREIGN KEY (PERSON_ID)
		REFERENCES PERSON(ID),
	CONSTRAINT PRODUCER_FILM_FK
		FOREIGN KEY (FILM_ID)
		REFERENCES FILM(ID)
);

CREATE TABLE FILM_EDITOR(
	PERSON_ID NUMBER,
	FILM_ID NUMBER,
	CONSTRAINT FILM_EDITOR_PK PRIMARY KEY(PERSON_ID, FILM_ID),
	CONSTRAINT FILM_EDITOR_PERSON_FK
		FOREIGN KEY (PERSON_ID)
		REFERENCES PERSON(ID),
	CONSTRAINT FILM_EDITOR_FILM_FK
		FOREIGN KEY (FILM_ID)
		REFERENCES FILM(ID)
);

CREATE TABLE MUSIC(
	PERSON_ID NUMBER,
	FILM_ID NUMBER,
	CONSTRAINT MUSIC_PK PRIMARY KEY(PERSON_ID, FILM_ID),
	CONSTRAINT MUSIC_PERSON_FK
		FOREIGN KEY (PERSON_ID)
		REFERENCES PERSON(ID),
	CONSTRAINT MUSIC_FILM_FK
		FOREIGN KEY (FILM_ID)
		REFERENCES FILM(ID)
);

CREATE TABLE SCREENWRITER(
	PERSON_ID NUMBER,
	FILM_ID NUMBER,
	CONSTRAINT SCREENWRITER_PK PRIMARY KEY(PERSON_ID, FILM_ID),
	CONSTRAINT SCREENWRITER_PERSON_FK
		FOREIGN KEY (PERSON_ID)
		REFERENCES PERSON(ID),
	CONSTRAINT SCREENWRITER_FILM_FK
		FOREIGN KEY (FILM_ID)
		REFERENCES FILM(ID)
);

CREATE TABLE COSTUME_DESIGNER(
	PERSON_ID NUMBER,
	FILM_ID NUMBER,
	CONSTRAINT COSTUME_DESIGNER_PK PRIMARY KEY(PERSON_ID, FILM_ID),
	CONSTRAINT COSTUME_DESIGNER_PERSON_FK
		FOREIGN KEY (PERSON_ID)
		REFERENCES PERSON(ID),
	CONSTRAINT COSTUME_DESIGNER_FILM_FK
		FOREIGN KEY (FILM_ID)
		REFERENCES FILM(ID)
);

CREATE TABLE CINEMATOGRAPHER(
	PERSON_ID NUMBER,
	FILM_ID NUMBER,
	CONSTRAINT CINEMATOGRAPHER_PK PRIMARY KEY(PERSON_ID, FILM_ID),
	CONSTRAINT CINEMATOGRAPHER_PERSON_FK
		FOREIGN KEY (PERSON_ID)
		REFERENCES PERSON(ID),
	CONSTRAINT CINEMATOGRAPHER_FILM_FK
		FOREIGN KEY (FILM_ID)
		REFERENCES FILM(ID)
);

CREATE TABLE COUNTRY(
	COUNTRY_CODE_ID VARCHAR2(2),
	COUNTRY_NAME VARCHAR2(100),
	CONSTRAINT COUNTRY_PK PRIMARY KEY(COUNTRY_CODE_ID)
);

CREATE TABLE FILM_COUNTRY(
	FILM_ID NUMBER,
	COUNTRY_ID VARCHAR2(2),
	CONSTRAINT FILM_COUNTRY_PK PRIMARY KEY(FILM_ID, COUNTRY_ID),
    CONSTRAINT FILM_COUNTR_F_FK
		FOREIGN KEY (FILM_ID)
		REFERENCES FILM(ID),
	CONSTRAINT FILM_COUNTRY_C_FK
		FOREIGN KEY (COUNTRY_ID)
		REFERENCES COUNTRY(COUNTRY_CODE_ID)
);

CREATE TABLE GENRE(
	ID NUMBER,
	GENRE_NAME VARCHAR2(500),
	CONSTRAINT GENRE_PK PRIMARY KEY(ID)
);

CREATE TABLE FILM_GENRE(
	FILM_ID NUMBER,
	GENRE_ID NUMBER,
	CONSTRAINT FILM_GENRE_PK PRIMARY KEY(GENRE_ID, FILM_ID),
	CONSTRAINT FILM_GENRE_FK
		FOREIGN KEY (FILM_ID)
		REFERENCES FILM(ID),
	CONSTRAINT GENRE_FILM_FK
		FOREIGN KEY (GENRE_ID)
		REFERENCES GENRE(ID)
);

CREATE TABLE FILM_RATING(
	USER_ID NUMBER,
	FILM_ID NUMBER,
	RATING NUMBER(1),
	DATE_OF_RATE DATE,
	CONSTRAINT FILM_RATING_PK PRIMARY KEY(USER_ID, FILM_ID),
	CONSTRAINT FILM_RATING_USER_FK
		FOREIGN KEY (USER_ID)
		REFERENCES USER_(ID),
	CONSTRAINT FILM_RATING_FILM_FK
		FOREIGN KEY (FILM_ID)
		REFERENCES FILM(ID)
);

CREATE SEQUENCE genre_seq
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  NOCACHE;


CREATE OR REPLACE TRIGGER genre_auto_increment
  BEFORE INSERT ON genre
  FOR EACH ROW
BEGIN
  SELECT genre_seq.nextval
  INTO :new.id
  FROM dual;
END;

/

CREATE OR REPLACE TRIGGER update_average_rating
BEFORE INSERT
  ON FILM_RATING
  FOR EACH ROW
DECLARE
  V_RATINGS_SUM NUMBER(11);
  V_NUMBER_OF_VOTES NUMBER(10);
  V_AVERAGE_RATING NUMBER(3, 1);
BEGIN
   SELECT f.RATINGS_SUM, f.NUMBER_OF_VOTES
     INTO V_RATINGS_SUM, V_NUMBER_OF_VOTES
   FROM FILM f
   WHERE f.ID = :new.film_id;

   V_RATINGS_SUM := V_RATINGS_SUM + :new.RATING;
   V_NUMBER_OF_VOTES := V_NUMBER_OF_VOTES + 1;
   V_AVERAGE_RATING := V_RATINGS_SUM / V_NUMBER_OF_VOTES;

   UPDATE FILM f
     SET f.RATINGS_SUM = V_RATINGS_SUM,
         f.NUMBER_OF_VOTES = V_NUMBER_OF_VOTES,
		 f.AVERAGE_RATING = V_AVERAGE_RATING
     WHERE f.ID = :new.film_id;
END;

/

CREATE SEQUENCE user_seq
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  NOCACHE;


INSERT INTO GENRE(GENRE_NAME) VALUES('akcja');
INSERT INTO GENRE(GENRE_NAME) VALUES('animacja');
INSERT INTO GENRE(GENRE_NAME) VALUES('anime');
INSERT INTO GENRE(GENRE_NAME) VALUES('biograficzny');
INSERT INTO GENRE(GENRE_NAME) VALUES('dokumentalny');
INSERT INTO GENRE(GENRE_NAME) VALUES('dramat');
INSERT INTO GENRE(GENRE_NAME) VALUES('dramat historyczny');
INSERT INTO GENRE(GENRE_NAME) VALUES('edukacyjny');
INSERT INTO GENRE(GENRE_NAME) VALUES('erotyczny');
INSERT INTO GENRE(GENRE_NAME) VALUES('etiuda');
INSERT INTO GENRE(GENRE_NAME) VALUES('familijny');
INSERT INTO GENRE(GENRE_NAME) VALUES('fantasy');
INSERT INTO GENRE(GENRE_NAME) VALUES('historyczny');
INSERT INTO GENRE(GENRE_NAME) VALUES('horror');
INSERT INTO GENRE(GENRE_NAME) VALUES('komedia');
INSERT INTO GENRE(GENRE_NAME) VALUES('komedia kryminalna');
INSERT INTO GENRE(GENRE_NAME) VALUES('komedia obyczajowa');
INSERT INTO GENRE(GENRE_NAME) VALUES('komedia romantyczna');
INSERT INTO GENRE(GENRE_NAME) VALUES('krótkometrażowy');
INSERT INTO GENRE(GENRE_NAME) VALUES('kryminał');
INSERT INTO GENRE(GENRE_NAME) VALUES('melodramat');
INSERT INTO GENRE(GENRE_NAME) VALUES('musical');
INSERT INTO GENRE(GENRE_NAME) VALUES('muzyczny');
INSERT INTO GENRE(GENRE_NAME) VALUES('niemy');
INSERT INTO GENRE(GENRE_NAME) VALUES('przygodowy');
INSERT INTO GENRE(GENRE_NAME) VALUES('romans');
INSERT INTO GENRE(GENRE_NAME) VALUES('sci-fi');
INSERT INTO GENRE(GENRE_NAME) VALUES('sportowy');
INSERT INTO GENRE(GENRE_NAME) VALUES('thriller');
INSERT INTO GENRE(GENRE_NAME) VALUES('western');
INSERT INTO GENRE(GENRE_NAME) VALUES('wojenny');
INSERT INTO GENRE(GENRE_NAME) VALUES('baśń');
INSERT INTO GENRE(GENRE_NAME) VALUES('biblijny');
INSERT INTO GENRE(GENRE_NAME) VALUES('czarna komedia');
INSERT INTO GENRE(GENRE_NAME) VALUES('dla dzieci');
INSERT INTO GENRE(GENRE_NAME) VALUES('dla młodzieży');
INSERT INTO GENRE(GENRE_NAME) VALUES('dokumentalizowany');
INSERT INTO GENRE(GENRE_NAME) VALUES('dramat obyczajowy');
INSERT INTO GENRE(GENRE_NAME) VALUES('dramat sądowy');
INSERT INTO GENRE(GENRE_NAME) VALUES('dramat społeczny');
INSERT INTO GENRE(GENRE_NAME) VALUES('dreszczowiec');
INSERT INTO GENRE(GENRE_NAME) VALUES('fabularyzowany dokument');
INSERT INTO GENRE(GENRE_NAME) VALUES('fikcja literacka');
INSERT INTO GENRE(GENRE_NAME) VALUES('film-noir');
INSERT INTO GENRE(GENRE_NAME) VALUES('gangsterski');
INSERT INTO GENRE(GENRE_NAME) VALUES('groteska filmowa');
INSERT INTO GENRE(GENRE_NAME) VALUES('karate');
INSERT INTO GENRE(GENRE_NAME) VALUES('katastroficzny');
INSERT INTO GENRE(GENRE_NAME) VALUES('komedia dokumentalna');
INSERT INTO GENRE(GENRE_NAME) VALUES('kostiumowy');
INSERT INTO GENRE(GENRE_NAME) VALUES('nowele filmowe');
INSERT INTO GENRE(GENRE_NAME) VALUES('obyczajowy');
INSERT INTO GENRE(GENRE_NAME) VALUES('poetycki');
INSERT INTO GENRE(GENRE_NAME) VALUES('polityczny');
INSERT INTO GENRE(GENRE_NAME) VALUES('prawniczy');
INSERT INTO GENRE(GENRE_NAME) VALUES('propagandowy');
INSERT INTO GENRE(GENRE_NAME) VALUES('przyrodniczy');
INSERT INTO GENRE(GENRE_NAME) VALUES('psychologiczny');
INSERT INTO GENRE(GENRE_NAME) VALUES('płaszcza i szpady');
INSERT INTO GENRE(GENRE_NAME) VALUES('religijny');
INSERT INTO GENRE(GENRE_NAME) VALUES('satyra');
INSERT INTO GENRE(GENRE_NAME) VALUES('sensacyjny');
INSERT INTO GENRE(GENRE_NAME) VALUES('surrealistyczny');
INSERT INTO GENRE(GENRE_NAME) VALUES('szpiegowski');
INSERT INTO GENRE(GENRE_NAME) VALUES('sztuki walki');
INSERT INTO GENRE(GENRE_NAME) VALUES('xxx');



INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AF', 'Afganistan');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AL', 'Albania');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('DZ', 'Algieria');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AD', 'Andora');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AO', 'Angola');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AI', 'Anguilla');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AQ', 'Antarktyda');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AG', 'Antigua i Barbuda');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AN', 'Antyle Holenderskie');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SA', 'Arabia Saudyjska');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AR', 'Argentyna');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AM', 'Armenia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AW', 'Aruba');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AU', 'Australia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AT', 'Austria');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AZ', 'Azerbejdżan');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BS', 'Bahamy');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BH', 'Bahrajn');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BD', 'Bangladesz');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BB', 'Barbados');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BE', 'Belgia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BZ', 'Belize');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BJ', 'Benin');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BM', 'Bermudy');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BT', 'Bhutan');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BY', 'Białoruś');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BO', 'Boliwia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BA', 'Bośnia i Hercegowina');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BW', 'Botswana');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BR', 'Brazylia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BN', 'Brunei Darussalam');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('IO', 'Brytyjskie Terytorium Oceanu Indyjskiego');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BG', 'Bułgaria');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BF', 'Burkina Faso');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BI', 'Burundi');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('XC', 'Ceuta');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CL', 'Chile');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CN', 'Chiny');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('HR', 'Chorwacja');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CY', 'Cypr');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TD', 'Czad');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('ME', 'Czarnogóra');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('DK', 'Dania');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('DM', 'Dominika');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('DO', 'Dominikana');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('DJ', 'Dżibuti');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('EG', 'Egipt');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('EC', 'Ekwador');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('ER', 'Erytrea');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('EE', 'Estonia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('ET', 'Etiopia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('FK', 'Falklandy');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('FJ', 'Fidżi Republika');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('PH', 'Filipiny');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('FI', 'Finlandia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TF', 'Francuskie Terytorium Południowe');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('FR', 'Francja');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GA', 'Gabon');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GM', 'Gambia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GH', 'Ghana');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GI', 'Gibraltar');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GR', 'Grecja');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GD', 'Grenada');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GL', 'Grenlandia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GE', 'Gruzja');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GU', 'Guam');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GY', 'Gujana');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GT', 'Gwatemala');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GN', 'Gwinea');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GQ', 'Gwinea Równikowa');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GW', 'Gwinea-Bissau');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('HT', 'Haiti');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('ES', 'Hiszpania');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('HN', 'Honduras');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('HK', 'Hongkong');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('IN', 'Indie');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('ID', 'Indonezja');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('IQ', 'Irak');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('IR', 'Iran');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('IE', 'Irlandia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('IS', 'Islandia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('IL', 'Izrael');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('JM', 'Jamajka');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('JP', 'Japonia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('YE', 'Jemen');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('JO', 'Jordania');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('KY', 'Kajmany');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('KH', 'Kambodża');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CM', 'Kamerun');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CA', 'Kanada');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('QA', 'Katar');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('KZ', 'Kazachstan');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('KE', 'Kenia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('KG', 'Kirgistan');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('KI', 'Kiribati');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CO', 'Kolumbia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('KM', 'Komory');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CG', 'Kongo');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CD', 'Kongo, Republika Demokratyczna');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('KP', 'Koreańska Republika Ludowo-Demokratyczna');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('XK', 'Kosowo');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CR', 'Kostaryka');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CU', 'Kuba');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('KW', 'Kuwejt');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('LA', 'Laos');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('LS', 'Lesotho');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('LB', 'Liban');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('LR', 'Liberia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('LY', 'Libia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('LI', 'Liechtenstein');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('LT', 'Litwa');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('LU', 'Luksemburg');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('LV', 'Łotwa');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MK', 'Macedonia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MG', 'Madagaskar');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('YT', 'Majotta');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MO', 'Makau');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MW', 'Malawi');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MV', 'Malediwy');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MY', 'Malezja');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('ML', 'Mali');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MT', 'Malta');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MP', 'Mariany Północne');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MA', 'Maroko');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MR', 'Mauretania');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MU', 'Mauritius');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MX', 'Meksyk');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('XL', 'Melilla');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('FM', 'Mikronezja');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('UM', 'Minor (Powiernicze Wyspy Pacyfiku Stanów Zjednoczonych)');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MD', 'Mołdowa');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MN', 'Mongolia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MS', 'Montserrat');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MZ', 'Mozambik');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MM', 'Myanmar (Burma)');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('NA', 'Namibia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('NR', 'Nauru');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('NP', 'Nepal');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('NL', 'Niderlandy');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('DE', 'Niemcy');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('NE', 'Niger');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('NG', 'Nigeria');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('NI', 'Nikaragua');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('NU', 'Niue');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('NF', 'Norfolk');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('NO', 'Norwegia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('NC', 'Nowa Kaledonia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('NZ', 'Nowa Zelandia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('PS', 'Okupowane Terytorium Palestyny');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('OM', 'Oman');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('PK', 'Pakistan');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('PW', 'Palau');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('PA', 'Panama');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('PG', 'Papua Nowa Gwinea');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('PY', 'Paragwaj');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('PE', 'Peru');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('PN', 'Pitcairn');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('PF', 'Polinezja Francuska');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('PL', 'Polska');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GS', 'Południowa Georgia i Południowe Wyspy Sandwich');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('PT', 'Portugalia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CZ', 'Republika Czeska');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('KR', 'Republika Korei');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('ZA', 'Rep.Połud.Afryki');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CF', 'Rep.Środkowoafryańska');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('RU', 'Rosja');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('RW', 'Rwanda');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('RO', 'Rumunia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SV', 'Salwador');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('WS', 'Samoa');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AS', 'Samoa Amerykańskie');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SM', 'San Marino');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SN', 'Senegal');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('XS', 'Serbia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SC', 'Seszele');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SL', 'Sierra Leone');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SG', 'Singapur');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SZ', 'Suazi');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SK', 'Słowacja');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SI', 'Słowenia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SO', 'Somalia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('LK', 'Sri Lanka');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('PM', 'St. Pierre i Miquelon');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('KN', 'St.Kitts i Nevis');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('LC', 'St.Lucia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('VC', 'St.Vincent i Grenadyny');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('US', 'Stany Zjedn. Ameryki');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SD', 'Sudan');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SR', 'Surinam');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SY', 'Syria');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CH', 'Szwajcaria');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SE', 'Szwecja');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SH', 'Święta Helena');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TJ', 'Tadżykistan');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TH', 'Tajlandia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TW', 'Tajwan');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TZ', 'Tanzania');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TG', 'Togo');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TK', 'Tokelau');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TO', 'Tonga');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TT', 'Trynidad i Tobago');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TN', 'Tunezja');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TR', 'Turcja');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TM', 'Turkmenistan');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TC', 'Wyspy Turks i Caicos');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TV', 'Tuvalu');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('UG', 'Uganda');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('UA', 'Ukraina');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('UY', 'Urugwaj');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('UZ', 'Uzbekistan');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('VU', 'Vanuatu');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('WF', 'Wallis i Futuna');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('VA', 'Watykan');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('VE', 'Wenezuela');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('HU', 'Węgry');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('GB', 'Wielka Brytania');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('VN', 'Wietnam');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('IT', 'Włochy');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('TL', 'Wschodni Tirom');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CI', 'Wyb.Kości Słoniowej');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('BV', 'Wyspa Bouveta');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CX', 'Wyspa Bożego Narodzenia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CK', 'Wyspy Cooka');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('VI', 'Wyspy Dziewicze-USA');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('VG', 'Wyspy Dziewicze-W.B');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('HM', 'Wyspy Heard i McDonald');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CC', 'Wyspy Kokosowe (Keelinga)');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('FO', 'Wyspy Owcze');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('MH', 'Wyspy Marshalla');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('SB', 'Wyspy Salomona');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('ST', 'Wyspy Św.Tomasza i Książęca');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('ZM', 'Zambia');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('CV', 'Zielony Przylądek');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('ZW', 'Zimbabwe');
INSERT INTO COUNTRY(COUNTRY_CODE_ID, COUNTRY_NAME) VALUES('AE', 'Zjedn.Emiraty Arabskie');

COMMIT;