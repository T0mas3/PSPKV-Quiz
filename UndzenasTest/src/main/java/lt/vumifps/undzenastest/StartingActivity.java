package lt.vumifps.undzenastest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import java.util.LinkedList;


public class StartingActivity extends Activity implements OnQuizListItemClickListener {

    LinkedList<Quiz> quizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_starting);

        TestLoader loader = new TestLoader(this);
        quizzes = loader.loadAllQuizzes();

        ListView quizzesListView = (ListView) this.findViewById(R.id.quizzesListView);
        QuizzesListViewAdapter quizzesListViewAdapter = new QuizzesListViewAdapter(this, quizzes);
        quizzesListViewAdapter.setOnQuizListItemClickListener(this);
        quizzesListView.setAdapter(quizzesListViewAdapter);
    }


    @Override
    public void onQuizClick(int index, int resId, boolean shouldRandomize) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.JSON_RES_ID_KEY, resId);
        intent.putExtra(MainActivity.QUIZ_JSON_KEY, quizzes.get(index).toJson().toString());
        intent.putExtra(MainActivity.SHOULD_RANDOMIZE_KEY, shouldRandomize);
        startActivity(intent);
        finish();
    }

}
