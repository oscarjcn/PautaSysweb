package br.mpeg.drosofila.persistencia;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.mpeg.drosofila.modelo.Indicador;
import br.mpeg.drosofila.persistencia.api.IIndicadorDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class IndicadorDao extends GenericDao<Indicador> implements
		IIndicadorDao {

	public List<Indicador> listaIndicadoresParaDataAtual(Date data, boolean desconto, boolean parcelado) {
		
		System.out.println("########################## \n Desconto: "+desconto);
		EntityManager em = getEntityManager();
		String hql = "Select i from Indicador i WHERE " +
				"i.dataAtivacao <= :data AND i.dataVencimento >= :data AND i.principal = :principal AND i.parcelado = :parcelado";
		TypedQuery<Indicador> query =  em.createQuery(hql, Indicador.class);
		query.setParameter("data", data);
		query.setParameter("principal", (!desconto));
		
		query.setParameter("parcelado", parcelado);
	
		return query.getResultList();
	}
	
	public Indicador buscaIndicadorParticipante(int participanteId) {
		
		EntityManager em = getEntityManager();
		String hql = "Select i from Indicador i" +
				" JOIN i.participantes p " +
				" WHERE " +
				" p.id = :participanteId ";
		TypedQuery<Indicador> query =  em.createQuery(hql, Indicador.class);
		query.setParameter("participanteId", participanteId);
	
		return query.getSingleResult();
	}
	
	@Override
	public void salvar(Indicador objeto) {
		super.save(objeto);
	}
	@Override
	public void alterar(Indicador objeto) {
		super.update(objeto);	
	}
	@Override
	public void excluir(Indicador objeto) {
		super.delete(objeto);
	}
	@Override
	public List<Indicador> listaTudo() {
		return findAll();
	}	
	
	public static void main(String[] args) {
		IndicadorDao dao = new IndicadorDao();
		Indicador indicador = dao.buscaIndicadorParticipante(3);
		//System.out.println(indicador.getNome());
		/*Calendar data = new  GregorianCalendar();		
		System.out.println(data.getTime());
		List lista = dao.listaIndicadoresParaDataAtual(data.getTime(), true);
		System.out.println(lista.size());
		*/
	}

}