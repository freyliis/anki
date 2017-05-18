package org.freyliis.anki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {

    @JsonProperty
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;
    @JsonProperty
    private List<Question> questions;

    public Deck(LocalDate date, List<Question> questions) {
        this.date = date;
        this.questions = questions;
    }

    public Deck() {
    }

    public LocalDate getDate() {
        return date;
    }

    @JsonIgnore
    public List<Question> getQuestionsToAnswerToday() {
        return questions.stream().filter(question -> question.shouldBeAnswered()).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    @JsonIgnore
    public boolean areAllQuestionsProperlyAnswered() {
        return questions.size() == questions.stream().filter(question -> question.isPropelyAnswered()).count();
    }
}
