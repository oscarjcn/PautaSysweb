package br.mpeg.drosofila.util.persistencia;

import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class GeraBanco {
	public static void main(String[] args) {
		Ejb3Configuration cfg = new Ejb3Configuration();

		// sad eh o nome do persistence-unit no persistence.xml.
		cfg.configure("drosofila", null);

		Configuration hbmcfg = cfg.getHibernateConfiguration();

		SchemaExport schemaExport = new SchemaExport(hbmcfg);
		schemaExport.create(true, true);
	}
}
