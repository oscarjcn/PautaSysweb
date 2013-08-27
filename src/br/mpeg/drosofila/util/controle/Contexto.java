package br.mpeg.drosofila.util.controle;

 
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.mpeg.drosofila.modelo.Usuario;

public class Contexto {

    /** Creates a new instance of Contexto */
    @SuppressWarnings("rawtypes")
	private static List mensagemErro = new ArrayList();
    public static final String USUARIO = "Usuario";
    public static final String RESUMOS = "drosofila.arquivos";
    public static final String BOLETOS = "drosofila.boletos";

    public Contexto() {
    }

    public static String getUrlResumo(){
    	String url = Contexto.getExternalContext().getInitParameter(Contexto.RESUMOS);
    	return url;
    }
    public static String getUrlBoleto(){
    	String url = Contexto.getExternalContext().getInitParameter(Contexto.BOLETOS);
    	return url;
    }
    
    public static Usuario getUsuario(){
    	Usuario user = (Usuario) Contexto.getSessao().getAttribute(Contexto.USUARIO);
    	return user;
    }
    
    public static HttpSession getSessao() {
    	FacesContext.getCurrentInstance();
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static String getPath(String caminho) {
        ServletContext request = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        return request.getRealPath(caminho);
    }
    

    @SuppressWarnings("rawtypes")
	public static List getMensagemErro() {
        return mensagemErro;
    }

    @SuppressWarnings("unchecked")
	public static void addMensagemErro(String MensagemErro) {
        mensagemErro.add(MensagemErro);
    }

    public static void clearMensagemErro() {
        mensagemErro.clear();
    }

    

    public static String getDatetoString(Date data) {
        if (data == null) {
            return null;
        }

        String sTemp = DateFormat.getDateInstance().format(data);
        //     dd/mm/yyyy
        String dataFinal = sTemp.substring(6);
        dataFinal = dataFinal + "/";
        dataFinal = dataFinal + sTemp.substring(3, 5);
        dataFinal = dataFinal + "/";
        dataFinal = dataFinal + sTemp.substring(0, 2);

        return dataFinal;


    }

    public static String getDateStringtoString(String data) {
        if (data.equals("")) {
            return null;
        }

        String sTemp = data;

        String dataFinal = sTemp.substring(6);
        dataFinal = dataFinal + "/";
        dataFinal = dataFinal + sTemp.substring(3, 5);
        dataFinal = dataFinal + "/";
        dataFinal = dataFinal + sTemp.substring(0, 2);

        return dataFinal;


    }
}