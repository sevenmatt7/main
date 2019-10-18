package com.algosenpai.app.chapters;

import com.algosenpai.app.Question;

import java.util.ArrayList;

public class QuestionGenerator {

    public ArrayList generateQuiz(int selectedChapters) {
        ArrayList<Question> questionList = new ArrayList<Question>();

        if (selectedChapters == 1) {
            for (int i = 0; i < 10; i++) {
                questionList.add(ChapterSorting.generateQuestions());
            }
        }
        return questionList;
    }
}
