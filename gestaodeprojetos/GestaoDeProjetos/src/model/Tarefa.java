package model;
// Classe da tarefa
import java.time.LocalDate;

public class Tarefa {

    private int id;
    private String nomeTarefa;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private Status status;

    public Tarefa(){

    }

    public enum Status {
        PENDENTE,
        EM_ANDAMENTO,
        CONCLUIDA,
        ATRASADA
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomeTarefa() { return nomeTarefa; }
    public void setNomeTarefa(String nomeTarefa) { this.nomeTarefa = nomeTarefa; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataFinal() { return dataFinal; }
    public void setDataFinal(LocalDate dataFinal) { this.dataFinal = dataFinal; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

}
