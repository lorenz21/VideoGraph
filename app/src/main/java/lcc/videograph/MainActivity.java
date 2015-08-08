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
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


public class  MainActivity extends Activity {
    String vidPathString;
    MediaController myMediaController;
    VideoView myVideoView;
    int count = 0;

    GraphData data;
    ScaleView test;
    int x1;
    int x2;
    int y1;
    int y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.openGallery();
        test = (ScaleView)findViewById(R.id.scaleView);
        data = test.getData();
        if (data != null) {
            x1 = data.getX1();
            x2 = data.getX2();
            y1 = data.getY1();
            y2 = data.getY2();
        }
        String test1 = String.valueOf(x1);
        Log.d("x1", test1);
        String test2 = String.valueOf(x2);
        Log.d("x2", test2);


    }

    private void openGallery(){
        Intent intentOpenGallery = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intentOpenGallery, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Video is picked
            if (requestCode == 1 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Video from data
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedVideo,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                // Used to get path of selected video.
                vidPathString = cursor.getString(columnIndex);
                cursor.close();
                Button submitButton = (Button) findViewById(R.id.submit_button);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View View) {
                        Intent intent = new Intent(getApplicationContext(), ScaleActivity.class);
                        intent.putExtra("x1",x1);
                        intent.putExtra("x2",x2);
                        intent.putExtra("y1",y1);
                        intent.putExtra("y2",y2);
                        intent.putExtra("vidPath", vidPathString);
                        startActivity(intent);
                    }
                });
                myVideoView = (VideoView) findViewById(R.id.videoView);
                // Set the Image in ImageView after decoding the String
                myVideoView.setVideoPath(vidPathString);
                myMediaController = new MediaController(this);
                myVideoView.setMediaController(myMediaController);
                //Set the surface holder height to the screen dimensions
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                myVideoView.getHolder().setFixedSize(size.x, size.y);
                myVideoView.start();

            } else {
                Toast.makeText(this, "You haven't selected a video",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "There was an error", Toast.LENGTH_LONG)
                    .show();
        }



    }

}



