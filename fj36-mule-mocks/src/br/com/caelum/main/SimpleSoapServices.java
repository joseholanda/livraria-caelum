package br.com.caelum.main;

import javax.xml.ws.Endpoint;

import br.com.caelum.estoque.ws.v1.EstoqueWSV1;
import br.com.caelum.estoque.ws.v2.EstoqueWSV2;

public class SimpleSoapServices {

	private Endpoint estoqueV1;
	private Endpoint estoqueV2;
	private int porta;

	public SimpleSoapServices(int porta) {
		this.porta = porta;
	}

	public void roda() {
		final String URI_v1 = "http://localhost:" + porta + "/v1/EstoqueWS";
		final String URI_v2 = "http://localhost:" + porta + "/v2/EstoqueWS";
		
		estoqueV1 = Endpoint.publish(URI_v1, new EstoqueWSV1());
		estoqueV2 = Endpoint.publish(URI_v2, new EstoqueWSV2());
		
		System.out.println("*** Serviços SOAP rodando");
		System.out.println(URI_v1);
		System.out.println(URI_v2);
	}
	
	public void para() {
		estoqueV1.stop();
		estoqueV2.stop();
	}

	
	
}
