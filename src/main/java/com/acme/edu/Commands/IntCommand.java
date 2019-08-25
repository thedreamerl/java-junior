package com.acme.edu.Commands;

import com.acme.edu.Logger.LoggingType;

import java.util.Objects;
import java.util.function.Predicate;

public class IntCommand implements Command {
    private static String PREFIX_PRIMITIVE = "primitive: ";

    private int message;
    private LoggingType type;


    public IntCommand(int message) {
        this.message = message;
        type = LoggingType.INT;
    }

    @Override
    public Object getMessage() {
        return message;
    }

    @Override
    public String flush() {
        int tmp_message = message;
        message = 0;
        return PREFIX_PRIMITIVE + tmp_message;
    }

    @Override
    public LoggingType getType() {
        return type;
    }

    @Override
    public boolean isTypeEquals(Command other) {
        return other instanceof IntCommand && other.getType() == type;
    }

    @Override
    public CommandAccumulateInfo accumulate(Command other) {
        if (other == null) return new CommandAccumulateInfo(this, null, -1);
        if (! (other instanceof IntCommand)) return new CommandAccumulateInfo(other, null, -1);
        String returnMessage = null;
        int otherMessage = (Integer)(other.getMessage());
        long sum = (long)message + otherMessage;
        if(Integer.MIN_VALUE > sum || sum > Integer.MAX_VALUE) {
            returnMessage = PREFIX_PRIMITIVE + message;
            sum = otherMessage;
        }

        return new CommandAccumulateInfo(this, returnMessage, 0);
    }
}
