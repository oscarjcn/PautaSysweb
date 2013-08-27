package br.mpeg.drosofila.controle;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import br.mpeg.drosofila.controle.api.IComprovanteControle;
import br.mpeg.drosofila.controle.api.IUsuarioControle;
import br.mpeg.drosofila.modelo.Comprovante;
import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.persistencia.ComprovanteDao;
import br.mpeg.drosofila.persistencia.api.IComprovanteDao;
import br.mpeg.drosofila.util.visao.ControleAcesso;


/**
 * @author Victor Coutinho
 */
public class ComprovanteControle implements IComprovanteControle, Serializable {

	private static final long serialVersionUID = 1L;
	private final IComprovanteDao dao = new ComprovanteDao();
	private IUsuarioControle usuarioControle ;
	private boolean utilisarCaminhoPadraoResumo = false;

	public ComprovanteControle(IUsuarioControle usuarioControle){
		this.usuarioControle = usuarioControle; 
	}
	
	public Comprovante save(Comprovante comprovante) {
		criaAruqivo(comprovante);
		dao.salvar(comprovante);
		return null;
	}

	@SuppressWarnings("unused")
	public List<Comprovante> update(Comprovante Comprovante) {
		dao.alterar(Comprovante);
		return dao.listaTudo();
	}

	public File pegaArquivo(Comprovante comprovante){
		String caminhoComprovante 
		= UsuarioControle.pegaCaminhoArquivo(utilisarCaminhoPadraoResumo)
		+"/"+comprovante.getCaminhoArquivo();
		return new File(caminhoComprovante);
	}
	public List<Comprovante> listaComprovantesParticipante(Integer id){
		return dao.listaComprovantesParticipante(id);
	}
	
	public List<Comprovante> delete(Comprovante comprovante) {
		File arquivo = pegaArquivo(comprovante);
		try{
			new ComprovanteDao().excluir(comprovante);
		}catch(Exception e){
			e.getStackTrace();
		}
		arquivo.delete();
		return dao.listaTudo();
	}

	public Comprovante findById(Integer id) {
		return dao.findById(id);
	}

	public Comprovante findByName(String nome) {
		return null;
	}

	public Comprovante instaceObject() {
		Comprovante Comprovante = new Comprovante();
		return Comprovante;
	}


	public List<Comprovante> findAll() {
		return dao.listaTudo();
	}

	public Comprovante findComprovanteByPedido() {
		
		return null;
	}

	
	private void criaAruqivo(Comprovante comprovante){
		String arquivo = usuarioControle.criaNome(comprovante.getCaminhoArquivo());
		//System.out.println("caminho arquivo" + arquivo);
		usuarioControle.criaArquivo(comprovante.getComprovante(), arquivo);
		comprovante.setCaminhoArquivo(arquivo.replace(UsuarioControle.pegaCaminhoArquivo(utilisarCaminhoPadraoResumo), ""));
		comprovante.setCaminhoArquivo(comprovante.getCaminhoArquivo().replace("/", ""));
		//new ComprovanteDao().salvar(comprovante);
	}
	
	public static void main(String[] args) {
		ComprovanteDao comprovanteDao =new ComprovanteDao();
		/*Comprovante comprovante = new Comprovante();
		comprovante.setNomeArquivo("Nome do arquivo");
		comprovante.setCaminhoArquivo("Caminho arquivo");
		
		ParticipanteControle controle = new ParticipanteControle();
		Participante participante = controle.findById(1);
		comprovante.setParticipante(participante);
		
		comprovanteDao.salvar(comprovante);*/
		
		//System.out.println(comprovanteDao.listaComprovantesParticipante(1).get(0).getCaminhoArquivo());
		/*ParticipanteControle controle = new ParticipanteControle();
		Participante participante = controle.findById(1);
		
		
		
		System.out.println(participante.getComprovantes().get(0).getNomeArquivo());*/
	}

	
	
	
}
