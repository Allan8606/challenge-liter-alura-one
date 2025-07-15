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
}
