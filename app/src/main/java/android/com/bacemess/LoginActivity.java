package android.com.bacemess;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.stream.JsonReader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */


    // UI references.
    private AutoCompleteTextView musernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ProgressBar pb;

    //global username password
    static String user = "";
    static String pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        musernameView = (AutoCompleteTextView) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        pb = findViewById(R.id.progressbar);

    }

    public void go(View v) {
        final String username =  musernameView.getText().toString().trim();
        final String password = mPasswordView.getText().toString().trim();

        pb.setVisibility(View.VISIBLE);


         if(!username.isEmpty()&&!password.isEmpty()) {

             Upload ui = ApiUtils.retroEntity().create(Upload.class);
             Call<UploadObject> cp = ui.login(username, password);

             cp.enqueue(new Callback<UploadObject>() {
                 @Override
                 public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                     Log.e("success",response.body().getStatus());
                 }

                 @Override
                 public void onFailure(Call<UploadObject> call, Throwable t) {
                     Log.e("failure",t.getMessage());
                 }
             });


//             cp.enqueue(new Callback<UploadObject>() {
//                 @Override
//                 public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
//                     Log.e("tag success", response.body().getStatus());
//                     pb.setVisibility(View.INVISIBLE);
//                     if (response.body().getStatus().equals("success")) {
//
//                         //global username password
//                         user = username;
//                         pass = password;
//
//                         Intent in = new Intent(getApplicationContext(), devotees.class);
//                         startActivity(in);
//                     } else {
//                         Toast.makeText(getApplicationContext(), "username or password is incorrect", Toast.LENGTH_SHORT).show();
//                     }
//                     pb.setVisibility(View.GONE);
//                 }
//
//                 @Override
//                 public void onFailure(Call<UploadObject> call, Throwable t) {
//                      Log.e("tag failure", t.getMessage());
//                     Toast.makeText(getApplicationContext(), "Failure Try Again Later...", Toast.LENGTH_SHORT).show();
//                     pb.setVisibility(View.GONE);
//                 }
//             });

         }else{
             pb.setVisibility(View.INVISIBLE);
             Toast.makeText(getApplicationContext(),"Empty fields are not allowed",Toast.LENGTH_SHORT).show();
         }

    }


}

