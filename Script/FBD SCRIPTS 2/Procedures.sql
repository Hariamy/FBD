-- Procedures de Adição tabelas individuais
create procedure Add_compositor( --add o compositor e o compperiod
	@Nome  varchar(80),
	@Id_compositor int,
	@DT_morte date,
	@DT_nascimento date,
	--@Cod_periodo int
)
as
begin
	insert into Compositor(@Nome,@Id_compositor,@DT_morte,@DT_nascimento)
	--insert into CompositorPeriodo(@Cod_periodo,@Id_compositor)
end

--adiiocnar faixa com compsoitor novo e cria esse antes e depoi sa faixa
--adicionar faxia com interprete novo e cria esse antes e depois a faixa
--adiconar faixa cm album novo e cria antes e depois a faixa
--combinação dos anteriores

create procedure Add_periodo(
	@Cod_periodo int,
	@Intervalo_tempo int,
	@Descricao varchar(111)
)
as
begin
	insert into Periodo(@Cod_periodo, @Intervalo_tempo, @Descricao)
end

create procedure Add_gravadora( --add gravadora
	@Cod_gravadora int,
	@Nome varchar(80),
	@Site varchar(111)
)
as
begin
	insert into Gravadora(@Cod_gravadora,@nome,@Site)
end

create procedure Add_telefones(
	@Cod_gravadora int,
	@Numero int,
	@Tipo varchar(50)
)
as
begin
	insert into Telefones(@Cod_gravadora, @Numero, @Ttipo)
end

create procedure Add_composicao(
	@Descricao varchar(111),
	@Id_composicao int,
)
as
begin
	insert into Composicao(@Descricao,@Id_composicao)
end

create procedure Add_interprete(
	@Cod_interprete int,
	@TP_interprete varchar(80),
	@Nome varchar(80),
)
as
begin
	insert into Interprete(@Cod_interprete,@TP_interprete,@Nome)
end

create procedure Add_album(
	@Cod_album int,
	@Descricao varchar(400),
	@Cod_gravadora int,
	@Preco decimal(2,2),
	@DT_compra date,
	@TP_compra varchar(8)
)
as
begin
	insert into Album(@Cod_album, @Descricao, @Cod_gravadora, @Precom @DT_compra, @TP_compra)
end

create procedure Add_faixa(
	@Numero int,
	@Descricao varchar(111),
	@TP_composicao varchar(3),
	--@Cod_compositor int,
	@Cod_composicao int,
	@Cod_album int,
	@Tempo int,
)
as
begin
	insert into Faixa(@Numero, @Descricao, @TP_composicao, /*@Cod_compositor,*/ @Cod_composicao, @Cod_album, @Tempo)
	insert into ALbumFaixa(@Cod_album,@Numero)
end

create procedure Add_playlist(
	@Id_playlist int,
	@Nome varchar(80),
	@DT_criacao date
)
as
begin
	insert into (@id_playlist, @Nome, @DT_criacao)
end

-- Procedures de Adição tabelas auxiliares

create procedure Add_FaixaCompositor(
	@Cod_album int,
	@Cod_faixa int,
	@Cod_compositor int
)
as
begin
	insert into FaixaCompositor(@Cod_album, @Cod_faixa, @Cod_compositor)
end

create procedure Add_AlbumFaixa(
	@Cod_album int,
	@Cod_faixa int
)
as
begin
	insert into ALbumFaixa(@Cod_album, @Cod_faixa)
end

create procedure Add_FaixaInterprete(
	@Cod_album int,
	@Cod_faixa int,
	@Cod_interprete int
)
as
begin
	insert into FaixaInterprete(@Cod_album, @Cod_faixa, @Cod_interprete)
end

create procedure Add_AlbumGravadora(
	@DT_gravacao date,
	@Cod_album int,
	@Cod_gravadora int
)
as
begin
	insert into AlbumGravadora(@DT_gravacao, @Cod_album, @Cod_gravadora)
end

create procedure Add_CompositorPeriodo(
	@Cod_periodo int,
	@Cod_compositor int
)
as
begin
	insert into CompositorPeriodo(@Cod_periodo, @Cod_compositor)
end

create procedure Add_AlbumFaixaPlaylist(
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
