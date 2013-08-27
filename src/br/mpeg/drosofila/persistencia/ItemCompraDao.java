package br.mpeg.drosofila.persistencia;

import java.util.List;

import br.mpeg.drosofila.modelo.ItemCompra;
import br.mpeg.drosofila.persistencia.api.IItemCompraDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class ItemCompraDao extends GenericDao<ItemCompra> implements IItemCompraDao{

	@Override
	public void salvar(ItemCompra objeto) {
		super.save(objeto);
		
	}

	@Override
	public void alterar(ItemCompra objeto) {
		super.update(objeto);
		
	}

	@Override
	public void excluir(ItemCompra objeto) {
		super.delete(objeto);
		
	}

	@Override
	public List<ItemCompra> listaTudo() {
		return findAll();
	}

}
