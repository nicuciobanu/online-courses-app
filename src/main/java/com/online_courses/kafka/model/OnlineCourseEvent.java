package com.online_courses.kafka.model;

public class OnlineCourseEvent<T> implements ObjectType<T> {
    private ActionType action;
    private T object;

    public OnlineCourseEvent() {}

    public OnlineCourseEvent(ActionType action, T object) {
        this.action = action;
        this.object = object;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
