package com.delarue.indiceimc;

import android.content.Intent;
import android.os.Bundle;

import com.delarue.indiceimc.dao.ImcDao;
import com.delarue.indiceimc.model.Imc;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {


    private ViewHolder mViewHolder = new ViewHolder();

    float imcr;
    String mensagem = "";
    boolean dadosValidados;
    boolean validadosCampos;

    float kilos;
    float tamanho;

    Imc imc, altImc;
    ImcDao imcDao;
    long retornoDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        altImc = (Imc) i.getSerializableExtra("imc-enviado");
        imc = new Imc();
        imcDao = new ImcDao(MainActivity.this);


        this.mViewHolder.editPeso = findViewById(R.id.editPeso);
        this.mViewHolder.editAltura = findViewById(R.id.editAltura);
        this.mViewHolder.txtResultado = findViewById(R.id.txtResultado);
        this.mViewHolder.txtDiagnostico = findViewById(R.id.txtDiagnostico);

        this.mViewHolder.fab = findViewById(R.id.fab);
        this.mViewHolder.btnVariavel = findViewById(R.id.btnVariavel);
        this.mViewHolder.btnCalcular = findViewById(R.id.btnCalcular);

        this.mViewHolder.editPeso.requestFocus();

        // Pegar a data do sistema Android formatada
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        // "date", varaaiavel date contem a data
        final String date = sdf.format(new Date());


        if (altImc != null) {

            this.mViewHolder.btnVariavel.setText(this.getString(R.string.texto_alterar));

            this.mViewHolder.editPeso.setText(altImc.getPeso());
            this.mViewHolder.editAltura.setText(altImc.getAltura());
            this.mViewHolder.txtResultado.setText(altImc.getResultado());
            this.mViewHolder.txtDiagnostico.setText(altImc.getDiagnostico());

            imc.setId(altImc.getId());

        } else {

            this.mViewHolder.btnVariavel.setText(this.getString(R.string.texto_salvar));

        }

        this.mViewHolder.btnVariavel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                try {

                    imc.setDatareg(date);
                    imc.setPeso(mViewHolder.editPeso.getText().toString());
                    imc.setAltura(mViewHolder.editAltura.getText().toString());
                    imc.setResultado(mViewHolder.txtResultado.getText().toString());
                    imc.setDiagnostico(mViewHolder.txtDiagnostico.getText().toString());


                    validadosCampos = validacaoCampos();

                    if (validadosCampos) {

                        // Log.i("CrudSQLite", "Testando App");


                        if (mViewHolder.btnVariavel.getText().toString().equals("Salvar")) {

                            retornoDB = imcDao.salvarImc(imc);
                            imcDao.close();

                            if (retornoDB == -1) {

                                alert("Erro Ao Cadastrar");
                            } else {

                                alert("Cadastro Realizado!");
                            }

                        } else {

                            retornoDB = imcDao.alterarImc(imc);
                            imcDao.close();

                            if (retornoDB == -1) {
                                alert("Erro Ao Atualizar Os Dados");

                            } else {

                                alert("Os Dados Foram Atualizados");
                            }

                        }

                        finish();

                    }


                } catch (Exception e) {


                }

            }

            private void alert(String s) {


                Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();

            }

        });


        this.mViewHolder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //  Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();


                Intent i = new Intent(MainActivity.this, DadosImc.class);

                startActivity(i);

                //Snackbar.make(view, "Os Dados Foram Salvos", Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_calcular) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void calculoImc(View view) {

        try {

            this.mViewHolder.txtResultado.setText("");
            this.mViewHolder.txtDiagnostico.setText("");

            dadosValidados = validarCampos();

            // Recuperar os valores digitados


            if (dadosValidados) {


                kilos = Float.parseFloat(this.mViewHolder.editPeso.getText().toString());
                tamanho = Float.parseFloat(this.mViewHolder.editAltura.getText().toString());


                imcr = kilos / (tamanho * tamanho);

                // Validação da IMC

                if (imcr < 17) {
                    mensagem = "Muito Abaixo Do Peso";

                } else if ((imcr >= 17) && (imcr < 18.5)) {

                    mensagem = "Abaixo Do Peso";

                } else if ((imcr >= 18.5) && (imcr < 25)) {

                    mensagem = "Peso Ideal";

                } else if ((imcr >= 25) && (imcr < 30)) {

                    mensagem = "Acima Do Peso";

                } else if ((imcr >= 30) && (imcr < 35)) {

                    mensagem = "Obesidade Grau I";

                } else if ((imcr >= 35) && (imcr < 40)) {

                    mensagem = "Obesidade Grau II";

                } else {

                    mensagem = "Obesidade Mórbida";

                }

                // Mostrar o resultado

                this.mViewHolder.txtResultado.setText(format("%.2f", imcr));
                this.mViewHolder.txtDiagnostico.setText(mensagem);


            }

        } catch (Exception e) {

        }

    }

    // Classe ViewHolder

    private static class ViewHolder {

        // Classe ViewHolder

        EditText editPeso;
        EditText editAltura;
        TextView txtResultado;
        TextView txtDiagnostico;
        Button btnVariavel;
        Button btnCalcular;
        FloatingActionButton fab;
    }


    public void calcularImcs(MenuItem item) {

        try {

            this.mViewHolder.txtResultado.setText("");
            this.mViewHolder.txtDiagnostico.setText("");

            dadosValidados = validarCampos();

            // Recuperar os valores digitados


            if (dadosValidados) {


                kilos = Float.parseFloat(this.mViewHolder.editPeso.getText().toString());
                tamanho = Float.parseFloat(this.mViewHolder.editAltura.getText().toString());


                imcr = kilos / (tamanho * tamanho);

                // Validação da IMC

                if (imcr < 17) {
                    mensagem = "Muito Abaixo Do Peso";

                } else if ((imcr >= 17) && (imcr < 18.5)) {

                    mensagem = "Abaixo Do Peso";

                } else if ((imcr >= 18.5) && (imcr < 25)) {

                    mensagem = "Peso Ideal";

                } else if ((imcr >= 25) && (imcr < 30)) {

                    mensagem = "Acima Do Peso";

                } else if ((imcr >= 30) && (imcr < 35)) {

                    mensagem = "Obesidade Grau I";

                } else if ((imcr >= 35) && (imcr < 40)) {

                    mensagem = "Obesidade Grau II";

                } else {

                    mensagem = "Obesidade Mórbida";

                }

                // Mostrar o resultado

                this.mViewHolder.txtResultado.setText(format("%.2f", imcr));
                this.mViewHolder.txtDiagnostico.setText(mensagem);


            }

        } catch (Exception e) {

        }

    }

    private boolean validarCampos() {

        boolean retorno = false;

        if (!TextUtils.isEmpty(this.mViewHolder.editPeso.getText().toString())) {
            retorno = true;
        } else {
            this.mViewHolder.editPeso.setError("Digite O Peso!");
            this.mViewHolder.editPeso.requestFocus();
        }

        if (!TextUtils.isEmpty(this.mViewHolder.editAltura.getText().toString())) {
            retorno = true;
        } else {
            this.mViewHolder.editAltura.setError("Digite A Altura!");
            this.mViewHolder.editAltura.requestFocus();
        }

        return retorno;
    }

    //Inicio Validação de Campos

    private boolean validacaoCampos() {

        boolean retorno = true;

        if (TextUtils.isEmpty(this.mViewHolder.editPeso.getText().toString())
                || this.mViewHolder.editPeso.getText().toString().trim().isEmpty()) {
            retorno = false;
            this.mViewHolder.editPeso.setError("Digite O Peso!");
            this.mViewHolder.editPeso.requestFocus();
        } else if (TextUtils.isEmpty(this.mViewHolder.editAltura.getText().toString())
                || this.mViewHolder.editAltura.getText().toString().trim().isEmpty()) {
            retorno = false;
            this.mViewHolder.editAltura.setError("Digite A Altura!");
            this.mViewHolder.editAltura.requestFocus();
        } else if (TextUtils.isEmpty(this.mViewHolder.txtResultado.getText().toString())
                || this.mViewHolder.txtResultado.getText().toString().trim().isEmpty()) {
            retorno = false;
            this.mViewHolder.txtResultado.setError("Calcule O Indice IMC!");

        } else if (TextUtils.isEmpty(this.mViewHolder.txtDiagnostico.getText().toString())
                || this.mViewHolder.txtDiagnostico.getText().toString().trim().isEmpty()) {
            retorno = false;
            this.mViewHolder.txtDiagnostico.setError("Calcule O Indice IMC!");
        }

        return retorno;
    }

    //Fim Validação de Campos

}









