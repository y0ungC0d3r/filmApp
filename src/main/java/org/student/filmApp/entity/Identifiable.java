package org.student.filmApp.entity;

public interface Identifiable<T> {
    T getId();
    void setId(T id);
}
