--azuiz
create table Gravadora(
	Cod_gravadora int not null,
	Nome varchar(80) not null,
	Site varchar(111),
	constraint PK_Cod_gravadora primary key(Cod_gravadora)
) ON SpotPer_Secundario

create table Telefones(
	Cod_gravadora int not null,
	Numero int not null,
	Tipo varchar(80) not null,
	constraint PK_numero primary key(Numero),
	constraint FK_Cod_gravadora foreign key (Cod_gravadora) references Gravadora
) ON SpotPer_Secundario

create table Composicao(
	Descricao varchar(111) not null,
	Id_composicao int not null,
	constraint PK_Id_composicao primary key(Id_composicao)
) ON SpotPer_Secundario

create table Playlist(
	Id_playlist int not null,
	Nome varchar(80) not null,
	DT_criacao date not null,
	Tempo_execucao int not null,
	constraint PK_Id_playlist primary key(Id_playlist)
) ON SpotPer_Terciario

create table Interprete(
	Cod_interprete int not null,
	TP_interprete varchar(80),
	Nome varchar(80) not null,
	constraint PK_Cod_interprete primary key(Cod_interprete)
) ON SpotPer_Secundario

create table Periodo(
	Cod_periodo int not null,
	Intervalo_tempo int not null,
	Descricao varchar(111) not null,
	constraint PK_Cod_periodo primary key(Cod_periodo)
) ON SpotPer_Secundario

create table Compositor(
	Nome varchar(80) not null,
	Id_compositor int not null,
	DT_morte date,
	DT_nascimento date not null,
	constraint PK_Id_compositor primary key(Id_compositor)
) ON SpotPer_Secundario
--vermelhos
create table Album(
	Cod_album int not null,
	Descricao varchar(400) not null,
	Cod_gravadora int not null,
	Preco decimal(10,2) not null,
	DT_compra date not null,
	TP_compra varchar(8) not null, -- fisico ou download
	constraint PK_Cod_album primary key(cod_album),
	constraint FK_Cod_gravadoraNoAlbum foreign key(Cod_gravadora) references Gravadora
	on delete no action 
	on update cascade
) ON SpotPer_Secundario

create table CompositorPeriodo(
	Cod_periodo int not null,
	Cod_compositor int not null,
	constraint FK_Cod_periodo foreign key(Cod_periodo) references Periodo
	on delete no action
	on update cascade,
	constraint FK_Cod_compositor foreign key(Cod_compositor) references Compositor
	on delete cascade
	on update cascade
) ON SpotPer_Secundario
-- roxos
create table Faixa(
	Numero int not null,
	Descricao varchar(111) not null,
	TP_composicao varchar(3) not null,
	Cod_composicao int not null,
	Tempo int not null, --de execução
	TP_gravacao varchar(3) not null, --add | ddd
	constraint PK_NumeroFaixa primary key (Numero),
	constraint FK_Cod_composicao foreign key(Cod_composicao) references Composicao
	on delete no action
	on update cascade,
) ON SpotPer_Terciario

create table AlbumGravadora( --auxiliar de album e gravadora
	--sem chave primaria
	DT_gravacao date not null,
	Cod_album int not null,
	Cod_gravadora int not null,
	constraint FK_Cod_albumNoAlbumGravadora foreign key(Cod_album) references Album
	on delete no action 
	on update no action, 
	constraint FK_Cod_gravadoraNoAlbumGravadora foreign key(Cod_gravadora) references Gravadora
	on delete no action 
	on update no action 
) ON SpotPer_Secundario

create table AlbumFaixa(
	Cod_album int not null,
	Cod_faixa int not null,
	constraint PK_Cod_albumFaixa primary key Nonclustered(Cod_album, Cod_faixa),
	constraint FK_Cod_albumNaAlbumFaixa foreign key(Cod_album) references Album
	on delete cascade 
	on update no action,
	constraint Fk_Cod_faixaNaAlbumFaixa foreign key(Cod_faixa) references Faixa
	on delete no action
	on update no action
) ON SpotPer_Terciario


create table FaixaInterprete( 
	--sem chave priamria
	Cod_album int not null,
	Cod_faixa int not null,
	Cod_interprete int not null,
	constraint FK_Cod_albumFaixa foreign key(Cod_album, Cod_faixa) references AlbumFaixa
	on delete cascade
	on update cascade,
	constraint FK_Cod_interprete foreign key(Cod_interprete) references Interprete
	on delete cascade
	on update cascade
) ON SpotPer_Secundario

create table AlbumFaixaPlaylist( 
	--sem chave primaria
	Frequencia int,
	DT_ultima_tocagem date,
	Cod_playlist int not null,
	Cod_album int not null,
	Cod_faixa int not null,
	constraint FK_Cod_albumFaixaNaAlbumFaixaPlaylist foreign key(Cod_album, Cod_faixa) references AlbumFaixa
	on delete cascade
	on update cascade,
	constraint FK_Cod_playlistNaAlbumFaixaPlaylist foreign key(Cod_playlist) references Playlist
	on delete cascade
	on update cascade
) ON SpotPer_Terciario

create table FaixaCompositor( 
	--sem chave primaria
	Cod_album int not null,
	Cod_faixa int not null,
	Cod_compositor int not null,
	constraint Fk_Cod_AlbumFaixaNaFaixaCompositor foreign key(Cod_album, Cod_faixa) references AlbumFaixa
	on delete cascade
	on update cascade,
	constraint FK_Cod_compositorNaFaixaCompositor foreign key(Cod_compositor) references Compositor
	on delete cascade
	on update cascade
) ON SpotPer_Secundario