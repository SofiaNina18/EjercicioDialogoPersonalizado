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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejerciciodialogopersonalizado.adaptadores.PersonajeAdapter;
import com.example.ejerciciodialogopersonalizado.bbdd.DBHelper;
import com.example.ejerciciodialogopersonalizado.model.Personaje;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;

public class MainActivity extends AppCompatActivity {

    private Button btnAdministrador;
    private Button btnAgua, btnElectrico, btnFuego;
    private ListView lvPersonajes;
    
    private DBHelper dbHelper;
    private ArrayList<Personaje> listaActual;
    private PersonajeAdapter adapter;

    private long ultimoClickTime = 0;
    private int ultimaPosicion = -1;

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

        dbHelper = new DBHelper(this);

        // Inicializar botones de categorías
        btnAgua = findViewById(R.id.btnAgua);
        btnElectrico = findViewById(R.id.btnElectrico);
        btnFuego = findViewById(R.id.btnFuego);
        btnAdministrador = findViewById(R.id.btnAdministrador);
        lvPersonajes = findViewById(R.id.lvPersonajes);

        // Cargar todos los personajes por defecto al iniciar la app
        cargarPersonajesPorCategoria(null);

        // Listeners para los botones de categorías
        btnAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarPersonajesPorCategoria("Agua");
            }
        });

        btnElectrico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarPersonajesPorCategoria("Electrico");
            }
        });

        btnFuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarPersonajesPorCategoria("Fuego");
            }
        });
        
        btnAdministrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CrearActivity.class);
                startActivity(intent);
            }
        });

        lvPersonajes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long clickTime = System.currentTimeMillis();

                if (position == ultimaPosicion && (clickTime - ultimoClickTime) < 300) {
                    // Obtener el personaje seleccionado
                    Personaje pSeleccionado = listaActual.get(position);


                    new android.app.AlertDialog.Builder(MainActivity.this)

                            .setMessage("¿Estás seguro de que quieres eliminar?")
                            .setPositiveButton("Aceptar", new android.content.DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(android.content.DialogInterface dialog, int which) {

                                    int filasAfectadas = dbHelper.eliminarPersonaje(pSeleccionado.getId());
                                    if (filasAfectadas > 0) {
                                        Toast.makeText(MainActivity.this, "Personaje eliminado", Toast.LENGTH_SHORT).show();
                                        cargarPersonajesPorCategoria(null);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
                }
                
                ultimoClickTime = clickTime;
                ultimaPosicion = position;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        cargarPersonajesPorCategoria(null);
    }

    private void cargarPersonajesPorCategoria(String categoria) {
        listaActual = dbHelper.obtenerPersonajes(categoria);
        adapter = new PersonajeAdapter(this, listaActual);
        lvPersonajes.setAdapter(adapter);

        if (categoria != null) {
            Toast.makeText(this, "Filtrado por: " + categoria, Toast.LENGTH_SHORT).show();
        }
    }
}