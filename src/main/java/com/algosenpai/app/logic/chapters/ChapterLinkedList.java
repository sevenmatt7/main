package com.algosenpai.app.logic.chapters;

import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.models.ReviewTracingListModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

class ChapterLinkedList {


    private static Random random = new Random();

    /**
     * Generates a random question related to linked lists.
     * @return a question model according to the random number being generated.
     */
    static QuestionModel generateQuestions() {
        int questionType = getRandomNumber(0, 4);
        switch (questionType) {
        case 0:
            return stackPopPushQuestion();
        case 1:
            return queuePopPushQuestion();
        case 2:
            return singleInsertLinkedListQuestion();
        case 3:
            return pseudoCodeQuestion();
        default:
            return null;
        }
    }

    /**
     * Generates a question related to the topic on pseudocode understanding.
     * @return a question to the generateQuestion function.
     */
    private static QuestionModel pseudoCodeQuestion() {
        //Generates a size for the array between 6 and 9.
        int arraySize = getRandomNumber(6, 4);
        //Populates the array with elements no greater than 10.
        ArrayList<Integer> array = new ArrayList<>(createList(arraySize, 10));
        //Formats the question
        String question = pseudoCodeQuestionGenerator(array);
        ArrayList<String> pseudoCode = new ArrayList<>();
        generatePseudoCode(pseudoCode);
        question += printPseudoCode(pseudoCode);
        String answer = calculateSum(array, pseudoCode);
        return new QuestionModel(question, answer, new ReviewTracingListModel());
    }

    /**
     * Calculates the sum of the values based on the pseudocode generated.
     * 
     * @param array      The ArrayList which makes up the Linked List.
     * @param pseudoCode The list of instructions in the pseudo-code.
     * @return The value of the sum given in the pseudo-code in a String format.
     */
    private static String calculateSum(ArrayList<Integer> array, ArrayList<String> pseudoCode) {
        int sum = 0;
        int index = 0;
        for (String i : pseudoCode) {
            switch (i) {
            case "n = n.next;":
                index++;
                break;
            case "sum += list.tail.value;":
                sum += array.get(array.size() - 1);
                break;
            case "sum += list.head.value;":
                sum += array.get(0);
                break;
            case "n = list.head;":
                index = 0;
                break;
            case "sum += n.value;":
                sum += array.get(index);
                break;
            case "sum += n.next.value;":
                sum += array.get(index + 1);
                break;
            default:
                break;
            }
        }
        return String.valueOf(sum);
    }

    /**
     * Generates a list of pseudo-code, such as "n = n.next", or "n = list.head".
     * This list of instructions will be printed to the user.
     * 
     * @param pseudoCode The list which will contain the instructions.
     */
    private static void generatePseudoCode(ArrayList<String> pseudoCode) {
        pseudoCode.add("populateList();");
        pseudoCode.add("int sum = 0;");
        pseudoCode.add("Node n = list.head; //list.head/list.tail points to the first/last integer in list");
        //Generates a number of commands between 4 and 7.
        int noOfCommands = getRandomNumber(4,4);
        for (int i = 0; i < noOfCommands; i++) {
            int commandToAdd = getRandomNumber(0,6);
            switch (commandToAdd) {
            case 0:
                pseudoCode.add("n = n.next;");
                break;
            case 1:
                pseudoCode.add("sum += list.tail.value;");
                break;
            case 2:
                pseudoCode.add("sum += list.head.value;");
                break;
            case 3:
                pseudoCode.add("n = list.head;");
                break;
            case 4:
                pseudoCode.add("sum += n.value;");
                break;
            case 5:
                pseudoCode.add("sum += n.next.value;");
                break;
            default:
                break;
            }
        }
        pseudoCode.add("print sum;");
    }

    /**
     * Generates a question relating to a single insertion in a Singly Linked List.
     * The question can be either appending to the head or the tail of the linked
     * list. A sequence of integers are taken in from the user as an answer.
     * 
     * @return a question which contains the question to the generateQuestions function.
     */
    private static QuestionModel singleInsertLinkedListQuestion() {
        //Generates a size for the linked list between 5 and 8.
        int listSize = getRandomNumber(5,4);
        //Populates the linked list with values.
        LinkedList<Integer> ll = createList(listSize, 100);
        //Decide on a value to be added between 0 and 100.
        int valueToAdd = getRandomNumber(0,100);
        //Decide on the position to be added.
        String positionToAdd = getPositionToAdd();
        //Formats the question.
        String question = singleInsertLinkedListQuestionGenerator(listSize, valueToAdd, positionToAdd);
        question += printList(ll);
        if (positionToAdd.equals("head")) {
            ll.addFirst(valueToAdd);
        } else {
            ll.addLast(valueToAdd);
        }
        String answer = printList(ll);
        return new QuestionModel(question, answer, new ReviewTracingListModel());
    }

