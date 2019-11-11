package com.algosenpai.app.logic.command;

import com.algosenpai.app.exceptions.FileParsingException;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HelpCommand extends Command {

    private UserStats userStats;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public HelpCommand(ArrayList<String> inputs, UserStats userStats) {
        super(inputs);
        this.userStats = userStats;
    }

    @Override
    public String execute()  {
        if (inputs.size() < 2) {
            return getDefaultMessage();
        } else {
            int index = userStats.getIndexByName(inputs.get(1));
            if (index == -1) {
                return getDefaultMessage();
            }

            try {
                userStats = UserStats.parseString(Storage.loadData("UserData.txt"));
            } catch (FileNotFoundException | FileParsingException e) {
                e.printStackTrace();
            }
            double percentageStat = userStats.getPercentageofQuestionsCorrect(index);
            return getRecommendedQuestions(index, percentageStat);
        }
    }

    /**
     * Returns default message if the help command is not called properly.
     * @return default message to use select command.
     */
    private String getDefaultMessage() {
        StringBuilder str = new StringBuilder(
                "No such chapter found. Please select the following:\nhelp <chapter name>\n");
        for (String chapter: userStats.getChapters()) {
            str.append(chapter).append("\n");
        }
        return str.toString();
    }

    private String getRecommendedQuestions(int index, double percentageStat) {
        if (index == 1) {
            if (percentageStat < 40.0) {
                return "Try solving these problems on Kattis:\n"
                        + "lineup, mjehuric, sidewayssorting";
            } else if (percentageStat < 60.0 && percentageStat > 40.0) {
                return "Try solving these problems on Kattis:\n"
                        + "chartingprogress, classy, dyslectionary";
            } else if (percentageStat > 60.0) {
                return "Try solving these problems on Kattis:\n"
                        + "lawnmower, sortofsorting, musicyourway";
            }
        } else if (index == 2) {
            if (percentageStat < 40.0) {
                return "Try solving these problems on Kattis:\n"
                        + "evenup, pairingsocks, coconut";
            } else if (percentageStat < 60.0) {
                return "Try solving these problems on Kattis:\n"
                        + "throwns, integerlists, joinstrings";
            } else if (percentageStat > 60.0) {
                return "Try solving these problems on Kattis:\n"
                        + "restaurant, ferryloading4, teque";
            }
        } else if (index == 3){
            if (percentageStat < 40.0) {
                return "Try solving these problems on Kattis:\n"
                        + "committeeassignment, pebblesolitaire";
            } else if (percentageStat < 60.0) {
                return "Try solving these problems on Kattis:\n"
                        + "pebblesolitaire2, equalsumeasy";
            }
            return "Try solving these problems on Kattis:\n"
                    + "robotturtles, hidingchickens";
        }
        return getDefaultMessage();
    }
}
