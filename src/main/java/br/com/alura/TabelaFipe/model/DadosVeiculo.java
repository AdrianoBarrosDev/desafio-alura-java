package br.com.alura.TabelaFipe.model;

public record DadosVeiculo(String codigo, String nome) {
	@Override
	public String toString() {
		return String.format("Cód: %s Descrição: %s", codigo, nome);
	}
}
