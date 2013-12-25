package lt.vumifps.undzenastest;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class StatsManager {

    private static final String PREFIX = "question:";

    private SharedPreferencesHelper sharedPreferencesHelper;

    public StatsManager(Context context) {

        this.sharedPreferencesHelper = new SharedPreferencesHelper(context);
    }

    public QuestionStatistics loadStats(int questionUniqueId) {

        try {
            String jsonString = sharedPreferencesHelper.loadString(getKeyForQuestion(questionUniqueId));

            return new QuestionStatistics(new JSONObject(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new QuestionStatistics(questionUniqueId);

    }

    public void saveStats(QuestionStatistics questionStatistics) {

        try {
            sharedPreferencesHelper.saveString(
                    getKeyForQuestion(questionStatistics.getQuestionUniqueId()),
                    questionStatistics.toJson().toString()
            );
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
