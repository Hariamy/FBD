--adicionar coisas

janela criar playlist

retornar todos os albuns
selecionar album
retornar todas as faixas do album
selecionar faixas
voltar a janela de albuns
sair da janela
janela perguntar se deseja sair sem salvar alterações
descartar
requer nome
requer verificação de data (aplicação)
gerar id
inserir

consulta todos os albuns retornando nome par aaplicação e codigo
consulta faixas do album
seselção salar dados da faixa e album selecionados
entrada de dados basicos da playlist
inserção de faixas na playlist em laço

/*Implemente um aplicativo Java, C ou Python, que permita a inserção playlists no
banco de dados. Este aplicativo deve mostrar todos os álbuns existentes. O
usuário pode, assim, escolher o(s) álbum(ns) e quais faixas destes devem compor
a playlist.*/

create procedure Add_playlist( --primeiro
	@Id_playlist int,
	@Nome varchar(80),
	@DT_criacao date
)
as
begin
	insert into (@id_playlist, @Nome, @DT_criacao)
end

create procedure AlbunsTodos --segundo
as
begin
	select Cod_album
	from Album

create procedure AlbumESuasFaixas( --todas as faixas de um album --terceiro
	@Cod_album int
)
as
	select	f.Numero, f.Descricao, f.TP_composicao, f.Cod_composicao,
			f.Tempo, f.TP_Gravacao, 
	from Faixas f
	where f.Cod_album = @Cod_album

create procedure Add_AlbumFaixaPlaylist( --quarto
	@Frequencia int,
	@DT_ultima_tocagem date,
	@Tempo_execucao int,
	@Cod_album int,
	@Cod_faixa int
)
as
begin
	insert into AlbumFaixaPlaylist(@Frequencia, @DT_ultima_tocagem, @Tempo_execucao, @Cod_album, @Cod_faixa)
end