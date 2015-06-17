package br.com.caelum.livraria.camel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracaoCamel {

	@Autowired
	private CamelContext camelContext;

	@PostConstruct
	void init() throws Exception {
		camelContext.addRoutes(new RotaLivraria());
		camelContext.start();
	}
	
	@PreDestroy
	void destroy() throws Exception {
		camelContext.stop();
	}

}

class RotaLivraria extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("jms:topic:jms/TOPICO.LIVRARIA?username=jms&password=jms")
		.log(LoggingLevel.INFO, "CAMEL: Recebendo mensagem ${id}")
		.to("jms:queue:jms/FILA.GERADOR?username=jms&password=jms");
	}
	
}
