package com.ort.profesionalinvoicemanager.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.ort.profesionalinvoicemanager.DAO.EmailHelper;
import com.ort.profesionalinvoicemanager.DAO.UserDAO;
import com.ort.profesionalinvoicemanager.model.user.User;
import com.ort.profesionalinvoicemanager.views.Utils.StringConstant;
import com.ort.profesionalinvoicemanager.views.Utils.ValidateHelper;

public class PasswordRecoveryDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.recovery_password, null);
        final TextInputEditText txtMail = (TextInputEditText) v.findViewById(R.id.recovery_email_txt);
        builder.setView(v)
                .setPositiveButton("Recuperar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       String mail=txtMail.getText().toString();
                       boolean hasError=false;
                        if (ValidateHelper.validateEmptyString(mail)||!ValidateHelper.isEmailValid(mail)) {
                            Toast.makeText(getContext(), "Ingrese un mail Valido", Toast.LENGTH_SHORT).show();
                        }else{
                            User u = UserDAO.getInstance().getUserByMail(mail);
                            if (u!=null){
                                String body="Su Usuario: "+u.getMail()+" Su Password: "+u.getPassword();
                                EmailHelper.sendEmail("Recuperacion de Password",body,u.getMail(),"Recuperacion de Password",getContext());
                                Toast.makeText(getContext(), "Se Envio un mail con el usuario", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), "El usuario no existe", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

}
