package br.tcc.venda.mapper;

import br.tcc.venda.domain.Venda;
import br.tcc.venda.record.VendaRecord;

public class VendaMapper {

	public static VendaRecord output(Venda output) {
		return new VendaRecord(
				output.getId(), 
				output.getProdutos(), 
				output.getData(),
				output.getIsFinalizada());
	}

}