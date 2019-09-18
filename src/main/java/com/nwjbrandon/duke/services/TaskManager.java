package com.nwjbrandon.duke.services;

import com.nwjbrandon.duke.constants.TaskCommands;
import com.nwjbrandon.duke.exceptions.DukeTaskCollisionException;
import com.nwjbrandon.duke.exceptions.DukeWrongCommandException;
import com.nwjbrandon.duke.exceptions.DukeWrongCommandFormatException;


import com.nwjbrandon.duke.services.command.*;
import com.nwjbrandon.duke.services.storage.Storage;
import com.nwjbrandon.duke.services.task.*;
import com.nwjbrandon.duke.services.ui.Terminal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class TaskManager {

    /**
     * Task Manager is running.
     */
    private boolean isRunning = true;

    /**
     * Container for list of task.
     */
    private TaskList tasksList;

    /**
     * Create task manager.
     */
    public TaskManager() {
        tasksList = new TaskList();
    }

    /**
     * IO handler.
     */
    private Terminal ioHandler = new Terminal();


    /**
     * Load tasks from file input.
     * @param taskDetails information of tasks from file input.
     */
    private void loadTasksList(String taskDetails) {
        try {
            String[] details = taskDetails.split("\\s\\|\\s");
            Task task = loadTask(details);
            tasksList.addTask(task);
        } catch (DukeWrongCommandFormatException
                | DukeTaskCollisionException e) {
            e.showError();
        }
    }

    /**
     * Sort and create task by types.
     * @param details String[] of information of tasks from file input.
     * @return task.
     */
    private Task loadTask(String[] details) throws DukeWrongCommandFormatException {
        Task task;
        if (details[0].equals("T")) {
            task = new Todos(details, tasksList.numberOfTasks() + 1);
        } else if (details[0].equals("D")) {
            task = new Deadlines(details, tasksList.numberOfTasks() + 1);
        } else if (details[0].equals("E")) {
            task = new Events(details, tasksList.numberOfTasks() + 1);
        } else {
            task = new FixedDuration(details, tasksList.numberOfTasks() + 1);
        }
        if (details[1].equals("1")) {
            task.setDoneStatus(true);
        }
        return task;
    }

    /**
     * Load data from file.
     * @param filePath file path.
     * @throws FileNotFoundException file not found.
     */
    public void loadData(String filePath) throws FileNotFoundException {
        ArrayList<String> inputList = Storage.loadData(filePath);
        for (String taskDetails: inputList) {
            loadTasksList(taskDetails);
        }
        tasksList.showAll();
    }

    /**
     * Format data to save.
     * @return data to save
     */
    private String saveTaskList() {
        StringBuilder output = new StringBuilder();
        for (Task task : tasksList.getTasksList()) {
            if (task instanceof Todos) {
                output.append("T");
            } else if (task instanceof Deadlines) {
                output.append("D");
            } else if (task instanceof Events) {
                output.append("E");
            } else {
                output.append("F");
            }
            output.append(" | ").append(task.getIsDoneStatus() ? "1" : "0")
                    .append(" | ").append(task.getTaskName());
            if (!(task instanceof Todos)) {
                output.append(" | ").append(task.getBy());
            }
            output.append("\n");
        }
        return output.toString();
    }

    /**
     * Save data to file.
     * @param filePath file path.
     * @throws IOException IO error.
     */
    public void saveData(String filePath) throws IOException {
        Storage.saveData(filePath, this.saveTaskList());
    }

    /**
     * Run the application.
     * @return whether the program is done
     */
    public boolean run() {
        try {
            if (ioHandler.hasInput()) {
                String userInput = ioHandler.readInput();
                int size = tasksList.numberOfTasks();
                Command c = executeCommands(userInput, size);
                c.execute(tasksList);
            } else {
                isRunning = false;
            }
        } catch (DukeWrongCommandException e) {
            e.showError();
        }
        return isRunning;
    }

    /**
     * Execute commands from user input.
     * @param userInput input by user.
     * @param size new number of tasks.
     * @throws DukeWrongCommandException wrong command.
     */
    private Command executeCommands(String userInput, int size) throws DukeWrongCommandException {
        if (userInput.equals(TaskCommands.LIST.toString())) {
            return new ListCommand();
        } else if (userInput.startsWith(TaskCommands.DONE.toString())) {
            return new DoneCommand(userInput, TaskCommands.DONE.toString(), size);
        } else if (userInput.startsWith(TaskCommands.TODO.toString())) {
            return new TodosCommand(userInput, TaskCommands.TODO.toString(), size + 1);
        } else if (userInput.startsWith(TaskCommands.EVENT.toString())) {
            return new EventsCommand(userInput, TaskCommands.EVENT.toString(), size + 1);
        } else if (userInput.startsWith(TaskCommands.DEADLINE.toString())) {
            return new DeadlinesCommand(userInput, TaskCommands.DEADLINE.toString(), size + 1);
        } else if (userInput.startsWith(TaskCommands.FIXED_DURATION.toString())) {
            return new FixedDurationCommand(userInput, TaskCommands.FIXED_DURATION.toString(), size + 1);
        } else if (userInput.startsWith(TaskCommands.DELETE.toString())) {
            return new DeleteCommand(userInput, TaskCommands.DELETE.toString(), size);
        } else if (userInput.startsWith(TaskCommands.FIND.toString())) {
            return new SearchCommand(userInput, TaskCommands.FIND.toString());
        } else if (userInput.startsWith(TaskCommands.VIEW_SCHEDULE.toString())) {
            return new ViewSchedulesCommand(userInput, TaskCommands.VIEW_SCHEDULE.toString(), size);
        } else if (userInput.equals(TaskCommands.REMINDER.toString())) {
            return new RemindersCommand(userInput, TaskCommands.REMINDER.toString(), size);
        } else if (userInput.equals(TaskCommands.BYE.toString())) {
            isRunning = false;
            return new InvalidCommand();
        } else if (userInput.startsWith(TaskCommands.SNOOZE.toString())) {
            return new SnoozeCommand(userInput, TaskCommands.SNOOZE.toString(), size);
        } else if (userInput.startsWith(TaskCommands.RECURRING.toString())) {
            return new RecurringCommand(userInput,TaskCommands.RECURRING.toString(),size);
        } else {
            throw new DukeWrongCommandException();
        }
    }
}
