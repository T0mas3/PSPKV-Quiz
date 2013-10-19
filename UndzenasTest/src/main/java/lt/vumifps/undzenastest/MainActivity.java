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
    ProgressBar progressBar;
    TextView progressTextView;

    int currentIndex;
    public static int resultCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        String questionsJsonString;
        questions = new LinkedList<Question>();

        Intent intent = getIntent();
        int testNumber = intent.getIntExtra("raw_data_number",0);

        try {
            questionsJsonString = loadQuestionsJsonString(testNumber);

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

        currentIndex = 0;

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressTextView = (TextView) findViewById(R.id.progressTextView);

        progressTextView.setText(getProgressText(progressBar.getMax(), currentIndex+1));


        answerLayout = (LinearLayout) this.findViewById(R.id.answersLinearLayout);
        mainScrollView = (ScrollView) this.findViewById(R.id.mainScrollView);
        Button homeButton = (Button) this.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);

        Button nextButton = (Button) this.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        Collections.shuffle(questions);

        showQuestion(questions.get(0));
    }


    private void showQuestion(Question question) {

        TextView questionsTextView = (TextView) this.findViewById(R.id.questionTextView);
        questionsTextView.setText(question.getQuestion());

        LinkedList<Answer> answers = question.getAnswers();
        Collections.shuffle(answers);

        answerLayout.removeAllViews();
        mainScrollView.fullScroll(View.FOCUS_UP);

        for (Answer answer : answers) {
            AnswerTextView answerTextView = getAnswerTextView(answer);
            answerLayout.addView(answerTextView);
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

    private String loadQuestionsJsonString(int testNumber) throws IOException {
        InputStream is = getResources().openRawResource(testNumber);
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

        switch (view.getId()){
            case R.id.nextButton:

                this.showNextQuestion();
                progressBar.incrementProgressBy(1);

                break;


            case R.id.homeButton:
                this.finish();
                Intent homeIntent = new Intent(this, StartingActivity.class);
                startActivity(homeIntent);
                break;
        }
    }

    private void showNextQuestion() {
        currentIndex++;

        if (currentIndex+1 > questions.size()) {
            Collections.shuffle(questions);
            currentIndex = 0;
            //Toast.makeText(this, "Fsio, rodau iš naujo random tvarka", Toast.LENGTH_LONG).show();
            progressBar.setProgress(0);
            Intent resultsIntent = new Intent(this, ResultsActivity.class);
            String results = resultCount + "/" + progressBar.getMax();
            resultsIntent.putExtra(StartingActivity.EXTRA_MESSAGE,results);
            startActivity(resultsIntent);
            finish();
        } else {
            showQuestion(questions.get(currentIndex));

            progressTextView.setText(getProgressText(progressBar.getMax(), currentIndex+1));
        }
    }


    private String getProgressText(int total, int current) {
        return current + " / " + total;
    }
}
