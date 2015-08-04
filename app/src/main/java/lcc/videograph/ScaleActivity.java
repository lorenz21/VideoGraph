package lcc.videograph;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ScaleActivity extends Activity {

    double scale;
    boolean Meters;
    boolean English;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String vidPath = getIntent().getStringExtra("vidPath");
        final RadioButton metersbutton = (RadioButton)findViewById(R.id.radioButton_meters);
        final RadioButton Englishbutton = (RadioButton)findViewById(R.id.radioButton_English);
        final EditText meterstext = (EditText)findViewById(R.id.editText_meters);
        final EditText feettext = (EditText)findViewById(R.id.editText_feet);
        final EditText inchestext = (EditText)findViewById(R.id.editText_inches);
        final TextView nextText = (TextView)findViewById(R.id.nextText);
        //final TextView test = (TextView)findViewById(R.id.testView);



        // Random x and y values that will be used until linked with other activity
        int x1 = pref.getInt("x1",0);
        int x2 = pref.getInt("x2",0);
        int y1 = pref.getInt("y1",0);
        int y2 = pref.getInt("y2",0);

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

                    Meters = true;
                    English = false;
                }


            }


        });



        Englishbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (Englishbutton.isChecked() == true) {

                    // Makes the text boxes visible and usable
                    feettext.setVisibility(View.VISIBLE);
                    inchestext.setVisibility(View.VISIBLE);

                    English = true;
                    Meters = false;


                }
            }

        });

        final Intent scaleIntent = new Intent(getApplicationContext(),PlotActivity.class);
        scaleIntent.putExtra("vidPath",vidPath);
        scaleIntent.putExtra("Scale",scale);

        nextText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if (Meters = true){

                    // Getting and parsing the value typed in the text box
                    String si =  meterstext.getText().toString();
                    double meterEntry = Double.parseDouble(si);

                    // This is where the scale is created from the typed value and pixel difference
                    scale = meterEntry/pix;
                    startActivity(scaleIntent);

                    //String tester = Double.toString(scale);
                    //test.setText(tester);


                }
                else if(English = true){

                    // Getting and parsing the values typed in the text boxes
                    String ft = feettext.getText().toString();
                    double feetEntry = Double.parseDouble(ft);
                    String in = inchestext.getText().toString();
                    double inchesEntry = Double.parseDouble(in);

                    // This is where the scale is created and converted into meters per pixel
                    scale = (feetEntry + (inchesEntry/12))/(m * pix);
                    startActivity(scaleIntent);
                }
            }
        });

    }

}
