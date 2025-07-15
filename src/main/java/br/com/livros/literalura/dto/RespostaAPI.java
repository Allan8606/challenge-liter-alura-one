package br.com.livros.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespostaAPI {

    private List<LivroDTO> results;

    public List<LivroDTO> getResults() { return results; }
}
