package lt.vumifps.undzenastest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class ResultsActivity extends Activity {

    public static final String RESULTS_KEY = "results";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        String result = intent.getStringExtra(ResultsActivity.RESULTS_KEY);
        TextView textView = (TextView) findViewById(R.id.resultText);
        textView.setTextSize(20);
        textView.setText(result);

    }

    public void restartQuiz(View view) {

        MainActivity.resultCount = 0;
        startActivity(new Intent(this,StartingActivity.class));
        finish();

    }
}
