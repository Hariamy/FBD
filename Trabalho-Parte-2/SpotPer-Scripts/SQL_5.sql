create view V_PlaylistAlbuns (Playlist, Albuns)
with schemabinding
AS
	select P.Nome as Playlist, count(*) as Albuns
	from dbo.AlbumFaixaPlaylist AS AFP inner join dbo.Playlist as P
	on P.Id_playlist = AFP.Cod_playlist
	group by P.Nome
