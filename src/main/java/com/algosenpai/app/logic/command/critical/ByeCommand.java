//@@author carrieng0323852

package com.algosenpai.app.logic.command.critical;

import com.algosenpai.app.logic.command.Command;

import java.util.ArrayList;

public class ByeCommand extends Command {

    /**
     * Initializes command to exit program.
     * @param inputs input from user.
     */
    public ByeCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        return "Bye!";
    }
}
