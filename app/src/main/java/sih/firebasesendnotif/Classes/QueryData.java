package sih.firebasesendnotif.Classes;

/**
 * Created by dhruvatara on 25/3/18.
 */

public class QueryData {
    //String city;
    String question;

    public QueryData() {
    }

    public QueryData(String question) {
        //this.city = city;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
