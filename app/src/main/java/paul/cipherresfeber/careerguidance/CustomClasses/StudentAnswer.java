package paul.cipherresfeber.careerguidance.CustomClasses;

import paul.cipherresfeber.careerguidance.Constants.Extra;

public class StudentAnswer {

    public String category;
    public String correctOption;
    public String userOption;
    public String answered;

    public StudentAnswer(String category, String correctOption, String userOption) {
        this.category = category;
        this.correctOption = correctOption;
        this.userOption = userOption;
    }

    public StudentAnswer() {
        this.answered = Extra.NO;
    }

    public String getAnswered() {
        return answered;
    }

    public void setValue(String category, String correctOption, String userOption, String answered){
        this.category = category;
        this.correctOption = correctOption;
        this.userOption = userOption;
        this.answered = answered;
    }

    public String getCategory() {
        return category;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public String getUserOption() {
        return userOption;
    }
}
