package org.bayasik.messages;

import java.io.IOException;
import java.io.InputStream;

public class BinaryMessageReaderStrategy implements MessageReaderStrategy {
    @Override
    public byte[] read(MessageReaderContext context) {
        try {
            if(context.getBytes().length-context.getIndex() <= 4) {
                return new byte[0];
            }

            var bytes = new byte[context.getBytes().length-context.getIndex()];
            System.arraycopy(context.getBytes(), context.getIndex(), bytes, 0, bytes.length);

            var length = new byte[] { bytes[0], bytes[1], bytes[2], bytes[3] };
            var lengthInt =  (length[0] << 24) | (length[1] << 16) | (length[2] << 8) | length[3];

            if(bytes.length < lengthInt + 4) {
                return new byte[0];
            }

            if(lengthInt == 0){
                return new byte[0];
            }

            var data = new byte[lengthInt];
            System.arraycopy(bytes, 4, data, 0, data.length);
            System.out.println(bytes);

            var newBytes = new byte[bytes.length - (data.length + 4)];
            System.arraycopy(bytes, data.length + 4, newBytes, 0, newBytes.length);
            context.setBytes(newBytes);
            return data;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