    /**
     * Generates a Queue question related to popping and pushing. A random size is
     * determined, followed by a series of instructions consisting of pop and push.
     * The algorithm will do the popping and pushing accordingly. An input is taken
     * in from the user as an answer.
     * 
     * @return the question to the generateQuestion function.
     */
    private static QuestionModel queuePopPushQuestion() {
        //Generates a size for the queue between 4 and 8.
        int queueSize = getRandomNumber(5,4);
        //Populates the queue with positive integers under 100.
        LinkedList<Integer> queue = createList(queueSize, 100);
        ArrayList<String> instructions = new ArrayList<>();
        //Determines the number of instructions to be added.
        int numberOfInstructions = getRandomNumber(4,3);
        //Populate instructions
        addInstructions(instructions, numberOfInstructions);
        //Formats the question
        String question = queuePopPushQuestionGenerator(queueSize);
        question += printQueue(queue);
        question += printInstructions(instructions);
        //Updates the queue according to the question.
        changeQueue(instructions, queue);
        String answer = String.valueOf(queue.getLast());
        return new QuestionModel(question, answer, new ReviewTracingListModel());
    }

    /**
     * Changes the queue according to the instructions given. If instruction is pop,
     * the front value of the list would be removed, or else a new value will be
     * pushed into the queue.
     * 
     * @param instructions The list of instructions given.
     * @param queue        The list which would be edited according to the
     *                     instructions given.
     */
    private static void changeQueue(ArrayList<String> instructions, LinkedList<Integer> queue) {
        for (String cmd : instructions) {
            if (cmd.contains("Pop")) {
                queue.removeLast();
            } else {
                String number = cmd.substring(5, cmd.length() - 2);
                int valuetoadd = Integer.parseInt(number);
                queue.addFirst(valuetoadd);
            }
        }
    }

    /**
     * Generates a Stack operations question related to pushing and popping. A
     * random stack size is determined and the stack is created. A sequence of
     * instructions consisting of push and pops are created, followed by which, the
     * algorithm will do the popping and pushing accordingly. An input is taken in
     * from the user as an answer.
     * 
     * @return the question being generated to generateQuestion.
     */
    private static QuestionModel stackPopPushQuestion() {
        //Generates a random number for the size of the stack between 4 and 8.
        int stackSize = getRandomNumber(4,5);
        //Populates the stack with numbers.
        LinkedList<Integer> stack = createList(stackSize, 100);
        ArrayList<String> instructions = new ArrayList<>();
        //Determines the number of instructions to be carried out between 4 and 6.
        int numberOfInstructions = getRandomNumber(4,6);
        addInstructions(instructions, numberOfInstructions);
        String question = stackPopPushQuestionGenerator(stackSize);
        question += printStack(stack);
        question += printInstructions(instructions);
        changeStack(instructions, stack);
        String answer = String.valueOf(stack.getLast());
        return new QuestionModel(question, answer, new ReviewTracingListModel());
    }

    /**
     * Changes the stack according to the instructions generated. Instructions are
     * either pop or push.
     * 
     * @param instructions The list of instructions provided.
     * @param stack        The data structure to be changed.
     */
    private static void changeStack(ArrayList<String> instructions, LinkedList<Integer> stack) {
        for (String cmd : instructions) {
            if (cmd.contains("Pop")) {
                stack.removeLast();
            } else {
                String number = cmd.substring(5, cmd.length() - 2);
                int valuetoadd = Integer.parseInt(number);
                stack.addLast(valuetoadd);
            }
        }
    }

    /**
     * Adds new instructions to the list through random generation of either 1 or 0.
     * If values is 0, a push command is added along with a value. Else, a pop
     * command is added.
     * 
     * @param instructions         The list where instructions will be added.
     * @param numberOfInstructions The number of instructions to be added into the
     *                             list.
     */
    private static void addInstructions(ArrayList<String> instructions, int numberOfInstructions) {
        for (int i = 0; i < numberOfInstructions; i++) {
            int val = getRandomNumber(0,2);
            int toadd = getRandomNumber(0,100);
            switch (val) {
            case 0:
                String combined = "Push(" + toadd + ");";
                instructions.add(combined);
                break;
            case 1:
                instructions.add("Pop();");
                break;
            default:
                break;
            }
        }
    }

    /**
     * Creates a Linked List and populate it with unique numbers.
     * 
     * @param size The number of elements to be in the Linked List.
     * @return The Linked List data structure to be used for the question.
     */
    private static LinkedList<Integer> createList(int size, int bound) {
        HashSet<Integer> set = new HashSet<>();
        while (set.size() != size) {
            int value = getRandomNumber(0, bound);
            set.add(value);
        }
        return new LinkedList<>(set);
    }

