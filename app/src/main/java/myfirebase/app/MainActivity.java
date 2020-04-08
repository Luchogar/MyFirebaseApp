package myfirebase.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import myfirebase.app.model.Persona;


public class MainActivity extends AppCompatActivity {

    EditText nomP, appP, correoP, passwordP;
    ListView ListV_personas;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nomP = findViewById(R.id.txt_nombrePersona);
        appP = findViewById(R.id.txt_apellidosPersona);
        correoP = findViewById(R.id.txt_nombreCorreo);
        passwordP = findViewById(R.id.txt_password);

        ListV_personas = findViewById(R.id.DatosPersonas);
        inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String nombre = nomP.getText().toString();
        String apellidos = appP.getText().toString();
        String correo = correoP.getText().toString();
        String password = passwordP.getText().toString();

        switch (item.getItemId()) {
            case R.id.icon_add: {
                if (nombre.equals("") || apellidos.equals("") || correo.equals("") || password.equals("")) {
                    validacion();
                } else {
                    Persona p = new Persona();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellido(apellidos);
                    p.setEmail(correo);
                    p.setPassword(password);
                    databaseReference.child("Persona").child(p.getUid()).setValue(p);
                    Toast.makeText(this, "Agregar", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                    break;
                }
            }
            case R.id.icon_save: {
                Toast.makeText(this, "Guardar", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.icon_delete: {
                Toast.makeText(this, "Eliminar", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                break;
        }
        return true;
    }

    private void limpiarCajas() {
        nomP.setText("");
        appP.setText("");
        correoP.setText("");
        passwordP.setText("");
    }


    private void validacion() {
        String nombre = nomP.getText().toString();
        String apellidos = appP.getText().toString();
        String correo = correoP.getText().toString();
        String password = passwordP.getText().toString();

        if (nombre.equals("")) {
            nomP.setError("Required");
        } else if (apellidos.equals("")) {
            nomP.setError("Required");
        } else if (correo.equals("")) {
            nomP.setError("Required");
        } else if (password.equals("")) {
            nomP.setError("Required");
        }
    }
}