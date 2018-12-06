create CLUSTERED index I_Pr_CodAlbum  --index primario --na horta d ecriar a faixa bote nonclustered ao lado da constriant pk
on Faixa (Cod_album)
	with (PAD_INDEX = ON, FILLFACTOR = 100)
--ON fliegroup

/*
CREATE [UNIQUE] [CLUSTERED |
NONCLUSTERED]
INDEX nome_índice
ON nome_tabela (nome_coluna [,
{nome_coluna})
[ With (PAD_INDEX= ON | OFF ]
[FILLFACTOR = taxa_preenchimento_nós) ]
[ON filegroup]
*/
create NONCLUSTERED index I_Sc_TPCompositor --index secunario
on Faixa(TP_composicao)
	with (PAD_INDEX = ON, FILLFACTOR = 100)
--ON filegroup alguma coisa