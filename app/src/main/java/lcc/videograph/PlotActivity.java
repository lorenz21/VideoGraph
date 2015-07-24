package lcc.videograph;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * Created by Joseph on 7/23/2015.
 */
public class PlotActivity extends Activity {
    //MediaMetadataRetriever class provides a unified interface for retrieving frame and meta data from an input media file
    MediaMetadataRetriever mediaMetadataRetriever;
    //MediaController class is used with media for controls like play, stop, seek.....
    //MediaController myMediaController;
    //A view used to play videos.
    VideoView myVideoView;
    //Variable that is used to increment a number and frames.
    int count = 0;
    int back = 0;
    double dnum = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        String vidPath = getIntent().getStringExtra("vidPath");
        //Initializing mediaMetadataRetriever and passing the video path as a parameter.
        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(vidPath);
        //Initializing videoView to link this java activity to the .xml layout.
        myVideoView = (VideoView) findViewById(R.id.videoView2);
        //Uniform Resource Identifier(Uri) is used as an address to identify things
        myVideoView.setVideoURI(Uri.parse(vidPath));
        //Initializing the MediaController for the video to be played.
        //myMediaController = new MediaController(this);
        //myVideoView.setMediaController(myMediaController);
        // These listeners help to get information about the videoview player
        // Completion listeners can be used to explaing when video is done, and what to do.
        myVideoView.setOnCompletionListener(myVideoViewCompletionListener);
        //Used to display time of still image from frame.
        myVideoView.setOnPreparedListener(MyVideoViewPreparedListener);
        //Used to display is any errors went on when video started or was playing.
        myVideoView.setOnErrorListener(myVideoViewErrorListener);
        //Sets focus on the widget
        myVideoView.requestFocus();
        //Initialize seekbutton to be used to go frame-by-frame in video
        Button buttonFwdSeek = (Button)findViewById(R.id.seek_fwd);
        //Set's OnClickListener to know when the button had been clicked, then executes the code.
        buttonFwdSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Gets the current position of video and makes sure its always less than the total
                 duration of the video so it doesn't over play.*/
                if(myVideoView.getCurrentPosition() < myVideoView.getDuration()) {
                    //Keeps a count and seeks video to specified frame..
                    //Which is 1/10 of a sec or 100 milliseconds.
                    myVideoView.seekTo(count);
                    //dnum = dnum + 33.33;
                    //count = (int)Math.round(dnum);
                    count = myVideoView.getCurrentPosition() + 250;
                    TextView runTime = (TextView)findViewById(R.id.time_text);
                    runTime.setText(Integer.toString(myVideoView.getCurrentPosition()) + " (ms)");
                }

            }
        });
        Button buttonBackSeek = (Button)findViewById(R.id.seek_back);
        buttonBackSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myVideoView.getCurrentPosition() >= 0){
                    count = myVideoView.getCurrentPosition() - 250;
                    myVideoView.seekTo(count
                    );
                    TextView runTime = (TextView)findViewById(R.id.time_text);
                    runTime.setText(Integer.toString(myVideoView.getCurrentPosition()) + " (ms)");
                }

            }
        });
        //Initializes capture button which takes a snapshoot of current frame and displays current time.
        Button buttonCapture = (Button)findViewById(R.id.capture_button);
        buttonCapture.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                //Used to print the current time on screenshot
                int currentPosition = myVideoView.getCurrentPosition(); //in millisecond
                //A toast provides simple feedback about an operation in a small popup
                Toast.makeText(PlotActivity.this,
                        "Current Position: " + currentPosition + " (ms)",
                        Toast.LENGTH_LONG).show();
                //BitMap defines a display space and the color for each pixel or "bit" in the display space
                Bitmap bmFrame = mediaMetadataRetriever
                        .getFrameAtTime(currentPosition * 1000); //unit in microsecond
                //If no bitmap or images is avaliable displays a message to user.
                if(bmFrame == null){
                    Toast.makeText(PlotActivity.this,
                            "bmFrame == null!",
                            Toast.LENGTH_LONG).show();
                }else{
                    AlertDialog.Builder myCaptureDialog =
                            new AlertDialog.Builder(PlotActivity.this);
                    //Initialize the imageview which is used to display images
                    ImageView capturedImageView = new ImageView(PlotActivity.this);
                    //Set the bitmap and pass the current screen frame as parameter
                    capturedImageView.setImageBitmap(bmFrame);
                    //Set the parameters of the current image to the view
                    ViewGroup.LayoutParams capturedImageViewLayoutParams =
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                    capturedImageView.setLayoutParams(capturedImageViewLayoutParams);
                    //
                    myCaptureDialog.setView(capturedImageView);
                    myCaptureDialog.show();
                }

            }});
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

