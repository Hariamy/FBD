-- tabelas com chaves estrangeiras

--azuiz
create table Gravadora( --ok
	Cod_gravadora int not null,
	Nome varchar(80) not null,
	Site varchar(111),
	constraint PK_Cod_gravadora primary key(Cod_gravadora)
) ON SpotPer_Secundario

create table Telefones( --ok
	Cod_gravadora int not null,
	Numero int not null,
	Tipo varchar(80) not null,
	constraint PK_numero primary key(Numero),
	constraint FK_Cod_gravadora foreign key (Cod_gravadora) references Gravadora --add
	--on update cascade
	--on delete cascade
	
) ON SpotPer_Secundario

create table Composicao( --ok
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
	constraint FK_Cod_gravadoraNoAlbum foreign key(Cod_gravadora) references Gravadora --add
	on delete no action --não há sentido remover a gravadora sem antes retirar todos os albuns
	on update cascade
) ON SpotPer_Secundario

create table CompositorPeriodo(
	--/NOME:CompitorPeríodo
	Cod_periodo int not null,
	Cod_compositor int not null,
	constraint FK_Cod_periodo foreign key(Cod_periodo) references Periodo --add
	on delete no action
	on update cascade,
	constraint FK_Cod_compositor foreign key(Cod_compositor) references Compositor --add
	on delete cascade
	on update cascade
) ON SpotPer_Secundario

-- roxos
create table Faixa(
	Numero int not null,
	Descricao varchar(111) not null,
	TP_composicao varchar(3) not null,
	--Cod_compositor int not null,
	Cod_composicao int not null,
	Cod_album int not null,
	Tempo int not null, --de execução
	TP_gravacao varchar(3) not null, --add | ddd
	constraint PK_NumeroFaixa primary key nonclustered(Numero),
	constraint FK_Cod_album foreign key(Cod_album) references Album --add
	on delete no action
	on update cascade,
	constraint FK_Cod_composicao foreign key(Cod_composicao) references Composicao --add
	on delete no action
	on update cascade,
	/*constraint FK_Cod_compositor foreign key(Cod_compositor) references Compositor --add
	on delete no action
	on update cascade*/
) ON SpotPer_Terciario

create table AlbumGravadora( --auxiliar de album e gravadora
	--/NOME:GRAValbum
	--sem chave primaria
	DT_gravacao date not null,
	Cod_album int not null,
	Cod_gravadora int not null,
	constraint FK_Cod_albumNoAlbumGravadora foreign key(Cod_album) references Album --add
	on delete no action --o album só é apagado na fonte em cascade
	on update no action, --prestou assim
	constraint FK_Cod_gravadoraNoAlbumGravadora foreign key(Cod_gravadora) references Gravadora --add
	on delete no action --a gravadora só é apagada na fonte em cascade
	on update no action --prestou assim
) ON SpotPer_Secundario
/*
Mensagem 1785, Nível 16, Estado 0, Linha 109
A introdução da restrição FOREIGN KEY 'FK_Cod_gravadoraNoAlbumGravadora' na tabela 'AlbumGravadora' pode causar ciclos
ou vários caminhos em cascata. Especifique ON DELETE NO ACTION ou ON UPDATE NO ACTION, ou modifique outras restrições FOREIGN KEY.
Mensagem 1750, Nível 16, Estado 1, Linha 109
Não foi possível criar a restrição ou o índice. Consulte os erros anteriores.

*/

create table AlbumFaixa(
	-- sem chave primaria
	Cod_album int not null,
	Cod_faixa int not null,
	constraint PK_Cod_albumFaixa primary key(Cod_album, Cod_faixa), --certo e pesquisa usando os dois
	constraint FK_Cod_albumNaAlbumFaixa foreign key(Cod_album) references Album --add
	on delete no action --deveria apagar todas as referecnias do album nesta tabela
	on update no action,
	constraint Fk_Cod_faixaNaAlbumFaixa foreign key(Cod_faixa) references Faixa --add
	on delete no action
	on update no action
) ON SpotPer_Terciario


