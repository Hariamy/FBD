create trigger Insert_Barroco_DDD on FaixaCompositor
for insert
as
if(	select	f.TP_composicao
	from	Faixa f, inserted i,
			FaixaCompositor FC, Compositor C,
			CompositorPeriodo CP, Periodo P
	where	f.Numero = i.Cod_faixa and
			--i.Cod_faixa = FC.Cod_faixa and
			i.Cod_compositor = C.Cod_compositor and
			C.Cod_compositor = CP.Cod_compositor and
			CP.Cod_periodo = P.Cod_periodo and
			P.Descricao = 'Barroco'
) = 'DDD'
as
begin
RAISERROR('Incompatibilidade de tipos',16,1)
ROLLBACK TRANSACTION 
DECLARE @Cod_faixa int
@Cod_faixa = (select Cod_faixa from inserted)
exec Del_FaixaInterprete(@Cod_faixa)
exec Del_AlbumFaixa(@Cod_faixa)
exec Del_Faixa(@Cod_faixa)

create trigger Update_Barroco_DDD on Faixa
for update
as
if(	select	i.TP_composicao
	from	inserted
	where	i.Numero = AF.Cod_faixa and
			AF.Cod_faixa = FC.Cod_faixa and
			FC.Cod_compositor = C.Cod_compositor and
			C.Cod_compositor = CP.Cod_compositor and
			CP.Cod_periodo = P.Cod_periodo and
			P.Descricao = 'Barroco'
) = 'DDD'
as
begin
RAISERROR('Incompatibilidade de tipos',16,2)
ROLLBACK TRANSACTION 


--adicionar faixa automatiza tudo


create trigger Limite_Faixas on AlbumFaixa
after insert, update
as
if(
	select count(AF.Cod_faixa)
	from AlbumFaixa AF, inserted i
	where AF.cod_album = i.cod_album
)>64
BEGIN
RAISERROR('Mais faixas que permitido pelo limite')
ROLLBACK TRANSACTION
END

create procedure Del_Album(
	@cod_album int,
)
as
begin
	DECLARE @Cod_faixa int
	@Cod_faixa = (	select Cod_faixa
					from AlbumFaixa
					where cod_album = @cod_album)
	exec Del_FaixaCompositor(@Cod_faixa)
	exec Del_FaixaInterprete(@Cod_faixa)
	exec Del_FaixaPlaylist(@Cod_faixa)
	exec Del_AlbumFaixa(@Cod_faixa)
	exec Del_Faixa(@Cod_faixa)
	delete from Album
	where @cod_album = cod_album

create procedure Del_FaixaCompositor(
	@Cod_faixa
)
as 
begin 
	delete from FaixaCompositor
	where Cod_faixa = @Cod_faixa

create procedure Del_FaixaInterprete(
	@Cod_faixa
)
as 
begin 
	delete from FaixaInterprete
	where Cod_faixa = @Cod_faixa

create procedure Del_FaixaPlaylist(
	@Cod_faixa
)
as 
begin 
	delete from AlbumFaixaPlaylist
	where Cod_faixa = @Cod_faixa

create procedure Del_AlbumFaixa(
	@Cod_faixa
)
as 
begin 
	delete from AlbumFaixa
	where Cod_faixa = @Cod_faixa

create procedure Del_Faixa(
	@Cod_faixa
)
as 
begin 
	delete from Faixa
	where Numero = @Cod_faixa

create procedure Ad_Faixa(
	@Numero int,
	@Descricao varchar(111),
	@TP_compsicao varchar(3),
	@Cod_composicao int,
	@Cod_album int,
	@Tempo int,
	@TP_gravacao varchar(3),
	@Cod_interprete int,
	@Cod_compositor int,
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
