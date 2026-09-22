package com.example.ejerciciodialogopersonalizado;

import android.content.Intent;
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

public class ModificarActivity extends AppCompatActivity {

    private EditText etNombre, etTipo, etFotoUrl;
    private Button btnUpdatePersonaje;
    private DBHelper dbHelper;
    private int personajeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modificar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);

        etNombre = findViewById(R.id.etNombre);
        etTipo = findViewById(R.id.etTipo);
        etFotoUrl = findViewById(R.id.etFotoUrl);
        btnUpdatePersonaje = findViewById(R.id.btnUpdatePersonaje);

        // Recibir los datos pasados desde MainActivity
        Intent intent = getIntent();
        if (intent != null) {
            personajeId = intent.getIntExtra("id", -1);
            String nombre = intent.getStringExtra("nombre");
            String tipo = intent.getStringExtra("tipo");
            String fotoUrl = intent.getStringExtra("fotoUrl");

            // Rellenar los campos con los datos actuales del personaje seleccionado
            etNombre.setText(nombre);
            etTipo.setText(tipo);
            etFotoUrl.setText(fotoUrl);
        }

        // Evento para guardar las modificaciones en la base de datos
        btnUpdatePersonaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarPersonaje();
            }
        });
    }

    private void modificarPersonaje() {
        String nombre = etNombre.getText().toString().trim();
        String tipo = etTipo.getText().toString().trim();
        String fotoUrl = etFotoUrl.getText().toString().trim();

        if (nombre.isEmpty() || tipo.isEmpty() || fotoUrl.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (personajeId == -1) {
            Toast.makeText(this, "Error: No se pudo identificar al personaje", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear el objeto personaje con los datos actualizados y su ID original
        Personaje personajeActualizado = new Personaje(personajeId, nombre, tipo, fotoUrl);
        int filasAfectadas = dbHelper.actualizarPersonaje(personajeActualizado);

        if (filasAfectadas > 0) {
            Toast.makeText(this, "Personaje modificado con éxito", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la pantalla y regresa al MainActivity
        } else {
            Toast.makeText(this, "Error al modificar el personaje", Toast.LENGTH_SHORT).show();
        }
    }
}