/*  1) Crie o banco de dados BDSpotPer, considerando o seguinte: o banco de dados
deve possuir três filegroups (tablespaces) e o arquivo de log. O filegroup primário
deve conter apenas o arquivo primário do banco de dados. Um segundo filegroup
deve conter dois arquivos e um terceiro deve conter apenas um arquivo.

2) As tabelas referentes aos conjuntos de playlists, faixas e de relacionamento entre
as duas devem ser alocadas no filegroup (tablespace), definido com apenas um
arquivo. As outras tabelas devem ser alocadas no filegroup com dois arquivos.*/

CREATE DATABASE SpotPer
ON
PRIMARY
(
	NAME = 'SpotPer',
	FILENAME = 'C:\FBD\FBD_01\SpotPer.mdf',
	SIZE = 5120KB,
	FILEGROWTH = 1024KB
),
FILEGROUP SpotPer_Secundario --dois arquivos
(
	NAME = 'SpotPer_Secundario_Arquivo_I',
	FILENAME = 'C:\FBD\FBD_02.1\SP_Sc02.ndf',
	SIZE = 1024KB,
	FILEGROWTH = 30%
),
(
	NAME ='SpotPer_Secundario_Arquivo_II',
	FILENAME = 'C:\FBD\FBD_02.2\SP_Sc02.ndf',
	SIZE = 1024KB,
	MAXSIZE = 3072KB,
	FILEGROWTH = 15%
),
FILEGROUP SpotPer_Terciario --um arquivo
(
	NAME = 'SpotPer_Terciario',
	FILENAME = 'C:\FBD\FBD_03\SP_Tc.ndf',
	SIZE = 2048KB,
	MAXSIZE = 5120KB,
	FILEGROWTH = 1024KB
)
LOG ON
(
	NAME = 'SpotPer_log',
	FILENAME = 'C:\FBD\FBD_LOG\SP_log.ldf',
	SIZE = 1024KB,
	FILEGROWTH = 10%
)