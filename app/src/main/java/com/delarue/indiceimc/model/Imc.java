package com.delarue.indiceimc.model;

public class Imc {

    private int id;
    private String data;
    private String peso;
    private String altura;
    private String resultado;
    private String diagnostico;

    // Construtor Vazio
    public Imc() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Override
    public String toString() {
        return "Imc{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", peso='" + peso + '\'' +
                ", altura='" + altura + '\'' +
                ", resultado='" + resultado + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                '}';
    }
}
