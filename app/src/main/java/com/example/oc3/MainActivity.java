package com.example.oc3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oc3.modelo.Modelo;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mfirebaseAutH;
    FirebaseAuth.AuthStateListener mAuthListener;

    public static final int REQUEST_CODE = 54654;

     List<AuthUI.IdpConfig> provider = Arrays.asList(
            new  AuthUI.IdpConfig.EmailBuilder().build(),
            new  AuthUI.IdpConfig.AnonymousBuilder().build()
    );

    public EditText telefono;
    public ImageButton buscar;
    public TextView vista;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference().child("MC");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        telefono = findViewById(R.id.ed_telefono);
        vista =(TextView) findViewById(R.id.txv_vista);
        buscar = (ImageButton) findViewById(R.id.img_buscar);

        // En este metodo iniciamos la verificacion para el inicio de sesion.
        mfirebaseAutH = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null){

                    Toast.makeText(MainActivity.this, "Iniciaste sesion ok ", Toast.LENGTH_LONG);

                }else {
                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(provider)
                            .setIsSmartLockEnabled(false)
                            .build(),REQUEST_CODE

                    );

                }


            }
        };

    }
    //Aqui esta el metodo del boton Buscar
    public void mc(View v){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("MC").child("TELEFONO");

        Query query = reference.orderByChild("telefono").equalTo(telefono.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               if (snapshot.exists()) {
                   for (DataSnapshot snapshot1: snapshot.getChildren()){

                       String t = snapshot1.child("mc").getValue().toString();

                       vista.setText("Puerto:  "+ t );
                   }
                   InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                   inputMethodManager.hideSoftInputFromWindow(telefono.getWindowToken(), 0);
                   telefono.setText("");


                } else {
                   //condicion por si no existiera el numero buscado
                   Toast.makeText(MainActivity.this, "Numero No existe en la Base de Datos",
                           Toast.LENGTH_LONG).show();
                }
            }
            //Aqui esta la funcion en caso no se lograra la conexion a la base de datos
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this, "No se puede los datos",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    // Aqui realizamos a verificacion del inicio de sesion.
    @Override
    protected void onResume() {
        super.onResume();

        mfirebaseAutH.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mfirebaseAutH.removeAuthStateListener(mAuthListener);
    }

    // Aqui esta el metodo del boton cerrar sesion.
    public void cerrarsesion(View view) {

        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Sesion cerrada ", Toast.LENGTH_LONG);
            }
        });
    }

    // Aqui esta el metodo del boton cerrar sesion.
    public void btn_cerrarsesion(View view) {

        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Sesion cerrada ", Toast.LENGTH_LONG);
            }
        });
    }

}
