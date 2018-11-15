package com.example.brunojular.pilldose;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/* limipar codigo*/

@RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {

    public static final String TAG = "YOUR-TAG-NAME";

    DatabaseHelper mDatabaseHelper;
    String name;

    /*
    String bt
     */

    String shower;

    Button buttonAdd, buttonNewPill, buttonSend;
    EditText editText, editText2, editText3;
    Spinner spinner, spinner2, spinner3, spinner4;

    public static String filter;
    /*
    RecyclerView
     */

    ArrayList<PillVO> listDatos;
    RecyclerView recycler;

    /*
      Listener for bar btns
    */

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                Intent intent = new Intent(this, BtActivity.class);
                startActivity(intent);

                Log.d(TAG, "BAR BTN MAIN ACTIVITY");

        }
                return super.onOptionsItemSelected(item);
    }

    /*

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalVariables.setmDatabaseHelper(new DatabaseHelper(this));

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
        spinner4 = (Spinner) findViewById(R.id.spinner4);

        GlobalVariables.setmDatabaseHelper(new DatabaseHelper(this));
        mDatabaseHelper = GlobalVariables.getmDatabaseHelper();


        /*
        RecyclerView Populator
         */

        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        listDatos = new ArrayList<>();

        /*
        Adapters for spinners
         */

        ArrayAdapter<CharSequence> adapterh = ArrayAdapter.createFromResource(this,
                R.array.string_h, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterm = ArrayAdapter.createFromResource(this,
                R.array.string_m, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterd = ArrayAdapter.createFromResource(this,
                R.array.string_days, android.R.layout.simple_spinner_item);

        adapterh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapterd);
        spinner3.setAdapter(adapterm);
        spinner4.setAdapter(adapterh);

        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);
        spinner4.setOnItemSelectedListener(this);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recycler.setAdapter(new AdapterDatos(PillVO.getPills(spinner.getSelectedItem().toString())));
                String name = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
        Listeners for btns
         */

        final Intent intnt2 = new Intent(this, Pop.class);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();

                if (editText.length() > 0) {
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

                String pill_name = editText2.getText().toString();
                String pill_module = editText3.getText().toString();

                String textDay = spinner2.getSelectedItem().toString();
                String textMinutos = spinner3.getSelectedItem().toString();
                String textHora = spinner4.getSelectedItem().toString();

                String numDay = textDay.replaceAll("\\D+","");

                /* aca poner el día de la semana */

                String horario = textHora + ":" + textMinutos + "/" + numDay;

                /* */

                String name = spinner.getSelectedItem().toString();

                if (editText2.length() > 0 && editText3.length() > 0) {

                    Integer intNewEntry2 = Integer.parseInt(pill_module);

                    if (intNewEntry2 <= 6) {

                        PillVO newPill = new PillVO(pill_module, pill_name, horario, name);
                        Log.e(TAG, "---------NEWPILL: " + newPill.getModulo());
                        newPill.save();

                        editText2.setText("");
                        editText3.setText("");

                        recycler.setAdapter(new AdapterDatos(PillVO.getPills(spinner.getSelectedItem().toString())));

                    } else {
                        toastMessage("Solo hay 10 modulos");
                    }

                } else {
                    toastMessage("Todos los campos deben ser completados");
                }

            }
        });


        /*
        Intent: for sending the string to the other activity
         */


        final Intent intnt = new Intent(this, BtActivity.class);

        /*
        Button bluetooth
         */

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Collector of Pills
                */

                ArrayList<PillVO> list = PillVO.getPills(spinner.getSelectedItem().toString());

                for (PillVO p : list) {

                    shower += " " + p.getModulo() + " " + p.getHorario() + " ";

                }

                /*
                BtActivity bta = new BtActivity();

                bta.SendMessageFromMain(shower);
*/

                intnt.putExtra("puzzle", shower);
                startActivity(intnt);

                Log.e(TAG, "-------------SHOWER: " + shower);

            }


        });

        loadSpinnerData();
    } /// on create finishes


    /// F*** bluetooth


    /// function for database, adding data

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if (insertData) {
            toastMessage("Data Succesfully entered");
        } else {
            toastMessage("Oopos! Something went wrong");
        }

        loadSpinnerData();

    }


    /*
    spinner populator
     */

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

        recycler.setAdapter(new AdapterDatos(PillVO.getPills(spinner.getSelectedItem().toString())));

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    /*
    Spinner ¿constructors?
     */

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
