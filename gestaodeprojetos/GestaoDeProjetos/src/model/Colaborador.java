package model;

public class Colaborador {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String usuario;
    private String senha;
    private Perfil perfil;

    public enum Perfil{
        ADMINISTRADOR, GERENTE, COLABORADOR
    }

    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

    public Colaborador(){

    }

    public int getId() { return id; }
    public void setId(int id){
        this.id = id;
    }
    public String getNome(){
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getCpf(){
        return this.cpf;
    }
    public void setCpf(String cpf){this.cpf = cpf;}
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getUsuario(){ return this.usuario; }
    public void setUsuario(String usuario){ this.usuario = usuario; }
    public String getSenha(){ return this.senha; }
    public void setSenha(String senha){ this.senha = senha; }

}
