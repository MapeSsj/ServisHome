package com.google.codelabs.mdc.java.shrine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.codelabs.mdc.java.shrine.Interface.mauricio17PAApi;
import com.google.codelabs.mdc.java.shrine.Model.MetodoEnc;
import com.google.codelabs.mdc.java.shrine.Model.ReqGuardarUsuario;
import com.google.codelabs.mdc.java.shrine.Model.RptaGeneral;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupFragment extends Fragment {
    TextView mMensajeGeneralText;
    TextView mTxtIrLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.shr_register_fragment, container, false);

        final TextInputLayout usernameTextInput = view.findViewById(R.id.username_text_input);
        final TextInputEditText usernameEditText = view.findViewById(R.id.username_edit_text);

        final TextInputLayout claveUsuarioTextInput = view.findViewById(R.id.claveUsuario_text_input);
        final TextInputEditText claveUsuarioEditText = view.findViewById(R.id.claveUsuario_edit_text);

        final TextInputLayout tipo_usuarioTextInput = view.findViewById(R.id.tipo_usuario_text_input);
        final TextInputEditText tipo_usuarioEditText = view.findViewById(R.id.tipo_usuario_edit_text);

        final TextInputLayout fecha_registroTextInput = view.findViewById(R.id.fecha_registro_text_input);
        final TextInputEditText fecha_registroEditText = view.findViewById(R.id.fecha_registro_edit_text);

        final TextInputLayout numDocumentoTextInput = view.findViewById(R.id.numDocumento_text_input);
        final TextInputEditText numDocumentoEditText = view.findViewById(R.id.numDocumento_edit_text);

        final TextInputLayout imgUsuarioTextInput = view.findViewById(R.id.imgUsuario_text_input);
        final TextInputEditText imgUsuarioEditText = view.findViewById(R.id.imgUsuario_edit_text);

        final TextInputLayout emailUsuarioTextInput = view.findViewById(R.id.emailUsuario_text_input);
        final TextInputEditText emailUsuarioEditText = view.findViewById(R.id.emailUsuario_edit_text);

        final TextInputLayout tipoUsuarioTextInput = view.findViewById(R.id.tipoUsuario_text_input);
        final TextInputEditText tipoUsuarioEditText = view.findViewById(R.id.tipoUsuario_edit_text);

        final TextInputLayout tokenUuarioTextInput = view.findViewById(R.id.tokenUuario_text_input);
        final TextInputEditText tokenUuarioEditText = view.findViewById(R.id.tokenUuario_edit_text);

        final TextInputLayout estadoTokenUsuarioTextInput = view.findViewById(R.id.estadoTokenUuario_text_input);
        final TextInputEditText estadoTokenUsuarioEditText = view.findViewById(R.id.estadoTokenUuario_edit_text);




        MaterialButton regButton = view.findViewById(R.id.btn_registrarse);
        MaterialButton cancelButton = view.findViewById(R.id.btn_cancel);






        mMensajeGeneralText = view.findViewById(R.id.txtMensajeGeneral);


        mMensajeGeneralText = view.findViewById(R.id.txtMensajeGeneral);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new LoginFragment(), false);

            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), encryptPassword("Prueba"), Toast.LENGTH_SHORT).show();
                /*
                SharedPreferences sharedPreferences= getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("token","xyz_abc");
                myEdit.apply();
                ¨*/

                guardarUsuario(usernameEditText.getText().toString(), claveUsuarioEditText.getText().toString(), tipo_usuarioEditText.getText().toString(),fecha_registroEditText.getText().toString(),
                        numDocumentoEditText.getText().toString(),imgUsuarioEditText.getText().toString(),emailUsuarioEditText.getText().toString(),tipoUsuarioEditText.getText().toString(),
                        tokenUuarioEditText.getText().toString(),estadoTokenUsuarioEditText.getText().toString() );
            }
        });



    return view;
    }





    private void guardarUsuario(String p_username, String p_claveUsuario, String p_tipo_usuario, String p_fecha_registro, String p_numDocumento,
                                String p_imgUsuario, String p_emailUsuario, String p_tipoUsuario, String p_tokenUsuario, String p_estadoTokenUsuario) {
        String ePassword;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ServisHome.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mauricio17PAApi damgb2024iusatPAApi = retrofit.create(mauricio17PAApi.class);
        ePassword = MetodoEnc.encryptPassword(p_claveUsuario);

        ReqGuardarUsuario reqGuardarUsuario = new ReqGuardarUsuario();
        reqGuardarUsuario.setUsername(p_username);
        reqGuardarUsuario.setClaveUsuario(ePassword);
        reqGuardarUsuario.setTipo_usuario(p_tipo_usuario);
        reqGuardarUsuario.setFecha_registro(p_fecha_registro);
        reqGuardarUsuario.setNumDocumento(p_numDocumento);
        reqGuardarUsuario.setImgUsuario(p_imgUsuario);
        reqGuardarUsuario.setEmailUsuario(p_emailUsuario);
        reqGuardarUsuario.setTipoUsuario(p_tipoUsuario);
        reqGuardarUsuario.setTokenUsuario(p_tokenUsuario);
        reqGuardarUsuario.setEstadoTokenUsuario(p_estadoTokenUsuario);

        Call<RptaGeneral> call = damgb2024iusatPAApi.saveUser(reqGuardarUsuario);
        call.enqueue(new Callback<RptaGeneral>() {
            @Override
            public void onResponse(Call<RptaGeneral> call, Response<RptaGeneral> response) {
                if (!response.isSuccessful()) {
                    mMensajeGeneralText.setText("Código: " + response.code());
                } else {
                    RptaGeneral rptaGeneral = response.body();
                    mMensajeGeneralText.setText(rptaGeneral.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RptaGeneral> call, Throwable t) {
                mMensajeGeneralText.setText(t.getMessage());
            }
        });
    }

}
