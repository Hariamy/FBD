create index CLUSTERED I_Pr_CodAlbum  --index primario
on Faixa (Cod_album)
	with PAD_INDEX = ON
FILLFACTOR = 100
--ON fliegroup


CREATE [UNIQUE] [CLUSTERED |
NONCLUSTERED]
INDEX nome_índice
ON nome_tabela (nome_coluna [,
{nome_coluna})
[ With (PAD_INDEX= ON | OFF ]
[FILLFACTOR = taxa_preenchimento_nós) ]
[ON filegroup]

create index NONCLUSTERED I_Sc_TPCompositor --index secunario
on Faixa(TP_composicao)
	with PAD_INDEX = ON
FILLFACTOR = 100
--ON filegroup alguma coisa