package dao;

import entities.Task;

import java.util.List;

public interface TaskDAO {
    void save(Task task);
    void update(Task task);
    void delete(Task task);
    Task getById(int id);
    List<Task> getAll();
}