/**/

/*
Msg 156, Nível 15, Estado 1, Procedimento Insert_Barroco_DDD, Linha 15 [Linha de Início do Lote 0]
Sintaxe incorreta próxima à palavra-chave 'as'.
Msg 102, Nível 15, Estado 1, Procedimento Insert_Barroco_DDD, Linha 20 [Linha de Início do Lote 0]
Sintaxe incorreta próxima a '@Cod_faixa'.
Msg 102, Nível 15, Estado 1, Procedimento Insert_Barroco_DDD, Linha 21 [Linha de Início do Lote 0]
Sintaxe incorreta próxima a '@Cod_faixa'.
Msg 102, Nível 15, Estado 1, Procedimento Insert_Barroco_DDD, Linha 22 [Linha de Início do Lote 0]
Sintaxe incorreta próxima a '@Cod_faixa'.
Msg 102, Nível 15, Estado 1, Procedimento Insert_Barroco_DDD, Linha 23 [Linha de Início do Lote 0]
Sintaxe incorreta próxima a '@Cod_faixa'.
*/
--item a
create procedure Del_FaixaInterprete( 
	@Cod_faixa int,
	@Cod_album int
)
as 
begin 
	delete from FaixaInterprete
	where Cod_faixa = @Cod_faixa and Cod_album = @Cod_album
end

create procedure Del_AlbumFaixa(
	@Cod_faixa int,
	@Cod_album int
)
as 
begin
	
	delete from AlbumFaixa
	where Cod_faixa = @Cod_faixa and Cod_album = @Cod_album
end

create procedure Del_Faixa(
	@Cod_faixa int,
	@Cod_album int
)
as
begin 
	delete from Faixa
	where	Numero = @Cod_faixa and Cod_album = @Cod_album
end

create trigger Insert_Barroco_DDD on FaixaCompositor
for insert
as
if(	select	f.TP_composicao
	from	Faixa f, inserted i,
			FaixaCompositor FC, Compositor C,
			CompositorPeriodo CP, Periodo P
	where	f.Numero = i.Cod_faixa and
			i.Cod_compositor = C.Id_compositor and
			C.Id_compositor = CP.Cod_compositor and
			CP.Cod_periodo = P.Cod_periodo and
			P.Descricao = 'Barroco'
) = 'DDD'
begin
RAISERROR('Incompatibilidade de tipos',16,1)
ROLLBACK TRANSACTION 
DECLARE @Cod_faixa int, @Cod_album int
set @Cod_faixa = (select Cod_faixa from inserted)
set @Cod_album = (select Cod_album from inserted)
exec Del_FaixaInterprete(@Cod_faixa, @Cod_album)
exec Del_AlbumFaixa(@Cod_faixa. @Cod_album)
exec Del_Faixa(@Cod_faixa, @Cod_album)

create trigger Update_Barroco_DDD on Faixa
for update
as
if(	select	i.TP_composicao
	from	inserted i, AlbumFaixa AF, Compositor C, FaixaCompositor FC, CompositorPeriodo CP, Periodo P
	where	i.Numero = AF.Cod_faixa and
			AF.Cod_faixa = FC.Cod_faixa and
			FC.Cod_compositor = C.Id_compositor and
			C.Id_compositor = CP.Cod_compositor and
			CP.Cod_periodo = P.Cod_periodo and
			P.Descricao = 'Barroco'
) = 'DDD'
--as
begin
RAISERROR('Incompatibilidade de tipos',16,3)
ROLLBACK TRANSACTION
end

--item b
create trigger Limite_Faixas on AlbumFaixa
after insert, update
as
if(
	select count(AF.Cod_faixa)
	from AlbumFaixa AF, inserted i
	where AF.cod_album = i.cod_album
)>64
BEGIN
RAISERROR('Mais faixas que permitido pelo limite',16,2)
ROLLBACK TRANSACTION
END

--item c
create procedure Del_FaixaCompositor( 
	@Cod_faixa int,
	@Cod_album int
)
as 
begin 
	delete from FaixaCompositor
	where Cod_faixa = @Cod_faixa and Cod_album = @Cod_album
end

create procedure Del_FaixaPlaylist( 
	@Cod_faixa int,
	@Cod_album int
)
as 
begin 
	delete from AlbumFaixaPlaylist
	where Cod_faixa = @Cod_faixa and Cod_album = @Cod_album
end

create procedure Del_Album(
	@cod_album int
)
as
begin
	DECLARE @Cod_faixa int
	WHILE (select * from AlbumFaixa where Cod_album = @cod_album) != null
	begin
	set @Cod_faixa = (	select Cod_faixa --um por um
					from AlbumFaixa
					where cod_album = @cod_album)
	exec Del_FaixaCompositor(@Cod_faixa, @cod_album) --erro
	exec Del_FaixaInterprete(@Cod_faixa, @cod_album) --pede
	exec Del_FaixaPlaylist(@Cod_faixa, @cod_album) --parâmetro
	exec Del_AlbumFaixa(@Cod_faixa, @cod_album)
	exec Del_Faixa(@Cod_faixa, @cod_album)
	end
	delete from Album
	where @cod_album = Cod_album
end

/*create procedure Ad_Faixa( --parece certo
	@Numero int,
	@Descricao varchar(111),
	@TP_compsicao varchar(3),
	@Cod_composicao int,
	@Cod_album int,
	@Tempo int,
	@TP_gravacao varchar(3),
	@Cod_interprete int,
	@Cod_compositor int
)
as
begin 
	insert into Faixa values(@Numero, @Descricao, 
							@TP_compsicao, @Cod_composicao, 
							@Cod_album, @Tempo, 
							@TP_gravacao)
	insert into AlbumFaixa values(@Cod_album, @Numero)
	insert into FaixaInterprete values(@Cod_album, @Numero, @Cod_interprete)
	insert into FaixaCompositor values(@Cod_album, @Numero, @Cod_compositor)

*/