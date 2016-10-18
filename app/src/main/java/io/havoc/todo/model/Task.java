package io.havoc.todo.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * POJO Task
 */
public class Task {

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
     * -1 = NONE (default), 1 = LOW, 2 = MEDIUM, 3 = HIGH
     */
    public TaskPriorityEnum priority = TaskPriorityEnum.NONE;
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
    /**
     * Name of the Task
     */
    private String name;

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
     * Gets the name of this Task
     *
     * @return name of the Task
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this Task
     *
     * @param name to set the name to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of this Task
     *
     * @return description of the Task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this Task
     *
     * @param description to set the description to
     */
    public void setDescription(String description) {
        this.description = description;
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

    /**
     * Gets the category of this Task
     *
     * @return category of the Task
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of this Task
     *
     * @param category to set the category to
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the dateDue of this Task
     *
     * @return dateDue of the Task
     */
    public Date getDateDue() {
        return dateDue;
    }

    /**
     * Sets the dateDue of this Task
     *
     * @param dateDue to set the dateDue to
     */
    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    /**
     * Gets the priority of this Task
     *
     * @return priority of the Task
     */
    public TaskPriorityEnum getPriority() {
        return priority;
    }

    /**
     * Sets the priority of this Task
     *
     * @param priority to set the status to
     */
    public void setPriority(TaskPriorityEnum priority) {
        this.priority = priority;
    }

    /**
     * Gets the taskId of this Task
     *
     * @return taskId of the Task
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Sets the taskId of this Task
     *
     * @param taskId to set the taskId to
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * Gets the userId of this Task
     *
     * @return userId of the Task
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the userId of this Task
     *
     * @param userId to set the userId to
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the indexInList of this Task
     *
     * @return indexInList of the Task
     */
    public int getIndexInList() {
        return indexInList;
    }

    /**
     * Sets the indexInList of this Task
     *
     * @param indexInList to set the indexInList to
     */
    public void setIndexInList(int indexInList) {
        this.indexInList = indexInList;
    }

    /**
     * Gets the subtasks of this Task
     *
     * @return subtasks of the Task
     */
    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    /**
     * Sets the subtasks of this Task
     *
     * @param subtasks to set the subtasks to
     */
    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

}
