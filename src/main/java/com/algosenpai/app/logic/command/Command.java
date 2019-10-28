package com.algosenpai.app.logic.command;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    protected ArrayList<String> inputs;

    /**
     * Create new command.
     */
    public Command(ArrayList<String> inputs) {
        this.inputs = inputs;
    }
    
    public abstract String execute() throws IOException;
}

