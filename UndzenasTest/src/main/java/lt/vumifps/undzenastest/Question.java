package lt.vumifps.undzenastest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class Question {

    String question;
    LinkedList<Answer> answers;


    public Question(JSONObject jsonObject) throws JSONException {

        question = jsonObject.getString("question");

        answers = new LinkedList<Answer>();
        answers.add(new Answer(jsonObject.getString("cor"), true, this));
        answers.add(new Answer(jsonObject.getString("uncor1"), false, this));
        answers.add(new Answer(jsonObject.getString("uncor2"), false, this));
        answers.add(new Answer(jsonObject.getString("uncor3"), false, this));

    }

    public String getQuestion() {
        return question;
    }

    public int getAnswerCount(){
        return answers.size();
    }

    public Answer getAnswer(int i){
        return answers.get(i);
    }


    public LinkedList<Answer> getAnswers() {
        return answers;
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("question", this.question);
            int i = 1;
            for (Answer answer : answers) {

                if (answer.getCorrect()) {
                    jsonObject.put("cor", answer.getText());
                } else {
                    jsonObject.put("uncor" + i, answer.getText());
                    i++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public int getCustomUniqueId() {
        return this.question.toLowerCase().replaceAll("\\s+", "").hashCode();
    }
}
