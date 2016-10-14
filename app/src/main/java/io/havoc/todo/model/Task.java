package io.havoc.todo.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.Date;
import java.util.List;

import io.havoc.todo.TaskPriorityEnum;
import io.havoc.todo.TaskStatusEnum;

/**
 * POJO Task
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class Task {

    /**
     * Name of the Task
     */
    public String name;

    /**
     * Unique identifier for the Task
     */
    @JsonField(name = "t_id")
    public String taskId;

    /**
     * Category of Task (School, Work, etc.)
     */
    public String category;

    /**
     * Current position of the Task in its parent list
     */
    public int indexInList;

    /**
     * Priority of the Task
     * 1 = LOW, 2 = MEDIUM, 3 = HIGH
     */
    @JsonField(typeConverter = PriorityEnumIntConverter.class)
    public TaskPriorityEnum priority;

    /**
     * Additional notes about the Task
     */
    public String description;

    /**
     * Due date of the Task
     */
    public Date dateDue;

    /**
     * The unique identifier of the User which this Task belongs to
     */
    @JsonField(name = "user")
    public String userId;

    /**
     * Status of the Task
     * DONE = "Done", INCOMPLETE = "Incomplete"
     */
    @JsonField(typeConverter = StatusEnumStringConverter.class)
    public TaskStatusEnum status;

    /**
     * Subtasks of the Task
     * Just a basic List of Strings
     */
    public List<Subtask> subtasks;

    public Task(String name, String taskId, String category, int indexInList,
                TaskPriorityEnum priority, String description, Date dateDue,
                String userId, TaskStatusEnum status, List<Subtask> subtasks) {
        this.name = name;
        this.taskId = taskId;
        this.category = category;
        this.indexInList = indexInList;
        this.priority = priority;
        this.description = description;
        this.dateDue = dateDue;
        this.userId = userId;
        this.status = status;
        this.subtasks = subtasks;
    }
}
