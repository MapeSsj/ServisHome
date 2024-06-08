package com.google.codelabs.mdc.java.shrine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.codelabs.mdc.java.shrine.Interface.JsonPlaceHolderApi;
import com.google.codelabs.mdc.java.shrine.Interface.mauricio17PAApi;
import com.google.codelabs.mdc.java.shrine.Model.MetodoEnc;
import com.google.codelabs.mdc.java.shrine.Model.Posts;
import com.google.codelabs.mdc.java.shrine.Model.ReqAuth;
import com.google.codelabs.mdc.java.shrine.Model.RptaAuth;
import com.google.codelabs.mdc.java.shrine.response.GenericoResponse;
import com.google.codelabs.mdc.java.shrine.retrofit.ApiService;
import com.google.codelabs.mdc.java.shrine.retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// import com.google.codelabs.mdc.java.shrine.

/**
 * Fragment representing the login screen for Shrine.
 */
public class LoginFragment extends Fragment {
    Button btnGetJWT;
    Button btnObtenerUsuarios;
    TextView mJsonText;
    String strJWT;

    TextView mTxtIrSignUp;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shr_login_fragment, container, false);
        final TextInputLayout userTextInput = view.findViewById(R.id.user_text_input);
        final TextInputEditText userEditText = view.findViewById(R.id.user_edit_text);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);
        MaterialButton nextButton = view.findViewById(R.id.next_button);
        MaterialButton registerButton = view.findViewById(R.id.reg_button);

        btnGetJWT = view.findViewById(R.id.btnGetJWT);
        btnObtenerUsuarios = view.findViewById(R.id.btnObtenerUsuario);
        mJsonText = view.findViewById(R.id.jsonText);
        mTxtIrSignUp = view.findViewById(R.id.txtIrSignUp);

        /*
        SharedPreferences sh = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String s1 = sh.getString("token","");
        mJsonText.setText(s1);
        */

        MaterialButton regButton = view.findViewById(R.id.reg_button);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new SignupFragment(), false);

            }
        });

        // Set an error if the password is less than 8 characters.
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Verificar si se ingresaron datos de usuario y contraseña
                if (username.isEmpty()) {
                    userTextInput.setError("Ingrese un nombre de usuario");
                    return;
                }
                if (password.isEmpty()) {
                    passwordTextInput.setError("Ingrese una contraseña");
                    return;
                }

                // Verificar la validez de las credenciales con el servidor
                final ApiService apiService = RetrofitClient.createService();
                final Call<GenericoResponse> call = apiService.login(username, password);
                call.enqueue(new Callback<GenericoResponse>() {
                    @Override
                    public void onResponse(final Call<GenericoResponse> call, final Response<GenericoResponse> response) {
                        if (response.isSuccessful()) {
                            GenericoResponse responseBody = response.body();
                            if (responseBody != null && responseBody.isStatus()) {
                                JsonObject data = responseBody.getData();
                                if (data != null && data.has("token") && data.has("usuario_id")) {
                                    SharedPreferences sesion = getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sesion.edit();
                                    editor.putString("token", data.get("token").getAsString());
                                    editor.putInt("id", data.get("usuario_id").getAsInt());
                                    editor.apply();

                                    Toast.makeText(getContext(), responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                                    ClienteGridFragment clienteGridFragment = new ClienteGridFragment();

                                    ((NavigationHost) getActivity()).navigateTo(clienteGridFragment, false);
                                } else {
                                    Toast.makeText(getContext(), "Error en la respuesta del servidor: datos faltantes", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Error en la respuesta del servidor: " + responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Error en la respuesta del servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(final Call<GenericoResponse> call, final Throwable t) {
                        Log.e("Error Login", t.getMessage());
                        Toast.makeText(getContext(), "Error al conectarse con el servidor", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(null); //Clear the error
                }
                return false;
            }
        });

        btnGetJWT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getJWT(userEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

        btnObtenerUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerUsuarios();
            }
        });



        mTxtIrSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new ClienteGridFragment(), false);
            }
        });

        // getPosts();
        // obtenerUsuarios();

        return view;
    }

    // "isPasswordValid" from "Navigate to the next Fragment" section method goes here
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }

    private void getPosts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Posts>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if(!response.isSuccessful()){
                    mJsonText.setText("Codigo: " + response.code());
                }
                List<Posts> listaPosts = response.body();
                for(Posts post: listaPosts){
                    String contenido = "";
                    contenido += "UserId: " + post.getId() + "\n";
                    contenido += "Id: " + post.getId() + "\n";
                    contenido += "Title: " + post.getTitle() + "\n";
                    contenido += "Body: " + post.getBody() + "\n";
                    mJsonText.append(contenido);
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                mJsonText.setText(t.getMessage());
            }
        });
    }


    private void getJWT(String p_username, String p_password){
        String ePassword;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ServisHome.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mauricio17PAApi damgb2024iusatPAApi = retrofit.create(mauricio17PAApi.class);
        ePassword = MetodoEnc.encryptPassword(p_password);

        ReqAuth reqAuth = new ReqAuth();
        reqAuth.setUsername(p_username);
        reqAuth.setClaveUsuario(ePassword);
        Toast.makeText(getContext(), p_username+ePassword, Toast.LENGTH_SHORT).show();
        Call<RptaAuth> call = damgb2024iusatPAApi.getJWT(reqAuth);
        call.enqueue(new Callback<RptaAuth>() {
            @Override
            public void onResponse(Call<RptaAuth> call, Response<RptaAuth> response) {
                if(!response.isSuccessful()){
                    mJsonText.setText("Codigo: " + response.code());
                }else{
                    RptaAuth rptaAuth = response.body();
                    strJWT = rptaAuth.getAccess_token();

                    SharedPreferences sharedPreferences= getActivity().getSharedPreferences("SP_JWT", Context.MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("strJWT",strJWT);
                    myEdit.apply();

                    ((NavigationHost) getActivity()).navigateTo(new ClienteGridFragment(), false); // Navigate to the next Fragment
                    mJsonText.setText(strJWT);
                }
            }

            @Override
            public void onFailure(Call<RptaAuth> call, Throwable t) {
                mJsonText.setText(t.getMessage());
            }
        });
    }

    private void obtenerUsuarios() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ServisHome.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mauricio17PAApi damgb2024iusatPAApi = retrofit.create(mauricio17PAApi.class);
        Call<List<List<Object>>> call = damgb2024iusatPAApi.obtenerUsuarios();
        call.enqueue(new Callback<List<List<Object>>>() {
            @Override
            public void onResponse(Call<List<List<Object>>> call, Response<List<List<Object>>> response) {
                if (!response.isSuccessful()) {
                    mJsonText.setText("Código: " + response.code());
                } else {
                    List<List<Object>> usuarios = response.body();
                    for (List<Object> usuario : usuarios) {
                        String contenido = "";
                        contenido += "Id: " + usuario.get(0) + "\n";
                        contenido += "Clave: " + usuario.get(2) + "\n";
                        contenido += "Username: " + usuario.get(1) + "\n";
                        mJsonText.append(contenido);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<List<Object>>> call, Throwable t) {
                mJsonText.setText(t.getMessage());
            }
        });





        btnObtenerUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerUsuarios();
            }
        });
    }
}
