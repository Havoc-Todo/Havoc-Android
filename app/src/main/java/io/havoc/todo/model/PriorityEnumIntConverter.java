package io.havoc.todo.model;


import com.bluelinelabs.logansquare.typeconverters.IntBasedTypeConverter;

import io.havoc.todo.TaskPriorityEnum;

public class PriorityEnumIntConverter extends IntBasedTypeConverter<TaskPriorityEnum> {

    @Override
    public TaskPriorityEnum getFromInt(int priority) {
        for (TaskPriorityEnum tpe : TaskPriorityEnum.values()) {
            if (tpe.ordinal() == priority) {
                return tpe;
            }
        }
        return null;
    }

    public int convertToInt(TaskPriorityEnum object) {
        return object.ordinal();
    }
}
