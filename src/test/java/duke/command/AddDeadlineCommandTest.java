package duke.command;

import duke.data.TaskStorageStub;
import duke.exceptions.EmptyDeadlineBodyException;
import duke.exceptions.InvalidDateTimeFormatException;
import duke.exceptions.InvalidDeadlineBodyException;
import duke.exceptions.InvalidTaskNumberException;
import duke.io.UserOutputHandlerStub;
import duke.tasks.Deadline;
import duke.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddDeadlineCommandTest {
    @Test
    void executingCommandWorks_userInput_addsDeadlineToTaskList() throws IOException, InvalidDateTimeFormatException,
            InvalidDeadlineBodyException, EmptyDeadlineBodyException, InvalidTaskNumberException {
        AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand("x /by 2021-12-12");
        Deadline expectedDeadline = new Deadline("x", false, LocalDate.parse("2021-12-12"));

        UserOutputHandlerStub userOutputHandlerStub = new UserOutputHandlerStub();
        TaskList taskList = new TaskList(new TaskStorageStub());
        addDeadlineCommand.execute(userOutputHandlerStub, taskList);

        assertEquals("____________________________________________________________\n" +
                "Got it. I've added this task:\n" +
                "\t[D][ ] x (by: DECEMBER 12 2021)\n" +
                "Now you have 1 task in the list.\n" +
                "____________________________________________________________", userOutputHandlerStub.getWrittenMessage());

        assertEquals(1, taskList.getNumOfTasks());
        assertEquals(expectedDeadline.getTaskRepresentation(), taskList.getTask(0).getTaskRepresentation());
    }
}
