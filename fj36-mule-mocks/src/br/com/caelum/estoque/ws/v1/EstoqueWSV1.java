package br.com.caelum.estoque.ws.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import br.com.caelum.estoque.ws.ItemEstoque;

@WebService(targetNamespace="http://caelum.com.br/estoquews/v1", serviceName="EstoqueWS",wsdlLocation="EstoqueWS_v1.wsdl")
@HandlerChain(file="handlers.xml")
public class EstoqueWSV1 {

	private Map<String, ItemEstoque> repositorio = new HashMap<>();

	public EstoqueWSV1() {
		repositorio.put("SOA", new ItemEstoque("SOA", 5));
		repositorio.put("TDD", new ItemEstoque("TDD", 1));
		repositorio.put("RES", new ItemEstoque("RES", 2));
		repositorio.put("LOG", new ItemEstoque("LOG", 4));
		repositorio.put("WEB", new ItemEstoque("WEB", 1));
		repositorio.put("ARQ", new ItemEstoque("ARQ", 2));
	}

	@WebMethod(operationName = "ItensPeloCodigo")
	@WebResult(name = "ItemEstoque")
	public List<ItemEstoque> getQuantidade(
			@WebParam(name = "codigo") List<String> codigos) {
		
		System.out.println("EstoqueWS V1 - " + codigos);
		
		List<ItemEstoque> itens = new ArrayList<>();

		if (codigos == null || codigos.isEmpty()) {
			return itens;
		}

		for (String codigo : codigos) {
			if (repositorio.containsKey(codigo)) {
				itens.add(repositorio.get(codigo));
			}
		}
		return itens;
	}
}
