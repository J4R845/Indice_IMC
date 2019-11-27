package com.delarue.indiceimc;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {


    private ViewHolder mViewHolder = new ViewHolder();

    float imc;
    String mensagem = "";
    boolean dadosValidados;

    float kilos;
    float tamanho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mViewHolder.editPeso = findViewById(R.id.editPeso);
        this.mViewHolder.editAltura = findViewById(R.id.editAltura);
        this.mViewHolder.txtResultado = findViewById(R.id.txtResultado);
        this.mViewHolder.txtDiagnostico = findViewById(R.id.txtDiagnostico);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Pegar a data do sistema Android formatada
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String date =sdf.format(new Date());
                // A varaaiavel date contem a data

                Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();

                Intent i = new Intent(MainActivity.this, DadosImc.class);

                startActivity(i);

               // Snackbar.make(view, "Deseja Arquivar Estes Dados?", Snackbar.LENGTH_LONG)
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_calcular) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static  class ViewHolder{

        EditText editPeso;
        EditText editAltura;
        TextView txtResultado;
        TextView txtDiagnostico;

    }

    public void salvarImc(MenuItem item) {

        try {

            this.mViewHolder.txtResultado.setText("");
            this.mViewHolder.txtDiagnostico.setText("");

            dadosValidados = validarCampos();

            // Recuperar os valores digitados


            if (dadosValidados) {


                kilos = Float.parseFloat(this.mViewHolder.editPeso.getText().toString());
                tamanho = Float.parseFloat(this.mViewHolder.editAltura.getText().toString());


                imc = kilos / (tamanho * tamanho);

                // Validação da IMC

                if (imc < 17) {
                    mensagem = "Muito Abaixo Do Peso";

                } else if ((imc >= 17) && (imc < 18.5)) {

                    mensagem = "Abaixo Do Peso";

                } else if ((imc >= 18.5) && (imc < 25)) {

                    mensagem = "Peso Ideal";

                } else if ((imc >= 25) && (imc < 30)) {

                    mensagem = "Acima Do Peso";

                } else if ((imc >= 30) && (imc < 35)) {

                    mensagem = "Obesidade Grau I";

                } else if ((imc >= 35) && (imc < 40)) {

                    mensagem = "Obesidade Grau II";

                } else {

                    mensagem = "Obesidade Mórbida";

                }

                // Mostrar o resultado

                this.mViewHolder.txtResultado.setText(format("%.2f", imc));
                this.mViewHolder.txtDiagnostico.setText(mensagem);


            }

        } catch (Exception e) {

        }

    }

        private boolean validarCampos(){


            boolean retorno = false;

            if(!TextUtils.isEmpty(this.mViewHolder.editPeso.getText().toString())) {
                retorno = true;
            }else{
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

    }









