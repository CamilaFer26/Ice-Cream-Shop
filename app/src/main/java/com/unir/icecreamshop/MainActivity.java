package com.unir.icecreamshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private RadioGroup radioGroup;
    private EditText editQtd;
    private Button btnFinalizar;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private String opcoes[] = {
            "Casquinha", "Cascão"
    };
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinner = findViewById(R.id.spinnerTipos);
        editQtd = findViewById(R.id.editTextNumberQtd);
        btnFinalizar = findViewById(R.id.buttonFinalizar);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        info = findViewById(R.id.textInfo);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    info.setText("Escolha até 1 sabor!");
                }else{
                    info.setText("Escolha até 2 sabores!");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                info.setText("");
            }
        });

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editQtd.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Informe a quantidade!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(getSabores().isEmpty()){
                    Toast.makeText(MainActivity.this, "Selecione pelo menos 1 sabor!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int qtd = Integer.parseInt(editQtd.getText().toString());
                int tipo = spinner.getSelectedItemPosition();
                ArrayList<Integer> selected = getSabores();
                if(tipo == 0 && selected.size() > 1){
                    Toast.makeText(MainActivity.this, "Selecione no MÁXIMO 1 sabor!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tipo == 1 && selected.size() > 2){
                    Toast.makeText(MainActivity.this, "Selecione no MÁXIMO 2 sabores!", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder janela = new AlertDialog.Builder(MainActivity.this);
                janela.setTitle("Finalizar pedido");
                janela.setMessage("Deseja finalizar o pedido?");
                janela.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                        intent.putExtra("tipo", tipo);
                        intent.putExtra("sabores", selected);
                        intent.putExtra("qtd", qtd);

                        startActivity(intent);
                    }
                });
                janela.show();
            }
        });
    }

    private ArrayList getSabores(){
        // retorna os sabores selecionados
        ArrayList<Integer> flavors = new ArrayList<Integer>();

        if(checkBox1.isChecked()){
            flavors.add(0);
        }
        if(checkBox2.isChecked()){
            flavors.add(1);
        }
        if(checkBox3.isChecked()){
            flavors.add(2);
        }

        return flavors;
    };
}