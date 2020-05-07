package org.eleven.exception;

public interface BussinessExceptionAssert extends IErrorCode, Assert {

    default MyException newException() {
        return new MyException(this);
    }

    default MyException newException(Object... args) {
        return new MyException(this);
    }

}
