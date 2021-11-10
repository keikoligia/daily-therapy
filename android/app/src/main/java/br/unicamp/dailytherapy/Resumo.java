package br.unicamp.dailytherapy;

import com.google.gson.annotations.SerializedName;
import androidx.appcompat.app.AppCompatActivity;



public class Resumo extends AppCompatActivity {

    @SerializedName("conteudoPdf")
    private String conteduoPdf;
    @SerializedName("idUsuario")
    private String idUsuario;
    @SerializedName("idRemedio")
    private String idRemedio;

    public String getConteduoPdf() { return conteduoPdf; }

    public void setConteduoPdf(String conteduoPdf) { this.conteduoPdf = conteduoPdf; }

    public String getIdUsuario() { return idUsuario; }

    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public String getIdRemedio() { return idRemedio; }

    public void setIdRemedio(String idRemedio) { this.idRemedio = idRemedio; }

    public void gerarPdf() {

        // cria um documento .pdf
        PdfDocument document = new PdfDocument();

        document.writeText("Resumo do Tratamento", 150, 50);
        document.writeText(getConteudoPdf());

        // write the document content
        document.writeTo(getOutputStream());

        document.save("resumo.pdf");
        document.close();
    }
 /*
    private void mostrarRemedio() {
        try
        {
            Remedio remedio = new Remedio(nomeRemedio, horario, frequencia, inicio, fim);

            Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
            Call<Remedio> call = service.mostrarRemedio(remedio);

            call.enqueue(new Callback<Remedio>() {
                @Override
                public void onResponse(Call<Remedio> call, Response<Remedio> response) {
                    if(response.isSuccessful())
                    {
                        Remedio remed = response.body();
                        Toast.makeText(Resumo.this, "REMEDIO" + remedio.getNomeRemedio() +
                                " " + remedio.getFrequencia() +
                                " " + remedio.getHorario() +
                                " " + remedio.getInicio(),
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        try
                        {
                            Gson gson = new Gson();
                            TrataErro erro = gson.fromJson(response.errorBody().string(), TrataErro.class);

                            Toast.makeText(Resumo.this, "toast1: "+erro.getError(), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception err)
                        {
                            Toast.makeText(Resumo.this, "toast2: "+err.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Remedio> call, Throwable t) {
                    Toast.makeText(Resumo.this, "Erro ao fazer cadastro", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Resumo.this, "toast3: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception err)
        {
            Toast.makeText(Resumo.this, "toast4: "+err.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
*/
}
