package lt.vumifps.undzenastest;

public class Answer {

    String text;

    Boolean correct;
    Question parent;

    public Answer(String text, Boolean correct, Question parent) {
        this.text = text;
        this.correct = correct;
        this.parent = parent;
    }


    public String getText() {
        return text;
    }

    public String getTextFormatted() {
        String formattedText = text.trim();
        return Character.toUpperCase(formattedText.charAt(0)) + formattedText.substring(1);
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Question getParent() {
        return parent;
    }

    public void setParent(Question parent) {
        this.parent = parent;
    }
}
