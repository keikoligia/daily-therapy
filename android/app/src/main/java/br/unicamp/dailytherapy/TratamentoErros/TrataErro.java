package br.unicamp.dailytherapy.TratamentoErros;

public class TrataErro {
    private int codigo;
    private String error;

    public int getCodigo(){
        return this.codigo;
    }

    public void setCodigo(int cod){
        this.codigo = cod;
    }

    public String getError(){
        return this.error;
    }

    public void setError(String msg){
        this.error = msg;
    }
}
