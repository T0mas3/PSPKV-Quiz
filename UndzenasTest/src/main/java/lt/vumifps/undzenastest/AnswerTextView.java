package lt.vumifps.undzenastest;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by tomas on 9/29/13.
 */

public class AnswerTextView extends TextView implements View.OnClickListener {

    boolean correct;

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

    }

    @Override
    public void onClick(View view) {

        if (this.correct){
            this.setBackgroundColor(Color.GREEN);
        } else {
            this.setBackgroundColor(Color.RED);
        }

    }
}
