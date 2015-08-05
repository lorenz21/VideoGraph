package lcc.videograph;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.net.URL;


public class Guides2Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides2);
        this.rolling();
        this.freefall();
        this.projectileMotion();
        this.kineticFriction();
        this.pendulum();
        this.momentOfInertia();
        this.website();
    }

    private void rolling() {
        LinearLayout rollingLayout = (LinearLayout)findViewById(R.id.layout_rolling);
        rollingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rollingIntent = new Intent(Guides2Activity.this, TitleScreenActivity.class);
                startActivity(rollingIntent);
            }
        });
    }

    private void freefall() {
        LinearLayout freefallLayout = (LinearLayout)findViewById(R.id.layout_freefall);
        freefallLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent freefallIntent = new Intent(Guides2Activity.this, TitleScreenActivity.class);
                startActivity(freefallIntent);
            }
        });
    }

    private void projectileMotion() {
        LinearLayout projectileLayout = (LinearLayout)findViewById(R.id.layout_projectile);
        projectileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent projectileIntent = new Intent(Guides2Activity.this, TitleScreenActivity.class);
                startActivity(projectileIntent);
            }
        });
    }

    private void kineticFriction() {
        LinearLayout kineticLayout = (LinearLayout)findViewById(R.id.layout_kinetic);
        kineticLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kineticIntent = new Intent(Guides2Activity.this, TitleScreenActivity.class);
                startActivity(kineticIntent);
            }
        });
    }

    private void pendulum() {
        LinearLayout pendulumLayout = (LinearLayout)findViewById(R.id.layout_pendulum);
        pendulumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendulumIntent = new Intent(Guides2Activity.this, TitleScreenActivity.class);
                startActivity(pendulumIntent);
            }
        });
    }

    private void momentOfInertia() {
        LinearLayout inertiaLayout = (LinearLayout)findViewById(R.id.layout_inertia);
        inertiaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inertiaIntent = new Intent(Guides2Activity.this, TitleScreenActivity.class);
                startActivity(inertiaIntent);
            }
        });
    }

    private void website() {
        LinearLayout websiteLayout = (LinearLayout)findViewById(R.id.layout_website);
        websiteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guides2, menu);
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

//blah blah