package main;

import record.Endereco;

import java.io.IOException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner leitura = new Scanner(System.in);
        ConsultaCep consultaCep = new ConsultaCep();
        System.out.println("Digite um cep para consulta:");
        var cep = leitura.nextLine();

        try {
            Endereco endereco = consultaCep.buscaEndereco("72220108");
            System.out.println(endereco);
            GeradorDeArquivo geradorDeArquivo = new GeradorDeArquivo();
            geradorDeArquivo.salvaJson(endereco);
        } catch (RuntimeException | IOException e){
            System.out.println(e.getMessage());
        }
    }
}