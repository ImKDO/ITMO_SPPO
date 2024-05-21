package org.example;

public interface Executable{
/**
 * Выполнить что-либо.
 *
 * @param arguments Аргумент для выполнения
 * @return результат выполнения
 */
    /**
     * Паттерный метод для всех команд
     * @param arguments
     * @return
     */
    public ExecutionResponse apply(String arguments);

}