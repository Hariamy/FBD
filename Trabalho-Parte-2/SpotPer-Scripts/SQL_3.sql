--item a
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
