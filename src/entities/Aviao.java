package entities;

import java.time.LocalTime;
import java.util.Date;

public class Aviao {
    public String nome;
    public boolean decolar;
    public Date data_nascimento;
    public Date data_fim;
    
    Aviao(String nome, boolean decolar, Date data_nascimento, Date data_fim) {
        this.nome = nome; 
        this.decolar = decolar;
        this.data_nascimento = data_nascimento;
        this.data_fim = data_fim;
    }
}
