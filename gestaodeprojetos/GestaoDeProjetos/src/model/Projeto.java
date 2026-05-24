package model;

import java.time.LocalDate;

public class Projeto {
    private int id;
    private String nomeProjeto;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private String descricao;
    private Equipe equipe;

    public Projeto(){

    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomeProjeto() { return nomeProjeto; }
    public void setNomeProjeto(String nomeProjeto) { this.nomeProjeto = nomeProjeto; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataFinal() { return dataFinal; }
    public void setDataFinal(LocalDate dataFinal) { this.dataFinal = dataFinal; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Equipe getEquipe() { return equipe; }
    public void setEquipe(Equipe equipe) { this.equipe = equipe; }

}