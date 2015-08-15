package lcc.videograph;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

/**
 * Created by steve on 8/9/15.
 */
public class ScaleVideo extends Activity {
    String vidPathString;
    VideoView scaleVideoView;
    int count = 0;
    float touchX,touchY;
    int x1,x2,y1,y2;
    ArrayList<Integer> xCorr = new ArrayList<Integer>();
    ArrayList<Integer> yCorr = new ArrayList<Integer>();
    ImageButton scalePause;
    ImageButton scaleDeleteButton;
    ImageButton scalePlay;
    ImageButton seekForward;
    ImageButton seekBackWard;
    ImageButton submitButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_video);

        vidPathString = getIntent().getStringExtra("vidPath");
        scaleVideoView = (VideoView) findViewById(R.id.scale_video);
        // Set the Image in ImageView after decoding the String
        scaleVideoView.setVideoPath(vidPathString);

        scaleDeleteButton = (ImageButton)findViewById(R.id.delete_scale_button);
        scaleDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        scalePause = (ImageButton)findViewById(R.id.scale_pause_button);
        scalePause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                scaleVideoView.pause();
                scalePause.setVisibility(View.INVISIBLE);
                scalePlay.setVisibility(View.VISIBLE);

            }
        });
        scalePlay = (ImageButton)findViewById(R.id.scale_play_button);
        scalePlay.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view){
                scaleVideoView.start();
                scalePause.setVisibility(View.VISIBLE);
                scalePlay.setVisibility(View.INVISIBLE);
            }
        });
        seekForward = (ImageButton)findViewById(R.id.scale_seek_forward);
        seekForward.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view){
              scaleVideoView.seekTo(scaleVideoView.getCurrentPosition() + 1000);
                if(scaleVideoView.isPlaying() == true) {
                    scaleVideoView.start();
                }
            }
        });
        seekBackWard = (ImageButton)findViewById(R.id.scale_seek_back);
        seekBackWard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleVideoView.seekTo(scaleVideoView.getCurrentPosition() - 1000);
                if (scaleVideoView.isPlaying() == true) {
                    scaleVideoView.start();
                }
            }
        });
        submitButton = (ImageButton) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                //Error checking to make sure at least two points were selected
                if (count >= 2) {
                    String tester1 = String.valueOf(x1);
                    Log.d("x1", tester1);
                    String tester2 = String.valueOf(x2);
                    Log.d("x2", tester2);
                    String tester3 = String.valueOf(y1);
                    Log.d("y1", tester3);
                    String tester4 = String.valueOf(y2);
                    Log.d("y2", tester4);
                    Intent intent = new Intent(getApplicationContext(), ScaleActivity.class);
                    intent.putExtra("x1", x1);
                    intent.putExtra("x2", x2);
                    intent.putExtra("y1", y1);
                    intent.putExtra("y2", y2);
                    intent.putExtra("vidPath", vidPathString);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ScaleVideo.this, "Please Select 2 points", Toast.LENGTH_SHORT).show();}
            }
        });


}
    @Override
    public boolean onTouchEvent(MotionEvent ScaleEvent) {
        if(ScaleEvent.getAction() == MotionEvent.ACTION_UP){
            touchX = ScaleEvent.getX();
            touchY = ScaleEvent.getY();
            xCorr.add(new Integer(Math.round(touchX)));
            yCorr.add(new Integer(Math.round(touchY)));
            count = count + 1;
            if (count == 2) {
                x1 = xCorr.get(0);
                y1 = yCorr.get(0);
                x2 = xCorr.get(1);
                y2 = yCorr.get(1);
                Log.d("xTester",""+x1);
            }

        }
        return super.onTouchEvent(ScaleEvent);
    }
}





