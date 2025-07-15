package br.com.livros.literalura.repositorio;

import br.com.livros.literalura.modelo.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
