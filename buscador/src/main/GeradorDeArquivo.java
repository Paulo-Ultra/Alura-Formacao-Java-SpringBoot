package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import record.Endereco;

import java.io.FileWriter;
import java.io.IOException;

public class GeradorDeArquivo {

    public void salvaJson(Endereco endereco) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        FileWriter writer = new FileWriter(endereco.cep() + ".json");
        writer.write(gson.toJson(endereco));
        writer.close();
    }
}
