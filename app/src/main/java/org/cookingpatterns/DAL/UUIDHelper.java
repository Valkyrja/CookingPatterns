package org.cookingpatterns.DAL;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by Andreas on 16.11.2015.
 */
public class UUIDHelper {

    //http://stackoverflow.com/a/2983319
    public static byte[] toByteArray(UUID uuid) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream(16);
        DataOutputStream da = new DataOutputStream(ba);
        try {
            da.writeLong(uuid.getMostSignificantBits());
            da.writeLong(uuid.getLeastSignificantBits());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ba.toByteArray();
    }

    //http://stackoverflow.com/a/24409153
    public static UUID getFromByteArray(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        UUID uuid = new UUID(high, low);
        return uuid;
    }
}
