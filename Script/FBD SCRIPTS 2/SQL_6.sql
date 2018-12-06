

/*CREATE [ALTER] FUNCTION [ schema_name. ] function_name
( [ { @parameter_name [ AS ][ type_schema_name. ] data_type
[ = default ] [ READONLY ] } [ ,...n ] ] )
RETURNS return_data_type
[ WITH <function_option> [ ,...n ] ]
[ AS ]
BEGIN
function_body
RETURN scalar_expression
END


CREATE [ALTER] FUNCTION [ schema_name. ]
function_name
( [ { @parameter_name [ AS ]
[ type_schema_name. ] data_type
[ = default ] [ READONLY ] } [ ,...n ] ] )
RETURNS TABLE
[ WITH <function_option> [ ,...n ] ]
[ AS ]
RETURN (consulta_SQL)
END*/
--totalmente erradollh
go
create function FC_Compositor_Obras (@Nome Varchar(80)) --roda
returns TABLE
AS
--BEGIN
return (select 	distinct A.Cod_album, C.Nome
		from 	Album a, Faixa f, FaixaCompositor FC, Compositor C, AlbumFaixa AF
		where	(select Nome from compositor where Nome like '%'+@Nome+'%') = C.Nome and --selecioanr um só
				C.Id_compositor = FC.Cod_compositor and
				(FC.Cod_faixa = AF.Cod_faixa and FC.Cod_album = AF.Cod_album) and
				AF.Cod_album = A.Cod_album)
--END
		
/*Defina uma função que tem como parâmetro de entrada o nome (ou parte do)
nome do compositor e o parâmetro de saída todos os álbuns com obras
compostas pelo compositor.*/

