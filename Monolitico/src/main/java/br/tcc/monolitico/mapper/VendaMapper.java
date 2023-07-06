package br.tcc.monolitico.mapper;

import br.tcc.monolitico.domain.Venda;
import br.tcc.monolitico.record.VendaRecord;

public class VendaMapper {

	public static VendaRecord output(Venda output) {
		return new VendaRecord(
				output.getId(), 
				output.getProdutos(), 
				output.getData(),
				output.getIsFinalizada());
	}

}