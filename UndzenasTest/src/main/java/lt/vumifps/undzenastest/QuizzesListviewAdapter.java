package lt.vumifps.undzenastest;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.LinkedList;

public class QuizzesListViewAdapter extends ArrayAdapter {

    private static int LAYOUT_ID = R.layout.quizzes_list_row;

    Activity activity;
    LinkedList<Quiz> quizzes;
    private OnQuizListItemClickListener onQuizListItemClickListener;


    public QuizzesListViewAdapter(Activity activity, LinkedList<Quiz> quizzes){
        super(activity, QuizzesListViewAdapter.LAYOUT_ID);

        this.activity = activity;
        this.quizzes = quizzes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater inflater = this.activity.getLayoutInflater();
            convertView = inflater.inflate(QuizzesListViewAdapter.LAYOUT_ID, parent, false);

            viewHolder = new ViewHolder();
            if (convertView != null) {
                viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
                viewHolder.shouldRandomizeCheckbox = (CheckBox) convertView.findViewById(R.id.randomizeCheckBox);
                viewHolder.startButton = (Button) convertView.findViewById(R.id.startButton);
                convertView.setTag(viewHolder);
            }

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Quiz currentQuiz = quizzes.get(position);

        if(currentQuiz != null) {
            viewHolder.titleTextView.setText(currentQuiz.getTitle());

                View.OnClickListener startOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onQuizListItemClickListener != null) {
                            onQuizListItemClickListener.onQuizClick(currentQuiz.getResourceId(), viewHolder.shouldRandomizeCheckbox.isChecked());
                        }
                    }
                };

            viewHolder.startButton.setOnClickListener(startOnClickListener);
        }

        return convertView;
    }

    public void setOnQuizListItemClickListener(OnQuizListItemClickListener l){
        this.onQuizListItemClickListener = l;
    }

    @Override
    public int getCount() {
        return this.quizzes.size();
    }

    static class ViewHolder {
        TextView titleTextView;
        CheckBox shouldRandomizeCheckbox;
        Button startButton;
    }

}
