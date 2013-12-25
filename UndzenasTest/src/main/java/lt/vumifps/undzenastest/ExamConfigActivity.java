package lt.vumifps.undzenastest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class ExamConfigActivity extends Activity implements View.OnClickListener {

    private static final int DEFAULT_NUM_QUESTIONS = 20;
    private Quiz filteredQuiz;
    private CheckBox shouldRandomizeCheckBox;
    private EditText questionsCountTextView;
    private CheckBox showAllCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exam_config);

        Intent intent = getIntent();
        String quizJsonString= intent.getStringExtra(MainActivity.QUIZ_JSON_KEY);

        try {
            filteredQuiz = new Quiz(new JSONObject(quizJsonString), -1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        shouldRandomizeCheckBox = (CheckBox) findViewById(R.id.randomizeCheckBox);
        showAllCheckBox = (CheckBox) findViewById(R.id.showAllCheckBox);
        questionsCountTextView = (EditText) findViewById(R.id.questionsCountEditText);
        questionsCountTextView.setText(String.valueOf(DEFAULT_NUM_QUESTIONS));


        Button startExamButton = (Button) findViewById(R.id.startButton);
        startExamButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        boolean shouldRandomize = shouldRandomizeCheckBox.isChecked();

        if (shouldRandomize) {
            filteredQuiz.shuffleQuestions();
        }

        if (!showAllCheckBox.isChecked()) {
            int questionsCount = DEFAULT_NUM_QUESTIONS;

            if (questionsCountTextView.getText() != null) {
                String questionsCountString = questionsCountTextView.getText().toString();

                try {
                    questionsCount = Integer.parseInt(questionsCountString);
                } catch (NumberFormatException e) {
                    // We should do something here
                }
            }

            if ((questionsCount < filteredQuiz.getQuestions().size()) && (questionsCount > 0)) {
                filteredQuiz.truncateTo(questionsCount);
            }
        }


        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.JSON_RES_ID_KEY, -1);
        intent.putExtra(MainActivity.QUIZ_JSON_KEY, filteredQuiz.toJson().toString());
        intent.putExtra(MainActivity.SHOULD_RANDOMIZE_KEY, shouldRandomize);
        startActivity(intent);
    }
}
