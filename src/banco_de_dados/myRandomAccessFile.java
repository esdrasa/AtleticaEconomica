/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco_de_dados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import modelo.Aluno;

/**
 *
 * @author Familia
 */
public class myRandomAccessFile extends RandomAccessFile{
    
    public myRandomAccessFile(File file, String mode) throws FileNotFoundException {
        super(file, mode);
    }
    
    public String readString(int size) throws IOException{
        String string = "";
        for(int i = 0; i < size; i++){
            string = string + this.readChar();
        }
        return string;
    }

    public Aluno readAluno() throws IOException {
        Aluno aluno = new Aluno();
        
        aluno.setMatricula(this.readLong());
        aluno.setCpf(this.readLong());
        aluno.setSexo(this.readShort());
        aluno.setVinculo(this.readShort());
        aluno.setNome(this.readString(60));
        aluno.setNascimento(this.readString(8));
        aluno.setRh(this.readString(3));
        aluno.setAlergia(this.readString(60));
        aluno.setMedicacao(this.readString(50));
        aluno.setCelular(this.readString(9));
        aluno.setTelefone(this.readString(8));
        aluno.setEmail(this.readString(60));
        aluno.setEndereco(this.readString(60));
        aluno.setEmergencia(this.readString(60));

        return aluno;
    }
    
                /*Pesa 776 bytes*/
    public void writeAluno(Aluno aluno) throws IOException{
        System.out.println("T5");
        while(aluno.getNome().length() < 60){
            aluno.setNome(aluno.getNome() + '\0');
        }
        System.out.println("T5.1");
        while(aluno.getRh().length() < 3){
            aluno.setRh(aluno.getRh() + '\0');
        }
        System.out.println("T5;2");
        while(aluno.getAlergia().length() < 60){
            aluno.setAlergia(aluno.getAlergia() + '\0');
        }
        System.out.println("T5.3");
        while(aluno.getMedicacao().length() < 50){
            aluno.setMedicacao(aluno.getMedicacao() + '\0');
        }
        System.out.println("T5.4");
        while(aluno.getCelular().length() < 9){
            aluno.setCelular(aluno.getCelular() + '\0');
        }
        System.out.println("T5.5");
        while(aluno.getTelefone().length() < 8){
            aluno.setTelefone(aluno.getCelular() + '\0');
        }
        System.out.println("T5.6");
        while(aluno.getNascimento().length() < 8){
            aluno.setNascimento(aluno.getNascimento() + '\0');
        }
        System.out.println("T5.7");
        while(aluno.getEmail().length() < 60){
            aluno.setEmail(aluno.getEmail() + '\0');
        }
        System.out.println("T5.8");
        while(aluno.getEndereco().length() < 60){
            aluno.setEndereco(aluno.getEndereco() + '\0');
        }
        System.out.println("T5.9");
        while(aluno.getEmergencia().length() < 60){
            aluno.setEmergencia(aluno.getEmergencia() + '\0');
        }
        System.out.println("T6");
        
        this.seek(this.length());
        this.writeLong(aluno.getMatricula());
        this.writeLong(aluno.getCpf());
        System.out.println("T7");
        this.writeShort(aluno.getSexo());
        this.writeShort(aluno.getVinculo());
        this.writeChars(aluno.getNome());
        this.writeChars(aluno.getNascimento());
        this.writeChars(aluno.getRh());
        this.writeChars(aluno.getAlergia());
        this.writeChars(aluno.getMedicacao());
        this.writeChars(aluno.getCelular());
        System.out.println("T8");
        this.writeChars(aluno.getTelefone());
        this.writeChars(aluno.getEmail());
        this.writeChars(aluno.getEndereco());
        this.writeChars(aluno.getEmergencia());
        System.out.println("T9");
    }
}