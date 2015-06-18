package br.com.caelum.estoque.ws.handler;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

@SuppressWarnings("rawtypes")
public class Autorizador implements SOAPHandler<SOAPMessageContext> {

	private static final String TOKEN_USUARIO = "tokenUsuario";
	private static final String NAMESPACE_ESTOQUEWS_V1 = "http://caelum.com.br/estoquews/v1";
	private static final String NAMESPACE_ESTOQUEWS_V2 = "http://caelum.com.br/estoquews/v2";

	
	@Override
	public boolean handleMessage(SOAPMessageContext context) {

		Boolean ehRequisicao = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (!ehRequisicao) {
			
			try {
				SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
				SOAPHeader soapHeader = envelope.getHeader();
				
				Iterator iterator = verificaSeExisteTokenNoHeader(soapHeader);

				System.out.println();
				if(iterator.hasNext()){
					Node token = (Node) iterator.next();
					System.out.println("(Autorizador) Token: " + token.getValue());
				} else {
					System.out.println("(Autorizador) Nenhum cabeçalho encontrado");
				}

				//sempre continua
				return true;
			} catch (SOAPException e) {
				throw new RuntimeException(e);
			}
		}
		return true;
	}

	private Iterator verificaSeExisteTokenNoHeader(SOAPHeader soapHeader) {
		if(soapHeader.hasChildNodes()){
			
			Iterator elements = soapHeader.getChildElements(new QName(NAMESPACE_ESTOQUEWS_V1, TOKEN_USUARIO));
			if(elements.hasNext()) return elements;

			elements = soapHeader.getChildElements(new QName(NAMESPACE_ESTOQUEWS_V2, TOKEN_USUARIO));
			if(elements.hasNext()) return elements;
			
		}
		
		return new EmptyIterator();
		
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	
	private static class EmptyIterator implements Iterator{

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Object next() {
			return null;
		}

		@Override
		public void remove() {
		}
	}
}
