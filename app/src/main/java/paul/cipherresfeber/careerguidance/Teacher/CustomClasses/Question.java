package paul.cipherresfeber.careerguidance.Teacher.CustomClasses;

public class Question {

    public String question;
    public String option1;
    public String option2;
    public String option3;
    public String option4;
    public String category;
    public String correctOption;

    public Question(){
        // empty constructor for firebase
    }

    public Question(String question, String option1, String option2, String option3, String option4, String category, String correctOption) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.category = category;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getCategory() {
        return category;
    }

    public String getCorrectOption() {
        return correctOption;
    }
}
