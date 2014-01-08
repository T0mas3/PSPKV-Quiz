package lt.vumifps.undzenastest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import java.util.LinkedList;


public class StartingActivity extends Activity implements
        OnQuizListItemClickListener,
        View.OnClickListener {

    private LinkedList<Quiz> quizzes;
    private TestLoader loader;
    private Button examButton;
    private Button clearStatsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_starting);


        loader = new TestLoader(this);
        quizzes = loader.loadAllQuizzes(this.getResources());

        ListView quizzesListView = (ListView) this.findViewById(R.id.quizzesListView);
        QuizzesListViewAdapter quizzesListViewAdapter = new QuizzesListViewAdapter(this, quizzes);
        quizzesListViewAdapter.setOnQuizListItemClickListener(this);
        quizzesListView.setAdapter(quizzesListViewAdapter);


        examButton = (Button) findViewById(R.id.examButton);
        examButton.setOnClickListener(this);
        clearStatsButton = (Button) findViewById(R.id.clearStats);
        clearStatsButton.setOnClickListener(this);
    }


    @Override
    public void onQuizClick(int index, int resId, boolean shouldRandomize) {
        Intent intent = new Intent(this, MainActivity.class);

        Quiz quizToLoad = quizzes.get(index);

        if (shouldRandomize) {
            quizToLoad.shuffleQuestions();
        }

        intent.putExtra(MainActivity.JSON_RES_ID_KEY, resId);
        intent.putExtra(MainActivity.QUIZ_JSON_KEY, quizToLoad.toJson().toString());
        intent.putExtra(MainActivity.SHOULD_RANDOMIZE_KEY, shouldRandomize);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        if (view == examButton) {
            Intent intent = new Intent(this, ExamConfigActivity.class);

            Quiz combinedQuiz = loader.getCombinedQuiz(quizzes);

            intent.putExtra(MainActivity.JSON_RES_ID_KEY, -1);
            intent.putExtra(MainActivity.QUIZ_JSON_KEY, combinedQuiz.toJson().toString());
            startActivity(intent);
        } else if (view == clearStatsButton) {
            new AlertDialog.Builder(this)
                    .setTitle(StartingActivity.this.getResources().getString(R.string.clear_stats_dialog_title))
                    .setMessage(StartingActivity.this.getResources().getString(R.string.clear_stats) + "?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            StatsManager statsManager = new StatsManager(StartingActivity.this);
                            statsManager.clearStats();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }

    }
}

