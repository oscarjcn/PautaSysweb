package br.mpeg.drosofila.persistencia;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.mpeg.drosofila.modelo.Estado;
import br.mpeg.drosofila.modelo.Municipio;
import br.mpeg.drosofila.persistencia.api.IMunicipioDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class MunicipioDao extends GenericDao<Municipio> implements
		IMunicipioDao {

	@Override
	public void salvar(Municipio objeto) {
		super.save(objeto);

	}

	@Override
	public void alterar(Municipio objeto) {
		super.update(objeto);

	}

	@Override
	public void excluir(Municipio objeto) {
		super.delete(objeto);

	}

	@Override
	public List<Municipio> listaTudo() {
		return super.findAll();
	}

	@SuppressWarnings("unchecked")
	public List<Municipio> listForState(Estado estado) {
		Session session;
		List<Municipio> retorno;
		session = (Session) getEntityManager().getDelegate();
		retorno = session.createCriteria(Municipio.class)
				.add(Restrictions.eq("estado", estado)).list();
		return retorno;
	}

}
