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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private TextView textView;
    private int saboresImg[] = {
            R.drawable.chocolate,
            R.drawable.cream,
            R.drawable.strawberry
    };
    private String sabores[] = {
            "Chocolate",
            "Creme",
            "Morango"
    };
    private String opcoes[] = {
            "Casquinha", "Cascão"
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
        int tipo = intent.getIntExtra("tipo", 0);
        int qtd = intent.getIntExtra("qtd", 0);
        ArrayList<Integer> flavors = intent.getIntegerArrayListExtra("sabores");
        float total = 0;

        if(tipo == 0){
            total = 5;
            addCone(R.drawable.cone, frameLayout);
        }else{
            total = 8;
            addCone(R.drawable.bigcone, frameLayout);
        }
        total = total * qtd;

        textView = findViewById(R.id.textPedido);
        String text = "";
        text = text.concat("Tipo: " + opcoes[tipo]);
        text = text.concat("\nSabor(es): ");
        for(int flavor : flavors){
            text = text.concat(sabores[flavor] + " ");
        }
        text = text.concat("\nQuantidade: " + qtd);
        text = text.concat("\nTotal: R$ " + total);
        textView.setText(text);

        int i = 0;
        for(int flavor : flavors){
            addFlavor(saboresImg[flavor], frameLayout, i);
            i++;
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
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(px(150), px(130));
        params.gravity = Gravity.BOTTOM;
        params.bottomMargin = px(65) * level + px(142);
        frameLayout.addView(flavor, params);
    }

    private void addCone(int img, FrameLayout frameLayout){
        ImageView flavor = new ImageView(this);

        flavor.setImageResource(img);
        flavor.setScaleType(ImageView.ScaleType.FIT_XY);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(px(150), px(150));
        params.gravity = Gravity.BOTTOM;
        params.bottomMargin = px(2);
        frameLayout.addView(flavor, params);
    }

    private int px(int dp){
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }
}