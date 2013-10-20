package lt.vumifps.undzenastest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.LinkedList;


public class StartingActivity extends Activity implements AdapterView.OnItemClickListener {

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
        quizzesListView.setAdapter(quizzesListViewAdapter);

        quizzesListView.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

        Intent intent = new Intent(this,MainActivity.class);

        intent.putExtra(MainActivity.JSON_RES_ID_KEY, this.quizzes.get(index).getResourceId());
        startActivity(intent);
        finish();

    }
}
