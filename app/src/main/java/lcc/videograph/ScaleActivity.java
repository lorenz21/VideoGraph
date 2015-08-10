package lcc.videograph;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ScaleActivity extends Activity {


    // Random x and y values that will be used until linked with other activity
    int x1;
    int x2;
    int y1;
    int y2;
    private double scale;
    boolean Meters;
    boolean English;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);

        x1 = getIntent().getIntExtra("x1",0);
        x2 = getIntent().getIntExtra("x2",0);
        y1 = getIntent().getIntExtra("y1",0);
        y2 = getIntent().getIntExtra("y2",0);
        String vidPath = getIntent().getStringExtra("vidPath");
        final RadioButton metersbutton = (RadioButton)findViewById(R.id.radioButton_meters);
        final RadioButton Englishbutton = (RadioButton)findViewById(R.id.radioButton_English);
        final EditText meterstext = (EditText)findViewById(R.id.editText_meters);
        final EditText feettext = (EditText)findViewById(R.id.editText_feet);
        final EditText inchestext = (EditText)findViewById(R.id.editText_inches);
        final TextView nextText = (TextView)findViewById(R.id.nextText);





        // Feet to meters constant
        final int m = 82021/25000;

        // Length between pixels equation
        final double pix = ((((x1 - x2) ^ 2) + ((y1 - y2) ^ 2))) ^ (1/2);
        /**
         * This is where the different units can be selected and the proper text box appears for the
         * units to be converted from pixels to the selected units.
         */
        metersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (metersbutton.isChecked() == true) {

                    // Makes the text box visible and usable
                    meterstext.setVisibility(View.VISIBLE);
                    feettext.setVisibility(View.INVISIBLE);
                    inchestext.setVisibility(View.INVISIBLE);

                    Meters = true;
                    English = false;
                }


            }


        });



        Englishbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (Englishbutton.isChecked() == true) {

                    // Makes the text boxes visible and usable
                    meterstext.setVisibility(View.INVISIBLE);
                    feettext.setVisibility(View.VISIBLE);
                    inchestext.setVisibility(View.VISIBLE);

                    English = true;
                    Meters = false;


                }
            }

        });

        final Intent scaleIntent = new Intent(getApplicationContext(),PlotActivity.class);
        scaleIntent.putExtra("vidPath",vidPath);


        nextText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Meters == true) {
                    String si = meterstext.getText().toString();
                    if(TextUtils.isEmpty(si)) {
                        meterstext.setError("Enter an amount in 'Meter's'");
                        return;
                        //Toast.makeText(ScaleActivity.this, "Enter 'metere's'", Toast.LENGTH_SHORT).show();
                   }
                    else {
                        // Getting and parsing the value typed in the text box
                        double meterEntry = Double.parseDouble(si);
                        // This is where the scale is created from the typed value and pixel difference
                        scale = meterEntry / pix;
                        scaleIntent.putExtra("Scale", scale);
                        String scaleTest = String.valueOf(scale);
                        Log.d("scaleNum", scaleTest);

                        startActivity(scaleIntent);

                        //String tester = Double.toString(scale);
                        //test.setText(tester);
                   }
                }
                if (English == true) {
                    String ft = feettext.getText().toString();
                    String in = inchestext.getText().toString();
                    if(TextUtils.isEmpty(ft)) {
                        feettext.setError("Enter an amount in 'Feet'");
                        return;
                    }
                    else if(TextUtils.isEmpty(in)){
                        inchestext.setError("Enter an amount in 'Inches'");
                        return;
                    }
                    else{
                        Toast.makeText(ScaleActivity.this, "Enter ft/in'", Toast.LENGTH_SHORT).show();
                        // Getting and parsing the values typed in the text boxes

                        double feetEntry = Double.parseDouble(ft);
                        double inchesEntry = Double.parseDouble(in);

                        // This is where the scale is created and converted into meters per pixel
                        scale = (feetEntry + (inchesEntry / 12)) / (m * pix);
                        scaleIntent.putExtra("Scale", scale);
                        startActivity(scaleIntent);
                    }
                }
            }
        });


    }

}
