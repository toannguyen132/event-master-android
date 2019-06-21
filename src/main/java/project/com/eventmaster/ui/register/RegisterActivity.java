package project.com.eventmaster.ui.register;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import project.com.eventmaster.R;
import project.com.eventmaster.data.Result;
import project.com.eventmaster.data.model.RegisterRequest;

public class RegisterActivity extends AppCompatActivity {

    EditText textName, textEmail, textPassword, textPasswordConfirm;
    Button button;
    RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // init
        setupInputs();
        setupViewModel();
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        viewModel.getRequestResult().observe(this, result -> {
            if (result instanceof Result.Success ) {
                // success
                finish();
            } else {
                Toast.makeText(this, "Error on register", Toast.LENGTH_SHORT).show();
                Log.d("REGISTER_ERROR", ((Result.Error)result).getError().getMessage());
            }
        });

    }

    private void setupInputs() {
        textName = findViewById(R.id.register_name);
        textEmail = findViewById(R.id.register_email);
        textPassword = findViewById(R.id.register_password);
        textPasswordConfirm = findViewById(R.id.register_password_confirm);
        button = findViewById(R.id.register_button);

        button.setOnClickListener(view -> {
            if (validate()) {
                RegisterRequest request = createRequest();
                viewModel.register(request);
            }
        });
    }

    private boolean validate() {


        return true;
    }

    private RegisterRequest createRequest() {
        RegisterRequest register = new RegisterRequest();

        register.setName(textName.getText().toString());
        register.setEmail(textEmail.getText().toString());
        register.setPassword(textPassword.getText().toString());
        register.setPasswordConfirm(textPassword.getText().toString());

        return register;
    }
}
