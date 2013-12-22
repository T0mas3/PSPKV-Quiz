package lt.vumifps.undzenastest;

import android.content.Context;
import android.content.res.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
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


    public LinkedList<Quiz> loadAllQuizzes(Resources resources){

        LinkedList<Quiz> quizzes = new LinkedList<Quiz>();

        Field[] fields = R.raw.class.getFields();
        for (Field field : fields) {

            try {
                int rid = field.getInt(field);
                if (resources.getResourceName(rid).contains("question")) {
                    quizzes.add(this.loadSingleQuiz(rid));
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        return quizzes;
    }

    public Quiz loadSingleQuiz(int resId){
        try {

            String jsonString = loadQuestionsJsonString(resId);

            JSONObject quizJsonObject = new JSONObject(jsonString);

            return new Quiz(quizJsonObject, resId);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}