package io.havoc.todo;


public enum TaskPriorityEnum {

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
    public int getPriority() {
        return priority;
    }
}
