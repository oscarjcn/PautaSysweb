package br.mpeg.drosofila.controle;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import br.mpeg.drosofila.controle.api.IProdutoControle;
import br.mpeg.drosofila.modelo.Produto;
import br.mpeg.drosofila.persistencia.ProdutoDao;
import br.mpeg.drosofila.util.controle.GenericControle;

public class ProdutoControle extends GenericControle<Produto> implements IProdutoControle, Serializable{
	
	public static final String PRODUTO_TIPO_MINI_CURSO = "C";
	public static final String PRODUTO_TIPO_INSCRICAO = "I";
	
	public ProdutoControle() {
		super(new ProdutoDao());
	}

	@Override
	public Produto instaceObject() {
		return super.instanceObject();
	}

	public List<Produto> procuraCurso(){
		return new ProdutoDao().listaPorTipo(PRODUTO_TIPO_MINI_CURSO);
	}

	
private List<Produto> removePassagemSemEstoque(List<Produto> passagens){
		
		/*for (Iterator<Pessoa> iterator = getListaPessoa().iterator(); iterator
		.hasNext();) {

			if (iterator.next().isSelecionado()) {
		
				iterator.remove();
			}
		}*/
		if(passagens==null || passagens.size()==0)
			return passagens;
		
		int index=0;
		//Iterator iterator = (Iterator) 
		for(Iterator<Produto> iterator =  passagens.iterator();  iterator.hasNext(); ){
			Produto produto= iterator.next();
			
			if(produto==null)
				break;
			
			if(produto.getQuantidadeDisponiveis()<=verificaQuantidadeProdutoPedida(produto.getId()))
					iterator.remove();
		}
			return passagens;
	}

	public List<Produto> procuraInscricao(){
		return new ProdutoDao().listaPorTipo("I");
	}
	
	public List<Produto> procuraMiniCurso(){
		return new ProdutoDao().listaPorTipo("C");
	}
	
	public Produto buscaInscricao(){
		return new ProdutoDao().buscaInscricao();
	}
	
	public Long verificaQuantidadeProdutoPedida(int idProduto){
		return  ((ProdutoDao)getDao()).findQuantidadeProdutosVendidos(idProduto);
	}
	
	public Long verificaQuantidadeProdutoVendida(int idProduto){
		return  ((ProdutoDao)getDao()).findQuantidadeProdutosVendidosConfirmados(idProduto);
	}
	
	
}
