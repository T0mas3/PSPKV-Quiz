package lt.vumifps.undzenastest;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionStatistics {

    int answeredCorrectly;
    int answeredIncorrectly;
    int questionUniqueId;

    public QuestionStatistics(JSONObject jsonObject) throws JSONException {

        this.answeredCorrectly = jsonObject.getInt("answeredCorrectly");
        this.answeredIncorrectly = jsonObject.getInt("answeredIncorrectly");
        this.questionUniqueId = jsonObject.getInt("questionUniqueId");
    }

    public QuestionStatistics(int questionUniqueId) {
        answeredCorrectly = 0;
        answeredIncorrectly = 0;
        this.questionUniqueId = questionUniqueId;
    }

    public JSONObject toJson() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("answeredCorrectly", this.answeredCorrectly);
        jsonObject.put("answeredIncorrectly", this.answeredIncorrectly);
        jsonObject.put("questionUniqueId", this.questionUniqueId);

        return jsonObject;
    }

    public void incrementCorrectCount() {
        this.answeredCorrectly++;
    }

    public void incrementIncorrectCount() {
        this.answeredIncorrectly++;
    }

    public int getAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(int answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

    public int getAnsweredIncorrectly() {
        return answeredIncorrectly;
    }

    public void setAnsweredIncorrectly(int answeredIncorrectly) {
        this.answeredIncorrectly = answeredIncorrectly;
    }

    public int getQuestionUniqueId() {
        return questionUniqueId;
    }

    public void setQuestionUniqueId(int questionUniqueId) {
        this.questionUniqueId = questionUniqueId;
    }

}
