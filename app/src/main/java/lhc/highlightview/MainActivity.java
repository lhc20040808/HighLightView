package lhc.highlightview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lhc.highlight.HighLight;
import com.lhc.highlight.position.bottomPosStategy;
import com.lhc.highlight.position.leftPosStrategy;
import com.lhc.highlight.position.rightPosStategy;
import com.lhc.highlight.position.topPosStrategy;
import com.lhc.highlight.shape.RectLightShape;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HighLight highLight = new HighLight(this)
                .addHighLight(R.id.tv_left_top, R.layout.info_gravity_left_down, new rightPosStategy(40), new RectLightShape(-5, 30))
                .addHighLight(R.id.tv_right_top, R.layout.info_gravity_left_down, new bottomPosStategy(40), new RectLightShape(10, 10))
                .addHighLight(R.id.tv_left_bottom, R.layout.info_gravity_left_down, new topPosStrategy(40), new RectLightShape(10, 10))
                .addHighLight(R.id.tv_right_bottom, R.layout.info_gravity_left_down, new leftPosStrategy(40), new RectLightShape(10, 10))
                .showInSequence()
                .show();
    }
}
