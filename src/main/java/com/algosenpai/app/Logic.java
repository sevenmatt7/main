package com.algosenpai.app;

import com.algosenpai.app.chapters.QuizGenerator;
import com.algosenpai.app.command.Command;

import java.util.ArrayList;

public class Logic {

    private static Ui ui;
    private static Parser parser;
    private QuizGenerator quizMaker = new QuizGenerator();

    //All variables for the settings of the program
    private static boolean isNew = true;
    private static int level = 0;
    private static String name = "????";

    //All variables for the quiz function
    private static boolean isQuizMode = false;
    private ArrayList<Question> quizList;
    private int questionNumber = 0;



    public Logic (Parser parser, Ui ui) {
        this.parser = parser;
        this.ui = ui;
    }

    public Command parseInputCommand(String userString) {
        return parser.parseInput(userString);
    }

//    public static void executeCommand(Command currCommand) {
//        if (currCommand.getType().equals()
//    }

    public String parseInput(String userString) {
        if (isQuizMode) {
            quizList.get(questionNumber).setAnswer(userString);
            questionNumber++;

            if (questionNumber < 10) {
                return quizList.get(questionNumber).getQuestion();
            } else if (questionNumber == 10) {
                isQuizMode = false;
                int correctCount = 0;

                for (int i = 0; i < 10; i++) {
                    Question currQuestion = quizList.get(i);
                    if (currQuestion.checkAnswer()) {
                        correctCount++;
                    }
                }

                String endQuizMessage = "You got " + correctCount + "/10 questions correct!";
                return endQuizMessage;
            }
        }
        if (userString.equals("start")) {
            isQuizMode = true;
            quizList = makeQuiz(quizList);
            return quizList.get(0).getQuestion();
        }
        else {
            return "NULL";
        }
    }


    public String checkStatus() {
        if (isNew) {
            isNew = false;
            return "Oh it seems that it is your first time here! Can I get your name?";
        }
        else {
            return " Welcome back " + name + " You are currently level " + level;
        }
    }

    public void setName(String userName) {
        name = userName;
    }

    public ArrayList<Question> makeQuiz(ArrayList<Question> quiz) {
        return quizMaker.generateQuiz(1, quiz);
    }


}
