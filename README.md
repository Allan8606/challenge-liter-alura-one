# 📚 Liter Alura - Desafio de Projeto Java com Spring Boot

Este projeto foi desenvolvido como parte do curso **Formação Java** ONE/Alura.  
O desafio consistiu em transformar uma aplicação de busca de livros por título, usando dados da [Gutendex API](https://gutendex.com/), em uma aplicação com persistência em banco de dados, menu interativo e funcionalidades adicionais.

---

## 🚀 Funcionalidades

O menu interativo da aplicação oferece as seguintes opções:
=== MENU ===
1 - Buscar livro pelo título
2 - Listar livros registrados
3 - Listar autores registrados
4 - Listar autores vivos em determinado ano
5 - Listar livros por idioma (PT, EN, ES, FR)
0 - Sair



### ✅ Descrição de cada funcionalidade:

1. **Buscar livro pelo título**  
   → Busca na API do projeto Gutendex e salva o livro e autor no banco.

2. **Listar livros registrados**  
   → Exibe todos os livros salvos com título, idioma, autor e downloads.

3. **Listar autores registrados**  
   → Lista autores com ano de nascimento, falecimento e os livros que escreveu.

4. **Listar autores vivos em determinado ano**  
   → Filtra os autores que estavam vivos no ano informado pelo usuário.

5. **Listar livros por idioma**  
   → Filtra livros pelo idioma (ex: `PT`, `EN`, `ES`, `FR`).

---

## 🧰 Tecnologias utilizadas

- Java 17+
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven
- API pública: [Gutendex](https://gutendex.com/)
- IntelliJ IDEA (IDE utilizada)

---

## 🗃️ Modelo de dados

### 📘 Livro
- Título
- Idioma
- Número de downloads
- Autor (relacionamento N:1)

### ✍️ Autor
- Nome
- Ano de nascimento
- Ano de falecimento
- Lista de livros (relacionamento 1:N)

---


# 🧠 Autor
Desenvolvido por Allan Isaac como parte do aprendizado da Formação ONE/Alura.


