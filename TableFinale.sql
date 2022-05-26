


-- les tables
 DROP TABLE PeutLireCodecAudio;
 DROP TABLE PeutLireCodectexte;
 DROP TABLE PeutLireCodecvideo;
 DROP TABLE FluxTexte;
 DROP TABLE FluxVideo;
 DROP TABLE FluxAudio;
 DROP TABLE flux;
 DROP TABLE CodecVideo;
 DROP TABLE CodecTexte;
 DROP TABLE CodecAudio;
 DROP TABLE ArtistePisteInstrument;
 DROP TABLE Instrument;
 DROP TABLE ArtisteDansFilm;
 DROP TABLE BiographieArtiste;
 DROP TABLE DateNaissanceArtiste;
 DROP TABLE Artiste;
 DROP TABLE fichier;
 DROP TABLE Client;
 DROP TABLE filmAPourCategorie;
 DROP TABLE CategorieFilm;
 DROP TABLE PisteApourCategorie;
 DROP TABLE Piste ;
 DROP TABLE AlbumApourCategorie;
 DROP TABLE CategorieMusic;
 DROP TABLE Album;
 DROP TABLE FilmUrlPhoto;
 DROP TABLE Film;
 DROP TABLE Utilisateur;
 DROP TABLE Langue;



CREATE TABLE Langue(
    NomLangue VARCHAR(200) PRIMARY KEY
);


CREATE TABLE Utilisateur(
    Mail  VARCHAR(120) PRIMARY KEY,
    Nom VARCHAR(25),
    Prenom VARCHAR(25),
    AGE INT NOT NULL,
    CodeAcces VARCHAR(4), -- Aa A quatre chiffres
    LangueUtilisateur VARCHAR(200),
    CONSTRAINT fk_User_Language
       FOREIGN KEY (LangueUtilisateur)
       REFERENCES Langue (NomLangue)
);




CREATE TABLE Film(
  Titre VARCHAR(50) NOT NULL,
  AnneeSortie NUMBER(4) NOT NULL,
  Resume VARCHAR(255),
  MinAge INT,
  URLFilm VARCHAR(200),
  PRIMARY KEY (Titre, AnneeSortie)
);

CREATE TABLE FilmUrlPhoto(
  Titre VARCHAR(50) NOT NULL,
  AnneeSortie NUMBER(4) NOT NULL,
  UrlPhoto Varchar(255) NOT NULL,
  Primary Key(Titre, AnneeSortie,UrlPhoto),
  CONSTRAINT fk_film_existe_Photo
  foreign key(titre, anneeSortie)
  REFERENCES Film(titre, anneeSortie)
  ON DELETE cascade
);
  
CREATE TABLE album (
  NumAlbum       number(10) NOT NULL,
  TitreAlbum         varchar2(255) NOT NULL,
  groupeArtiste varchar2(255) NOT NULL,
  dateSortie    varchar(20) NOT NULL,
  urlPochette   varchar2(255),
  PRIMARY KEY (NumAlbum)
);


CREATE TABLE CategorieMusic(
Categorie VARCHAR(30) PRIMARY KEY
);

CREATE TABLE AlbumApourCategorie(
  numalbum INT NOT NULL,
  categorie varchar(20) NOT NULL,
  PRIMARY KEY(NumAlbum, categorie),
  CONSTRAINT fk_album_existe
      FOREIGN KEY(Numalbum)
      REFERENCES Album(Numalbum),
  Constraint fk_categorie_existe
   foreign key(categorie)
    references CategorieMusic(categorie)
);

CREATE TABLE Piste (
  NumPiste number(10) NOT NULL,
  NumAlbum number(10) NOT NULL,
  Titre   varchar2(255) NOT NULL,
  Duree   number(19) NOT NULL,
  PRIMARY KEY (NumPiste, NumAlbum),
  CONSTRAINT fk_Album_Exist
     FOREIGN KEY (NumAlbum)
     REFERENCES Album (NumAlbum)
     ON DELETE cascade
);

Create TABLE PisteApourCategorie(
 numPiste int not null,
 numAlbum int not null,
 categorie varchar(20) not null,
 PRIMARY KEY(numPiste, NumAlbum, categorie),
 Constraint fk_piste_existe_categorie
   Foreign KEY(numAlbum, numPiste)
   References Piste(NumAlbum, NumPiste),
 Constraint fk_categorie_existe_music
   Foreign KEY(categorie)
   REFERENCES CategorieMusic(categorie)
);

