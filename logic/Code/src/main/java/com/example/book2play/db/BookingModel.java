package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Booking;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;

public interface BookingModel {

    /**
     * Return a collection of bookings that were made with the given court
     *
     * @param courtId       an id that uniquely identifies the court in the given sport center
     * @param cityId        an id that uniquely identifies the city
     * @param sportCenterId an id that uniquely identifies the sport center in the given city
     * @param date          the booking date of all the bookings in the collection
     * @return the collection of bookings of the court in the given date
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    Collection<Booking> getCourtBookings(String courtId, String cityId, String sportCenterId, Date date) throws MySQLException;

    /**
     * Return a collection of bookings that were made with the given sport center
     *
     * @param sportCenterId an id that uniquely identifies the sport center in the given city
     * @param cityId        an id that uniquely identifies the city
     * @param date          the booking date of all the bookings in the collection
     * @return the collection of bookings of the sport center in the given date
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    Collection<Booking> getSportCenterBookings(String sportCenterId, String cityId, Date date) throws MySQLException;

    /**
     * Return a collection of bookings that were made by the player
     *
     * @param playerId an id that uniquely identifies the player
     * @return the collection of bookings that is owned by the player with the given player is
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    Collection<Booking> getPlayerBookings(String playerId) throws MySQLException;

    /**
     * Return a collection of bookings that were made by the player
     *
     * @param playerId an id that uniquely identifies the player
     * @param cityId   an id that uniquely identifies the city
     * @param date     the booking date of all the bookings in the collection
     * @return the collection of bookings that is owned by the player in the given city and date
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    Collection<Booking> getPlayerBookingsInCity(String playerId, String cityId, Date date) throws MySQLException;


    /**
     * Create a new booking in the relation with the given attribute values
     *
     * @param timestamp     the date and time of when the booking was created
     * @param date          the date of the booking
     * @param startTime     the start time of the booking
     * @param endTime       the end time of the booking
     * @param cityId        the unique identifier to the city
     * @param sportCenterId the unique identifier, in the given city, to a sport center
     * @param courtId       the unique identifier, in the given sport center, to a court
     * @param playerId      the unique identifier of the player who made the booking
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    void createBooking(
            Timestamp timestamp,
            Date date,
            Time startTime,
            Time endTime,
            String cityId,
            String sportCenterId,
            String courtId,
            String playerId
    ) throws MySQLException;

    /**
     * Update the status of the give booking, the staff must be authorized to perform this action
     *
     * @param status        the new status
     * @param bookingId     the unique identifier of the booking
     * @param cityId        the unique identifier of the city
     * @param sportCenterId the unique identifier of the sport center in the given city
     * @param staffId       the unique identifier of the staff in the given sport center
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    void updateBookingStatus(Boolean status, String bookingId, String cityId, String sportCenterId, String staffId) throws MySQLException;

    /**
     * Remove the given booking from the database
     *
     * @param bookingId the unique identifier of the booking that will be removed
     * @param playerId  the unique identifier of the player performing this action
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    void cancelBooking(String bookingId, String playerId) throws MySQLException;

}
