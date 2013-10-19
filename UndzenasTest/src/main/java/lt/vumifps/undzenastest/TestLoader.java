package lt.vumifps.undzenastest;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;

public class TestLoader {


    private Context context;

    public TestLoader(Context context) {
        this.context = context;
    }

    private String loadQuestionsJsonString(int testNumber) throws IOException {
        InputStream is = this.context.getResources().openRawResource(testNumber);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        return writer.toString();
    }


    public LinkedList<Question> loadQuestions(int resourceId){

        String questionsJsonString;
        LinkedList<Question> questions = new LinkedList<Question>();

        try {
            questionsJsonString = loadQuestionsJsonString(resourceId);

            JSONArray questionsJsonArray = new JSONArray(questionsJsonString);

            for (int i = 0; i < questionsJsonArray.length(); i++) {

                try {
                    questions.add(new Question(questionsJsonArray.getJSONObject(i)));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return questions;
    }
}