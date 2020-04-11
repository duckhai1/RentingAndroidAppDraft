package com.example.book2play.db.types;

import com.example.book2play.db.MySQLModel;
import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.utils.BookingProcedures;



import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Booking extends MySQLModel implements BookingProcedures {
    private int bookingPK;
    private String bookingId;
    private Date bookingDate;
    private Time bookingStartTime;
    private Time bookingEndTime;
    private Timestamp createAt;
    private boolean isPaid;
    private int playerPk;
    private int courtPK;

    public Booking(int bookingPK, String bookingId, Date bookingDate, Time bookingStartTime, Time bookingEndTime, Timestamp createAt, boolean isPaid, int playerPk, int courtPK) {
        this.bookingPK = bookingPK;
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.bookingStartTime = bookingStartTime;
        this.bookingEndTime = bookingEndTime;
        this.createAt = createAt;
        this.isPaid = isPaid;
        this.playerPk = playerPk;
        this.courtPK = courtPK;
    }
    public Booking (MySQLServer db){super(db);}

    @Override
    public Booking getBookingInfo(String bookingId) throws MySQLException {
        Connection conn;
        CallableStatement stm;
        ResultSet rs;
        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call getBookingInfo(?,?)}");
            stm.setString(1, bookingId);
            stm.registerOutParameter(2, Types.INTEGER );
            rs = stm.executeQuery();
            var statusCode = stm.getInt(2);
            if(statusCode >= 400 && statusCode < 500){
                throw new MySQLException(statusCode);
            }
            Booking booking = new Booking (
                rs.getInt("bookingPK"),
                rs.getString("bookingId"),
                rs.getDate("bookingDate"),
                rs.getTime("bookingStartTime"),
                rs.getTime("bookingEndTime"),
                rs.getTimestamp("createAt"),
                rs.getBoolean("isPaid"),
                rs.getInt("playerPk"),
                rs.getInt("courtPk")
                    );
                return booking;

        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception"+ e.getMessage(),e);
        }
    }

    @Override
    public Collection<Booking> getBookings(String cityId, String sportCenterId, String courtId, Date date) throws MySQLException {
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        Connection conn;
        CallableStatement stm;
        ResultSet rs;
        try{
            conn= this.db.getConnection();
            stm = conn.prepareCall("{call getBookings(?,?,?,?,?)}");
            stm.setString(1, cityId);
            stm.setString(2, sportCenterId);
            stm.setString(3, courtId);
            stm.setDate(4, date);
            stm.registerOutParameter(5, Types.INTEGER);
            rs = stm.executeQuery();
            var statusCode = stm.getInt(5);
            if(statusCode>5000){
                throw new MySQLException(statusCode);
            }
            while(rs.next()){
                bookings.add(new Booking(
                    rs.getInt("bookingPK"),
                    rs.getString("bookingId"),
                    rs.getDate("bookingDate"),
                    rs.getTime("bookingStartTime"),
                    rs.getTime("bookingEndTime"),
                    rs.getTimestamp("createAt"),
                    rs.getBoolean("isPaid"),
                    rs.getInt("playerPk"),
                    rs.getInt("courtPk")
                ));
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception"+ e.getMessage(), e);
        }
    }

    @Override
    public Collection<Booking> getSportCenterBookings(String sportCenterId, Date date) throws MySQLException {
        ArrayList<Booking> bookings = new ArayList<Booking>();
        Connection conn;
        CallableStatement stm ;
        ResultSet rs;
        try{
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call getSportenterBookings(?,?,?)}");
            stm.setString(1, sportCenterId);
            stm.setDate(2, date);
            stm.registerOutParameter(2, Types.INTEGER);
            rs = stm.executeQuery();
            var statusCode = stm.getInt(2);
            if(statusCode >500){
                throw new MySQLException(statusCode);
            }
            while(rs.next()){
                bookings.add(new Booking(
                    rs.getInt("bookingPK"),
                    rs.getString("bookingId"),
                    rs.getDate("bookingDate"),
                    rs.getTime("bookingStartTime"),
                    rs.getTime("bookingEndTime"),
                    rs.getTimestamp("createAt"),
                    rs.getBoolean("isPaid"),
                    rs.getInt("playerPk"),
                    rs.getInt("courtPk")
                ));
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception"+ e.getMessage(),e);
        }
        return bookings;
    }

    @Override
    public Collection<Booking> getPlayerBookings(String playerId, String cityId, Date date) throws MySQLException {
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        Connection conn;
        CallableStatement stm;
        ResultSet rs;
        try{
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call getPlayerBookings(?,?,?)}");
            stm.setString(1, playerId);
            stm.setString(2, cityId);
            stm.registerOutParameter(3,Types.INTEGER);
            rs = stm.executeQuery();
            var statusCode = stm.getInt(3);
            if(statusCode >500){
                throw new MySQLException(statusCode);
            }
            while(rs.next()){
                bookings.add(new Booking(
                    rs.getInt("bookingPK"),
                    rs.getString("bookingId"),
                    rs.getDate("bookingDate"),
                    rs.getTime("bookingStartTime"),
                    rs.getTime("bookingEndTime"),
                    rs.getTimestamp("createAt"),
                    rs.getBoolean("isPaid"),
                    rs.getInt("playerPk"),
                    rs.getInt("courtPk")
                ));
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception"+ e.getMessage(), e);
        }
        return bookings;
    }

    @Override
    public void createBooking(String bookingId, Timestamp timestamp, Date date, Time startTime, Time endTime, String cityId, String sportCenterId, String courtId, String playerId) throws MySQLException {
        Connection conn;
        CallableStatement stm;
        try{
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call createBooking(?,?,?,?,?,?,?,?,?)}");
            stm.setString(1, bookingId);
            stm.setTimestamp(2, timestamp);
            stm.setDate(3, date);
            stm.setTime(4, startTime);
            stm.setTime(5, endTime);
            stm.setString(6, cityId);
            stm.setString(7,sportCenterId);
            stm.setString(8, courtId);
            stm.setString(9, playerId);
            stm.registerOutParameter(10, Types.INTEGER);
            stm.executeUpdate();
            var statusCode = stm.getInt(10);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        }

    }

    @Override
    public void updateBookingStatus(Boolean status, String bookingId, String playerId, String staffId) throws MySQLException {
        Connection conn;
        CallableStatement stm;
        try{
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call(?,?,?,?,?) }");
            stm.setBoolean(1, status);
            stm.setString(2, bookingId);
            stm.setString(3, playerId);
            stm.setString(4, staffId);
            stm.registerOutParameter(5, Types.INTEGER);
            stm.executeUpdate();
            var statusCode = stm.getInt(5);
            if(statusCode>500){
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception"+ e.getMessage(), e);
        }
    }

    @Override
    public void cancelBooking(String bookingId, String playerId) throws MySQLException {
        Connection conn;
        CallableStatement stm;
        try{
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call cancelBooking(?,?,?)}");
            stm.setString(1, bookingId);
            stm.setString(2, playerId);
            stm.registerOutParameter(3, Types.INTEGER);
            stm.executeUpdate();
            var statusCode = stm.getInt(3);
            if(statusCode>500){
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception"+ e.getMessage(), e);
        }
    }

    @Override
    public void clearBooking(String bookingId) throws MySQLException {
        Connection conn;
        PreparedStatement stm;
        try{
            conn = this.db.getConnection();
            stm = conn.prepareStatement("DELETE FROM booking WHERE bookingId == ?");
            stm.setString(1, bookingId);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception"+ e.getMessage(), e);
        }
    }

