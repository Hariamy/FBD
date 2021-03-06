

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

create function FC_Compositor_Obras (@Nome Varchar(80))
returns TABLE
AS
return (select 	A.Cod_album as C.Nome
		from 	Album A, Faixa f, FaixaCompositor FC
		where	(select Nome from compositor where Nome like '%@Nome_') = C.Nome and --selecioanr um só
				C.Id_compositor = FC.Cod_compositor and
				FC.Cod_faixa = AF.Cod_faixa and
				AF.Cod_album = A.Cod_album)
		
/*Defina uma função que tem como parâmetro de entrada o nome (ou parte do)
nome do compositor e o parâmetro de saída todos os álbuns com obras
compostas pelo compositor.*/

