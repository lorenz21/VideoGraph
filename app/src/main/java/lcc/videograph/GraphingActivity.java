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
        String xTest1 = String.valueOf(tPoints[0]);
        Log.d("x1", xTest1);
        String xTest2 = String.valueOf(tPoints[1]);
        Log.d("x2", xTest2);
        String xTest3 = String.valueOf(tPoints[2]);
        Log.d("x3", xTest3);

        super.onResume();
    }
}