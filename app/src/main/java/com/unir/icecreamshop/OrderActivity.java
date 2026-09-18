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
    private String toppings[] = {
            "Nenhum",
            "Cereja",
            "Granulado de chocolate",
            "Granulado colorido"
    };
    private int toppingsImg[] = {
            R.drawable.nullimg,
            R.drawable.cherry,
            R.drawable.chocolate_sprinkle,
            R.drawable.rainbow_sprinkle
    };
    private String syrups[] = {
            "Nenhuma",
            "Chocolate",
            "Leite condensado",
            "Morango"
    };
    private int syrupsImg[] = {
            R.drawable.nullimg,
            R.drawable.chocolate_syrup,
            R.drawable.condensed_milk_syrup,
            R.drawable.strawberry_syrup
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
        int topping = intent.getIntExtra("topping", 0);
        int syrup = intent.getIntExtra("syrup", 0);
        ArrayList<Integer> flavors = intent.getIntegerArrayListExtra("sabores");
        float total = 0;

        // preço e desenho do cone escolhido
        if(tipo == 0){
            total = 5;
            addCone(R.drawable.cone, frameLayout);
        }else{
            total = 8;
            addCone(R.drawable.bigcone, frameLayout);
        }
        total = total * qtd;

        // Relatório do pedido
        textView = findViewById(R.id.textPedido);
        String text = "";
        text = text.concat("Tipo: " + opcoes[tipo]);
        text = text.concat("\nSabor(es): ");
        for(int flavor : flavors){
            text = text.concat(sabores[flavor] + " ");
        }
        text = text.concat("\nAcompanhamento: " + toppings[topping]);
        text = text.concat("\nCobertura: " + syrups[syrup]);
        text = text.concat("\nQuantidade: " + qtd);
        text = text.concat("\nTotal: R$ " + total);
        textView.setText(text);

        int levelmax = flavors.size()-1;
        // adiciona as bolas de sorvete
        int i = 0;
        for(int flavor : flavors){
            addFlavor(saboresImg[flavor], frameLayout, i);
            i++;
        }
        if(tipo == 1 && flavors.size() < 2){
            levelmax++;
            addFlavor(saboresImg[flavors.get(0)], frameLayout, 1);
        }

        // adiciona a cobertura
        addFlavor(syrupsImg[syrup], frameLayout, levelmax);

        // adiciona o acompanhamento
        addFlavor(toppingsImg[topping], frameLayout, levelmax);

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
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(px(150), px(150));
        params.gravity = Gravity.BOTTOM;
        params.bottomMargin = px(90) * level + px(180);
        frameLayout.addView(flavor, params);
    }

    private void addCone(int img, FrameLayout frameLayout){
        ImageView flavor = new ImageView(this);

        flavor.setImageResource(img);
        flavor.setScaleType(ImageView.ScaleType.FIT_XY);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(px(130), px(220));
        params.gravity = Gravity.BOTTOM;
        params.bottomMargin = px(2);
        params.leftMargin = px(15);
        frameLayout.addView(flavor, params);
    }

    private int px(int dp){
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }
}