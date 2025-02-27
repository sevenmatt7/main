package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.parser.Parser;

import java.util.ArrayList;

public class DeleteCommand extends Command {

    /**
     * Initializes command to undo chat.
     * @param inputs input from user.
     */
    public DeleteCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    /**
     * Returns the number of chat messages to delete.
     * @return number of chat messages to delete.
     */
    @Override
    public String execute() {
        if (inputs.size() == 1) {
            return "1";
        }
        if (Parser.isInteger(inputs.get(1))) {
            int number =  Integer.parseInt(inputs.get(1));
            return Integer.toString(number < 0 ? -number : number);
        } else {
            return "Sorry, you did not enter a valid number (ᵟ︵ ᵟ)";
        }
    }
}
