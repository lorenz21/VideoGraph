package lcc.videograph;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


public class  MainActivity extends Activity {
    private static int RESULT_LOAD_IMG = 1;
    String vidPathString;
    MediaController myMediaController;
    VideoView myVideoView;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.openGallery();

    }

    private void openGallery(){
        Intent intentOpenGallery = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intentOpenGallery, RESULT_LOAD_IMG);
    }

    /*public void loadVideoFromGallery(View view) {
        // Create intent to Open Video applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Video is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
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
                vidPathString = cursor.getString(columnIndex);
                cursor.close();
                Button submitButton = (Button) findViewById(R.id.submit_button);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View View) {
                        Intent intent = new Intent(getApplicationContext(), PlotActivity.class);
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
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }


    }

}



