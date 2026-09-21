package com.example.ejerciciodialogopersonalizado;
//
//FOto nombre, tipo,
//tres botones agua , electrico, fuego y cargarlo desde la base de datos
//boton administrador que lleva a una ventana de crear
//sobre clcick donde los personajes long click lleva nuevas activitys este caso Modificar
//y doble click eliminar

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    private Button btnAdministrador;
    private ListView lvPersonajes;

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
    btnAdministrador = findViewById(R.id.btnAdministrador);
    btnAdministrador.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CrearActivity.class);
            startActivity(intent);
        }
    });

    lvPersonajes = findViewById(R.id.lvPersonajes);
    lvPersonajes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(), ModificarActivity.class);
            startActivity(intent);
            return true;
        }
    });

    }

}