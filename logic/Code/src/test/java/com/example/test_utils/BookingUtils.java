package com.example.test_utils;

import com.example.book2play.types.Booking;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.util.logging.Logger;


public class BookingUtils {

    private final static Logger LOG = Logger.getLogger("BOOKING_UTILS");

    public static Booking createBooking(Date bookingDate,
                                        Time bookingStartTime,
                                        Time bookingEndTime,
                                        Boolean isPaid,
                                        String cityId,
                                        String sportCenterId,
                                        String courtId,
                                        String playerId) {
        String bookingId = "";
        try {
            var md = MessageDigest.getInstance("MD5");
            var strBuilder = new StringBuilder();
            strBuilder.append(bookingDate.toString());
            strBuilder.append(bookingStartTime.toString());
            strBuilder.append(bookingEndTime.toString());
            strBuilder.append(cityId);
            strBuilder.append(sportCenterId);
            strBuilder.append(courtId);
            strBuilder.append(playerId);

            md.update(strBuilder.toString().getBytes());
            bookingId = bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            LOG.warning(e.getMessage());
        }

        LOG.info(bookingId);
        return new Booking(
                bookingId,
                TimeUtils.getTimestamp(),
                bookingDate,
                bookingStartTime,
                bookingEndTime,
                isPaid,
                cityId,
                sportCenterId,
                courtId,
                playerId
        );
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }
}
