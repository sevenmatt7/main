package com.algosenpai.app.logic;

import com.algosenpai.app.logic.command.ByeCommand;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.command.MenuCommand;
import com.algosenpai.app.logic.command.PrintCommand;
import com.algosenpai.app.logic.command.ResultCommand;
import com.algosenpai.app.logic.command.CommandEnum;
import com.algosenpai.app.logic.chapters.QuizGenerator;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.parser.Parser;
import com.algosenpai.app.stats.UserStats;

import java.util.ArrayList;

public class Logic {

    private Parser parser;
    private UserStats userStats;
    private QuizGenerator quizMaker;

    //All variables for the settings of the program
    private int setupStage = 0;
    private boolean isSettingUp = false;
    private int level = 1;
    private boolean isBoy = true;
    private String name;

    //All variables for the quiz function
    private int selectedChapters = 0;
    private boolean isQuizMode = false;
    private ArrayList<QuestionModel> quizList;
    private int questionNumber = 0;
    private int prevResult = 0;

    // Review features;
    private ArrayList<QuestionModel> reviewList;

    //

    /**
     * Initializes logic for the application.
     * @param parser parser for user inputs.
     * @param userStats user states.
     */
    public Logic(Parser parser, UserStats userStats) {
        this.parser = parser;
        this.userStats = userStats;
        quizMaker = new QuizGenerator();
    }

    /**
     * Gets the boolean of whether the user is a boy or girl.
     * @return the boolean value of whether the user is a boy.
     */
    public boolean isBoy() {
        return this.isBoy;
    }

    /**
     * Parses the user input for the command to be created and executed.
     * @param userString the user input from the GUI.
     * @return the Command object with the correct attributes to be executed.
     */
    public Command parseInputCommand(String userString) {
        //if the program is in quiz mode, the input is not parsed
        if (isQuizMode) {
            return new Command(CommandEnum.QUIZ, 0, userString);
        } else if (isSettingUp) {
            return new Command(CommandEnum.SETUP, 0, userString);
        } else {
            return parser.parseInput(userString);
        }
    }

    /**
     * Executes the command.
     * @param currCommand the Command to be executed.
     * @return the program String response to be displayed.
     */
    public String executeCommand(Command currCommand) {
        String responseString;
        switch (currCommand.getType()) {
        case SETUP:
            if (setupStage == 0) {
                responseString = checkStatus();
                setupStage++;
                return responseString;
            } else if (setupStage == 1) {
                setName(currCommand.getUserString());
                responseString = "Are you a boy or a girl?";
                setupStage++;
                return responseString;
            } else if (setupStage == 2) {
                if (currCommand.getUserString().equals("girl")) {
                    isBoy = false;
                }
                responseString = "You have set your profile! Time to start your journey!";
                setupStage++;
                isSettingUp = false;
                return responseString;
            } else {
                responseString = checkStatus();
                return responseString;
            }
        case HELP:
        case MENU:
            MenuCommand menuCommand = new MenuCommand(currCommand);
            return menuCommand.execute();
        case START:
            isQuizMode = true;
            quizList = quizMaker.generateQuiz(selectedChapters, quizList);
            return quizList.get(0).getQuestion();
        case SELECT:
            selectedChapters = currCommand.getParameter();
            responseString = "You have selected chapter "
                    + currCommand.getParameter()
                    + " for the quiz!";
            return responseString;
        case RESULT:
            ResultCommand resultCommand = new ResultCommand(currCommand, prevResult);
            return resultCommand.execute();
        case REPORT:
            responseString = "report";
            return responseString;
        case BACK:
            responseString = "back";
            return responseString;
        case HISTORY:
            responseString = "history";
            return responseString;
        case UNDO:
            responseString = "testing";
            return responseString;
        case CLEAR:
            responseString = "clear";
            return responseString;
        case RESET:
            responseString = "reset";
            return responseString;
        case SAVE:
            responseString = "save";
            return responseString;
        case EXIT:
            ByeCommand byeCommand = new ByeCommand(currCommand);
            return byeCommand.execute();
        case PRINT:
            PrintCommand printCommand = new PrintCommand(currCommand, quizList);
            return printCommand.execute();
        case ARCHIVE:
            responseString = "archive";
            return responseString;
        case QUIZ:
            quizList.get(questionNumber).setAnswer(currCommand.getUserString());
            questionNumber++;

            if (questionNumber < 10) {
                return quizList.get(questionNumber).getQuestion();
            } else if (questionNumber == 10) {
                isQuizMode = false;
                int correctCount = 0;

                for (int i = 0; i < 10; i++) {
                    QuestionModel currQuestion = quizList.get(i);
                    if (currQuestion.checkAnswer()) {
                        correctCount++;
                    }
                }
                questionNumber = 0;
                String endQuizMessage = "You got " + correctCount + "/10 questions correct!";
                return endQuizMessage;
            }
            return "quiz";
        default:
            return "INVALID";
        }
    }


    /**
     * Checks whether it is the user's first time using the application.
     * If it is his/her first time, the isSettingUp boolean flag will be set to true.
     */
    public String checkStatus() {
        if (setupStage == 0) {
            isSettingUp = true;
            return "Oh it seems that it is your first time here! Can I get your name?";
        } else {
            return " Welcome back " + name + " You are currently Level " + level;
        }
    }

    /**
     * Sets the name of the user.
     * @param userName the name input by the user.
     */
    public void setName(String userName) {
        name = userName;
    }

    public String getName() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }
}
