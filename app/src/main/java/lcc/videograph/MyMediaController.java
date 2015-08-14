package lcc.videograph;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.MediaController;

/**
 * Created by steve on 8/11/15.
 */
public class MyMediaController extends MediaController {
    public MyMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
    }

    public MyMediaController(Context context) {
        super(context);
    }

    @Override
    public void show(int timeout) {
        super.show(0);
    }

}