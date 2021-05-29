package com.example.covid_19;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    String [] rouges={"Ariana","Kébili","Tunis","Medenine","Mahdia","Gabès"};
    Button submit;
    EditText age;
    RadioButton fievreTrue,fievreFalse,touxTrue,touxFalse;
    CheckBox hypertension,asthme,diabete;
    Spinner gouvernorat;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit=findViewById(R.id.buttonSubmit);
        age=findViewById(R.id.editTextAge);
        age.setFilters(new InputFilter[]{new MinMaxFilter("1","104")});
        fievreTrue=findViewById(R.id.radioButtonFievreTrue);
        fievreFalse=findViewById(R.id.radioButtonFievreFalse);
        touxTrue=findViewById(R.id.radioButtonTouxTrue);
        touxFalse=findViewById(R.id.radioButtonTouxFalse);
        gouvernorat=findViewById(R.id.spinnerGouvernorats);
        hypertension=findViewById(R.id.checkBoxHypertension);
        asthme=findViewById(R.id.checkBoxAsthme);
        diabete=findViewById(R.id.checkBoxDiabete);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( age.getText().toString().isEmpty()
                        || gouvernorat.getSelectedItem().toString().equalsIgnoreCase("--Gouvernorat d'actuelle résidence--"))
                {
                    Toast.makeText(MainActivity.this,"Vérifier vos entrées",Toast.LENGTH_LONG).show();
                }
                else {
                    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                    adb.setMessage("Réponse");
                    if (fievreTrue.isChecked() && touxTrue.isChecked() && Integer.parseInt(age.getText().toString()) >= 60
                            && Arrays.asList(rouges).contains(gouvernorat.getSelectedItem().toString()) )
                        msg = "Etat préoccupant. Prière d'appeler le 190.";
                    if (fievreTrue.isChecked() && touxTrue.isChecked() && Integer.parseInt(age.getText().toString()) >= 60
                            && Arrays.asList(rouges).contains(gouvernorat.getSelectedItem().toString()) && (hypertension.isChecked() || asthme.isChecked() || diabete.isChecked()))
                        msg = "Etat fortement préoccupant. Prière d'appeler le 190.";
                    else if (fievreFalse.isChecked()
                            && touxFalse.isChecked()
                            && Integer.parseInt(age.getText().toString()) >= 60
                            && Arrays.asList(rouges).contains(gouvernorat.getSelectedItem().toString()))
                        msg = "Etat préoccupant , restez chez vous SVP.";
                    else if (fievreFalse.isChecked()
                            && touxFalse.isChecked()
                            && Integer.parseInt(age.getText().toString()) >= 60
                            && !Arrays.asList(rouges).contains(gouvernorat.getSelectedItem().toString()))
                        msg = "Etat faiblement préoccupant , restez vigilent SVP.";
                    else if (fievreFalse.isChecked()
                            && touxFalse.isChecked()
                            && Integer.parseInt(age.getText().toString()) < 60
                            && Arrays.asList(rouges).contains(gouvernorat.getSelectedItem().toString()))
                        msg = "Etat normal , restez chez vous SVP.";
                    else if (fievreFalse.isChecked()
                            && touxFalse.isChecked()
                            && !Arrays.asList(rouges).contains(gouvernorat.getSelectedItem().toString()))
                        msg = "Etat normal , restez vigilant SVP.";

                    adb.setPositiveButton(msg, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent it = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(it);
                        }
                    });
                    adb.show();
                }
            }
        });

    }
}
