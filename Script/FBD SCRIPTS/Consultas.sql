create procedure TodasFaixas
as select * from AlbumFaixa

create procedure AlbumESuasFaixas( --todas as faixas de um album --inviável mostrar todos os ocmpositores e iterretes em grandes qauntidades
	@Cod_album int
)
as
	select	f.Numero, f.Descricao, f.TP_composicao, f.Cod_composicao,
			f.Tempo, f.TP_Gravacao, 
	from Faixas f
	where f.Cod_album = @Cod_album

create procedure CompositoresDaFaixa( --Compositores de uma faixa
	@Cod_album int,
	@cod_faixa int
)
as
	select c.Nome, c.DT_nascimento, c.DT_morte
	from Compositores c, FaixaCompositor FC--, AlbumFaixa AF
	where	c.Id_compositor = FC.Cod_compositor and
			FC.Cod_album = @Cod_album and
			FC.Cod_faixa = @Cod_faixa


create procedure InterpreteDeFaixa( --Interpretes de uma Faixa
	@Cod_album int,
	@Cod_faixa int
)
as
	select i.Nome
	from Interprete i, FaixaInterprete FI--, AlbumFaixa AF
	where	i.Cod_interprete = FI.Cod_interprete and
			FI.Cod_album = @Cod_album and
			FI.Cod_faixa = @Cod_faixa

create procedure AlbunsDaGravadora( --Albuns da gravadora
	@Cod_gravadora
)

as
	select AG.Cod_album
	from gravadora G, AlbumGravadora AG
	where	G.Cod_gravadora = @Cod_gravadora and
			G.Cod_gravadora = AG.Cod_gravadora

create procedure FaixasDePeriodo( -- faixas de um determinado periodo
	@Cod_periodo int
)
as
	select AF.Cod_faixa, AF.Cod_album
	from CompositorPeriodo CP, FaixaCompositor FC, AlbumFaixa AF
	where	CP.Cod_periodo = @Cod_periodo and
			CP.Cod_compositor = FC.Id_compositor and
			FC.Cod_faixa = AF.Cod_faixa
			group by (AF.Cod_album)

create procedure FaixasDeCompositor( --as faixas que um compositor já compos
	@Cod_compositor int
)
as
	select AF.Cod_faixa, AF.Cod_album
	from FaixaCompositor FC, AlbumFaixa AF
	where	FC.Cod_compositor = @Cod_compositor and
			FC.Cod_faixa = AF.Cod_faixa and
			FC.Cod_album = AF.Cod_album
			group by (AF.Cod_album)
 
create procedure FaixasDeInterprete( --as faixas que um interprete já cantou
	@Cod_interprete int
)
as
	select AF.Cod_faixa, AF.Cod_album
	from FaixaInterprete FI, AlbumFaixa AF
	where	FI.Cod_interprete = @Cod_interprete and
			FI.Cod_faixa = AF.Cod_faixa and
			FI.Cod_album = AF.Cod_album
			group by (AF.Cod_album)

create procedure TelefonesDaGravadora( --telefones de uma gravadora
	@Cod_gravadora int
)
as
	select Numero from Telefones where Cod_gravadora = @Cod_gravadora

create procedure TodasPlaylist --todas as playlists
as select * from Playlist

create procedure MusicasDaPlaylist( --todas as musicas d euma playlist
	@Cod_playlist int
)
as
	select AFP.Cod_faixa, AFP.Cod_album
	from Playlist P, AlbumFaixaPlaylist AFP
	where	P.Id_playlist = @Cod_playlist and
			P.Id_playlist = AFP.Cod_playlist
			group by(AFP.Cod_album)

create procedure FaixasDDD --as faixas do tipo ddd por album
as
	select AF.Cod_faixas, AF.Cod_album
	from	AlbumFaixa AF, Faixa f
	where	AF.Cod_faixa = f.Numero and
			f.TP_gravacao = 'DDD'
			group by(AF.Cod_album)

create procedure FaixasADD --as faixas do tipo add por album
as
	select AF.Cod_faixas, AF.Cod_album
	from	AlbumFaixa AF, Faixa f
	where	AF.Cod_faixa = f.Numero and
			f.TP_gravacao = 'ADD'
			group by(AF.Cod_album)

create procedure FaixasDeInterpreteDeTipo( --as faixas por interprete de determinado tipo
	@InterpreteTipo varchar(80)
)
as
	select AF.Cod_faixa, AF.Cod_album
	from	AlbumFaixa AF, FaixaInterprete FI, Interprete i
	where	AF.Cod_album = FI.Cod_album and
			AF.Cod_faixa = FI.Cod_faixa and
			FI.Cod_interprete = i.Cod_interprete and
			i.TP_interprete = @InterpreteTipo
			group by(AF.Cod_album)

create procedure AlbunsPorTipo --Albuns Fisicos
as
	select Cod_album
	from Album
	where TP_compra = 'Fisico'

create procedure AlbunsPorTipo --Albuns Download
as
	select Cod_album
	from Album
	where TP_compra = 'Download'

create procedure FaixasPorAlbumTipo --Faixas de albuns Fisico
as
	select AF.Cod_faixa, AF.Cod_album
	from	AlbumFaixa AF, Album a
	where	AF.Cod_album = a.Cod_album and
			a.Cod_album = 'Fisico'

create procedure FaixasPorAlbumTipo --Faixas de albuns Download
as
	select AF.Cod_faixa, AF.Cod_album
	from	AlbumFaixa AF, Album a
	where	AF.Cod_album = a.Cod_album and
			a.Cod_album = 'Download'

