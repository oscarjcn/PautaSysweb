package br.mpeg.drosofila.controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

import br.mpeg.drosofila.controle.api.IUsuarioControle;
import br.mpeg.drosofila.modelo.Parametros;
import br.mpeg.drosofila.modelo.Resumo;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.persistencia.ParametrosDao;
import br.mpeg.drosofila.persistencia.ParticipanteDao;
import br.mpeg.drosofila.persistencia.ResumoDao;
import br.mpeg.drosofila.persistencia.UsuarioDao;
import br.mpeg.drosofila.persistencia.api.IUsuarioDao;
import br.mpeg.drosofila.util.String.StringUtil;
import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.util.criptografia.GeraHash;
import br.mpeg.drosofila.util.persistencia.GenericDao;
import br.mpeg.drosofila.visao.ParticipanteBeans;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public class UsuarioControle implements IUsuarioControle, Serializable {

	
	private static final long serialVersionUID = 1L;
	private final IUsuarioDao dao = new UsuarioDao();
	private boolean utilisarCaminhoPadraoResumo = false;
	public static final String REVISOR="R";
	public static final String ADMINISTRADOR="A";
	
	public Usuario save(Usuario usuario) {

		usuario.setSenha(GeraHash.criptografar(usuario.getSenha()));

		dao.salvar(usuario);
		return buscaPorEmail(usuario.getEmail().toString());
	}
	
	private Usuario buscaPorEmail(String email){
		String field = "";
		try {
			field = Usuario.class.getDeclaredField("email").getName();
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		}
		return dao.findByUniqueColumn(field, email);
	}
	
	public Usuario atualizaSenha(String email, String senha) {
		Usuario usuario = buscaPorEmail(email);
		usuario.setSenha(GeraHash.criptografar(senha));
		dao.alterar(usuario);
		return usuario;
	}

	public void limpaCacheBanco(){
		dao.limpaCache();
	}
	
	@SuppressWarnings("unused")
	public List<Usuario> update(Usuario usuario) {
	//	Usuario user = new UsuarioDao().findById(usuario.getId());
		//List<Resumo> persistidos = user.getParticipante().getResumos();
		
	//	System.out.println("atualiados" +atualizados.size());
		//System.out.println("persistidos" +persistidos.size());
		/*for (Resumo res : persistidos) {
			if(!atualizados.contains(res)){
				System.out.println("REMOVENDO EXCLUIDOS PELO USU�Rio");
				res.setParticipante(null);
				new ResumoDao().excluir(res);
				deletaArquivo(res.getCaminhoArquivo());
			}
		}*/
		try{
			
			List<Resumo> atualizados
			= new ResumoDao().listaResumoParticipanteId(usuario.getParticipante().getId());
			
		//List<Resumo> atualizados = usuario.getParticipante().getResumos();
		
		for (Resumo res : atualizados) {
			if(res.getId()==null){
				res.setParticipante(usuario.getParticipante());
				String arquivo = criaNome(res.getCaminhoArquivo());
				//System.out.println("caminho arquivo" + arquivo);
				criaArquivo(res.getResumo(), arquivo);
				res.setCaminhoArquivo(arquivo.replace(pegaCaminhoArquivo(utilisarCaminhoPadraoResumo), ""));
				res.setCaminhoArquivo(res.getCaminhoArquivo().replace("/", ""));
				new ResumoDao().salvar(res);
			}
		}
		}catch (NullPointerException e) {
			
		}
		
		try{
			Integer identificador = usuario.getParticipante().getId();
			
			
		}catch (NullPointerException e) {
			new ParticipanteDao().salvar(usuario.getParticipante());
			usuario.setParticipante(new ParticipanteDao().findByUniqueColumn(
					"cpf", usuario.getParticipante().getCpf()));
		}
		

		dao.alterar(usuario);
		return dao.listaTudo();
	}
	
	
	public static String pegaCaminhoArquivo(boolean padrao){
/*		if(padrao)
			return Contexto.getPath("/arquivos/");
		
		ParametrosDao dao = new ParametrosDao();
		List<Parametros>parametros = dao.findAll();
		
		return parametros.get(0).getcaminhoResumos();*/
		return "";
	}
	
	
	@SuppressWarnings("unused")
	public List<Usuario> updateResumos(Usuario usuario) {
		try{
			
		List<Resumo> atualizados = usuario.getParticipante().getResumos();
		
		for (Resumo res : atualizados) {
			if(res.getId()==null){
				res.setParticipante(usuario.getParticipante());
				String arquivo = criaNome(res.getCaminhoArquivo());
				System.out.println("caminho arquivo" + arquivo);
				//criaArquivo(res.getResumo(), arquivo);
				res.setCaminhoArquivo(arquivo.replace(pegaCaminhoArquivo(utilisarCaminhoPadraoResumo), ""));
				res.setCaminhoArquivo(res.getCaminhoArquivo().replace("/", ""));
				new ResumoDao().salvar(res);
			}
		}
		}catch (NullPointerException e) {
			
		}
		
		try{
			Integer identificador = usuario.getParticipante().getId();
			
			
		}catch (NullPointerException e) {
			new ParticipanteDao().salvar(usuario.getParticipante());
			usuario.setParticipante(new ParticipanteDao().findByUniqueColumn(
					"cpf", usuario.getParticipante().getCpf()));
		}
		

		dao.alterar(usuario);
		return dao.listaTudo();
	}

	public List<Usuario> delete(Usuario usuario) {
		new UsuarioDao().excluir(usuario);
		return dao.listaTudo();
	}

	public Usuario findById(Integer id) {
		return dao.findById(id);
	}

	public Usuario findByName(String nome) {
		return dao.findByName(nome);
	}

	public Usuario instaceObject() {
		Usuario usuario = new Usuario();
		return usuario;
	}

	public Usuario logar(String login, String senha) {
		return dao.logar(login, GeraHash.criptografar(senha));
	}

	public List<Usuario> findAll() {
		return dao.listaTudo();
	}

	public Usuario usuarioPrimeiroAcesso(String loginName) {
		return dao.primeiroAcesso(loginName);

	}

	public Usuario inserirSenha(Usuario usuario) {
		dao.alterar(usuario);
		return dao.findByUniqueColumn("email", usuario.getEmail());
	}

	public Usuario pesquisaPorEmail(String email) {
		return new UsuarioDao().pesquisaPorEmail(email);
	}

	public void criaArquivo(byte[] bytes, String arquivo) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);
			
			fos.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(ParticipanteBeans.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ParticipanteBeans.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	public String criaNome(String nome) {
		Integer codigo = (int) (Math.random() * 1000);
		String novoNome = codigo+"-" + limparNomeArquivo(nome);
		//String arquivox = Contexto.getPath("/arquivos") + "/"+ novoNome.replace("/", "");
		String arquivox = pegaCaminhoArquivo(utilisarCaminhoPadraoResumo) + "/"+ novoNome.replace("/", "");
		/*System.out.println("arquivox" + arquivox);
		File f = new File(arquivox);
		if (f.exists()) {
			arquivox = criaNome(nome);
		}*/

		return arquivox;
	}
	
	public static String limparNomeArquivo(String nome){
		String extensao = nome.substring(nome.lastIndexOf("."), nome.length());
		return StringUtil.apenasAlphaNumerico(nome.substring(0, nome.lastIndexOf(".")).trim())+extensao.trim();
	}
	
	private void deletaArquivo(String nome){
		String arquivo = pegaCaminhoArquivo(utilisarCaminhoPadraoResumo) + "/"+nome;
		File f = new File(arquivo);
		if(f.exists()){
			f.deleteOnExit();
		}
	}
	
	public List<Usuario> listaPorPapel(String papel){
		return dao.listaPorPapel(papel);
	}
	
	public List<Usuario> listaPorPapelRevisor(){
		return dao.listaPorPapel(REVISOR);
	}
	
	public static void main(String[] args) {
		/*ResumoDao resumoDao = new ResumoDao();
	
		List lista = resumoDao.listaResumoParticipanteId(1);
		System.out.println("Resultado consulta: "+lista.size());*/
	
		//System.out.println(pegaCaminhoArquivo(true));
		System.out.println(limparNomeArquivo("881roque & tidon_(simpósio_belém).doc"));
		
	}
	
}
