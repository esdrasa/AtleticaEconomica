/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco_de_dados;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Familia
 */
public class Teste {
    public static void main(String args[]) throws IOException, FileNotFoundException, ClassNotFoundException{
        BancoPopular bd = new BancoPopular("database");
    }
}
