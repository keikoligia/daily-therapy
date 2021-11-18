package br.unicamp.dailytherapy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;
import br.unicamp.dailytherapy.*;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Chat extends AppCompatActivity {

    RadioGroup rgbMensagem;
    RadioButton rbRemedio, rbIngerir, rbHorario, rbPersonalizado;
    Button btnEnviar;
    String sEmail, sPassword;
    String nomeCtt, emailCtt;
    TextView tvContato;
    EditText edtMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        rgbMensagem = (RadioGroup) findViewById(R.id.rgbMensagem);
        rbRemedio = (RadioButton) findViewById(R.id.rbRemedio);
        rbIngerir = (RadioButton) findViewById(R.id.rbIngerir);
        rbPersonalizado = (RadioButton) findViewById(R.id.rbPersonalizado);
        tvContato = (TextView) findViewById(R.id.tvContato);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        edtMensagem = (EditText) findViewById(R.id.edtMensagem);
        
        br.unicamp.dailytherapy.Session session = new br.unicamp.dailytherapy.Session(Chat.this);

        sEmail = session.getEmail();;
        sPassword = session.getSenha();
        nomeCtt = session.getCttNome();
        emailCtt = session.getEmailCtt();

        tvContato.setText(nomeCtt);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Properties properties = new Properties();
                properties.put("mail.smtp.auth","true");
                properties.put("mail.smtp.starttls.enable","true");
                properties.put("mail.smtp.host","smtp@gmail.com");
                properties.put("mail.smtp.port","465");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sEmail, sPassword);
                    }
                });
                try
                {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(sEmail));
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(emailCtt));
                    message.setSubject("Seu paciente precisa de ajuda!");
                    message.setText("Olá, " + nomeCtt + "\n Seu paciente precisa de ajuda, esta é a mensagem dele: " + getMensagem());

                    new SendMail().execute(message);
                }
                catch (AddressException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getMensagem()
    {
        String mensagem = "";
        if(rbRemedio.isChecked())
            mensagem = "Necessito de ajuda com os medicamentos";
        else if(rbIngerir.isChecked())
            mensagem = "Ingeri o medicamento errado";
        else if(rbHorario.isChecked())
            mensagem = "Esqueci o horário do medicamento";
        else if(rbPersonalizado.isChecked())
            mensagem = edtMensagem.getText().toString();

        return mensagem;
    }

    private class SendMail extends AsyncTask<Message, String, String>
    {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Chat.this,
                    "Por favor espere", "Enviando email...", true, false);

        }
        @Override
        protected String doInBackground(Message... messages)
        {
            try
            {
                Transport.send(messages[0]);
                return "Email enviado com sucesso!";
            }
            catch (MessagingException e)
            {
                e.printStackTrace();
                return "Email não enviado.";
            }
        }
        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s.equals("Sucess"))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Chat.this) ;
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#509324'>Sucess</font>"));
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            } else {
                /*Toast.makeText(getApplicationContext(),
                        "Alguma coisa deu errado",
                        Toast.LENGTH_SHORT).show();*/
            }
        }
    }
}