package br.com.caelum.main;


public class RodaServicos {

	public static void main(String[] args) {
		
		new SimpleSoapServices(8088).roda();
		new SimpleHttpServer(8089).roda();

	}
}
