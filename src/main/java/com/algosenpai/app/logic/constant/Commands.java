//@@author carrieng0323852

package com.algosenpai.app.logic.constant;

public enum Commands {
    menu,
    start,
    select,
    report,
    result,
    history,
    clear,
    quiz,
    back,
    undo,
    help,
    setup,
    print,
    bye;

    /**
     * Returns the valid commands in a string.
     * @return Array of strings
     */
    public static String[] getNames() {
        Commands[] commands = values();
        String[] names = new String[commands.length];
        for (int i = 0; i < commands.length; i++) {
            names[i] = commands[i].name();
        }
        return names;
    }

    // public static String[] names() {
    // return Arrays.toString(Commands.values()).split(", ");
    //}
}
