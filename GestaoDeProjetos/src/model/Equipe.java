package model;

import java.util.List;

public class Equipe {

    private int id;
    private String nomeEquipe;
    private Colaborador gerente;
    private Colaborador lider;
    private List<Colaborador> colaboradores;
    private List<Projeto> projetos;

    public Equipe() {

    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomeEquipe() { return nomeEquipe; }
    public void setNomeEquipe(String nomeEquipe) { this.nomeEquipe = nomeEquipe; }
    public Colaborador getGerente() { return gerente; }
    public void setGerente(Colaborador gerente) { this.gerente = gerente; }
    public Colaborador getLider() { return lider; }
    public void setLider(Colaborador lider) { this.lider = lider; }
    public List<Colaborador> getColaboradores() { return colaboradores; }
    public void setColaboradores(List<Colaborador> colaboradores) { this.colaboradores = colaboradores; }
    public List<Projeto> getProjetos() { return projetos; }
    public void setProjetos(List<Projeto> projetos) { this.projetos = projetos; }

}