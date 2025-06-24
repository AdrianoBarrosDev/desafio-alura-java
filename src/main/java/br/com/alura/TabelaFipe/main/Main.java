package br.com.alura.TabelaFipe.main;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import br.com.alura.TabelaFipe.model.DadosVeiculo;
import br.com.alura.TabelaFipe.model.ModelosMarca;
import br.com.alura.TabelaFipe.model.Veiculo;
import br.com.alura.TabelaFipe.service.ConsumoApi;
import br.com.alura.TabelaFipe.service.ConverteDados;

public class Main {
	
	private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
	
	private Scanner scanner = new Scanner(System.in);
	private ConsumoApi consumo = new ConsumoApi();
	private ConverteDados conversor = new ConverteDados();
	private ModelosMarca modelosMarca;
	private String endereco;
	
	public void showMenu() {
		
		System.out.print("""
				
				==========================
				1 - Carro
				2 - Motos
				3 - Caminhões
				==========================
				
				Digite sua escolha:
				""");
		
		var option = scanner.nextInt();
		scanner.nextLine();
		
		// Verifica qual das opções o usuário escolheu
		if(option == 1) {
			this.endereco = URL_BASE + "carros/marcas";
		} else if(option == 2) {
			this.endereco = URL_BASE + "motos/marcas";
		} else {
			this.endereco = URL_BASE + "caminhoes/marcas";
		}
		
		// Mostra todas as marcas do veículo que o usuário escolheu
		mostrarMarcasVeiculo();
		
		// Usuário seleciona o código da marca
		System.out.println("\nInforme o código da marca para consulta: ");
		var marca = scanner.nextLine();
		this.endereco = this.endereco + "/" + marca + "/modelos";
		
		// Mostra os modelos da marca que o usuário digitou o código
		mostrarModelosMarcaEspecifica();
		
		// Filtragem de modelos
		System.out.println("\nDigite um trecho do nome do carro a ser buscado: ");
		var nomeVeiculo = scanner.nextLine();
		
		// Mostra os modelos filtrados de acordo com a entrada do usuário
		mostrarModelosFiltrados(nomeVeiculo);
		
		// Consulta de valores do modelo escolhido pelo usuário
		System.out.println("\nDigite o código do modelo para consultar valores: ");
		var modeloEscolhido = scanner.nextLine();
		System.out.println();
		
		// Mostra todas as informações do modelo do veículo escolhido pelo usuário
		mostrarModelosEscolhidos(modeloEscolhido);
		
		System.out.println("\nSistema finalizado.");
	}
	
	
	public void mostrarMarcasVeiculo() {
		
		// Obtém os dados baseado no tipo de veículo que o usuário escolheu na api
		String json = consumo.obterDados(this.endereco);
		
		// Ordena as marcas do tipo de veículo escolhido e mostra no console
		System.out.println("\nMarcas do veículo: ");
		
		var marcas = conversor.obterLista(json, DadosVeiculo.class);
		
		System.out.println(marcas);
		marcas.stream()
			.sorted(Comparator.comparing(DadosVeiculo::codigo))
			.forEach(System.out::println);
		
	}
	
	
	public void mostrarModelosMarcaEspecifica() {
		
		// Obtém os modelos da marca na api
		String json = consumo.obterDados(this.endereco);
		
		// Ordena os modelos da marca escolhida e mostra no console
		System.out.println("\nModelos dessa marca: ");
		
		this.modelosMarca = conversor.obterDados(json, ModelosMarca.class);
		
		modelosMarca.modelos().stream()
			.sorted(Comparator.comparing(DadosVeiculo::codigo))
			.forEach(System.out::println);
		
	}
	
	
	public void mostrarModelosFiltrados(String nomeVeiculo) {
		
		System.out.println("\nModelos Filtrados");
		
		// Lista de modelos filtrados
		this.modelosMarca.modelos().stream()
				.filter(v -> v.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
				.forEach(System.out::println);
		
	}
	
	
	public void mostrarModelosEscolhidos(String modeloEscolhido) {
		
		this.endereco = this.endereco + "/" + modeloEscolhido + "/anos";
		
		// Obtém os dados do modelo escolhido
		String json = consumo.obterDados(this.endereco);
		
		// Retorna a lista com todos os anos do modelo escolhido
		List<DadosVeiculo> listaAnos = conversor.obterLista(json, DadosVeiculo.class);
		
		// Mostra cada veículo da lista com suas informações
		for(int i = 0; i < listaAnos.size(); i++) {
			
			String enderecoVeiculo = this.endereco + "/" + listaAnos.get(i).codigo();
			json = consumo.obterDados(enderecoVeiculo);
			
			Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
			System.out.println(veiculo);
			
		}
		
	}

}
