select Cod_album --cinsulta baseada na descricao inserida
from Album
where Descricao like '%'+@Descricao_Inserida+'%'

--select * from Album where Descricao like '%'+@Descricao_Inserida+'%'

update Album
set	Cod_album = @Cod_album and
	Preco = @Preco and
	Cod_gravadora = @Cod_gravadora and
	DT_compra = @DT_compra and
	TP_compra = @TP_compra and
	Descricao = @Descricao
where Cod_album = @P_Cod_Album