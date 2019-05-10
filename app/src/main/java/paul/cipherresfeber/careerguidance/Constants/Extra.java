package paul.cipherresfeber.careerguidance.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extra {

    public static final String YES = "yes";
    public static final String NO = "no";

    public static final String OPTION_1 = "OPTION 1";
    public static final String OPTION_2 = "OPTION 2";
    public static final String OPTION_3 = "OPTION 3";
    public static final String OPTION_4 = "OPTION 4";

    public static final String USER_TYPE_STUDENT = "STUDENT";
    public static final String USER_TYPE_TEACHER = "TEACHER";

    public static final String labels[] = {
            "ACCOUNTING",
            "ARCHITECTURE",
            "BIOLOGY",
            "BUSINESS",
            "CIVIL",
            "COMPUTER SCIENCE",
            "CRIMINOLOGY",
            "ELECTRICAL",
            "FASHION",
            "GEOGRAPHY",
            "GENERAL KNOWLEDGE",
            "GRAPHIC DESIGN",
            "LAW",
            "MATHEMATICS",
            "MUSIC",
            "PHYSICS"
    };

    public static final String sLabels[] = {
            "Acc.",
            "Arc.",
            "Bio.",
            "Buss.",
            "Civ.",
            "Cse",
            "Crime.",
            "Ele.",
            "Fas.",
            "Geo.",
            "Gk.",
            "Graph.",
            "Law",
            "Maths.",
            "Mus.",
            "Phy."
    };

    // check for valid email
    public static boolean isValidEmail(String email){
        Pattern p = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher matcher = p.matcher(email);
        return matcher.matches();
    }

}

