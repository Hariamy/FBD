-- tabelas sem chaves estrangeiras

--azuiz
create table Gravadora(
	Cod_gravadora int not null,
	Nome varchar(80) not null,
	Telefone int not null,
	Site varchar(111),
	constraint PK_Cod_gravadora primary key(Cod_gravadora)
)

create table Telefones(
	--cod gravadora
	Numero int not null,
	Tipo varchar(80) not null,
	constraint PK_numero primary key(Numero)
)

create table Composicao(
	Descricao varchar(111) not null,
	Id_composicao int not null,
	constraint PK_Id_composicao primary key(Id_composicao)
)

create table Playlist(
	Id_playlist int not null,
	Nome varchar(80) not null,
	DT_criacao date not null,
	constraint PK_Id_playlist primary key(Id_playlist)
)

create table Interprete(
	Cod_interprete int not null,
	TP_interprete ????
	Nome varchar(80) not null,
	constraint PK_Cod_interprete primary key(Cod_interprete)
)

create table Periodo(
	Cod_periodo int not null,
	Intervalo_tempo 
	Descricao varchar(111) not null
	constraint PK_Cod_periodo primary key(Cod_periodo)
)

create table Compositor(
	Nome varchar(80) not null,
	Id_compositor int not null,
	DT_morte date,
	DT_nascimento date not null,
	constraint PK_Id_compositor primary key(Id_compositor)
)

--vermelhos

create table Album(
	Cod_album int not null,
	Descricao varchar(400) not null,
	--cod gravadora
	Preco decimal(2,2) not null,
	DT_compra date not null,
	TP_compra varchar(8) not null, -- fisico ou download
	constraint PK_Cod_album primary key(cod_album)
)

create table CompositorPeriodo(
	--/NOME:CompitorPeríodo
	--cod periodo
	--cod compositor
)

-- roxos
create table Faixa(
	Numero int not null,
	Descricao varchar(111) not null,
	TP_composicao ????
	--compitor estrangeiro
	--cod composição estrangeiro
	--cod album estrangeiro
	Tempo int not null, --de execução
	TP_gravacao varchar(3) not null, --add | ddd
	constraint PK_Numero primary key(Numero)
)

create table Gravador( --auxiliar de album e gravadora
	--/NOME:GRAValbum
	DT_gravacao date not null,
	-- codigo album estrangeiro
	--codigo gravadora estrangeiro
)

create table AlbumFaixa(
	--cod album estrangeiro
	--cod faixa estrangeiro
)


create table FaixaInterprete( --auxiliar de interprete e faixa
	--/NOME:Interpretar
	--cod da AlbumFaixa
	--cod do interprete
)

create table FaixaPlaylist( --auxiliar playlist e AlbumFaixa
	--/NOME:Pertencer entre playlist e faixa
	--frqeucnia
	--dt de ultima tocagem
	--tempo executado

	--recebe chaves de AlbumFaixa
)

create table FaixaCompositor( --auxiliar de compositor e faixa
	--/NOME:FaixaCompositor
	-- cod faixa
	-- cod compsitor
)

