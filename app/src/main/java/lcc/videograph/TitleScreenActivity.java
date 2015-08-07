package lcc.videograph;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;


public class TitleScreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);
        getSupportActionBar().hide();

        this.startButton();
        this.guidesButton();
    }

    private void startButton() {
        final TextView start = (TextView)findViewById(R.id.textView_start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start.setShadowLayer(10, 5, 5, Color.BLACK);

                Intent startIntent = new Intent(TitleScreenActivity.this, HomeActivity.class);
                startActivity(startIntent);
            }
        });
    }

    private void guidesButton() {
        final TextView guides = (TextView)findViewById(R.id.textView_guides);

        guides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //guides.setShadowLayer(10, 5, 5, Color.BLACK);

                Intent intentGoToGuides = new Intent(TitleScreenActivity.this, Guides2Activity.class);
                startActivity(intentGoToGuides);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_title_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}