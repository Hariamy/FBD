go
create function FC_Compositor_Obras (@Nome Varchar(80))
returns TABLE
AS
return (select 	distinct A.Cod_album, C.Nome
		from 	Album A, FaixaCompositor FC, Compositor C, AlbumFaixa AF
		where	(select Nome from compositor where Nome like '%'+@Nome+'%') = C.Nome and --selecioanr um só
				C.Id_compositor = FC.Cod_compositor and
				(FC.Cod_faixa = AF.Cod_faixa and FC.Cod_album = AF.Cod_album) and
				AF.Cod_album = A.Cod_album)

