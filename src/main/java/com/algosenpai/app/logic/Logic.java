package com.algosenpai.app.logic;

import com.algosenpai.app.logic.chapters.QuizGenerator;
import com.algosenpai.app.logic.command.BackCommand;
import com.algosenpai.app.logic.command.ByeCommand;
import com.algosenpai.app.logic.command.ClearCommand;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.command.HelpCommand;
import com.algosenpai.app.logic.command.HistoryCommand;

import com.algosenpai.app.logic.command.StatsCommand;
import com.algosenpai.app.logic.command.InvalidCommand;
import com.algosenpai.app.logic.command.MenuCommand;
import com.algosenpai.app.logic.command.SelectCommand;
import com.algosenpai.app.logic.command.PrintCommand;
import com.algosenpai.app.logic.command.QuizCommand;
import com.algosenpai.app.logic.command.ArchiveCommand;
import com.algosenpai.app.logic.command.SaveCommand;
import com.algosenpai.app.logic.command.ResultCommand;
import com.algosenpai.app.logic.command.SetupCommand;
import com.algosenpai.app.logic.command.UndoCommand;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.parser.Parser;
import com.algosenpai.app.stats.UserStats;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;

public class Logic {

    private Parser parser;
    private UserStats userStats;
    private QuizGenerator quizMaker;

    //All variables for the settings of the program
    private double playerExp = 0.0;

    //All variables for the quiz function
    private AtomicInteger chapterNumber = new AtomicInteger(0);
    private AtomicBoolean isQuizMode = new AtomicBoolean(false);
    private AtomicBoolean isNewQuiz = new AtomicBoolean(true);
    private ArrayList<QuestionModel> quizList;
    private AtomicInteger questionNumber = new AtomicInteger(0);
    private int prevResult = 0;

    // VariabReview features;
    private ArrayList<QuestionModel> archiveList;

    // History features;
    private ArrayList<String> historyList;

    /**
     * Initializes logic for the application with all the different components.
     */
    public Logic() throws FileNotFoundException {
        this.parser = new Parser();
        this.userStats =  new UserStats();
        quizMaker = new QuizGenerator();
        historyList = new ArrayList<>();
        archiveList = new ArrayList<>();
    }

    /**
     * Gets the player exp level.
     * @return the double value representing the exp level.
     */
    public double getPlayerExp() {
        return this.playerExp;
    }

    /**
     * Executes the command.
     * @param input user input.
     * @return the command object to be executed.
     */
    public Command executeCommand(String input) {
        ArrayList<String> inputs = Parser.parseInput(input);
        historyList.add(input);

        if (isQuizMode.get()) {
            if (inputs.get(0).equals("back")) {
                if (isQuizMode.get()) {
                    return new BackCommand(inputs, quizList, questionNumber);
                }
            }
            return new QuizCommand(inputs, quizList, questionNumber, isQuizMode, isNewQuiz);
        }

        switch (inputs.get(0)) {
        case "hello":
            return new SetupCommand(inputs);
        case "help":
            return new HelpCommand(inputs);
        case "menu":
            return new MenuCommand(inputs);
        case "select":
            return new SelectCommand(inputs, chapterNumber, userStats);
        case "result":
            return new ResultCommand(inputs, prevResult);
        case "report":
            return new PrintCommand(inputs, userStats);
        case "history":
            return new HistoryCommand(inputs, historyList);
        case "undo":
            return new UndoCommand(inputs);
        case "clear":
            return new ClearCommand(inputs);
        case "reset":
            // TODO
            return null;
        case "save":
            return new SaveCommand(inputs, userStats);
        case "exit":
            return new ByeCommand(inputs);
        case "print":
            return new PrintCommand(inputs, quizList);
        case "stats":
            return new StatsCommand(inputs, userStats);
        case "archive":
            return new ArchiveCommand(inputs, quizList, archiveList);
        case "quiz":
            if (isNewQuiz.get()) {
                quizList = quizMaker.generateQuiz(chapterNumber.get(), quizList);
                isNewQuiz.set(false);
                isQuizMode.set(true);
            }
            return new QuizCommand(inputs, quizList, questionNumber, isQuizMode, isNewQuiz);
        case "back":
            if (isQuizMode.get()) {
                return new BackCommand(inputs, quizList, questionNumber);
            }
            return new InvalidCommand(inputs);
        default:
            return new InvalidCommand(inputs);
        }
    }
}
