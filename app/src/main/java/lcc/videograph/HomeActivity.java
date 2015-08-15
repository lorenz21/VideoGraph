package lcc.videograph;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
String vidPathString;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.captureButton();
        this.analyzeButton();
        this.guidesButton();
        this.aboutButton();
        }

        private void aboutButton() {
                LinearLayout about = (LinearLayout)findViewById(R.id.linearLayout_about);
                about.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent whoIntent = new Intent(HomeActivity.this, AboutActivity.class);
                                startActivity(whoIntent);
                        }
                });
        }

private void captureButton() {
        LinearLayout capture = (LinearLayout)findViewById(R.id.linearLayout_capture);
        capture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intentToRecordVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        if (intentToRecordVideo.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(intentToRecordVideo, 1);
                        }
                }
        });
        }

private void analyzeButton(){
        LinearLayout analyze = (LinearLayout)findViewById(R.id.linearLayout_analyze);
        analyze.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        this.openGallery();
                }

                private void openGallery() {
                        Intent intentOpenGallery = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intentOpenGallery, 2);
                }
        });
    }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                try {
                        // When an Video is picked
                        if (requestCode == 2 && resultCode == RESULT_OK
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
                                Intent i = new Intent(HomeActivity.this , ScaleVideo.class);
                                i.putExtra("vidPath", vidPathString);
                                startActivity(i);
                        }

                }
                catch (Exception e) {
                        Toast.makeText(this, "There was an error", Toast.LENGTH_LONG)
                                .show();

                }
        }
        private void guidesButton() {
                LinearLayout guides = (LinearLayout)findViewById(R.id.linearLayout_guides);
                guides.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent intentToGuides = new Intent(HomeActivity.this, Guides2Activity.class);
                                startActivity(intentToGuides);
                        }

                });
        }
}

