package org.freyliis.anki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {

    @JsonProperty
    private String question;
    @JsonProperty
    private String answer;
    @JsonProperty
    private Box box = Box.RED;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Question() {
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public boolean shouldBeAnswered() {
        return box.equals(Box.RED);
    }
    @JsonIgnore
    public boolean isProperlyAnswered() {
        return box.equals(Box.GREEN);
    }


    public boolean answerQuestion(String answer) {
        boolean isAnswered = false;
        if(this.answer.equals(answer)){
            this.box = Box.GREEN;
            isAnswered = true;
        } else if (this.answer.contains(answer)) {
            this.box = Box.ORANGE;
        } else {
            this.box = Box.RED;
        }
        return isAnswered;
    }


    public Box getBox() {
        return box;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }
}
