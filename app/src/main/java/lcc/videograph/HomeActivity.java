package lcc.videograph;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Joseph on 7/23/2015.
 */
public class HomeActivity extends Activity {

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.recordButton();
        this.plotButton();
        }

private void recordButton() {
        TextView record = (TextView)findViewById(R.id.textView_record);
        record.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intentToRecordVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(intentToRecordVideo.resolveActivity(getPackageManager()) != null){
        startActivityForResult(intentToRecordVideo, 1);
        }
        }
        });
        }

private void plotButton(){
        TextView plot = (TextView)findViewById(R.id.textView_plotPoints);
        plot.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intentToChangeActivity = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intentToChangeActivity);
        }
        });
    }

}

