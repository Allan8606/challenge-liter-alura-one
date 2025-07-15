package br.com.livros.literalura;

import br.com.livros.literalura.servico.LivroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Bean
	public CommandLineRunner executar(LivroService livroService) {
		return args -> {
			Scanner entrada = new Scanner(System.in);
			int opcao = -1;

			while (opcao != 0) {
				System.out.println("\n=== MENU ===");
				System.out.println("Escolha o número da opção desejada:");
				System.out.println("1 - Buscar livro pelo título");
				System.out.println("2 - Listar livros registrados");
				System.out.println("3 - Listar autores registrados");
				System.out.println("4 - Listar autores vivos em determinado ano");
				System.out.println("5 - Listar livros por idioma (PT, EN, ES, FR)");
				System.out.println("0 - Sair");
				System.out.print("Escolha uma opção: ");


				try {
					opcao = Integer.parseInt(entrada.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("Opção inválida.");
					continue;
				}
				System.out.println("------------------------------");
				switch (opcao) {
					case 1:
						System.out.print("Digite o título do livro: ");
						String titulo = entrada.nextLine();
						livroService.buscarEGuardarLivro(titulo);
						break;
					case 2:
						livroService.listarLivrosRegistrados();
						break;
					case 3:
						livroService.listarAutoresRegistrados();
						break;
					case 4:
						System.out.print("Digite o ano desejado: ");
						int ano = Integer.parseInt(entrada.nextLine());
						livroService.listarAutoresVivosEm(ano);
						break;
					case 5:
						System.out.print("Digite o idioma (PT, EN, ES, FR): ");
						String idioma = entrada.nextLine();
						livroService.listarLivrosPorIdioma(idioma);
						break;
					case 0:
						System.out.println("Encerrando...");
						System.exit(0);
						break;
					default:
						System.out.println("Opção inválida.");

				}

			}
		};
	}
}
