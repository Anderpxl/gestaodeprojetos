package controller.service;

import dao.EquipeDAO;
import dao.ProjetoDAO;
import model.Equipe;
import model.Projeto;
import model.Tarefa;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class RelatorioService {
    // Método para gerar um menu para gerar relatórios de projeto ou equipe
    public void menuRelatorios() {

        Scanner scanner = new Scanner(System.in);
        RelatorioService relatorioService = new RelatorioService();

        int opcao = -1;

        while(opcao != 0) {
            System.out.println("\n====== RELATÓRIOS ======");

            System.out.println("1 - Relatório de Projeto");
            System.out.println("2 - Relatório de Equipe");
            System.out.println("0 - Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {
                case 1:
                    System.out.print("Digite o ID do projeto: ");

                    int projetoId = scanner.nextInt();
                    scanner.nextLine();

                    relatorioService.gerarRelatorioProjeto(projetoId);
                    break;

                case 2:
                    System.out.print("Digite o ID da equipe: ");

                    int equipeId = scanner.nextInt();
                    scanner.nextLine();

                    relatorioService.gerarRelatorioEquipe(equipeId);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
    // Método para gerar relatório de um projeto baseado no ID e relatar ele em um arquivo txt e salvar na pasta "relatorios"
    public void gerarRelatorioProjeto(int projetoId) {

        ProjetoDAO projetoDAO = new ProjetoDAO();
        Projeto projeto = projetoDAO.buscarPorId(projetoId);

        if(projeto == null) {
            System.out.println("Projeto não encontrado.");
            return;
        }

        List<Tarefa> tarefas = projetoDAO.buscarTarefasProjeto(projetoId);

        File pasta = new File("relatorios");

        if(!pasta.exists()) {
            pasta.mkdir();
        }

        LocalDateTime agora = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String dataHora = agora.format(formatter);

        String nomeProjeto = projeto.getNomeProjeto().replaceAll("[^a-zA-Z0-9_-]", "_");

        String nomeArquivo = "relatorios/Projeto_" + nomeProjeto + "_" + dataHora + ".txt";

        if(projeto == null) {
            System.out.println("Projeto não encontrado.");
            return;
        }

        int total = projetoDAO.contarTarefasProjeto(projetoId);
        int pendentes = projetoDAO.contarTarefasPorStatus(projetoId, "PENDENTE");
        int andamento = projetoDAO.contarTarefasPorStatus(projetoId, "EM_ANDAMENTO");
        int concluidas = projetoDAO.contarTarefasPorStatus(projetoId, "CONCLUIDA");
        int atrasadas = projetoDAO.contarTarefasPorStatus(projetoId, "ATRASADA");

        double percentual = 0;

        if(total > 0) {
            percentual = ((double) concluidas / total) * 100;
        }

        try {

            FileWriter writer = new FileWriter(nomeArquivo);

            writer.write("===== RELATÓRIO DE PROJETO =====\n\n");
            writer.write("Projeto: " + projeto.getNomeProjeto() + "\n");

            if(projeto.getEquipe() != null) {
                writer.write("Equipe: " + projeto.getEquipe().getNomeEquipe() + "\n");
            }

            writer.write("Data Início: " + projeto.getDataInicio() + "\n");
            writer.write("Data Final: " + projeto.getDataFinal() + "\n\n");
            writer.write("Total de tarefas: " + total + "\n");
            writer.write("Pendentes: " + pendentes + "\n");
            writer.write("Em andamento: " + andamento + "\n");
            writer.write("Concluídas: " + concluidas + "\n");
            writer.write("Atrasadas: " + atrasadas + "\n");
            writer.write(String.format("Percentual concluído: %.2f%%\n\n", percentual));
            writer.write("===== TAREFAS =====\n\n");

            for(Tarefa t : tarefas) {
                writer.write("Tarefa: " + t.getNomeTarefa() + "\n");
                writer.write("Status: " + t.getStatus() + "\n");
                writer.write("Data início: " + t.getDataInicio() + "\n");
                writer.write("Data final: " + t.getDataFinal() + "\n\n");
            }

            writer.close();

            System.out.println("Relatório gerado com sucesso.");
            System.out.println("Relatório salvo em: " + new File(nomeArquivo).getAbsolutePath());

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    // Método para gerar relatório de uma equipe baseado no ID e relatar ele em um arquivo txt e salvar na pasta "relatorios"
    public void gerarRelatorioEquipe(int equipeId) {

        EquipeDAO equipeDAO = new EquipeDAO();
        Equipe equipe = equipeDAO.buscarPorId(equipeId);

        if(equipe == null) {
            System.out.println("Equipe não encontrada.");
            return;
        }

        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String dataHora = agora.format(formatter);

        String nomeEquipe = equipe.getNomeEquipe().replaceAll("[^a-zA-Z0-9_-]", "_");

        String nomeArquivo = "relatorios/Equipe_" + nomeEquipe + "_" + dataHora + ".txt";

        if(equipe == null) {
            System.out.println("Equipe não encontrada.");
            return;
        }

        List<Projeto> projetos = equipeDAO.buscarProjetosEquipe(equipeId);

        try {

            FileWriter writer = new FileWriter(nomeArquivo);

            writer.write("===== RELATÓRIO DE EQUIPE =====\n\n");
            writer.write("Equipe: " + equipe.getNomeEquipe() + "\n\n");
            writer.write("Projetos vinculados: " + projetos.size() + "\n\n");

            for(Projeto p : projetos) {
                writer.write("Projeto: " + p.getNomeProjeto() + "\n");
                writer.write("Início: " + p.getDataInicio() + "\n");
                writer.write("Final: " + p.getDataFinal() + "\n\n");
            }

            writer.close();

            System.out.println("Relatório gerado com sucesso.");
            System.out.println("Relatório salvo em: " + new File(nomeArquivo).getAbsolutePath());

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
