package io.havoc.todo;


public enum TaskStatusEnum {

    DONE("Done"),
    ARCHIVED("Archived"),
    INCOMPLETE("Incomplete");

    //String the TaskStatusEnum is mapped to
    private String status;

    TaskStatusEnum(String status) {
        this.status = status;
    }

    /**
     * Getter for the status in String form
     *
     * @return the status as a String
     */
    public String getStatus() {
        return status;
    }
}
