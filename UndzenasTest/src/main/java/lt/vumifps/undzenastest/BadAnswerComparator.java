package lt.vumifps.undzenastest;

import java.util.Comparator;

public class BadAnswerComparator implements Comparator<Question> {

    private StatsManager statsManager;

    public BadAnswerComparator(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @Override
    public int compare(Question question, Question question2) {

        QuestionStatistics questionStatistics = statsManager.loadStats(question.getCustomUniqueId());
        QuestionStatistics question2Statistics = statsManager.loadStats(question2.getCustomUniqueId());

        float questionScore = this.getQuestionIncorrectScore(questionStatistics);
        float question2Score = this.getQuestionIncorrectScore(question2Statistics);

        if (questionScore < question2Score) {
            return 1;
        } else if (questionScore > question2Score) {
            return -1;
        } else {
            return 0;
        }
    }

    private float getQuestionIncorrectScore(QuestionStatistics questionStatistics) {
        float answeredCorrectly = questionStatistics.getAnsweredCorrectly();
        float answeredIncorrectly = questionStatistics.getAnsweredIncorrectly();
        float totalAsked = answeredCorrectly + answeredIncorrectly;

        if (totalAsked == 0) {
            return 0;
        } else if (totalAsked == answeredIncorrectly) {
            return answeredIncorrectly;
        } else {
            return answeredIncorrectly / totalAsked;
        }
    }
}
