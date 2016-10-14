package io.havoc.todo.model;


import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;

import io.havoc.todo.TaskStatusEnum;

public class StatusEnumStringConverter extends StringBasedTypeConverter<TaskStatusEnum> {

    @Override
    public TaskStatusEnum getFromString(String status) {
        return TaskStatusEnum.valueOf(status);
    }

    public String convertToString(TaskStatusEnum object) {
        return object.toString();
    }
}
