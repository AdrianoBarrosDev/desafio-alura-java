package br.com.alura.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(
		@JsonAlias("Valor") String valor, 
		@JsonAlias("Marca") String marca, 
		@JsonAlias("Modelo") String modelo,
		@JsonAlias("AnoModelo") Integer ano,
		@JsonAlias("Combustivel") String tipoCombustivel
		) {
	
	@Override
	public String toString() {
		return String.format("%s %s Ano: %d Valor: %s Combustível: %s", marca, modelo, ano, valor, tipoCombustivel);
	}
	
}
