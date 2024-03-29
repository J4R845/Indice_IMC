package com.delarue.indiceimc.model;

import java.io.Serializable;

public class Imc implements Serializable {

    private int id;
    private String datareg;
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

    public String getDatareg() {
        return datareg;
    }

    public void setDatareg(String datareg) {
        this.datareg = datareg;
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
        return   //id + "\n" +
                "Data: " + datareg  +"\n" +
                        "Peso: " + peso + " Anos"+"\n" +
                        "Alturar: " + altura +"\n" +
                        "Índice IMC: " + resultado +"\n" +
                        "Diginostico: " + diagnostico;
    }
}
