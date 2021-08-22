package fulfillment;

import exceptions.DukeException;
import exceptions.InvalidCommandException;
import exceptions.InvalidTaskNumberException;
import io.InputHandler;
import io.OutputHandler;
import messages.ByeMessage;
import messages.GreetingMessage;
import messages.Message;
import messages.MessageConstants;
import messages.TaskAddMessage;
import messages.TaskDeleteMessage;
import messages.TaskDoneMessage;
import messages.TaskListMessage;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.io.IOException;

/**
 * Handles commands from user.
 *
 * @author kevin9foong
 */
public class FulfillmentHandler {
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public FulfillmentHandler(InputHandler inputHandler, OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    /**
     * Initializes the Chatbot
     *
     * @throws IOException thrown when an error connecting
     *                     to input/output stream occurs.
     */
    public void initializeChatbot() throws IOException {
        handleGreeting();

        while (true) {
            String userInput = inputHandler.readInput();
            String[] splitUserInput = userInput.trim().split(" ", 2);
            Command userCommand = Command.getCommand(splitUserInput[0].trim());
            String userInputBody = null;

            if (splitUserInput.length == 2) {
                userInputBody = splitUserInput[1];
            }

            try {
                if (userCommand != null) {
                    switch (userCommand) {
                    case LIST:
                        handleTaskList();
                        break;
                    case TODO:
                        handleTaskAdd(new ToDo(userInputBody));
                        break;
                    case DEADLINE:
                        handleTaskAdd(new Deadline(userInputBody));
                        break;
                    case EVENT:
                        handleTaskAdd(new Event(userInputBody));
                        break;
                    case DONE:
                        handleTaskDone(userInputBody);
                        break;
                    case DELETE:
                        handleTaskDelete(userInputBody);
                        break;
                    case BYE:
                        handleBye();
                        return;
                    // default case in case unexpected no matches occurs.
                    default:
                        throw new InvalidCommandException();
                    }
                } else {
                    throw new InvalidCommandException();
                }
            } catch (DukeException e) {
                outputHandler.writeMessage(new Message(e.getMessage()));
            }
        }
    }

    private void handleGreeting() {
        outputHandler.writeMessage(new GreetingMessage());
    }

    private void handleBye() {
        outputHandler.writeMessage(new ByeMessage());
    }

    private void handleTaskList() {
        outputHandler.writeMessage(new TaskListMessage(Task.getAllTasks()));
    }

    private void handleTaskAdd(Task task) throws IOException {
        Task addedTask = Task.addTask(task);
        outputHandler.writeMessage(new TaskAddMessage(addedTask.toString(),
                Task.getNumOfTasks()));
    }

    private void handleTaskDone(String userInputBody) throws InvalidTaskNumberException {
        try {
            // user input is 1 greater than index.
            int index = Integer.parseInt(userInputBody) - 1;
            Task doneTask = Task.getTask(index);
            doneTask.setDone();
            outputHandler.writeMessage(new TaskDoneMessage(doneTask));
        } catch (NumberFormatException | IOException nfe) {
            outputHandler.writeMessage(new Message(MessageConstants.INVALID_INTEGER_MESSAGE));
        }
    }

    private void handleTaskDelete(String userInputBody) throws InvalidTaskNumberException {
        try {
            // user input is 1 greater than index.
            int index = Integer.parseInt(userInputBody) - 1;
            Task deletedTask = Task.deleteTask(index);
            outputHandler.writeMessage(new TaskDeleteMessage(deletedTask.toString(), Task.getNumOfTasks()));
        } catch (NumberFormatException | IOException nfe) {
            outputHandler.writeMessage(new Message(MessageConstants.INVALID_INTEGER_MESSAGE));
        }
    }
}
