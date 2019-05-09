package paul.cipherresfeber.careerguidance.CustomClasses;

import paul.cipherresfeber.careerguidance.Constants.Extra;

public class QuestionPaper {

    // all variables are declared public because of firebase
    public String questionPaperName;
    public String timePerQuestion;
    public String teacherName;
    public String creationDate;
    public String questionsId;
    public String isCompleted;
    public String totalNumOfQuestions;
    public String teacherUID;

    public QuestionPaper(){
        // empty constructor for firebase
    }

    public QuestionPaper(String questionPaperName, String timePerQuestion, String teacherName,
                         String teacherUID, String creationDate, String questionsId){
        this.questionPaperName = questionPaperName;
        this.timePerQuestion = timePerQuestion;
        this.teacherName = teacherName;
        this.creationDate = creationDate;
        this.questionsId = questionsId;
        this.isCompleted = Extra.NO;
        this.teacherUID = teacherUID;
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

    public String getCreationDate(){
        return creationDate;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public String getTotalNumberOfQuestions(){
        return totalNumOfQuestions;
    }

    public String getTeacherUID(){
        return teacherUID;
    }

}
