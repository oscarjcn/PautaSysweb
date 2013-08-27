package br.mpeg.drosofila.visao;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.mpeg.drosofila.controle.UsuarioControle;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.util.controle.MantemConexao;

@WebServlet("/testeRequisicao")
public class RequisicaoLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static  String urlWebSiteEvento="http://marte.museu-goeldi.br/viicbc/index.php";//="http://127.0.0.1/requisicaoLoginEvento.php";  
	private String urlSysweb;
    
    public RequisicaoLogin() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	private void iniciaVerificacaoConexao(){
		if(!MantemConexao.iniciado)
			 new MantemConexao().start();
		
	}
	
    private void logar(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	Index index = new Index();
    	String email = request.getParameter("email");
    	String senha = request.getParameter("senha");
    	urlWebSiteEvento = request.getParameter("urlWebSiteEvento");
    	urlSysweb = request.getParameter("urlSysweb");
    	String caminhoAplicacao = request.getSession().getServletContext().getContextPath();
    	
    	System.out.println("email: "+email+ " -  senha:"+senha);
    	Usuario logado;
    	
    	UsuarioControle controle = new UsuarioControle();
		logado = controle.logar(email, senha);
	
    	if(logado!=null){
    		insereObjetoEmSessao("Usuario", logado, request);
    		String nomeUsuario[] = logado.getNome().split(" ");
    		iniciaVerificacaoConexao();
    		//response.sendRedirect(urlWebSiteEvento+"/inscricoes/area-do-usuario.html?nomeUsuario="+nomeUsuario[0]+"%20"+nomeUsuario[nomeUsuario.length-1]);
    		response.sendRedirect(urlSysweb+"?nomeUsuario="+nomeUsuario[0]+"%20"+nomeUsuario[nomeUsuario.length-1]);
    		//response.sendRedirect(urlWebSiteEvento+"?msg=Usuário%20ou%20senha%20inválidos.%20Por%20favor%20tente%20novamente.");
    	}else{
    		System.out.println("Erro");
    		response.sendRedirect(urlWebSiteEvento+"?msg=Usuário%20ou%20senha%20inválidos.%20Por%20favor%20tente%20novamente.");
    	}
    }
    
    private void insereObjetoEmSessao(String nome, Object objeto, HttpServletRequest hreq){
        hreq.getSession().setAttribute(nome, objeto);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*PrintWriter pw = response.getWriter();
		pw.println("<html>");
			pw.println("<body> <h2> Teste </h2>");
			pw.println("<body>");
		pw.println("</html>");
		pw.close();
		System.out.println("\n\n\n GET \n" );
		System.out.println("############ chamou o servlet");
		System.out.println("\n\n\n");*/
		
		logar(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("################################ CHAMOU POST");
		logar(request, response);
		/*System.out.println("\n\n\n POST \n" );
		PrintWriter pw = response.getWriter();
			pw.println("<html>");
			pw.println("<body> <h2> Teste </h2>");
			pw.println("<body>");
			pw.println("</html>");
			response.sendRedirect("http://127.0.0.1/cadastro.php?msg=usuario%20invalido");
			
			response.sendRedirect("http://127.0.0.1/cadastro.php?msg=usuario%20invalido");
			
		
		System.out.println("############ chamou o servlet");
		System.out.println("\n\n\n");*/		
	}

}
