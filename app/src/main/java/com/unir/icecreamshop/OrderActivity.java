package com.unir.icecreamshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OrderActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private TextView textView;
    private int sabores[] = {
            R.drawable.chocolate,
            R.drawable.cream,
            R.drawable.strawberry
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        frameLayout = findViewById(R.id.frameLayout);

        Intent intent = getIntent();
        String tipo = intent.getStringExtra("tipo");
        int qtd = intent.getIntExtra("qtd", 0);
        String sabor = intent.getStringExtra("sabor");
        float total = 0;
        if(tipo.equalsIgnoreCase("Casquinha")){
            total = 5;
            addFlavor(R.drawable.casquinha, frameLayout, 0);
        }else{
            total = 8;
            addFlavor(R.drawable.cascao, frameLayout, 0);
        }
        total = total*qtd;

        textView = findViewById(R.id.textPedido);
        textView.setText("Tipo: " + tipo + "\nSabor: " + sabor + "\nQuantidade: " + qtd +
                "\nTotal: " + total + " reais");

        if(sabor.equalsIgnoreCase("Chocolate")){
            addFlavor(R.drawable.chocolate, frameLayout, 1);
        }else if(sabor.equalsIgnoreCase("Creme")){
            addFlavor(R.drawable.cream, frameLayout, 1);
        }else{
            addFlavor(R.drawable.strawberry, frameLayout, 1);
        }

        Button btnVoltar = findViewById(R.id.button);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addFlavor(int img, FrameLayout frameLayout, int level){
        ImageView flavor = new ImageView(this);

        flavor.setImageResource(img);
        flavor.setScaleType(ImageView.ScaleType.FIT_XY);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(px(100), px(100));
        params.gravity = Gravity.BOTTOM;
        params.bottomMargin = px(50) * level;
        frameLayout.addView(flavor, params);
    }

    private int px(int dp){
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }
}