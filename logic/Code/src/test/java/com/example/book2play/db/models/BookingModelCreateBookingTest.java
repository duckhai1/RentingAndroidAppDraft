package com.example.book2play.db.models;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.utils.TimeUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BookingModelCreateBookingTest extends ModelTestSetup {

    private static int BOOKINGS_LIMIT = 3;

    @Before
    public void setupPreconditionsForCreatingABooking() throws MySQLException {
        CITY.createCity("HCM");
        SPORT_CENTER.createCityCenter("Q1", "HCM");
        COURT.createCityCenterCourt("Court1", "HCM", "Q1");
        PLAYER.createPlayer("Alice");
    }

    @Test
    public void testCreateMultipleBookingsUpToTheLimit() throws Exception {
        for (int i = 0; i < BOOKINGS_LIMIT; i++) {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice" + i,
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7 + i),
                    TimeUtils.getTime(11, 0, 0),
                    TimeUtils.getTime(12, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
        }
    }

    @Test
    public void testCreateBookingWithDuplicateId() throws Exception {
        final int EXPECTED_CODE = 407;
        BOOKING.createBooking(
                "HCMQ1Court1Alice",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(11, 0, 0),
                TimeUtils.getTime(12, 0, 0),
                "HCM", "Q1", "Court1", "Alice"
        );

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(8),
                    TimeUtils.getTime(11, 0, 0),
                    TimeUtils.getTime(12, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWhenBookingsLimitReached() throws Exception {
        final int EXPECTED_CODE = 410;

        for (int i = 0; i < BOOKINGS_LIMIT; i++) {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice" + i,
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7 + i),
                    TimeUtils.getTime(11, 0, 0),
                    TimeUtils.getTime(12, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
        }

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice" + (BOOKINGS_LIMIT + 1),
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7 + BOOKINGS_LIMIT + 1),
                    TimeUtils.getTime(11, 0, 0),
                    TimeUtils.getTime(12, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }


    @Test
    public void testCreateABookingWhenPlayerHasOnePaidPastBooking() throws Exception {
        rawInsertBooking(
                "HCMQ1Court1Alice1",
                TimeUtils.getDate(-7),
                TimeUtils.getTime(11, 0, 0),
                TimeUtils.getTime(12, 0, 0),
                true,
                "HCM", "Q1", "Court1", "Alice"
        );

        BOOKING.createBooking(
                "HCMQ1Court1Alice2",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(11, 0, 0),
                TimeUtils.getTime(12, 0, 0),
                "HCM", "Q1", "Court1", "Alice"
        );
    }


    @Test
    public void testCreateABookingWhenPlayerHasOneUnpaidPastBooking() throws Exception {
        final int EXPECTED_CODE = 412;

        rawInsertBooking(
                "HCMQ1Court1Alice1",
                TimeUtils.getDate(-7),
                TimeUtils.getTime(11, 0, 0),
                TimeUtils.getTime(12, 0, 0),
                false,
                "HCM", "Q1", "Court1", "Alice"
        );

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice2",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(11, 0, 0),
                    TimeUtils.getTime(12, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testIfBookingsInConsecutiveTimeSlotCanBeMade() throws Exception {
        for (int i = 0; i < BOOKINGS_LIMIT; i++) {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice" + i,
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9 + i, 0, 0),
                    TimeUtils.getTime(10 + i, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
        }
    }

    @Test
    public void testCreateBookingWithOverlappingTimeCase1() throws Exception {
        final int EXPECTED_CODE = 413;
        BOOKING.createBooking(
                "HCMQ1Court1Alice1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "Court1", "Alice"
        );

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice2",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(10, 0, 0),
                    TimeUtils.getTime(11, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithOverlappingTimeCase2() throws Exception {
        final int EXPECTED_CODE = 413;
        BOOKING.createBooking(
                "HCMQ1Court1Alice1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "Court1", "Alice"
        );

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice2",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(8, 30, 0),
                    TimeUtils.getTime(10, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithOverlappingTimeCase3() throws Exception {
        final int EXPECTED_CODE = 413;
        BOOKING.createBooking(
                "HCMQ1Court1Alice1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "Court1", "Alice"
        );

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice2",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 0, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithOverlappingTimeCase4() throws Exception {
        final int EXPECTED_CODE = 413;
        BOOKING.createBooking(
                "HCMQ1Court1Alice1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "Court1", "Alice"
        );

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice2",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 0, 0),
                    TimeUtils.getTime(10, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithOverlappingTimeCase5() throws Exception {
        final int EXPECTED_CODE = 413;
        BOOKING.createBooking(
                "HCMQ1Court1Alice1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "Court1", "Alice"
        );

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice2",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 30, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithInvalidCityId() {
        final int EXPECTED_CODE = 460;

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice1",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 30, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HaNoi", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithInvalidSportCenterId() {
        final int EXPECTED_CODE = 461;

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice1",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 30, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HCM", "Q13", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithInvalidCourtId() {
        final int EXPECTED_CODE = 462;

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice1",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 30, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HCM", "Q1", "Court2", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithInvalidPlayerId() {
        final int EXPECTED_CODE = 464;

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Bob1",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 30, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HCM", "Q1", "Court1", "Bob"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithInvalidDate() {
        final int EXPECTED_CODE = 466;

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice1",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(-7),
                    TimeUtils.getTime(9, 30, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBooking30MinDuration() {
        final int EXPECTED_CODE = 467;

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice1",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 0, 0),
                    TimeUtils.getTime(9, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }
    @Test
    public void testCreateBooking2HourDuration() {
        final int EXPECTED_CODE = 467;

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice1",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 0, 0),
                    TimeUtils.getTime(11, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingStartTimeNotFactorOf15Min() {
        final int EXPECTED_CODE = 468;

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice1",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 10, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingStartTimeNotInWorkingHours() {
        final int EXPECTED_CODE = 468;

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice1",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(6, 0, 0),
                    TimeUtils.getTime(7, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingEndTimeNotFactorOf15Min() {
        final int EXPECTED_CODE = 469;

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice1",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 0, 0),
                    TimeUtils.getTime(10, 20, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingEndTimeNotInWorkingHours() {
        final int EXPECTED_CODE = 469;

        try {
            BOOKING.createBooking(
                    "HCMQ1Court1Alice1",
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(20, 0, 0),
                    TimeUtils.getTime(21, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    private void rawInsertBooking(
            String bookingId,
            java.sql.Date bookingDate,
            java.sql.Time bookingStartTime,
            java.sql.Time bookingEndTime,
            Boolean isPaid,
            String cityId,
            String sportCenterId,
            String courtId,
            String playerId
    ) throws Exception {
        var conn = DB.getConnection();

        // get courtPk
        var stmt1 = conn.prepareStatement("SELECT courtPk FROM courts NATURAL JOIN sportCenters NATURAL JOIN cities WHERE cityId = ? AND sportCenterId = ? AND courtId = ?");
        stmt1.setString(1, cityId);
        stmt1.setString(2, sportCenterId);
        stmt1.setString(3, courtId);
        var stmt1RS = stmt1.executeQuery();
        if (!stmt1RS.next()) {
            throw new Exception("Data not found");
        }
        var courtPk = stmt1RS.getString("courtPk");

        // get playerPk
        var stmt2 = conn.prepareStatement("SELECT playerPk FROM players WHERE playerId = ?");
        stmt2.setString(1, playerId);
        var stmt2RS = stmt2.executeQuery();
        if (!stmt2RS.next()) {
            throw new Exception("Data not found");
        }
        var playerPk = stmt2RS.getString("playerPk");

        // create booking
        var stmt = conn.prepareStatement("" +
                "INSERT INTO bookings (" +
                "bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, courtPk, playerPk" +
                ")" + " VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, "HCMQ1Court1Alice1");
        stmt.setTimestamp(2, TimeUtils.getTimestamp());
        stmt.setDate(3, bookingDate);
        stmt.setTime(4, bookingStartTime);
        stmt.setTime(5, bookingEndTime);
        stmt.setBoolean(6, isPaid);
        stmt.setString(7, courtPk);
        stmt.setString(8, playerPk);
        if (stmt.executeUpdate() != 1) {
            throw new Exception("Incorrect number of updated rows");
        }
    }
}
