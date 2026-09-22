package com.example.ejerciciodialogopersonalizado;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejerciciodialogopersonalizado.bbdd.DBHelper;
import com.example.ejerciciodialogopersonalizado.model.Personaje;

public class CrearActivity extends AppCompatActivity {

    private EditText etNombre, etTipo, etFotoUrl;
    private Button btnNuevoPersonaje;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);

        etNombre = findViewById(R.id.etNombre);
        etTipo = findViewById(R.id.etTipo);
        etFotoUrl = findViewById(R.id.etFotoUrl);
        btnNuevoPersonaje = findViewById(R.id.btnNuevoPersonaje);

        btnNuevoPersonaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPersonaje();
            }
        });
    }

    private void guardarPersonaje() {
        String nombre = etNombre.getText().toString().trim();
        String tipo = etTipo.getText().toString().trim();
        String fotoUrl = etFotoUrl.getText().toString().trim();

        if (nombre.isEmpty() || tipo.isEmpty() || fotoUrl.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Personaje nuevoPersonaje = new Personaje(nombre, tipo, fotoUrl);
        long id = dbHelper.insertarPersonaje(nuevoPersonaje);

        if (id != -1) {
            Toast.makeText(this, "Personaje creado con éxito", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad y vuelve a la anterior
        } else {
            Toast.makeText(this, "Error al crear el personaje", Toast.LENGTH_SHORT).show();
        }
    }
}