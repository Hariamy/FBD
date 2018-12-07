create CLUSTERED index I_Pr_CodAlbum 
on AlbumFaixa (Cod_album)
	with (PAD_INDEX = ON, FILLFACTOR = 100)

create NONCLUSTERED index I_Sc_TPCompositor
on Faixa(TP_composicao)
	with (PAD_INDEX = ON, FILLFACTOR = 100)
