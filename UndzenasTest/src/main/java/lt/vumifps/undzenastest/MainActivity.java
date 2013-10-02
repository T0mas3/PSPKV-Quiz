package lt.vumifps.undzenastest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.LinkedList;

public class MainActivity extends Activity implements View.OnClickListener {

    LinkedList<Question> questions;

    LinearLayout answerLayout;
    ScrollView mainScrollView;

    int currentIndex;
    public static int resultCount = 0;
    public final static String EXTRA_MESSAGE = "lt.vumifps.undzenastest.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        String questionsJsonString = "";
        questions = new LinkedList<Question>();

        try {
            questionsJsonString = loadQuestionsJsonString();

            JSONArray questionsJsonArray = new JSONArray(questionsJsonString);

            for (int i = 0; i < questionsJsonArray.length(); i++) {

                try {
                    questions.add(new Question(questionsJsonArray.getJSONObject(i)));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        answerLayout = (LinearLayout) this.findViewById(R.id.answersLinearLayout);
        mainScrollView = (ScrollView) this.findViewById(R.id.mainScrollView);

        Button nextButton = (Button) this.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        Collections.shuffle(questions);

        currentIndex = 0;
        showQuestion(questions.get(0));
    }


    private void showQuestion(Question question) {

        TextView questionsTextView = (TextView) this.findViewById(R.id.questionTextView);
        questionsTextView.setText(question.getQuestion());

        LinkedList<AnswerTextView> answerTextViews = new LinkedList<AnswerTextView>();


        LinkedList<Answer> answers = question.getAnswers();
        Collections.shuffle(answers);

        answerLayout.removeAllViews();
        mainScrollView.fullScroll(View.FOCUS_UP);

        for (Answer answer : answers) {
            AnswerTextView answerTextView = getAnswerTextView(answer);
            answerLayout.addView(answerTextView);
            answerTextViews.add(answerTextView);

        }

    }

    private AnswerTextView getAnswerTextView(Answer answer) {

        AnswerTextView answerTextView = new AnswerTextView(this);
        answerTextView.setAnswer(answer);

        LinearLayout.LayoutParams linearLayoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        linearLayoutParams.setMargins(0, 10, 0, 10);
        answerTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        answerTextView.setBackgroundColor(Color.parseColor("#E0E0E0"));

        answerTextView.setLayoutParams(linearLayoutParams);

        return answerTextView;
    }

    private String loadQuestionsJsonString() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.questions);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        return writer.toString();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.nextButton) {
            this.showNextQuestion();
            ((ProgressBar) findViewById(R.id.progressBar)).incrementProgressBy(1);
        }
    }

    private void showNextQuestion() {
        currentIndex++;
        if (currentIndex + 1 > questions.size()) {
            Collections.shuffle(questions);
            currentIndex = 0;
            Toast.makeText(this, "Fsio, rodau iš naujo random tvarka", Toast.LENGTH_LONG).show();
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setProgress(0);
            Intent resultsIntent = new Intent(this, ResultsActivity.class);
            String results = resultCount + "/" + progressBar.getMax();
            resultsIntent.putExtra(EXTRA_MESSAGE,results);
            startActivity(resultsIntent);
            finish();
        }

        showQuestion(questions.get(currentIndex));
    }
}
