package model;

import java.time.LocalDate;

public class Projeto {
    private int id;
    private String nomeProjeto;
    private LocalDate dataInicio;
    private LocalDate dataFinal;

    public Projeto(){

    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomeProjeto() { return nomeProjeto; }
    public void setNomeProjeto(String nome_projeto) { this.nomeProjeto = nomeProjeto; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setData_inicio(LocalDate data_inicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataFinal() { return dataFinal; }
    public void setDataFinal(LocalDate data_final) { this.dataFinal = dataFinal; }

}