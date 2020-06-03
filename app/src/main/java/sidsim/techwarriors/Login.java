package sidsim.techwarriors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    EditText etPass,etEmail;
    ImageView imgEmail,imgPass;
    Button btnLogin,btnSignUp;
    TextView tvForget;
    FirebaseAuth auth;
    ProgressDialog dialog;
    SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            permission();
        }
        setIds();
        FirebaseApp.initializeApp(this);
    }

  private void setIds() {
        etEmail = findViewById(R.id.username_input);
        etPass = findViewById(R.id.pass);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        imgEmail = findViewById(R.id.username_icon);
        imgPass = findViewById(R.id.icon);
        tvForget = findViewById(R.id.tvforget);
        dialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        signInButton = findViewById(R.id.signInButton);
  }

    private void permission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_DENIED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123 && grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "LOCATION PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit App");
        builder.setMessage("Do you want to exit this application??");
        builder.setCancelable(false);
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void forget(View view) {
        Toast.makeText(this, "Will be available soon", Toast.LENGTH_SHORT).show();
    }

    public void login(View view) {
        if(!(etEmail.length()==0 || etPass.length()==0)){
            if(!(etPass.length() < 6))
                    authenticate();
            else
                Toast.makeText(this, "Password Length more than 6", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Fields can't be left empty", Toast.LENGTH_SHORT).show();
    }

    private void authenticate() {
        String user = etEmail.getText().toString();
        Toast.makeText(this, "-----"+user+"------", Toast.LENGTH_SHORT).show();
        dialog.setMessage("Logging In");
        dialog.show();
        auth.signInWithEmailAndPassword(user.trim(),etPass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(Login.this,BasicDetails.class));
                    Toast.makeText(Login.this, "Successfully Login!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    etEmail.setText("");
                    etPass.setText("");
                }

            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Fail to Login "+e.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                etEmail.setText("");
                etPass.setText("");
            }
        });
    }

    public void signup(View view) {
        startActivity(new Intent(this,Registration.class));
    }

    public void signInButton(View view) {

    }
}
