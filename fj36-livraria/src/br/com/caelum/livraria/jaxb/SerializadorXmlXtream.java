package br.com.caelum.livraria.jaxb;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import br.com.caelum.livraria.modelo.Pedido;

public class SerializadorXmlXtream {

	public String toXml(Pedido pedido) {

		XStream xs = new XStream(new DomDriver());
		xs.alias("pedido", Pedido.class);
		xs.autodetectAnnotations(true);
		return xs.toXML(pedido);

	}

}
