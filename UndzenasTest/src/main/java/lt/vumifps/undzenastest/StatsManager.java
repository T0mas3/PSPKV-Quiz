package lt.vumifps.undzenastest;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class StatsManager {

    private static final String SHARED_PREFERENCES_KEY = "PSPKV-Quiz";
    private static final String PREFIX = "question:";


    private Context context;

    public StatsManager(Context context) {

        this.context = context;

    }

    public QuestionStatistics loadStats(int questionUniqueId) {

        SharedPreferences sharedPreferences = this.context.getSharedPreferences(
                SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE
        );

        try {

            String jsonString = sharedPreferences.getString(getKeyForQuestion(questionUniqueId), "");

            return new QuestionStatistics(new JSONObject(jsonString));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new QuestionStatistics(questionUniqueId);

    }

    public void saveStats(QuestionStatistics questionStatistics) {

        SharedPreferences sharedPreferences = this.context.getSharedPreferences(
                SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE
        );

        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(
                    getKeyForQuestion(questionStatistics.getQuestionUniqueId()),
                    questionStatistics.toJson().toString()
            );

            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void increaseCorrect(Question question) {
        QuestionStatistics questionStatistics = this.loadStats(question.getCustomUniqueId());

        if (questionStatistics != null) {
            questionStatistics.incrementCorrectCount();
            this.saveStats(questionStatistics);
        }
    }

    public void increaseIncorrect(Question question) {
        QuestionStatistics questionStatistics = this.loadStats(question.getCustomUniqueId());

        if (questionStatistics != null) {
            questionStatistics.incrementIncorrectCount();
            this.saveStats(questionStatistics);
        }
    }

    private String getKeyForQuestion(int questionId) {
        return PREFIX + String.valueOf(questionId);
    }

}
