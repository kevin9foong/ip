package duke.messages;

import duke.tasks.Task;

/**
 * Class is responsible for generating message indicating given task is marked as done.
 *
 * @author kevin9foong
 */
public class TaskDoneMessage extends Message {
    public TaskDoneMessage(Task task) {
        super(MessageConstants.MESSAGE_TASK_DONE_HEADER + "\n\t" + task.toString());
    }
}
