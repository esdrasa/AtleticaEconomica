/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelaDispersao;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Familia
 */
public class Hashtable implements Serializable{
    private final int compartimentos;
    private final ArrayList<Item> tabela[];
    
    public Hashtable(int compartimentos){
        this.compartimentos = compartimentos;
        this.tabela = new ArrayList[compartimentos];
        for(int i = 0; i < compartimentos; i++){
            tabela[i] = new ArrayList<Item>();
        }   
    }
    
    private int dispersao(long matricula){
        return (int) (matricula % this.compartimentos);
    }
    
    public boolean inserir(Item item){
        int dispersao = this.dispersao(item.getMatricula());
        
        for (Item i : tabela[dispersao]) {
            if(i.getMatricula() == item.getMatricula()){
                return false;
            }
        }
        
        tabela[dispersao].add(item);
        return true;
    }
    
    public Item remover(long matricula){
        int dispersao = this.dispersao(matricula);
        
        int i = 0;
        for (Item item : tabela[dispersao]) {
            if(item.getMatricula() == matricula){
                tabela[dispersao].remove(i);
                return item;
            }
            i++;
        }
        return null;
    }
    
    public Item buscar(long matricula){
        int dispersao = this.dispersao(matricula);
        
        for (Item i : tabela[dispersao]) {
            if(i.getMatricula() == matricula){
                return i;
            }
        }
        
        return null;
    }
    
    public void mostrarCompartimento(int compartimento){
        System.out.print("Elementos: ");
        for (Item i : tabela[compartimento]) {
            System.out.print(i.getMatricula() + " ");
        }
        System.out.println("");
    }
}

