package com.lpufoodie.lpufoodie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements LpuFoodie {

    private Intent goToMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        goToMainActivity = new Intent(LoginActivity.this, MainActivity.class);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException ignored) {
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        LF_Auth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = LF_User.get();
                DatabaseReference dr = LF_DatabaseReference.apply("Users/" + user.getUid());
                dr.child("user_name").setValue(user.getDisplayName());
                dr.child("email").setValue(user.getEmail());
                dr.child("photo_url").setValue(user.getPhotoUrl().toString());
                dr.child("number").setValue(user.getPhoneNumber());
                dr.child("orders").setValue(!LF_Orders.isEmpty());
                startActivity(goToMainActivity);
            } else {
                Snackbar.make(findViewById(R.id.login), "Authentication Failed.", Snackbar.LENGTH_SHORT)
                        .setAction("Ok", view -> {
                            startActivity(goToMainActivity);
                        })
                        .show();
            }

        });
    }

    public void loginWithGoogle(View view) {
        Intent signInIntent = LF_GoogleSignInClient.apply(this).getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onBackPressed() {
        startActivity(goToMainActivity);
    }

}
