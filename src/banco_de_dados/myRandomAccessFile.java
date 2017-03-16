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
        while(aluno.getNome().length() < 60){
            aluno.setNome(aluno.getNome() + '\0');
        }
        
        while(aluno.getRh().length() < 3){
            aluno.setRh(aluno.getRh() + '\0');
        }
        
        while(aluno.getAlergia().length() < 60){
            aluno.setAlergia(aluno.getAlergia() + '\0');
        }
        
        while(aluno.getMedicacao().length() < 50){
            aluno.setMedicacao(aluno.getMedicacao() + '\0');
        }
        
        while(aluno.getCelular().length() < 9){
            aluno.setCelular(aluno.getCelular() + '\0');
        }
        
        while(aluno.getEmail().length() < 60){
            aluno.setEmail(aluno.getEmail() + '\0');
        }
        
        while(aluno.getEndereco().length() < 60){
            aluno.setEndereco(aluno.getEndereco() + '\0');
        }
        
        while(aluno.getEmergencia().length() < 60){
            aluno.setEmergencia(aluno.getEmergencia() + '\0');
        }
        
        this.seek(this.length());
        this.writeLong(aluno.getMatricula());
        this.writeLong(aluno.getCpf());
        this.writeShort(aluno.getSexo());
        this.writeShort(aluno.getVinculo());
        this.writeChars(aluno.getNome());
        this.writeChars(aluno.getNascimento());
        this.writeChars(aluno.getRh());
        this.writeChars(aluno.getAlergia());
        this.writeChars(aluno.getMedicacao());
        this.writeChars(aluno.getCelular());
        this.writeChars(aluno.getTelefone());
        this.writeChars(aluno.getEmail());
        this.writeChars(aluno.getEndereco());
        this.writeChars(aluno.getEmergencia());
    }
}