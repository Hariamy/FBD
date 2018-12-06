--item a

select Cod_album --contando com ele
from Album
where Preco >	(select sum(Preco)
				from Album)/
				(select count(*) from Album) --sem função agregada em wheres


--item b
select G.Nome as Gravadora
from Gravadora G
inner join AlbumGravadora AG
on G.Cod_gravadora = AG.Cod_gravadora
inner join AlbumFaixa AF
on AG.Cod_album = AF.Cod_album
inner join FaixaCompositor FC
on AF.Cod_album = FC.Cod_album and AF.Cod_faixa = FC.Cod_faixa
inner join Compositor C
on FC.Cod_compositor = C.Id_compositor and C.Nome = 'Dvorack'
inner join AlbumFaixaPlaylist AFP
on AF.Cod_album = AFP.Cod_album and AF.Cod_faixa = AFP.Cod_faixa
inner join Playlist P
on AFP.Cod_playlist = P.Id_playlist
group by  G.Cod_gravadora
having count(AFP.Cod_playlist) >= all(
	select count(AFP.Cod_playlist)
	from Gravadora G
	inner join AlbumGravadora AG
	on G.Cod_gravadora = AG.Cod_gravadora
	inner join AlbumFaixa AF
	on AG.Cod_album = AF.Cod_album
	inner join FaixaCompositor FC
	on AF.Cod_album = FC.Cod_album and AF.Cod_faixa = FC.Cod_faixa
	inner join Compositor C
	on FC.Cod_compositor = C.Id_compositor and C.Nome = 'Dvorack'
	inner join AlbumFaixaPlaylist AFP
	on AF.Cod_album = AFP.Cod_album and AF.Cod_faixa = AFP.Cod_faixa
	inner join Playlist P
	on AFP.Cod_playlist = P.Id_playlist
	group by  G.Cod_gravadora
)
--item c
(select C.Nome, count(AFP.Cod_album) --aplicação separa o nome avisar o prof
from AlbumFaixaPlaylist AFP, AlbumFaixa AF, FaixaCompositor FC, Compositor C
where	(AFP.Cod_album = AF.Cod_album and AFP.Cod_faixa = AF.Cod_faixa) and
		(AF.Cod_album = FC.Cod_album and AF.Cod_faixa = FC.Cod_faixa) and
		(FC.Cod_compositor = C.Id_compositor)
group by C.Id_compositor, C.Nome)
order by 2 desc offset 0 rows fetch next 1 rows only

--item d
select Nome
from Playlist
inner join AlbumFaixaPlaylist
on Id_playlist = Cod_playlist
where (
select f.TP_composicao --seleciona o tipo de composição
from Faixa f, Playlist P, AlbumFaixaPlaylist AFP, AlbumFaixa AF
where P.Id_playlist = AFP.Cod_playlist and --verifica se o cod da playlist coindicee com a da auxiliar
		(AFP.Cod_faixa = AF.Cod_faixa and AFP.Cod_album = AF.Cod_album) and --se os cods da axuilair coincidem com as albumfaixa
		(AF.Cod_faixa = f.Numero and AF.Cod_album = f.Cod_album)
) = 'concerto' and
(select pe.Descricao
from Periodo pe, Playlist P, Compositor C, CompositorPeriodo CP, AlbumFaixa AF, AlbumFaixaPlaylist AFP, FaixaCompositor FC
where	P.Id_playlist = AFP.Cod_playlist and
		(AFP.Cod_album = AF.Cod_album and AFP.Cod_faixa = AF.Cod_faixa) and
		(AF.Cod_album = FC.Cod_album and AF.Cod_faixa = FC.Cod_faixa) and
		FC.Cod_compositor = C.Id_compositor and
		C.Id_compositor = CP.Cod_compositor and
		CP.Cod_periodo = pe.Cod_periodo
) = 'barroco'