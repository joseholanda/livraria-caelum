package br.com.caelum.livraria.rest;

import java.io.Serializable;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;

import org.springframework.stereotype.Component;

import br.com.caelum.livraria.modelo.Link;
import br.com.caelum.livraria.modelo.Pagamento;
import br.com.caelum.livraria.modelo.Transacao;

@Component
public class ClienteRest implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String SERVER_URI = "http://localhost:8080/fj36-webservice";
	private static final String ENTRY_POINT = "/pagamentos/";

	public Pagamento criarPagamento(Transacao transacao) {

		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(SERVER_URI + ENTRY_POINT);
		Builder request = target.request();
		Invocation invocation = request.buildPost(Entity.json(transacao));
		return invocation.invoke(Pagamento.class);
	}

	public Pagamento confirmarPagamento(Pagamento pagamento) {
		Link linkConfirmar = pagamento.getLinkPeloRel("confirmar");
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(SERVER_URI+linkConfirmar.getUri());
		Builder request = target.request();
		Invocation invocation = request.build(linkConfirmar.getMethod());
		return invocation.invoke(Pagamento.class);
	}

}