CREATE TABLE CategorieFilm(
  Categorie VARCHAR(30) PRIMARY KEY
);

Create TABLE filmAPourCategorie(
titreFilm varchar(20) not null,
anneeSortie int not null,
categorie varchar(30) not null,
Primary key(titrefilm,anneesortie, categorie),
CONSTRAINT fk_film_existe_categorie
foreign key(titreFilm, anneeSortie)
REFERENCES Film(titre, anneeSortie)
ON DELETE cascade,
Constraint fk_categorie_existe_categorie
FOREIGN KEY(categorie)
REFERENCES CategorieFilm(categorie)
);


CREATE TABLE Client(
  Marque VARCHAR(50) NOT NULL,
  Modele VARCHAR(50) NOT NULL,
  ResLargeurMax INT,
  ResHauteurMax INT,
  PRIMARY KEY (Marque, Modele)
);


CREATE TABLE fichier (
  IdFichier       number(10) NOT NULL,
  Taille          number(19) NOT NULL,
  DateDepot       date NOT NULL,
  MailUser varchar2(255) NOT NULL,
  TitreFilm       varchar2(255) ,
  AnneeSortie NUMBER(4) ,
  idPiste         number(10) ,
  idAlbum         number(10) ,
  PRIMARY KEY (idFichier),
  CONSTRAINT fk_User_Upload
       FOREIGN KEY (MailUser)
       REFERENCES Utilisateur (Mail)
       ON DELETE cascade,
  CONSTRAINT fk_film_existe_fichier
       FOREIGN KEY (TitreFilm, AnneeSortie)
       REFERENCES Film (Titre, AnneeSortie)
       ON DELETE cascade,
  CONSTRAINT fk_piste_existe_fichier
       FOREIGN KEY (idPiste, idAlbum)
       REFERENCES Piste(NumPiste, NumAlbum)
       ON DELETE cascade
);


CREATE TABLE Artiste (
  NumArtiste     number(10) NOT NULL,
  NomArtiste           varchar2(255) NOT NULL,
  URLPhotoArtiste      varchar2(255),
  SpecialitePrincipale    varchar2(255) NOT NULL,
  PRIMARY KEY (NumArtiste));





CREATE TABLE DateNaissanceArtiste(
DateArtiste DATE NOT NULL,
IdArtiste INT NOT NULL ,
PRIMARY KEY (IdArtiste),
CONSTRAINT fk_Artiste_Exist
   FOREIGN KEY (IdArtiste)
   REFERENCES Artiste(NumArtiste)
   ON DELETE cascade);


CREATE TABLE BiographieArtiste(
Biographie VARCHAR(255) NOT NULL,
IdArtiste INT NOT NULL,
PRIMARY KEY(IdArtiste),
CONSTRAINT fk_Artiste_Exist_Bio
   FOREIGN KEY(IdArtiste)
   REFERENCES Artiste (NumArtiste)
   ON DELETE cascade
);

CREATE TABLE ArtisteDansFilm(
   NumArtiste INT NOT NULL,
   AnneeSortie number(4) NOT NULL,
   Titre varchar(20) NOT NULL,
   Role varchar(20) NOT NULL,
   PRIMARY KEY(NumArtiste, AnneeSortie, Titre),
   CONSTRAINT fk_artiste_existe
      FOREIGN KEY(NumArtiste)
      REFERENCES Artiste(NumArtiste),
   CONSTRAINT fk_Filme_existe
        FOREIGN KEY(AnneeSortie, Titre)
        REFERENCES Film(AnneeSortie , Titre)
);

CREATE TABLE Instrument(
    Instrument VARCHAR(20) PRIMARY KEY
);


CREATE TABLE ArtistePisteInstrument(
    NumArtiste INT NOT NULL,
    NumAlbum NUMBER(10) NOT NULL,
    NumPiste NUMBER(10) NOT NULL,
    Instrument VARCHAR(20) NOT NULL,
    PRIMARY KEY (NumArtiste, NumAlbum, NumPiste, Instrument),
    CONSTRAINT fk_artiste_existe_instrument
        FOREIGN KEY (NumArtiste)
        REFERENCES Artiste(NumArtiste)
        ON DELETE cascade,
    CONSTRAINT fk_piste
        FOREIGN KEY (NumAlbum, NumPiste)
        REFERENCES Piste(NumAlbum, NumPiste)
        ON DELETE cascade,
    CONSTRAINT fk_instrument
        FOREIGN KEY (Instrument)
        REFERENCES Instrument(Instrument)
        ON DELETE cascade
);
-- ****************************************
-- *            Table Codec               *
-- ****************************************


