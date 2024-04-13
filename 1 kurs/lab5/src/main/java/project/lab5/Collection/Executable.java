package project.lab5.Collection;

import project.lab5.AskManager.Ask;

public interface Executable{
/**
 * Выполнить что-либо.
 *
 * @param arguments Аргумент для выполнения
 * @return результат выполнения
 */
public ExecutionResponse apply(String arguments) throws Ask.AskBreak;

}