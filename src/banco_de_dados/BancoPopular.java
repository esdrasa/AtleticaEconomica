/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco_de_dados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        
        System.out.println("Montando tabela de dispersão");
        while(arquibin.getFilePointer() < arquibin.length()){
            Item it = new Item(arquibin.readLong(), arquibin.getFilePointer() - 8);
            hash.inserir(it);
            arquibin.skipBytes(768);
        }
        System.out.println("Tabela dispersão montada");
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
    
    public boolean inserir(Aluno aluno) throws IOException{
        System.out.println("T1");
       Item item = hash.buscar(aluno.getMatricula());
       System.out.println("T2");
        if(item == null){
            System.out.println("T3");
            arquibin.seek(arquibin.length());
            System.out.println("T4");
            arquibin.writeAluno(aluno);
            return true;
        } else{
            System.out.println("T3.1");
            return false;
        } 
    }
    
    public boolean remover(long matricula) throws IOException{
        Item item = hash.buscar(matricula);
        if(item != null){
            arquibin.seek(arquibin.length() - 776);         
            Aluno aluno = arquibin.readAluno();
            
            arquibin.seek(item.getPosicao());
            arquibin.writeAluno(aluno);
            
            item = hash.buscar(aluno.getMatricula());
            item.setPosicao(arquibin.getFilePointer() - 776);
            
            return true;
        }else{
            return false;
        }
    }
}
