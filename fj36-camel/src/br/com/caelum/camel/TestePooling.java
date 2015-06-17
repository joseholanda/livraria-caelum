package br.com.caelum.camel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import br.com.caelum.livraria.modelo.Livro;

public class TestePooling {
	public static void main(String[] args) throws Exception {
		
		MysqlConnectionPoolDataSource mysqlDS = new MysqlConnectionPoolDataSource(); 
		mysqlDS.setDatabaseName("camel");
		mysqlDS.setServerName("localhost");
		mysqlDS.setPort(3306);
		mysqlDS.setUser("root");
		mysqlDS.setPassword("");

		JndiContext jndi = new JndiContext();
		jndi.rebind("mysqlDataSource", mysqlDS);
		
		CamelContext c = new DefaultCamelContext(jndi);
		c.addRoutes(new MinhaRota());
		
		c.start();
		new Scanner(System.in).next();
		c.stop();
	}
}

class MinhaRota extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("http://localhost:8088/fj36-livraria/loja/livros/mais-vendidos")
				.delay(1000).unmarshal().json().process(new MeuProcessor()).to("direct:livros");
		from("direct:livros").split(body()).process(new ProcessorInsertLivro())
		.setBody(
				simple("insert into Livros (nomeAutor) values ('${header[nomeAutor]}')")
		)
		.to("jdbc:mysqlDataSource");
	}

}
class ProcessorInsertLivro implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		Livro livro = (Livro)in.getBody();
		String nomeAutor = livro.getNomeAutor();
		in.setHeader("nomeAutor", nomeAutor);
	}
	
}

class MeuProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		List<?> body = (List<?>) exchange.getIn().getBody();
		ArrayList<Livro> livros = (ArrayList<Livro>) body.get(0);

		Message message = exchange.getIn();
		message.setBody(livros);
	}

}
