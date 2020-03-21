package org.lamisplus.modules.base.controller;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException() {
    }
    public RecordNotFoundException(String record) {
        super(record);
    }
/*    public RecordNotFoundException(Long id) {
        super(String.valueOf(id));
    }
    public RecordNotFoundException(String message) {
        super(message);
    }
    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }*/
}
