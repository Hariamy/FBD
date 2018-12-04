--albuns de preço maior que media de preço de todos os albuns

select Cod_album --contando com ele
from Album
where Preco > avg(
	select Preco
	from Album
)

--Nome da gravadora com maior numero de playlists com uma faixa pelo menos composta por Dvorack

--Compositor ocm maior numero de faixas em playlists existetes
select Nome
from Compositor
inner join FaixaCompositor
on Id_compositor = Cod_compositor
inner join AlbumFaixa
on Cod_faixa = Cod_faixa and Cod_album = Cod_album
inner join AlbumFaixaPlaylist
on Cod_album = Cod_album and Cod_faixa = Cod_faixa
where count(Cod_album, Cod_faixa) >= ALL count(Cod_album, Cod_faixa)
--Playlisrs onde faixas são todas do tipo de ocmpo Convcerto e periodo Barroco
select Nome
from Playlist
inner join AlbumFaixaPlaylist
on Id_playlist = Cod_playlist
where all (

select f.TP_composicao --seleciona o tipo de composição
from Faixa f, Playlist P, AlbumFaixaPlaylist AFP, AlbumFaixa AF
where P.Id_playlist = AFP.Cod_playlist and --verifica se o cod da playlist coindicee com a da auxiliar
		(AFP.Cod_faixa = AF.Cod_faixa and AFP.Cod_album = AF.Cod_album) and --se os cods da axuilair coincidem com as albumfaixa
		(AF.Cod_faixa = f.Numero and AF.Cod_album = f.Cod_album)

) = 'concerto' and
all (

select pe.Descricao
from Periodo pe, Playlist P, Compositor C, CompositorPeriodo CP, AlbumFaixa AF,
where	P.Id_playlist = AFP.Cod_playlist and
		(AFP.Cod_album = AF.Cod_album and AFP.Cod_faixa = AF.Cod_faixa) and
		(AF.Cod_album = FC.Cod_album and AF.Cod_faixa = FC.Cod_faixa) and
		FC.Cod_compositor = C.Cod_compositor and
		C.Cod_compositor = CP.Cod_compositor and
		CP.Cod_periodo = P.Cod_periodo

) = 'barroco'

---seleciona o tipo de composição
/*select f.TP_composicao --seleciona o tipo de composição
from Faixa f, Playlist P, AlbumFaixaPlaylist AFP, AlbumFaixa AF
where P.Id_playlist = AFP.Cod_playlist and --verifica se o cod da playlist coindicee com a da auxiliar
		AFP.Cod_faixa = AF.Cod_faixa and AFP.Cod_album = AF.Cod_album and --se os cods da axuilair coincidem com as albumfaixa
		AF.Cod_faixa = f.Numero and AF.Cod_album = f.Cod_album --se essa coincide com os da faixa em qestão

select pe.Descricao
from Periodo pe, Playlist P, Compositor C, CompositorPeriodo CP, AlbumFaixa AF,
where	P.Id_playlist = AFP.Cod_playlist and
		(AFP.Cod_album = AF.Cod_album and AFP.Cod_faixa = AF.Cod_faixa) and
		(AF.Cod_album = FC.Cod_album and AF.Cod_faixa = FC.Cod_faixa) and
		FC.Cod_compositor = C.Cod_compositor and
		C.Cod_compositor = CP.Cod_compositor and
		CP.Cod_periodo = P.Cod_periodo
*/