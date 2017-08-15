package lhc.highlightview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lhc.highlight.HighLight;
import com.lhc.highlight.position.BottomPosStrategy;
import com.lhc.highlight.position.LeftPosStrategy;
import com.lhc.highlight.position.RightPosStrategy;
import com.lhc.highlight.position.TopPosStrategy;
import com.lhc.highlight.shape.RectLightShape;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HighLight highLight = new HighLight(this)
                .addHighLight(R.id.tv_left_top, R.layout.info_gravity_left_down, new RightPosStrategy(40), new RectLightShape(-5, 30))
                .addHighLight(R.id.tv_right_top, R.layout.info_gravity_left_down, new BottomPosStrategy(40), new RectLightShape(10, 10))
                .addHighLight(R.id.tv_left_bottom, R.layout.info_gravity_left_down, new TopPosStrategy(40), new RectLightShape(10, 10))
                .addHighLight(R.id.tv_right_bottom, R.layout.info_gravity_left_down, new LeftPosStrategy(40), new RectLightShape(10, 10))
                .showInSequence()
                .show();
    }
}