create procedure PlaylistsMaisTocadas --as playlists mais tocadas
as 
	select P.Id_playlist
	from Playlist P, AlbumFaixaPlaylist AFP
	where P.Id_playlist = AFP.Cod_playlist
	order by (AFP.Frequencia)

--calculadores de quantidades: Por tabela

create procedure NumeroPeriodos --nuemro de periodos registrados
as select count(*) from Periodo

create procedure NumeroAlbuns --numero de albuns registrados
as select count(*) from Album 

create procedure NumeroPlaylists --numero de playlists registradas
as select count(*) from Playlist

create procedure NumeroFaixas --numero total de faixas registradas
as select count(*) from Faixa

create procedure NumeroGravadoras --numero de gravadoras registradas
as select count(*) from Gravadora

create procedure NumeroCompositores --numero de compositores registrador
as select count(*) from Compositor

create procedure NumeroInterpretes --nuemro de inetrpreets registardos
as select count(*) from Interprete

create procedure NumeroComposicoes --numero de composições registradas
as select count(*) from Composicao
--calculadores de quantidades: Por atributo da própria tabela
create procedure NumeroFaixasDDD --numero de faixas ddd
as
	select count(AF.Cod_faixa)
	from Faixas f, Album a, AlbumFaixa AF
	where	TP_Gravacao = 'DDD' and
			f.Numero = AF.Cod_faixas and
			f.Cod_album = AF.Cod_album
			group by(AF.Cod_album)

create procedure NumeroFaixasADD --numero de faixas add
as
	select count(AF.Cod_faixa)
	from Faixas f, Album a, AlbumFaixa AF
	where	TP_Gravacao = 'ADD' and
			f.Numero = AF.Cod_faixas and
			f.Cod_album = AF.Cod_album
			group by(AF.Cod_album)

create procedure NumeroAlbunsFisicos --numero de albuns fisicos
as
	select count(*)
	from	Album
	where	TP_compra = 'Fisico'

create procedure NumeroAlbunsFisicos --numero de albuns download
as
	select count(*)
	from	Album
	where	TP_compra = 'Download'

create procedure NumeroAlbumESuasFaixas( --nuero total de faixas de album
	@Cod_album int
)
as
	select	count(f.Numero)
	from Faixas f
	where f.Cod_album = @Cod_album

create procedure NumeroMusicasDaPlaylist( --numero total de faixas de playlist
	@Cod_playlist int
)
as
	select count(AFP.Cod_faixa, AFP.Cod_album)
	from Playlist P, AlbumFaixaPlaylist AFP
	where	P.Id_playlist = @Cod_playlist and
			P.Id_playlist = AFP.Cod_playlist
			group by(AFP.Cod_album)

create procedure NumeroFaixasDeInterprete( --numero de faixas por tipo de interprete
	@Cod_interprete int
)
as
	select count(AF.Cod_faixa, AF.Cod_album)
	from FaixaInterprete FI, AlbumFaixa AF
	where	FI.Cod_interprete = @Cod_interprete and
			FI.Cod_faixa = AF.Cod_faixa and
			FI.Cod_album = AF.Cod_album
			group by (AF.Cod_album)

create procedure NumeroFaixasDeCompositor( --numero de faixas do compositor
	@Cod_compositor int
)
as
	select Count(AF.Cod_faixa, AF.Cod_album)
	from FaixaCompositor FC, AlbumFaixa AF
	where	FC.Cod_compositor = @Cod_compositor and
			FC.Cod_faixa = AF.Cod_faixa and
			FC.Cod_album = AF.Cod_album
			group by (AF.Cod_album)
 
create procedure NumeroFaixasDeInterprete( --numero de faixas do interprete
	@Cod_interprete int
)
as
	select count(AF.Cod_faixa, AF.Cod_album)
	from FaixaInterprete FI, AlbumFaixa AF
	where	FI.Cod_interprete = @Cod_interprete and
			FI.Cod_faixa = AF.Cod_faixa and
			FI.Cod_album = AF.Cod_album
			group by (AF.Cod_album)

create procedure NumeroAlbunsDaGravadora( --nuemro de albuns por graavdora
	@Cod_gravadora
)
as
	select count(AG.Cod_album)
	from gravadora G, AlbumGravadora AG
	where	G.Cod_gravadora = @Cod_gravadora and
			G.Cod_gravadora = AG.Cod_gravadora
--calculadores de quantidades: Por atributo de outra tabela
create procedure NumeroFaixasDePeriodo( --numero de faixas por periodo
	@Cod_periodo int
)
as
	select count(AF.Cod_faixa, AF.Cod_album)
	from CompositorPeriodo CP, FaixaCompositor FC, AlbumFaixa AF
	where	CP.Cod_periodo = @Cod_periodo and
			CP.Cod_compositor = FC.Id_compositor and
			FC.Cod_faixa = AF.Cod_faixa
			group by (AF.Cod_album)

create procedure NumeroAlbunsPeriodo( --numero de albuns por periodo
	@Cod_periodo int
)
as
	select count(AF.Cod_album)
	from AlbumFaixa AF, FaixaCompositor FC, CompositorPeriodo CP, Faixa f
	where	f.Numero = AF.Cod_faixa and
			f.Cod_album = AF.Cod_album and
			AF.Cod_faixa = FC.Cod_faixa and
			FC.Cod_compositor = CP.Cod_compositor and
			CP.Cod_periodo = @Cod_periodo