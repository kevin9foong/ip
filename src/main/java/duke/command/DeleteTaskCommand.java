package duke.command;

import duke.exceptions.InvalidTaskNumberException;
import duke.io.UserOutputHandler;
import duke.messages.Message;
import duke.messages.MessageConstants;
import duke.messages.TaskDeleteMessage;
import duke.tasks.Task;
import duke.tasks.TaskList;

import java.io.IOException;

/**
 * Represents a user command to delete the specified <code>Task</code> for persisted Tasks.
 *
 * @author kevin9foong
 */
public class DeleteTaskCommand extends Command {
    public DeleteTaskCommand(String getUserInputBody) {
        super(getUserInputBody);
    }

    /**
     * Deletes the <code>Task</code> with the given task number.
     *
     * @param userOutputHandler handles outputting messages to the output destination.
     * @param taskList          handles task operations including adding, deleting, marking as done and retrieval.
     * @throws InvalidTaskNumberException thrown when the task associated with the given number is not found.
     */
    @Override
    public void execute(UserOutputHandler userOutputHandler, TaskList taskList) throws InvalidTaskNumberException {
        try {
            // user input is 1 greater than index.
            int index = Integer.parseInt(super.getUserInputBody()) - 1;
            Task deletedTask = taskList.deleteTask(index);
            userOutputHandler.writeMessage(new TaskDeleteMessage(deletedTask.toString(), taskList.getNumOfTasks()));
        } catch (NumberFormatException | IOException nfe) {
            userOutputHandler.writeMessage(new Message(MessageConstants.MESSAGE_INVALID_INTEGER));
        }
    }

    /**
     * Returns false to indicate program should not terminate after command is executed.
     *
     * @return false to indicate program should not terminate after command is executed.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
