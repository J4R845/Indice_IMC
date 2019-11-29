package com.delarue.indiceimc;

import android.content.Intent;
import android.os.Bundle;

import com.delarue.indiceimc.dao.ImcDao;
import com.delarue.indiceimc.model.Imc;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DadosImc extends AppCompatActivity {

    ListView listVisivel;
    Imc imc;
    ImcDao imcDao;
    ArrayList<Imc> arrayListImc;
    ArrayAdapter<Imc> arrayAdapterImc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_imc);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listVisivel = findViewById(R.id.list_imc);
        registerForContextMenu(listVisivel);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(DadosImc.this, MainActivity.class);

                startActivity(i);

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
            }
        });

        listVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Imc imcEnviado = arrayAdapterImc.getItem(position);
                Intent i = new Intent(DadosImc.this, MainActivity.class);
                i.putExtra("imc-enviado", imcEnviado);


                startActivity(i);

            }
        });

        listVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                imc = arrayAdapterImc.getItem(position);

                return false;
            }
        });

    }


    public void populaLista(){

        imcDao = new ImcDao(DadosImc.this);

        arrayListImc = imcDao.selectAllImc();

        imcDao.close();

        if (listVisivel != null){

            arrayAdapterImc = new ArrayAdapter<Imc>(DadosImc.this,
                    android.R.layout.simple_list_item_1,arrayListImc);
            listVisivel.setAdapter(arrayAdapterImc);
        }

    }

    protected void onResume() {
        super.onResume();
        populaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem mDelete = menu.add("Excluir Estes Dados?");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                long retornoDB;

                imcDao = new ImcDao(DadosImc.this);

                retornoDB = imcDao.excluirImc(imc);

                imcDao.close();

                if (retornoDB == -1){

                    alert("Erro Durante A Exclusão");
                }else {
                    alert("O Registro Foi Excluido!");
                }

                populaLista();

                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void alert (String s){

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();


    }

}


