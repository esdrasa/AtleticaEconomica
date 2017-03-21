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
        aluno.setDoença(this.readString(50));

        return aluno;
    }
    
                /*Pesa 876 bytes*/
    public void writeAluno(Aluno aluno) throws IOException{
        System.out.println("T5");
        while(aluno.getNome(false).length() < 60){
            aluno.setNome(aluno.getNome(false) + '\0');
        }
        System.out.println("T5.1");
        while(aluno.getRh(false).length() < 3){
            aluno.setRh(aluno.getRh(false) + '\0');
        }
        System.out.println("T5;2");
        while(aluno.getAlergia(false).length() < 60){
            aluno.setAlergia(aluno.getAlergia(false) + '\0');
        }
        System.out.println("T5.3");
        while(aluno.getMedicacao(false).length() < 50){
            aluno.setMedicacao(aluno.getMedicacao(false) + '\0');
        }
        System.out.println("T5.4");
        while(aluno.getCelular(false).length() < 9){
            aluno.setCelular(aluno.getCelular(false) + '\0');
        }
        System.out.println("T5.5");
        while(aluno.getTelefone(false).length() < 8){
            aluno.setTelefone(aluno.getTelefone(false) + '\0');
        }
        System.out.println("T5.6");
        while(aluno.getNascimento(false).length() < 8){
            aluno.setNascimento(aluno.getNascimento(false) + '\0');
        }
        System.out.println("T5.7");
        while(aluno.getEmail(false).length() < 60){
            aluno.setEmail(aluno.getEmail(false) + '\0');
        }
        System.out.println("T5.8");
        while(aluno.getEndereco(false).length() < 60){
            aluno.setEndereco(aluno.getEndereco(false) + '\0');
        }
        System.out.println("T5.9");
        while(aluno.getEmergencia(false).length() < 60){
            aluno.setEmergencia(aluno.getEmergencia(false) + '\0');
        }
        
        while(aluno.getDoença(false).length() < 50){
            aluno.setDoença(aluno.getDoença(false) + '\0');
        }
        
        System.out.println("T6");
        
        this.writeLong(aluno.getMatricula());
        this.writeLong(aluno.getCpf());
        this.writeShort(aluno.getSexo());
        this.writeShort(aluno.getVinculo());
        
        System.out.println("Escrevendo nome: " + aluno.getNome(false));
        
        
        this.writeChars(aluno.getNome(false));
        
        this.seek(this.getFilePointer() - 120);
        System.out.println("Nome escrito: " + this.readString(60));
        
        
        this.writeChars(aluno.getNascimento(false));
        this.writeChars(aluno.getRh(false));
        this.writeChars(aluno.getAlergia(false));
        this.writeChars(aluno.getMedicacao(false));
        this.writeChars(aluno.getCelular(false));
        this.writeChars(aluno.getTelefone(false));
        this.writeChars(aluno.getEmail(false));
        this.writeChars(aluno.getEndereco(false));
        this.writeChars(aluno.getEmergencia(false));
        this.writeChars(aluno.getDoença(false));
        System.out.println("T9");
    }
}