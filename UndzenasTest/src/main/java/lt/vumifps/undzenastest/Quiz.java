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

    public Quiz(String title) {
        this.title = title;
        questions = new LinkedList<Question>();
        resourceId = -1;
    }


    public void appendQuiz(Quiz quiz) {
        LinkedList<Question> newQuestions = quiz.getQuestions();
        if (newQuestions.size() > 0) {
            for (Question newQuestion : newQuestions) {
                this.questions.add(newQuestion);
            }
        }
    }

    public void truncateTo(int count) {
        if ((count < this.questions.size()) && (count >= 0)) {
            LinkedList<Question> newQuestions = new LinkedList<Question>();

            for (int i = 0; i < count; i++) {
                newQuestions.add(this.questions.get(i));
            }

            this.questions = newQuestions;
        }
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", this.getTitle());

            JSONArray jsonArray = new JSONArray();
            for (Question question : questions) {
                jsonArray.put(question.toJson());
            }


            jsonObject.put("questions", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
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

    public void addQuestion(Question question){
        questions.add(question);
    }
}
