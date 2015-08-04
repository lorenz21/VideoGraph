package lcc.videograph;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
    int back = 0;
    double dnum = 0.0;
    int currentTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        final View drawInstance = (View) findViewById(R.id.drawView);
        String vidPath = getIntent().getStringExtra("vidPath");
        //Initializing videoView to link this java activity to the .xml layout.
        myVideoView = (VideoView) findViewById(R.id.videoView2);
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
        //Initialize seekbutton to be used to go frame-by-frame in video
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
                    drawInstance.setTag(currentTime);
                    myVideoView.seekTo(count);
                    //dnum = dnum + 33.33;
                    //count = (int)Math.round(dnum);
                    count = myVideoView.getCurrentPosition() + 250;
                    TextView runTime = (TextView) findViewById(R.id.time_text);
                    runTime.setText(Integer.toString(myVideoView.getCurrentPosition()) + " (ms)");

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
                    drawInstance.setTag(currentTime);
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
                    drawInstance.setTag(currentTime);
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
                    drawInstance.setTag(currentTime);
                    myVideoView.seekTo(count);
                    count = myVideoView.getCurrentPosition() + 1000;
                    TextView runTime = (TextView)findViewById(R.id.time_text);
                    runTime.setText(Integer.toString(myVideoView.getCurrentPosition()) + " (ms)");
                }


            }
        });
        ImageButton deleteButton = (ImageButton) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        final ArrayList<Integer> time = (ArrayList<Integer>) DrawView.getTime();
        ImageButton graphButton = (ImageButton) findViewById(R.id.graph_button);
        graphButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                String timeTest = String.valueOf(time);
                Log.d("MyTag4", timeTest);
            }
        });


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

