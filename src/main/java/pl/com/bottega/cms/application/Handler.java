package pl.com.bottega.cms.application;

import pl.com.bottega.cms.model.commands.Command;

public interface Handler<C extends Command, R> {

    R handle(C command);

    Class<? extends Command> getSupportedCommandClass();

    default boolean canHandle(Command command) {
        return command.getClass().equals(getSupportedCommandClass());
    }

}