create table FaixaInterprete( --auxiliar de interprete e faixa --de oolho aqui
	--/NOME:Interpretar
	--sem chave priamria
	Cod_album int not null,
	Cod_faixa int not null,
	Cod_interprete int not null,
	constraint FK_Cod_albumFaixa foreign key(Cod_album, Cod_faixa) references AlbumFaixa --add
	on delete cascade
	on update cascade,
	constraint FK_Cod_interprete foreign key(Cod_interprete) references Interprete --add
	on delete no action
	on update cascade
) ON SpotPer_Secundario

create table AlbumFaixaPlaylist( --auxiliar playlist e AlbumFaixa
	--/NOME:Pertencer entre playlist e faixa
	--sem chave primaria
	Frequencia int,
	DT_ultima_tocagem date,
	--Tempo_execucao int not null,
	Cod_playlist int not null,
	Cod_album int not null,
	Cod_faixa int not null,
	constraint FK_Cod_albumFaixaNaAlbumFaixaPlaylist foreign key(Cod_album, Cod_faixa) references AlbumFaixa --add
	on delete cascade
	on update cascade,
	constraint FK_Cod_playlistNaAlbumFaixaPlaylist foreign key(Cod_playlist) references Playlist --on falta
) ON SpotPer_Terciario

create table FaixaCompositor( --auxiliar de compositor e faixa
	--/NOME:FaixaCompositor
	--sem chave primaria
	Cod_album int not null,
	Cod_faixa int not null,
	Cod_compositor int not null,
	constraint Fk_Cod_AlbumFaixaNaFaixaCompositor foreign key(Cod_album, Cod_faixa) references AlbumFaixa --add
	on delete cascade
	on update cascade,
	constraint FK_Cod_compositorNaFaixaCompositor foreign key(Cod_compositor) references Compositor --add
	on delete no action
	on update cascade
) ON SpotPer_Secundario

create trigger TR_Faixa_Tipo on Faixa --restrição do tipo da faixa
for insert,update
as
	if(	(select TP_composicao
		from inserted) != 'ADD' or
		(select TP_composicao
		from inserted) != 'DDD')
	begin
		raiserror('tipo de composição inválido',9,1)
		rollback transaction
	end

create trigger TR_Interprete_Tipo on Interprete --restrição de tipo de intérprete
for insert, update
as
		declare @TipoInterprete varchar(80);
		select @TipoInterprete;
		set @TipoInterprete = (	select TP_interprete
								from inserted);
	if(	@TipoInterprete != 'Orquestral' and
		@TipoInterprete != 'Trio' and
		@TipoInterprete != 'Quarteto' and
		@TipoInterprete != 'Ensemble' and
		@TipoInterprete != 'Soprano' and
		@TipoInterprete != 'Tenor'
		)
	begin
		raiserror('Tipo de intérprete inválido',10,1)
		rollback transaction
	end

create trigger TR_Preco_Album on Album --Restrição dos custos de albuns
for insert, update
as 
	declare @PrecoAlbum decimal(10,2);
	select @PrecoAlbum;
	set @PrecoAlbum = (	select Preco
						from inserted);
	declare @PrecoMedio decimal(10,2);
	select @PrecoMedio;
	set @PrecoMedio =(	((select sum(a.Preco)
						from Album a, inserted i, Faixa f
						where (a.Cod_album != i.Cod_album) and
							  (a.Cod_album = f.Cod_album)  and
							  (f.TP_gravacao = 'DDD'))      +
						      (select Preco from inserted)) /
						(select distinct count(a.preco)
						from Album a, Faixa f 
						where (a.Cod_album = f.Cod_album)  and
							  (f.TP_gravacao = 'DDD'))
					)
	if(	('DDD' like (select f.TP_gravacao --testar
						from inserted i, Faixa f
						where i.Cod_album = f.Cod_album) )	and
		((select @PrecoAlbum) > 3*(select @PrecoMedio)) --testar
		)
	begin
		raiserror('Preco de album maior que permitido',11,1)
		rollback transaction
	end
--deu certo até aqui
