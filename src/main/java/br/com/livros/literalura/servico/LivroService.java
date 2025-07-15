package br.com.livros.literalura.servico;

import br.com.livros.literalura.dto.RespostaAPI;
import br.com.livros.literalura.modelo.Autor;
import br.com.livros.literalura.modelo.Livro;
import br.com.livros.literalura.repositorio.AutorRepository;
import br.com.livros.literalura.repositorio.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final ConsumoApi consumoApi;

    private final ObjectMapper mapper = new ObjectMapper();

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.consumoApi = new ConsumoApi();
    }

    public void buscarEGuardarLivro(String titulo) {
        String json = consumoApi.buscarLivroPorTitulo(titulo);

        try {
            RespostaAPI resposta = mapper.readValue(json, RespostaAPI.class);

            if (resposta.getResults().isEmpty()) {
                System.out.println("Livro não encontrado.");
                return;
            }

            var livroApi = resposta.getResults().get(0);
            var autorApi = livroApi.getAuthors().get(0);

            Autor autor = new Autor();
            autor.setNome(autorApi.getName());

            if (autorApi.getBirth_year() != null)
                autor.setNascimento(LocalDate.of(autorApi.getBirth_year(), 1, 1));

            if (autorApi.getDeath_year() != null)
                autor.setFalecimento(LocalDate.of(autorApi.getDeath_year(), 1, 1));

            autor = autorRepository.save(autor);

            Livro livro = new Livro();
            livro.setTitulo(livroApi.getTitle());
            livro.setIdioma(livroApi.getLanguages().get(0));
            livro.setDownloads(livroApi.getDownload_count());
            livro.setAutor(autor);

            livroRepository.save(livro);

            System.out.println("Livro salvo com sucesso!");

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler resposta da API", e);
        }
    }

    public void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado no sistema.");
            return;
        }

        System.out.println("Livros registrados:");
        for (Livro livro : livros) {
            System.out.println("----- LIVRO -----");
            System.out.println("Titulo: " + livro.getTitulo());
            System.out.println("Autor: " + livro.getAutor().getNome());
            System.out.println("Idioma: " + livro.getIdioma());
            System.out.println("Downloads: " + livro.getDownloads());
        }
    }

    public void listarAutoresRegistrados() {
        var autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado no sistema.");
            return;
        }

        System.out.println("Autores registrados:");
        for (var autor : autores) {
            String nascimento = (autor.getNascimento() != null) ? String.valueOf(autor.getNascimento().getYear()) : "Desconhecido";
            String falecimento = (autor.getFalecimento() != null) ? String.valueOf(autor.getFalecimento().getYear()) : "Desconhecido";

            System.out.println("Autor: " + autor.getNome());
            System.out.println("Ano de Nascimento: " + nascimento);
            System.out.println("Ano de Falecimento: " + falecimento);

            var livros = autor.getLivros();
            if (livros != null && !livros.isEmpty()) {
                System.out.print("Livros: [");
                for (int i = 0; i < livros.size(); i++) {
                    System.out.print(livros.get(i).getTitulo());
                    if (i < livros.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println("]");
            } else {
                System.out.println("Livros: Nenhum livro cadastrado.");
            }

            System.out.println("-----------------");
        }
    }

    public void listarAutoresVivosEm(int ano) {
        var autores = autorRepository.findAll();

        var autoresVivos = autores.stream()
                .filter(a -> a.getNascimento() != null && a.getNascimento().getYear() <= ano)
                .filter(a -> a.getFalecimento() == null || a.getFalecimento().getYear() > ano)
                .toList();

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor vivo no ano de " + ano + ".");
            return;
        }

        System.out.println("Autores vivos em " + ano + ":");
        for (var autor : autoresVivos) {
            String nascimento = String.valueOf(autor.getNascimento().getYear());
            String falecimento = (autor.getFalecimento() != null) ? String.valueOf(autor.getFalecimento().getYear()) : "Ainda vivo";

            System.out.println("Autor: " + autor.getNome());
            System.out.println("Nascimento: " + nascimento);
            System.out.println("Falecimento: " + falecimento);
            System.out.println("-----------------");
        }
    }

    public void listarLivrosPorIdioma(String idiomaUsuario) {
        String idioma = idiomaUsuario.toLowerCase();

        var livros = livroRepository.findAll()
                .stream()
                .filter(l -> l.getIdioma().equalsIgnoreCase(idioma))
                .toList();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma: " + idiomaUsuario);
            return;
        }

        System.out.println("Livros no idioma " + idiomaUsuario.toUpperCase() + ":");
        for (var livro : livros) {
            System.out.println("----- LIVRO -----");
            System.out.println("Titulo: " + livro.getTitulo());
            System.out.println("Autor: " + livro.getAutor().getNome());
            System.out.println("Downloads: " + livro.getDownloads());
        }
    }
}
