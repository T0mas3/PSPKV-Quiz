package lt.vumifps.undzenastest;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;

public class AnswerTextView extends TextView implements View.OnClickListener {

    boolean correct;
    Answer answer;
    LinkedList<OnClickListener> onClickListeners = new LinkedList<OnClickListener>();


    public AnswerTextView(Context context) {
        super(context);
        this.setOnClickListener(this);
    }

    public AnswerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(this);

    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public void setAnswer(Answer answer){

        this.correct = answer.getCorrect();
        this.setText(answer.getText());
        this.answer = answer;

    }

    public Answer getAnswer() {
        return answer;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {

        if (l != this){
            this.onClickListeners.add(l);
        } else {
            super.setOnClickListener(l);
        }

    }

    @Override
    public void onClick(View view) {

        for (View.OnClickListener onClickListener: this.onClickListeners){

            if (onClickListener != this){
                onClickListener.onClick(this);
            }
        }

        if (this.correct){
            this.setBackgroundColor(Color.GREEN);

        } else {
            this.setBackgroundColor(Color.RED);
        }

    }
}
