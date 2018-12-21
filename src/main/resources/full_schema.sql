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
	COUNTRY_ID NUMBER,
	COUNTRY_NAME VARCHAR2(100),
	CONSTRAINT COUNTRY_PK PRIMARY KEY(COUNTRY_ID)
);

CREATE TABLE FILM_COUNTRY(
	FILM_ID NUMBER,
	COUNTRY_ID NUMBER,
	CONSTRAINT FILM_COUNTRY_PK PRIMARY KEY(FILM_ID, COUNTRY_ID),
    CONSTRAINT FILM_COUNTR_F_FK
		FOREIGN KEY (FILM_ID)
		REFERENCES FILM(ID),
	CONSTRAINT FILM_COUNTRY_C_FK
		FOREIGN KEY (COUNTRY_ID)
		REFERENCES COUNTRY(COUNTRY_ID)
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

CREATE SEQUENCE country_seq
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  NOCACHE;


CREATE OR REPLACE TRIGGER country_auto_increment
  BEFORE INSERT ON country
  FOR EACH ROW
BEGIN
  SELECT country_seq.nextval
  INTO :new.id
  FROM dual;
END;

/

CREATE OR REPLACE TRIGGER insert_average_rating
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

CREATE OR REPLACE TRIGGER update_average_rating
BEFORE UPDATE
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

   V_RATINGS_SUM := V_RATINGS_SUM + :new.RATING - :old.RATING;
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



INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Australia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Filipiny');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Francja');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Grecja');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Hiszpania');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Hongkong');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Indie');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Japonia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Kanada');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Meksyk');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Niemcy');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Polska');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('RFN');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('USA');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Wielka Brytania');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Włochy');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('ZSRR');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Afganistan');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Albania');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Algieria');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Andora');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Angola');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Antigua i Barbuda');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Arabia Saudyjska');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Argentyna');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Armenia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Aruba');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Austria');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Autonomia Palestyńska');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Azerbejdżan');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Bahamy');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Bahrajn');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Bangladesz');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Barbados');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Belgia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Belize');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Benin');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Bhutan');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Białoruś');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Birma');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Boliwia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Bośnia i Hercegowina');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Botswana');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Brazylia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Brunei');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Bułgaria');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Burkina Faso');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Burundi');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Chile');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Chiny');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Chorwacja');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Cypr');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Czad');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Czarnogóra');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Czechosłowacja');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Czechy');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Dania');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Demokratyczna Republika Konga');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Dominika');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Dominikana');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Dżibuti');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Egipt');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Ekwador');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Erytrea');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Estonia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Etiopia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Fed. Rep. Jugosławii');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Fidżi');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Finlandia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Gabon');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Gambia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Ghana');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Grenada');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Grenlandia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Gruzja');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Gujana');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Gwadelupa');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Gwatemala');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Gwinea');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Gwinea Bissau');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Gwinea Równikowa');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Haiti');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Holandia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Honduras');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Indonezja');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Irak');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Iran');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Irlandia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Islandia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Izrael');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Jamajka');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Jemen');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Jordania');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Jugosławia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Kambodża');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Kamerun');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Katar');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Kazachstan');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Kenia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Kirgistan');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Kiribati');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Kolumbia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Komory');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Kongo');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Korea');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Korea Południowa');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Korea Północna');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Kosowo');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Kostaryka');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Kuba');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Kuwejt');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Laos');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Lesotho');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Liban');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Liberia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Libia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Liechtenstein');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Litwa');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Luksemburg');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Łotwa');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Macedonia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Madagaskar');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Makau');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Malawi');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Malediwy');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Malezja');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Mali');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Malta');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Maroko');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Martynika');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Mauretania');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Mauritius');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Mikronezja');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Mołdawia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Monako');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Mongolia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Mozambik');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Namibia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Nepal');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Niger');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Nigeria');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Nikaragua');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Niue');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Norwegia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Nowa Zelandia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('NRD');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Oman');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Pakistan');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Palau');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Palestyna');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Panama');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Papua Nowa Gwinea');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Paragwaj');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Peru');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Portoryko');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Portugalia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Republika Środkowoafrykańska');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Republika Zielonego Przylądka');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Rosja');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('RPA');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Ruanda');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Rumunia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Sahara Zachodnia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Saint Lucia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Saint Vincent i Grenadyny');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Salwador');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Samoa');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('San Marino');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Senegal');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Serbia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Serbia i Czarnogóra');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Seszele');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Sierra Leone');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Singapur');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Słowacja');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Słowenia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Somalia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Sri Lanka');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Suazi');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Sudan');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Surinam');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Syjam');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Syria');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Szwajcaria');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Szwecja');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Tadżykistan');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Tajlandia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Tajwan');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Tanzania');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Togo');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Tonga');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Trynidad i Tobago');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Tunezja');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Turcja');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Turkmenistan');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Tuvalu');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Uganda');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Ukraina');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Urugwaj');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Uzbekistan');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Vanuatu');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Watykan');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Wenezuela');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Węgry');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Wietnam');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Wietnam Północny');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Wybrzeże Kości Słoniowej');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Wyspy Marshalla');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Wyspy Owcze');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Zair');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Zambia');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Zimbabwe');
INSERT INTO COUNTRY(COUNTRY_NAME) VALUES('Zjednoczone Emiraty Arabskie');

COMMIT;