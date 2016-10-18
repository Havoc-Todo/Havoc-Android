package io.havoc.todo.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * POJO Task
 */
public class Task {

    /**
     * Name of the Task
     */
    public String name;

    /**
     * Unique identifier for the Task
     */
    @SerializedName("t_id")
    public String taskId;

    /**
     * The unique identifier of the User which this Task belongs to
     */
    @SerializedName("user")
    public String userId;

    /**
     * Category of Task (School, Work, etc.)
     */
    public String category;

    /**
     * Current position of the Task in its parent list
     */
    public int indexInList;

    /**
     * Additional notes about the Task
     */
    public String description;

    /**
     * Due date of the Task
     */
    public Date dateDue;

    /**
     * Priority of the Task
     * 1 = LOW, 2 = MEDIUM, 3 = HIGH
     */
    public TaskPriorityEnum priority;

    /**
     * Status of the Task
     * DONE = "Done", INCOMPLETE = "Incomplete"
     */
    public TaskStatusEnum status;

    /**
     * Subtasks of the Task
     * Just a basic List of Strings
     */
    public List<Subtask> subtasks;

    public Task(String name, String description, String category,
                Date dateDue, String userId, int indexInList, TaskPriorityEnum priority,
                TaskStatusEnum status, List<Subtask> subtasks) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.dateDue = dateDue;
        this.userId = userId;
        this.indexInList = indexInList;
        this.priority = priority;
        this.status = status;
        this.subtasks = subtasks;
    }

    /**
     * Sets the name of this Task
     *
     * @param name to set the name to
     */
    public void setName(String name) {
        this.name = name;
    }

    // get the name of this task
    public String getName() {
        return name;
    }

    /**
     * Gets the status of this Task
     *
     * @return status of the Task
     */
    public TaskStatusEnum getStatus() {
        return status;
    }

    /**
     * Sets the status of this Task
     *
     * @param status to set the status to
     */
    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }


    // set the description of this task
    public void setDescription(String description) {
        this.description = description;
    }

    // get the description of this task
    public String getDescription() { return description; }

    // set the category of this task
    public void setCategory(String category) { this.category = category; }

    // get the category of this task
    public String getCategory() { return category; }

    // set the due date of this task
    public void setDateDue(Date dateDue) { this.dateDue = dateDue; }

    // get the due date of this task
    public Date getDateDue() { return dateDue; }

    // set the priority of this task
    public void setPriority(TaskPriorityEnum priority) { this.priority = priority; }

    // get the priority of this task
    public TaskPriorityEnum getPriority() { return priority; }

    // set the id of this task
    public void setTaskId(String taskId) { this.taskId = taskId; }

    // get the id of this task
    public String getTaskId() { return taskId; }

    // set the user id of this task
    public void setUserId(String userId) { this.userId = userId; }

    // get the user id of this task
    public String getUserId() { return userId; }

    // set the index of this task in the parent list
    public void setIndexInList(int indexInList) { this.indexInList = indexInList; }

    // get the index of this task in the parent list
    public int getIndexInList() { return indexInList; }

    // set the subtasks
    public void setSubtasks(List<Subtask> subtasks) { this.subtasks = subtasks; }

    // get the subtasks
    public List<Subtask> getSubtasks() { return subtasks; }

}
