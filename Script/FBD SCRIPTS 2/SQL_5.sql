/*CREATE VIEW nome_da_visão [(nome_coluna {,nome_coluna …})]

[WITH <propriedade_da_visão> [ ,...n ] ]
AS expressão_SQL [WITH CHECK OPTION]

<propriedade_da_visão> ::= {[SCHEMABINDING] --visão materializada tem que ter schemabinging
[ENCRYPTION ] [ VIEW_METADATA ] }*/

create view V_PlaylistAlbuns (Playlist, Albuns)
with schemabinding
AS
	select P.Nome as Playlist, count(*) as Albuns
	from dbo.AlbumFaixaPlaylist AS AFP inner join dbo.Playlist as P
	on P.Id_playlist = AFP.Cod_playlist
	group by P.Nome

/*create view V_PlaylistAlbuns (Playlist, Albuns)
with schemabinding
AS
	select P.Nome as Playlist, count(*) as Albuns
	from dbo.AlbumFaixaPlaylist AS AFP inner join dbo.Playlist as P
	on P.Id_playlist = AFP.Cod_playlist
	group by P.Nome*/
--with check option

--definir materializada

--consequencias:
--agora, se algo implica em retirar nome ou album resultados da visão
--o banco impede a ação de ser feita, logo só insert e update

--Criar uma visão materializada que tem como atributos o nome da playlist e a
--quantidade de álbuns que a compõem.