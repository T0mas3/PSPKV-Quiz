package lt.vumifps.undzenastest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by Nastute on 13.10.17.
 */
public class StartingActivity extends Activity implements View.OnClickListener{

    public final static String EXTRA_MESSAGE = "lt.vumifps.undzenastest.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_starting);

        Button button_test1 = (Button) this.findViewById(R.id.button_test1);
        button_test1.setOnClickListener(this);

        Button button_test2 = (Button) this.findViewById(R.id.button_test2);
        button_test2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    int raw_data_number= 0;
        if (view.getId() == R.id.button_test1) {
            Intent intent = new Intent(this,MainActivity.class);
            raw_data_number = R.raw.questions;
            intent.putExtra("raw_data_number",raw_data_number);
            startActivity(intent);
        }
        if (view.getId() == R.id.button_test2) {
            Intent intent = new Intent(this,MainActivity.class);
            raw_data_number = R.raw.questions2;
            intent.putExtra("raw_data_number",raw_data_number);
            startActivity(intent);
            finish();
        }

    }



}
