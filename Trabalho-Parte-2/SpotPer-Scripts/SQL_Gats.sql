--Gatilhos básicos restrições especificadas
create trigger TR_Faixa_Tipo on Faixa --restrição do tipo da faixa
for insert,update
as
	if(	(select TP_composicao
		from inserted) != 'ADD' or
		(select TP_composicao
		from inserted) != 'DDD')
	begin
		raiserror('tipo de composição inválido',9,1)
		rollback transaction
	end

create trigger TR_Interprete_Tipo on Interprete --restrição de tipo de intérprete
for insert, update
as
		declare @TipoInterprete varchar(80);
		select @TipoInterprete;
		set @TipoInterprete = (	select TP_interprete
								from inserted);
	if(	@TipoInterprete != 'Orquestral' and
		@TipoInterprete != 'Trio' and
		@TipoInterprete != 'Quarteto' and
		@TipoInterprete != 'Ensemble' and
		@TipoInterprete != 'Soprano' and
		@TipoInterprete != 'Tenor'
		)
	begin
		raiserror('Tipo de intérprete inválido',10,1)
		rollback transaction
	end

create trigger TR_Preco_Album on Album --Restrição dos custos de albuns
for insert, update
as 
	declare @PrecoAlbum decimal(10,2);
	select @PrecoAlbum;
	set @PrecoAlbum = (	select Preco
						from inserted);
	declare @PrecoMedio decimal(10,2);
	select @PrecoMedio;
	set @PrecoMedio =(	((select sum(a.Preco)
						from Album a, inserted i, Faixa f
						where (a.Cod_album != i.Cod_album) and
							  (a.Cod_album = f.Cod_album)  and
							  (f.TP_gravacao = 'DDD'))      +
						      (select Preco from inserted)) /
						(select distinct count(a.preco)
						from Album a, Faixa f 
						where (a.Cod_album = f.Cod_album)  and
							  (f.TP_gravacao = 'DDD'))
					)
	if(	('DDD' like (select f.TP_gravacao --testar
						from inserted i, Faixa f
						where i.Cod_album = f.Cod_album) )	and
		((select @PrecoAlbum) > 3*(select @PrecoMedio)) --testar
		)
	begin
		raiserror('Preco de album maior que permitido',11,1)
		rollback transaction
	end