package com.example.brunojular.pilldose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    DatabaseHelper mDatabaseHelper;

    Button buttonAdd, buttonNewPill, buttonSend;
    EditText editText, editText2, editText3;
    Spinner spinner, spinner2, spinner3;
    RecyclerView recyclerView;

    /* Bluetooth */

    private OutputStream outputStream;
    private InputStream inStream;

    /*
        ---------
     */


    private static final String TAG= "ListDataActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Calling objects
         */

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonNewPill = (Button) findViewById(R.id.buttonNewPill);
        buttonSend = (Button) findViewById(R.id.buttonSend);

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mDatabaseHelper = new DatabaseHelper(this);



        /*
        Adapters
         */

        ArrayAdapter<CharSequence> adapterh = ArrayAdapter.createFromResource(this,
                R.array.string_h, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterm = ArrayAdapter.createFromResource(this,
                R.array.string_m, android.R.layout.simple_spinner_item);

        adapterh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapterh);
        spinner3.setAdapter(adapterm);

        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);

        /*
        Listeners for btns
         */

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();

                if (editText.length() > 0){
                    AddData(newEntry);
                    editText.setText("");
                } else {
                    toastMessage("Isn't your field empty?");
                }

            }
        });

        buttonNewPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry1 = editText2.getText().toString();
                String newEntry2 = editText3.getText().toString();
                String textSp1 = spinner2.getSelectedItem().toString();
                String textSp2 = spinner3.getSelectedItem().toString();

                String horario = textSp1 + ":" + textSp2;

                Integer id_ppl = 1;

                Toast.makeText(getApplicationContext(), "horario is "+ horario, Toast.LENGTH_LONG).show();

            /*

                Toast.makeText(getApplicationContext(), "text is "+ textSp, Toast.LENGTH_LONG).show();
            */

                if (editText2.length() > 0 && editText3.length() > 0){

                    Integer intNewEntry2 = Integer.parseInt(newEntry2);

                    if (intNewEntry2 <= 10){

                        AddData2(newEntry1, newEntry2, horario, id_ppl);

                        editText2.setText("");
                        editText3.setText("");

                    } else {
                        toastMessage("Solo hay 10 modulos");
                    }

                } else {
                    toastMessage("Isn't your field empty?");
                }

            }
        });

        loadSpinnerData();
    }

    public void AddData(String newEntry){
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if (insertData){
            toastMessage("Data Succesfully entered");
        } else {
            toastMessage("Oops! Something went wrong");
        }

        loadSpinnerData();

    }

    private void AddData2(String newEntry1, String newEntry2, String horario, Integer id_ppl) {

        boolean insertData = mDatabaseHelper.addData2(newEntry1, newEntry2, horario, id_ppl);

        if (insertData){
            toastMessage("Data Succesfully entered");
        } else {
            toastMessage("Oops! Something went wrong");
        }

    }


    private void loadSpinnerData() {
        // database handler
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        // Spinner Drop down elements
        List<String> lables = db.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
