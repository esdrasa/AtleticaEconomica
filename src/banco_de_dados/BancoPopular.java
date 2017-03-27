/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco_de_dados;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import modelo.Aluno;
import tabelaDispersao.Hashtable;
import tabelaDispersao.Item;

/**
 *
 * @author Familia
 */
public class BancoPopular {
    private File arquivo;
    public Hashtable hash;
    private myRandomAccessFile arquibin;
    
    public BancoPopular(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
        arquivo = new File(fileName);
        hash = new Hashtable(60);
        arquibin = new myRandomAccessFile(arquivo, "rw");
        
        while(arquibin.getFilePointer() < arquibin.length()){
            Item it = new Item(arquibin.readLong(), arquibin.getFilePointer() - 8);
            hash.inserir(it);
            arquibin.skipBytes(868);
        }
    }
    
    public Aluno buscar(long matricula) throws IOException{
        Item item = hash.buscar(matricula);
        if(item != null){
            arquibin.seek(item.getPosicao());
            return arquibin.readAluno();
        } else{
            return null;
        }
        
    }
    
    /*Retorna a posicao do aluno no arquivo*/
    public long buscarPosicao(long matricula) throws IOException{
        Item item = hash.buscar(matricula);
        if(item != null){
            return item.getPosicao();
        } else{
            return -1;
        }
    }
    
    public boolean inserir(Aluno aluno) throws IOException{
       System.out.println("T1");
       Item item = hash.buscar(aluno.getMatricula());
       System.out.println("T2");
        if(item == null){
            System.out.println("T3");
            arquibin.seek(arquibin.length());
            System.out.println("T4");
            arquibin.writeAluno(aluno);
            hash.inserir(new Item(aluno.getMatricula(), arquibin.length() -  876));
            return true;
        } else{
            System.out.println("T3.1");
            return false;
        } 
    }
    
    public void inserir(Aluno aluno, long posicao) throws IOException{
        arquibin.seek(posicao);
        arquibin.writeAluno(aluno);
    }
    
    public boolean remover(long matricula) throws IOException{
        Item item = hash.buscar(matricula);
        if(item != null){
            hash.remover(item.getMatricula());
            if(arquibin.length() == 876){
            /*So tem 1 elemento no arquivo*/
                arquibin.setLength(0);
            }else if(item.getPosicao() == arquibin.length() - 876){
            /*O elemento é o ultimo*/
                arquibin.setLength(arquibin.length() - 876);
            }else{
                arquibin.seek(arquibin.length() - 876);         
                Aluno aluno = arquibin.readAluno();

                arquibin.seek(item.getPosicao());
                arquibin.writeAluno(aluno);

                item = hash.buscar(aluno.getMatricula());
                item.setPosicao(arquibin.getFilePointer() - 876);
            }
            return true;
        }else{
            return false;
        }
    }
    
    public ArrayList<Aluno> getAlunos() throws IOException{
        ArrayList<Aluno> alunos = new ArrayList();
        Aluno aluno;
        
        arquibin.seek(0);
        while(arquibin.getFilePointer() < arquibin.length()){
            aluno = arquibin.readAluno();
            alunos.add(aluno);
        }
        
        return alunos;
    }
    
    public void gerarTxt() throws IOException{
        ArrayList<Aluno> alunos = this.getAlunos();
        
        File file = new File("texto");
        FileWriter fw = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fw);
        
        
        for(Aluno aluno: alunos){
            writer.write(aluno.getNome(true) + '\t');
            writer.write(Long.toString(aluno.getMatricula()) + '\t');
            String studentCPFNoFormat = Long.toString(aluno.getCpf());
            writer.write(studentCPFNoFormat.substring(0, 3)
                            +   "."
                            +   studentCPFNoFormat.substring(3, 6)
                            +   "."
                            +   studentCPFNoFormat.substring(6, 9)
                            +   "-"
                            +   studentCPFNoFormat.substring(9) + '\t');
            
            writer.write(aluno.getNascimento(true).substring(0, 2) 
                                   + "/" + aluno.getNascimento(true).substring(2, 4)
                                   + "/" + aluno.getNascimento(true).substring(4) + '\t');
            
            
            writer.newLine();
        }
        
        writer.close();
        fw.close();
    }
}
