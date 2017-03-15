/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelaDispersao;

/**
 *
 * @author Familia
 */
public class Item{
    private long matricula;
    private long posicao;
    
    public Item(long matricula, long posicao){
        this.matricula = matricula;
        this.posicao = posicao;
    }
    
    public Item(){
        this.matricula = -1;
        this.posicao = -1;
    }

    /**
     * @return the matricula
     */
    public long getMatricula() {
        return matricula;
    }

    /**
     * @param matricula the matricula to set
     */
    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    /**
     * @return the posicao
     */
    public long getPosicao() {
        return posicao;
    }

    /**
     * @param posicao the posicao to set
     */
    public void setPosicao(long posicao) {
        this.posicao = posicao;
    }
    
}
