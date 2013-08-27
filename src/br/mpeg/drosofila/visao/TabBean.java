package br.mpeg.drosofila.visao;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.TabChangeEvent;

@ManagedBean
@SessionScoped
public class TabBean {
	public void onTabChange(TabChangeEvent event) {
		FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab"
				+ event.getTab().getId());
		System.out.println("chegou aqui");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
