package com.unir.icecreamshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private RadioGroup radioGroup;
    private EditText editQtd;
    private Button btnFinalizar;
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
        radioGroup = findViewById(R.id.radioGroup);
        editQtd = findViewById(R.id.editTextNumberQtd);
        btnFinalizar = findViewById(R.id.buttonFinalizar);

        String opcoes[] = {
                "Casquinha", "Cascão"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editQtd.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Informe a quantidade!", Toast.LENGTH_SHORT).show();
                }else{
                    int qtd = Integer.parseInt(editQtd.getText().toString());
                    String tipo = spinner.getSelectedItem().toString();

                    int selected = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(selected);
                    String sabor = radioButton.getText().toString();

                    AlertDialog.Builder janela = new AlertDialog.Builder(MainActivity.this);
                    janela.setTitle("Finalizar pedido");
                    janela.setMessage("Deseja finalizar o pedido?");
                    janela.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                            intent.putExtra("tipo", tipo);
                            intent.putExtra("sabor", sabor);
                            intent.putExtra("qtd", qtd);

                            startActivity(intent);
                        }
                    });
                    janela.show();
                }
            }
        });
    }
}