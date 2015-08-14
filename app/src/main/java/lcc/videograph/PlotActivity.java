package lcc.videograph;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joseph on 7/23/2015.
 */
public class PlotActivity extends Activity {

    //MediaController class is used with media for controls like play, stop, seek.....
    //MediaController myMediaController;
    //A view used to play videos.
    VideoView myVideoView;
    //Variable that is used to increment a number and frames.
    int count = 0;
    int clickCount = 0;
    double dnum = 0.0;
    int currentTime;
    float touchX, touchY;
    double xTouch, yTouch;
    double dTime;
    private  List<Double> time = new ArrayList<Double>();
    private  List<Double> xTap = new ArrayList<Double>();
    private  List<Double> yTap = new ArrayList<Double>();
    double scale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        String vidPath = getIntent().getStringExtra("vidPath");
        scale = getIntent().getDoubleExtra("Scale", 0);
        String timeTest = String.valueOf(scale);
        Log.d("MyTag4", timeTest);
        //Initializing videoView to link this java activity to the .xml layout.
        myVideoView = (VideoView) findViewById(R.id.plot_video);
        //Uniform Resource Identifier(Uri) is used as an address to identify things
        myVideoView.setVideoURI(Uri.parse(vidPath));
        //Initializing the MediaController for the video to be played.
        //myMediaController = new MediaController(this);
        //myVideoView.setMediaController(myMediaController);
        // These listeners help to get information about the videoview player
        // Completion listeners can be used to explaning when video is done, and what to do.
        myVideoView.setOnCompletionListener(myVideoViewCompletionListener);
        //Used to display time of still image from frame.
        myVideoView.setOnPreparedListener(MyVideoViewPreparedListener);
        //Used to display is any errors went on when video started or was playing.
        myVideoView.setOnErrorListener(myVideoViewErrorListener);
        //Sets focus on the widget
        myVideoView.requestFocus();
        TextView runTime = (TextView) findViewById(R.id.time_text);
        runTime.setText(Integer.toString(myVideoView.getCurrentPosition()) + " (ms)");
        myVideoView.seekTo(count);
        //Initialize seekbutton to be used to go frame-by-frame in video.
        ImageButton buttonFwdSeek = (ImageButton)findViewById(R.id.seek_fwd);
        //Set's OnClickListener to know when the button had been clicked, then executes the code.
        buttonFwdSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Gets the current position of video and makes sure its always less than the total
                 duration of the video so it doesn't over play.*/
                if(myVideoView.getCurrentPosition() < myVideoView.getDuration()) {
                    //Keeps a count and seeks video to specified frame..
                    //Which is 1/10 of a sec or 100 milliseconds.
                    currentTime = count;
                    //
                    count = myVideoView.getCurrentPosition() + 250;
                    TextView runTime = (TextView) findViewById(R.id.time_text);
                    runTime.setText(Integer.toString(myVideoView.getCurrentPosition()) + " (ms)");
                    myVideoView.seekTo(count);
                    //dnum = dnum + 33.33;
                    //count = (int)Math.round(dnum);


                }

            }
        });
        ImageButton buttonBackSeek = (ImageButton)findViewById(R.id.seek_back);
        buttonBackSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myVideoView.getCurrentPosition() >= 0){
                    count = myVideoView.getCurrentPosition() - 250;
                    //
                    currentTime = count;
                    myVideoView.seekTo(count
                    );
                    TextView runTime = (TextView)findViewById(R.id.time_text);
                    runTime.setText(Integer.toString(myVideoView.getCurrentPosition()) + " (ms)");
                }

            }
        });
        ImageButton backButton = (ImageButton)findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myVideoView.getCurrentPosition() >= 0){
                    count = myVideoView.getCurrentPosition() - 1000;
                    //
                    currentTime = count;
                    myVideoView.seekTo(count
                    );
                    TextView runTime = (TextView)findViewById(R.id.time_text);
                    runTime.setText(Integer.toString(myVideoView.getCurrentPosition()) + " (ms)");
                }
            }
        });
        ImageButton fwdButton = (ImageButton)findViewById(R.id.fwd_button);
        fwdButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(myVideoView.getCurrentPosition() < myVideoView.getDuration()) {
                    //Keeps a count and seeks video to specified frame.Which is 1 sec, let's user get through video faster.
                    currentTime = count;
                    myVideoView.seekTo(count);
                    count = myVideoView.getCurrentPosition() + 1000;
                    TextView runTime = (TextView)findViewById(R.id.time_text);
                    runTime.setText(Integer.toString(myVideoView.getCurrentPosition()) + " (ms)");
                }


            }
        });
        //Intent that restarts the current activity.
        ImageButton deleteButton = (ImageButton) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        ImageButton graphButton = (ImageButton) findViewById(R.id.graph_button);
        graphButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                if(clickCount >= 3) {
                    double tPoints[] = new double[time.size()];
                    for (int i = 0; i < time.size(); i++) {
                        tPoints[i] = time.get(i);
                    }
                    double xPoints[] = new double[xTap.size()];
                    for (int i = 0; i < xTap.size(); i++) {
                        xPoints[i] = xTap.get(i);
                    }

                    Intent graphIntent = new Intent(getApplicationContext(), GraphingActivity.class);
                    graphIntent.putExtra("tPoints", tPoints);
                    graphIntent.putExtra("xPoints", xPoints);
                    startActivity(graphIntent);
                }
                else{
                    Toast.makeText(PlotActivity.this, "plot more points", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent PlotEvent) {
        if (PlotEvent.getAction() == MotionEvent.ACTION_UP){
            clickCount = clickCount +1;
            touchX = PlotEvent.getX();
            touchY = PlotEvent.getY();
            xTouch = (double)(touchX);
            yTouch = (double)(touchY);
            xTap.add(scale * xTouch);
            yTap.add(scale * yTouch);
            dTime = (double)(currentTime);
            time.add(dTime);
            String xTest3 = String.valueOf(time);
            Log.d("x3", xTest3);
        }
        return super.onTouchEvent(PlotEvent);
    }

    MediaPlayer.OnCompletionListener myVideoViewCompletionListener =
            new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer arg0) {
                    Toast.makeText(PlotActivity.this, "End of Video",
                            Toast.LENGTH_LONG).show();
                }
            };

    MediaPlayer.OnPreparedListener MyVideoViewPreparedListener =
            new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {

                    long duration = myVideoView.getDuration(); //in millisecond
                    Toast.makeText(PlotActivity.this,
                            "Duration: " + duration + " (ms)",
                            Toast.LENGTH_LONG).show();

                }
            };

    MediaPlayer.OnErrorListener myVideoViewErrorListener =
            new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {

                    Toast.makeText(PlotActivity.this,
                            "ERROR....",
                            Toast.LENGTH_LONG).show();
                    return true;
                }
            };

}

