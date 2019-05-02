package paul.cipherresfeber.careerguidance.Teacher.CustomClasses;

public class QuestionPaper {

    // all variables are declared public because of firebase
    public String questionPaperName;
    public String timePerQuestion;
    public String teacherName;
    public String questionsId;

    public QuestionPaper(){
        // empty constructor for firebase
    }

    public QuestionPaper(String questionPaperName, String timePerQuestion, String teacherName, String questionsId){
        this.questionPaperName = questionPaperName;
        this.timePerQuestion = timePerQuestion;
        this.teacherName = teacherName;
        this.questionsId = questionsId;
    }

    public String getQuestionPaperName() {
        return questionPaperName;
    }

    public String getQuestionsId() {
        return questionsId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTimePerQuestion() {
        return timePerQuestion;
    }
}
