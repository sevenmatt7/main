package com.algosenpai.app.command;

import com.algosenpai.app.exceptions.DukeExceptions;

public class Command {

    private CommandEnum commandType;

    private int commandParameter;
    /**
     * Create new command.
     */
    public Command(CommandEnum commandType, int specifier) {
        this.commandType = commandType;
        this.commandParameter = specifier;
    }

    public CommandEnum getType() {
        return this.commandType;
    }

}
