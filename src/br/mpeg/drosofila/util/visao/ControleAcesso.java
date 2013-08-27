/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mpeg.drosofila.util.visao;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.mpeg.drosofila.controle.PapelControle;
import br.mpeg.drosofila.modelo.Papel;
import br.mpeg.drosofila.modelo.Usuario;


/**
 *
 * @author Victor Coutinho
 */
public class ControleAcesso implements Filter, Serializable {
    private static final boolean debug = false;
    private FilterConfig filterConfig = null;
    public ControleAcesso() {
    }
private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("seguranca: antes do processo");
        }
    }
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("seguranca: depois do processo");
        }
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("seguranca: Inicialização do Filtro");
            }
        }
    }

    
    public boolean podeAcessarAreaAdministrativa(Usuario usuario, String page, String path){
    	if(usuario.isAdministrador())
        	return true;
    	
    	return !(page.indexOf("administracao")>-1 ); 
    }
    

    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        if (debug) {
            log("seguranca: Filtro");
        }
        doBeforeProcessing(request, response);
        Throwable problem = null;
        try {
            HttpServletRequest hreq = (HttpServletRequest) request;
            String pagina = hreq.getServletPath();
            String path = hreq.getSession().getServletContext().getContextPath();
            Usuario usuario = (Usuario) hreq.getSession().getAttribute("Usuario");
            if(usuario == null){
            	if (!pagina.equals("/index.jsf")
            			&& !(pagina.indexOf("/requisicaoLogin")>-1)
            			&& !(pagina.indexOf("/senha/recuperar.jsf")>-1)
            			&& !pagina.equals("/sistema/entrar.jsf") 
            			&& !pagina.equals("/css/style.css")
                        && !pagina.startsWith("/images") 
                        && !pagina.startsWith("/resources") 
            		&& !pagina.startsWith("/javax.faces.resource") ) {
                    if (usuario == null) {
                    	
                        ((HttpServletResponse) response).sendRedirect(path + "/index.jsf");
                    } 
                }
            }else{
            	if(!podeAcessarAreaAdministrativa(usuario, pagina, path) || pagina.equals("/index.jsf"))
            		((HttpServletResponse) response).sendRedirect(path + "/sistema/proxpaginas/pagina1.jsf");

            }
            
            try {
                chain.doFilter(request, response);
            } catch (Exception e) {
            }
        } catch (Throwable t) {
            problem = t;
        }
        doAfterProcessing(request, response);
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }
    @Override
    public void destroy() {
    }
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n");
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>");
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
     public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    } 
}