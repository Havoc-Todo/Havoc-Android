package io.havoc.todo.model;


public enum TaskPriorityEnum {

    NONE(-1),
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    //int the TaskPriorityEnum is mapped to
    private final int priority;

    TaskPriorityEnum(int priority) {
        this.priority = priority;
    }

    /**
     * Getter for the Priority level in int form
     *
     * @return the priority as an int
     */
    public int getPriorityInt() {
        return priority;
    }

    public String toString() {
        return this.name();
    }
}
