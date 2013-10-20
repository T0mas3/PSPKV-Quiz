package lt.vumifps.undzenastest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.LinkedList;

public class Quiz {

    String title;
    LinkedList<Question> questions;
    int resourceId;

    public Quiz(JSONObject jsonObject, int resourceId){

        this.resourceId = resourceId;
        questions = new LinkedList<Question>();

        try {
            this.title = jsonObject.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray questionsJsonArray = jsonObject.getJSONArray("questions");

            for (int i = 0; i < questionsJsonArray.length(); i++) {

                try {
                    questions.add(new Question(questionsJsonArray.getJSONObject(i)));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getNumberOfquestions(){
        return questions.size();
    }

    public Question getQuestion(int index){
        return this.questions.get(index);
    }


    public void shuffleQuestions(){
        Collections.shuffle(this.questions);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LinkedList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(LinkedList<Question> questions) {
        this.questions = questions;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
