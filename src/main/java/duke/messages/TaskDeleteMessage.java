package duke.messages;

public class TaskDeleteMessage extends Message {
    public TaskDeleteMessage(String taskDescription, int numOfTasks) {
        super(MessageConstants.TASK_DELETE_HEADER
                + taskDescription
                + "\nNow you have " + numOfTasks
                + (numOfTasks == 1 ? " task " : " tasks ")
                + "in the list.");
    }
}
