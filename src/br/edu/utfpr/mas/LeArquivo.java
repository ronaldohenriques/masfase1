/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.mas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ronaldo
 */
public class LeArquivo {

    private static final String ESPACO = " ";       //#define do c

    /**
     * Lê o arquivo e faz a transformações necessárias, armazenado-as no
     * stringbuilder
     *
     * @param nomearquivo Noem do arquivo a ser lido
     * @return stringbuilder já tudo OK.
     */
    public StringBuilder leitura(String nomearquivo) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(nomearquivo));
            String linha = br.readLine();
            int espaco = linha.indexOf(ESPACO);

            String nomeServidor = linha.substring(0, espaco);   //O nome deo servidor é sempre o mesmo para cada arquivo
            linha = linha.substring(espaco + 1);                //Daí pra frente
            espaco = linha.indexOf(ESPACO);                     //Para encontrar a data
            String dia = linha.substring(0, espaco);            // O dia é sempre o memso para cada arquivo
            dia = validadia(dia);                               //Para ter certeza de que é um dia
            String hora = linha.substring(espaco + 1);          //A hora muda frquentemente
            hora = validahora(hora);                            //Para ter certeza de que é um hora

            linha = br.readLine();                              //Le a nova linha

            while (linha != null) {
                if (!linha.startsWith("procs") && !linha.startsWith(" r")) {
                    if (linha.startsWith(nomeServidor)) {
                        hora = validahora(linha.substring(linha.lastIndexOf(ESPACO) + 1));      // PAra ter certeza de que realmente é uma hora
                    } else {
                        sb.append(nomeServidor).append(" ").append(dia).append(" ").append(hora).append(" ").append(linha).append("\n");
                    }
                }
                linha = br.readLine();  //Le a proxima linha
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LeArquivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LeArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.print(sb);
        return sb;
    }

    /**
     * Para verifiar se realmente é um data
     *
     * @param dia o dia lido do arquivo
     * @return String novamente, mas validada pois realmente é uma data Acho que
     * não precisa retransformar em String, mas ssim funcionou
     */
    private String validadia(String dia) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date resultado = null;
        try {
            resultado = df.parse(dia);      //valida a data e transforma o tipo para data
        } catch (ParseException ex) {
            Logger.getLogger(LeArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return df.format(resultado);            //Retorna a data já transformada em string novamente
    }

    /**
     * Para verificar se é uma hora
     *
     * @param hora a hora conforme lida no arquivo
     * @return String novamete, mas parseada, ou seja, é realmente uma hora Acho
     * que não precisa retransformar em String, mas assim funcionou
     */
    private String validahora(String hora) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date resultado = null;
        try {
            resultado = df.parse(hora);     //Converte para hora e a valida
        } catch (ParseException ex) {
            Logger.getLogger(LeArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return df.format(resultado);        //Reconverte para String e a retorna
    }
}
