package br.mpeg.drosofila.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.mpeg.drosofila.controle.api.IParticipanteControle;
import br.mpeg.drosofila.modelo.Endereco;
import br.mpeg.drosofila.modelo.Indicador;
import br.mpeg.drosofila.modelo.Instituicao;
import br.mpeg.drosofila.modelo.ItemCompra;
import br.mpeg.drosofila.modelo.Municipio;
import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.modelo.Resumo;
import br.mpeg.drosofila.persistencia.IndicadorDao;
import br.mpeg.drosofila.persistencia.InstituicaoDao;
import br.mpeg.drosofila.persistencia.MunicipioDao;
import br.mpeg.drosofila.persistencia.ParticipanteDao;
import br.mpeg.drosofila.persistencia.api.IParticipanteDao;
import br.mpeg.drosofila.util.controle.GenericControle;
/**
 * 
 * @author Victor Coutinho
 *
 */
public class ParticipanteControle extends GenericControle<Participante>
		implements IParticipanteControle, Serializable {

	public ParticipanteControle() {
		super(new ParticipanteDao());
	}
	
	public Participante buscaPorCPF(String cpf){
		return ((ParticipanteDao)getDao()).pesquisaPorCPF(cpf);
	}
	
	public Participante buscaPorRG(String rg){
		return ((ParticipanteDao)getDao()).pesquisaPorRg(rg);
	}
	@Override
	public Participante instaceObject() {
		Participante p = new Participante();
		return p;
	}

	@Override
	public Participante save(Participante obj) {
		return  super.save(obj);
	}
	
	public Participante buscaPorNumeroAssociado(Integer numero){
		return ((IParticipanteDao)getDao()).buscaPorNumeroAssociado(numero);
	}
	
	public Participante pesquisaPorEmail(String email){
		return ((IParticipanteDao)getDao()).pesquisaPorEmail(email);
	}
	
	public static void main(String[] args) {
		ParticipanteControle participanteControle =new ParticipanteControle();
		participanteControle.buscaPorNumeroAssociado(258);
	}
	
	public Participante buscaParticipanteUsuarioId(Integer usuarioId){
		return ((ParticipanteDao)getDao()).buscaParticipanteUsuarioId(usuarioId);
	}
	
	/*public static void main(String[] args) {
		Participante p = new Participante();
		
		p.setCpf("90435214266");
		p.setEmail("victorcoutinho1985@gmail.com");
		Endereco endereco = new Endereco();
		endereco.setBairro("sacramenta");
		endereco.setCep("66083496");
		endereco.setComplemento("casa 112 a");
		endereco.setLogradouro("al sao joao (da lomas valentias)");
		Municipio municipio = new Municipio();
		municipio = new MunicipioDao().findById(4499);
		endereco.setMunicipio(municipio);
		p.setEndereco(endereco);
		p.setFormacao("analista");
		Indicador indicador = new Indicador();
		indicador = new IndicadorDao().findById(1);
		p.setIndicador(indicador);
		Instituicao instituicao = new Instituicao();
		instituicao = new InstituicaoDao().findById(1);
		p.setInstituicao(instituicao);
		
		List<ItemCompra> listaCompra = new ArrayList<ItemCompra>();
		ItemCompra ic = new ItemCompra();
		
		
		ic.setQuantidade(1);
		//ic.setProduto(new ProdutoControle().procuraCamisa().get(0));
		listaCompra.add(ic);
		p.setTelefone("9132544574");
		List<Resumo> resumos = new ArrayList<Resumo>();
		Resumo r = new Resumo();
		
		r.setCpfAutor("90435214268");
		r.setTema("xxx");
		r.setTitulo("asdasdasdsa");
		r.setCaminhoArquivo("hahahaha");
		resumos.add(r);
		p.setResumos(resumos);
		
		
		ParticipanteControle pc = new ParticipanteControle();
		
		pc.save(p);
		
	}*/

}
