package br.unicamp.dailytherapy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DadosTratamento extends AppCompatActivity
{
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

    public void gerarPdf()
    {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo detalhes =
                new PdfDocument.PageInfo.Builder(500, 600, 1).create();

        PdfDocument.Page newPage = document.startPage(detalhes);
        Canvas canvas = newPage.getCanvas();
        Paint corTexto = new Paint();
        corTexto.setColor(Color.BLACK);

        canvas.drawText("Resumo do tratamento", 105, 100, corTexto);

        document.finishPage(newPage);

        File filePath = new File(Environment.getExternalStorageDirectory() + "/Download", "resumo.pdf");

        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("Erro 1");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erro 2");
            e.printStackTrace();
        }

        document.close();
    }
}