    /**
     * Creates a String containing the list of instructions used in the pseudocode.
     * 
     * @param pseudoCode The list of instructions in the pseudo-code.
     * @return The String containing the instructions.
     */
    private static String printPseudoCode(ArrayList<String> pseudoCode) {
        StringBuilder string = new StringBuilder();
        for (String cmd : pseudoCode) {
            string.append(cmd).append("\n");
        }
        return string.toString();
    }

    /**
     * Creates a formatted String from the linkedlist provided.
     * 
     * @param ll The linked list provided.
     * @return The string representing the linkedlist.
     */
    private static String printList(LinkedList<Integer> ll) {
        StringBuilder linkedListString = new StringBuilder();
        for (int i : ll) {
            linkedListString.append("[").append(i).append("]");
            if (i != ll.getLast()) {
                linkedListString.append(" -> ");
            }
        }
        linkedListString.append("\n");
        return linkedListString.toString();
    }

    /**
     * Creates a formatted String which represents the stack given.
     * 
     * @param stack The stack containing the elements.
     * @return The string which represents the stack.
     */
    private static String printStack(LinkedList<Integer> stack) {
        StringBuilder s = new StringBuilder();
        for (int i : stack) {
            s.append("[").append(i).append("] <- ");
        }
        s.append("Head\n");
        return s.toString();
    }

    /**
     * Creates a formatted String which contains the elements in the queue.
     * 
     * @param queue The queue containing the elements.
     * @return The formatted String.
     */
    private static String printQueue(LinkedList<Integer> queue) {
        StringBuilder q = new StringBuilder();
        for (int i : queue) {
            q.append("[").append(i).append("] -> ");
        }
        q.append("Front");
        return q.toString();
    }

    /**
     * Creates a String with the instruction given by the list on separate new lines.
     * 
     * @param instructions The list of instructions provided.
     * @return The String containing the instructions given by the list.
     */
    private static String printInstructions(ArrayList<String> instructions) {
        StringBuilder instructs = new StringBuilder();
        int i = 1;
        for (String s : instructions) {
            instructs.append(i).append(". ").append(s).append("\n");
            i++;
        }
        return instructs.toString();
    }

    /**
     * Generates an integer.
     * @param minimum The minimum value of the integer generated.
     * @param bound Any value below this could be added to minimum.
     * @return The integer which is randomly generated.
     */
    private static int getRandomNumber(int minimum, int bound) {
        return random.nextInt(bound) + minimum;
    }

    /**
     * Formats the question for pseudoCodeQuestion.
     * @param array The array representing the singly linked list.
     * @return The formatted string.
     */
    private static String pseudoCodeQuestionGenerator(ArrayList<Integer> array) {
        return "In the pseudocode program below, list is an initially empty Singly Linked List.\n"
                + "The function populateList() adds the integers " + array + " to the tail of the list sequentially.\n"
                + "What is the output of the program?";
    }

    /**
     * Decides the position to add the value to by generating a random number 1, or 2.
     * @return A String containing the position. Either "head" or "tail"
     */
    private static String getPositionToAdd() {
        String positionToAdd;
        if (getRandomNumber(0,2) == 1) {
            positionToAdd = "head";
        } else {
            positionToAdd = "tail";
        }
        return positionToAdd;
    }

    /**
     * Formats the question for the singleInsertLinkedListQuestion.
     * @param listSize The size of the linkedlist.
     * @param valueToAdd The value to add to the linked list.
     * @param positionToAdd The position at which to add the value at.
     * @return The formatted String question.
     */
    private static String singleInsertLinkedListQuestionGenerator(int listSize, int valueToAdd, String positionToAdd) {
        return "Consider the Singly Linked List of size " + listSize + " below."
                + " It undergoes an insertion of value " + valueToAdd + " at the " + positionToAdd
                + ".\nWhat would be the new sequence of integers?";
    }

    /**
     * Formats the question for the queuePopPushQuestion.
     * @param queueSize The size of the queue in question.
     * @return The formatted String.
     */
    private static String queuePopPushQuestionGenerator(int queueSize) {
        return "A Queue of size " + queueSize
                + " undergoes a series of operations as shown below.\n"
                + "What would be the new value called upon queue.peek()?";
    }

    /**
     * Formats the question for the stackPopPushQuestion.
     * @param stackSize The size of the stack in question.
     * @return The formatted String.
     */
    private static String stackPopPushQuestionGenerator(int stackSize) {
        return "A Stack of size " + stackSize
                + " undergoes a series of operations as shown below.\n"
                + "What would be the new value called upon stack.peek()?";
    }




}