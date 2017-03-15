/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco_de_dados;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        
        if (arquivo.exists()){
            System.out.println("O arquivo existe");
            FileInputStream in = new FileInputStream("dados/hashSerial");
            ObjectInputStream objIn = new ObjectInputStream(in);
            
            System.out.println("Carregando Hash");
            hash = (Hashtable) objIn.readObject();
            System.out.println("Carreguei Hash");
            
        }else{  //Carregar a hash
            hash = new Hashtable(60);       //Tem que iniciar a hash aqui 
        }
        
        arquibin = new myRandomAccessFile(arquivo, "rw");
    }
    
    public Aluno buscar(int matricula) throws IOException{
        Item item = hash.buscar(matricula);
        if(item != null){
            arquibin.seek(item.getPosicao());
            return arquibin.lerAluno();
        } else{
            return null;
        }
    }
    
    public boolean inserir(Aluno aluno) throws IOException{
       Item item = hash.buscar(aluno.getMatricula());
        if(item == null){
            arquibin.seek(arquibin.length());
            arquibin.writeAluno(aluno);
            return true;
        } else{
            return false;
        } 
    }
}
