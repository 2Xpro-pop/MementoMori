package org.bayasik.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

public interface MessageReaderStrategy {
    byte[] read(MessageReaderContext context);

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class MessageReaderContext {
        private int index;
        private byte[] bytes;
    }
}
