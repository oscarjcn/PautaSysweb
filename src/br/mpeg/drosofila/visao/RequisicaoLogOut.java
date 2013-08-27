package br.mpeg.drosofila.visao;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RequisicaoLogOut
 */
@WebServlet("/RequisicaoLogOut")
public class RequisicaoLogOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RequisicaoLogOut() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void sair(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	System.out.println("\n\n Tentando sair com Requsicao http \n\n\n\n");
    	request.getSession().invalidate();    	
    	response.sendRedirect(RequisicaoLogin.urlWebSiteEvento);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sair(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sair(request, response);
	}

}
