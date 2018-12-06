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

/*create trigger Insert_Barroco_DDD on FaixaCompositor
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
end

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
begin
RAISERROR('Incompatibilidade de tipos',16,3)
ROLLBACK TRANSACTION
end*/

create trigger Update_Barroco_DDD on Faixa
for insert, update
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

create trigger Del_Album on Album --incompleta dúvida
for delete
as
BEGIN
	delete from Faixa F, Deleted D
	where F.Cod_album = D.Cod_album
END