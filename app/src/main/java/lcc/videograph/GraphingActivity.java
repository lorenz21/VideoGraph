package lcc.videograph;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GraphingActivity extends Activity {

    GraphView graphView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphing);



    }

    @Override
    protected void onResume() {
        graphView = (GraphView) findViewById(R.id.graph_view);
        double [] tPoints = getIntent().getDoubleArrayExtra("tPoints");
        double [] xPoints = getIntent().getDoubleArrayExtra("xPoints");
        graphView.setTPoints(tPoints);
        graphView.setXpoints(xPoints);

        super.onResume();
    }
}