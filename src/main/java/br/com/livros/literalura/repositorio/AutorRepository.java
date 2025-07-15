package br.com.livros.literalura.repositorio;

import br.com.livros.literalura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
