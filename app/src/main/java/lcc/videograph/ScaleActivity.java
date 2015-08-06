package lcc.videograph;

import android.app.Activity;
import android.content.Context;
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


    // Random x and y values that will be used until linked with other activity
    int x1 = ScaleView.getX1();
    int x2 = ScaleView.getX2();
    int y1 = ScaleView.getY1();
    int y2 = ScaleView.getY2();
    private static double scale;
    boolean Meters;
    boolean English;
    RadioButton metersbutton = (RadioButton)findViewById(R.id.radioButton_meters);
    RadioButton Englishbutton = (RadioButton)findViewById(R.id.radioButton_English);
    EditText meterstext = (EditText)findViewById(R.id.editText_meters);
    EditText feettext = (EditText)findViewById(R.id.editText_feet);
    EditText inchestext = (EditText)findViewById(R.id.editText_inches);
    TextView nextText = (TextView)findViewById(R.id.nextText);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);
        String vidPath = getIntent().getStringExtra("vidPath");

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


        nextText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (Meters = true){

                    // Getting and parsing the value typed in the text box
                    String si =  meterstext.getText().toString();
                    double meterEntry = Double.parseDouble(si);

                    // This is where the scale is created from the typed value and pixel difference
                    scale = meterEntry/pix;
                    scaleIntent.putExtra("Scale",scale);
                    startActivity(scaleIntent);


                }
                else if(English = true){

                    // Getting and parsing the values typed in the text boxes
                    String ft = feettext.getText().toString();
                    double feetEntry = Double.parseDouble(ft);
                    String in = inchestext.getText().toString();
                    double inchesEntry = Double.parseDouble(in);

                    // This is where the scale is created and converted into meters per pixel
                    scale = (feetEntry + (inchesEntry/12))/(m * pix);
                    scaleIntent.putExtra("Scale",scale);
                    startActivity(scaleIntent);
                }
            }
        });


    }
    public static double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
