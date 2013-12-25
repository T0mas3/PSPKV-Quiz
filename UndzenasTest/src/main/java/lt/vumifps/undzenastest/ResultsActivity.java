package lt.vumifps.undzenastest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultsActivity extends Activity {

    public static final String RESULTS_KEY = "results";
    public static final String WRONGLY_ANSWERED_QUESTIONS_JSON_KEY = "wrongly_answered_questions_json";
    public static final String WAS_RANDOMIZED_KEY = "was_randomized";
    private boolean wasRandomized;
    private Button quizForUnansweredButton;
    private Quiz wronglyAnsweredQuestionsQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_results);

        quizForUnansweredButton = (Button) this.findViewById(R.id.quizForUnansweredButton);

        Intent intent = getIntent();
        String result = intent.getStringExtra(ResultsActivity.RESULTS_KEY);

        String wronglyAnsweredJson = intent.getStringExtra(ResultsActivity.WRONGLY_ANSWERED_QUESTIONS_JSON_KEY);
        wasRandomized = intent.getBooleanExtra(ResultsActivity.WAS_RANDOMIZED_KEY, false);
        TextView textView = (TextView) findViewById(R.id.resultText);
        textView.setTextSize(20);
        textView.setText(result);

        try {

            wronglyAnsweredQuestionsQuiz = new Quiz(new JSONObject(wronglyAnsweredJson), -1);
            if (wronglyAnsweredQuestionsQuiz.getNumberOfquestions() == 0){
                quizForUnansweredButton.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void quizForUnanswered(View view){
        Intent intent = new Intent(this, MainActivity.class);

        if (wronglyAnsweredQuestionsQuiz != null)
        {
            if (wasRandomized) {
                wronglyAnsweredQuestionsQuiz.shuffleQuestions();
            }

            intent.putExtra(MainActivity.QUIZ_JSON_KEY,
                    wronglyAnsweredQuestionsQuiz.toJson().toString()
            );

            intent.putExtra(MainActivity.SHOULD_RANDOMIZE_KEY, wasRandomized);

            startActivity(intent);

        }

        finish();
    }

    public void restartQuiz(View view) {

        startActivity(new Intent(this,StartingActivity.class));
        finish();

    }
}
