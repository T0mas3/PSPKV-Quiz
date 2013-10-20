package lt.vumifps.undzenastest;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.LinkedList;

public class QuizzesListViewAdapter extends ArrayAdapter {

    private static int LAYOUT_ID = R.layout.quizzes_list_row;

    Activity activity;
    LinkedList<Quiz> quizzes;


    public QuizzesListViewAdapter(Activity activity, LinkedList<Quiz> quizzes){
        super(activity, QuizzesListViewAdapter.LAYOUT_ID);

        this.activity = activity;
        this.quizzes = quizzes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater inflater = this.activity.getLayoutInflater();
            convertView = inflater.inflate(QuizzesListViewAdapter.LAYOUT_ID, parent, false);

            viewHolder = new ViewHolder();
            if (convertView != null) {
                viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
                convertView.setTag(viewHolder);
            }

        }else{

            viewHolder = (ViewHolder) convertView.getTag();
        }

        Quiz currentQuiz = quizzes.get(position);

        if(currentQuiz != null) {
            viewHolder.titleTextView.setText(currentQuiz.getTitle());
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return this.quizzes.size();
    }

    static class ViewHolder {
        TextView titleTextView;
    }

}
