package paul.cipherresfeber.careerguidance.Teacher.CustomClasses;

public class QuestionPaper {

    // all variables are declared public because of firebase
    public String questionPaperName;
    public String timePerQuestion;
    public String teacherName;
    public String questions_id;

    public QuestionPaper(){
        // empty constructor for firebase
    }

    public QuestionPaper(String questionPaperName, String timePerQuestion, String teacherName, String questions_id){
        this.questionPaperName = questionPaperName;
        this.timePerQuestion = timePerQuestion;
        this.teacherName = teacherName;
        this.questions_id = questions_id;
    }

    public String getQuestionPaperName() {
        return questionPaperName;
    }

    public String getQuestions_id() {
        return questions_id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTimePerQuestion() {
        return timePerQuestion;
    }
}
