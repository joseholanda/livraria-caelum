package br.com.caelum;

import java.util.Scanner;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EnviaMensagemParaOTopico {
	public static void main(String[] args) throws NamingException {
		InitialContext initialContext = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) initialContext
				.lookup("jms/RemoteConnectionFactory");
		Topic topico = (Topic) initialContext.lookup("jms/TOPICO.LIVRARIA");
		
		try (JMSContext context = factory.createContext("jms", "jms")) {
			JMSProducer producer = context.createProducer();
			producer.setProperty("formato", "ebook");
			
			Scanner teclado = new Scanner(System.in);
			while( teclado.hasNextLine() ) {
				String line = teclado.nextLine();
				producer.send(topico, line);
			}
			
			teclado.close();
		}
	}
}
