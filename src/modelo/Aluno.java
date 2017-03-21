
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Familia
 */
public class Aluno {
    
    private String nome; 
    private long matricula; 
    private long cpf;
    private short sexo;       /*0 = masculino 1 = Feminino*/ 
    private String nascimento; 
    private String rh; 
    private String alergia; 
    private String doença; 
    private String medicacao;
    private String celular; 
    private String telefone; 
    private String email; 
    private String endereco; 
    private short  vinculo;   /* 0 = ASSOCIADO ; 1 = ATLETA; 2 = ATLETA E ASSOCIADO */
    private String emergencia;

    private String ajuste(String s){/*Retorna a string sem os \0*/
        String result = "";
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '\0'){
                break;
            }
            result = result + s.charAt(i);
        }
        return result;
    }
    
    public String getNome(boolean ajuste) {
        return (ajuste) ? ajuste(nome) : nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public String getNascimento(boolean ajuste) {
        return (ajuste) ? ajuste(nascimento) : nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getRh(boolean ajuste) {
        return (ajuste) ? ajuste(rh) : rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getAlergia(boolean ajuste) {
        return (ajuste) ? ajuste(alergia) : alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public String getDoença(boolean ajuste) {
        return (ajuste) ? ajuste(doença) : doença;
    }

    public void setDoença(String doença) {
        this.doença = doença;
    }

    public String getMedicacao(boolean ajuste) {
        return (ajuste) ? ajuste(medicacao) : medicacao;
    }

    public void setMedicacao(String medicacao) {
        this.medicacao = medicacao;
    }

    public String getCelular(boolean ajuste) {
        return (ajuste) ? ajuste(celular) : celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefone(boolean ajuste) {
        return (ajuste) ? ajuste(telefone) : telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail(boolean ajuste) {
        return (ajuste) ? ajuste(email) : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco(boolean ajuste) {
        return (ajuste) ? ajuste(endereco) : endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmergencia(boolean ajuste) {
        return (ajuste) ? ajuste(emergencia) : emergencia;
    }

    public void setEmergencia(String emergencia) {
        this.emergencia = emergencia;
    }

    public short getSexo() {
        return sexo;
    }

    public void setSexo(short sexo) {
        this.sexo = sexo;
    }

    public short getVinculo() {
        return vinculo;
    }

    public void setVinculo(short vinculo) {
        this.vinculo = vinculo;
    }
    
    public void exibirImportante(){
        System.out.println("Nome: " + this.getNome(true) + "\n"
                + "Matricula: " + this.getMatricula() + "\n"
                + "CPF: " + this.getCpf());
    }
    
}