-- -----------------------------------------------------
-- Table CodecAudio
-- -----------------------------------------------------

CREATE TABLE CodecAudio(
    Codec VARCHAR(10) PRIMARY KEY
);

-- -----------------------------------------------------
-- Table CodecTexte
-- -----------------------------------------------------

CREATE TABLE CodecTexte(
    Codec VARCHAR(10) PRIMARY KEY
);

-- -----------------------------------------------------
-- Table CodecVideo
-- -----------------------------------------------------

CREATE TABLE CodecVideo(
    Codec VARCHAR(10) PRIMARY KEY
);

CREATE TABLE flux (
  idFlux    number(10) NOT NULL,
  debit     number(19) NOT NULL,
  idFichier number(10) NOT NULL,
  PRIMARY KEY (idFlux),
  CONSTRAINT fk_fichier_Existe_Flux
	FOREIGN KEY (IdFichier)
	REFERENCEs Fichier(IdFichier)
	ON DELETE cascade
);

CREATE TABLE FluxAudio(
    IdFlux NUMBER PRIMARY KEY,
    Echantillonage INT,
    Langue VARCHAR(20),
    Codec VARCHAR(10),
    CONSTRAINT fk_Flux_Audio
        FOREIGN KEY (IdFlux)
        REFERENCES Flux (IdFlux)
        ON DELETE cascade,
    CONSTRAINT fk_LangueAudio
        FOREIGN KEY (Langue)
        REFERENCES Langue (NomLangue),
    CONSTRAINT fk_Codec_Audio
        FOREIGN KEY (Codec)
        REFERENCES CodecAudio (Codec)
);
	
CREATE TABLE FluxVideo(
    IdFlux NUMBER PRIMARY KEY,
    Codec VARCHAR(10),
    Largeur INT NOT NULL,
    Hauteur INT NOT NULL,
    CONSTRAINT fk_Flux_Video
        FOREIGN KEY (IdFlux)
        REFERENCES Flux (IdFlux)
        ON DELETE cascade,
    CONSTRAINT fk_Codec_Video_flux
        FOREIGN KEY (Codec)
        REFERENCES CodecVideo (Codec)
);

CREATE TABLE FluxTexte(
    IdFlux INT PRIMARY KEY,
    Langue VARCHAR(20),
    Codec VARCHAR(10),
    CONSTRAINT fk_Flux_Existe
        FOREIGN KEY (IdFlux)
        REFERENCES Flux (IdFlux)
        ON DELETE cascade,
    CONSTRAINT fk__Flux_Langue_Text
        FOREIGN KEY (Langue)
        REFERENCES Langue (NomLangue),
    CONSTRAINT fk_Codec_Text
        FOREIGN KEY (Codec)
        REFERENCES CodecTexte (Codec)
);

CREATE TABLE PeutLireCodecVideo(
   Marque VARCHAR(50) NOT NULL,
   Modele VARCHAR(50) NOT NULL,
   Codec  VARCHAR(10) NOT NULL,
   CONSTRAINT fk_client_existe_video
        FOREIGN KEY (Marque, Modele)
        REFERENCES Client(Marque, Modele)
        ON DELETE cascade
);

CREATE TABLE PeutLireCodecTexte(
   Marque VARCHAR(50) NOT NULL,
   Modele VARCHAR(50) NOT NULL,
   Codec  VARCHAR(10) NOT NULL,
   CONSTRAINT fk_client_existe_text
        FOREIGN KEY (Marque, Modele)
        REFERENCES Client(Marque, Modele)
        ON DELETE cascade
);

CREATE TABLE PeutLireCodecAudio(
   Marque VARCHAR(50) NOT NULL,
   Modele VARCHAR(50) NOT NULL,
   Codec  VARCHAR(10) NOT NULL,
   CONSTRAINT fk_client_existe_audio
        FOREIGN KEY (Marque, Modele)
        REFERENCES Client(Marque, Modele)
        ON DELETE cascade
);


CREATE SEQUENCE NumArtiste
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;

CREATE SEQUENCE IdFichier
  START WITH     1
  INCREMENT BY   1
  NOCACHE
  NOCYCLE;

CREATE SEQUENCE IdFlux
  START WITH     1
  INCREMENT BY   1
  NOCACHE
  NOCYCLE;
