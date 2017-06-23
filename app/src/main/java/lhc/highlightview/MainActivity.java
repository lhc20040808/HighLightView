package lhc.highlightview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lhc.highlightview.position.leftPosStrategy;
import lhc.highlightview.shape.RectLightShape;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HighLight highLight = new HighLight(this)
                .addHighLight(R.id.tv_left, R.layout.info_gravity_left_down, new leftPosStrategy(40), new RectLightShape(0, 0, 10))
                .addHighLight(R.id.tv_left_1, R.layout.info_gravity_left_down, new leftPosStrategy(40), new RectLightShape(0, 0, 10))
                .showInSequence()
                .show();
    }
}
