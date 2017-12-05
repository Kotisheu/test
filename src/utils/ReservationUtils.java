package utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import beans.Bids;
import beans.Reservation;

public class ReservationUtils {

	public static List<Reservation> queryPassengerReservation(Connection conn) throws SQLException {
		String sql = "SELECT r.AccountNo, r.ResrNo, r.ResrDate, r.BookingFee, r.TotalFare, r.RepSSN "
				+ "FROM ABSTravellings.Reservation R, ABSTravellings.Customer C "
				+ "WHERE C.Id= 1  AND R.AccountNo= C.AccountNo " + "GROUP BY R.ResrNo;";

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();

		List<Reservation> list = new ArrayList<Reservation>();

		while (rs.next()) {
			int resNum = rs.getInt("ResrNo");
			Date resDate = rs.getDate("ResrDate");
			float bookingFee = rs.getFloat("BookingFee");
			float totalFare = rs.getFloat("TotalFare");
			int ssn = rs.getInt("RepSSN");
			int accountNo = rs.getInt("AccountNo");

			Reservation reservation = new Reservation();
			reservation.setReservationNumber(resNum);
			reservation.setReservationDate(resDate);
			reservation.setBookingFee(bookingFee);
			reservation.setTotalFare(totalFare);
			reservation.setSSN(ssn);
			reservation.setAccountNo(accountNo);
			list.add(reservation);
		}

		return list;

	}

	public static List<Reservation> queryCurrentReservation(Connection conn) throws SQLException {
		String sql = "SELECT r.AccountNo, r.ResrNo, r.ResrDate, r.BookingFee, r.TotalFare, r.RepSSN "
				+ "FROM ABSTravellings.Reservation R, ABSTravellings.Customer C, ABSTravellings.Includes I "
				+ "WHERE C.Id=1 AND R.AccountNo= C.AccountNo AND I.ResrNo = R.ResrNo AND I.Date >= now() "
				+ "GROUP BY R.ResrNo;";
		
		String sql2 = "now()";
		
		System.out.println(sql2);
		
		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();

		List<Reservation> list = new ArrayList<Reservation>();

		while (rs.next()) {
			int resNum = rs.getInt("ResrNo");
			Date resDate = rs.getDate("ResrDate");
			float bookingFee = rs.getFloat("BookingFee");
			float totalFare = rs.getFloat("TotalFare");
			int ssn = rs.getInt("RepSSN");
			int accountNo = rs.getInt("AccountNo");

			Reservation reservation = new Reservation();
			reservation.setReservationNumber(resNum);
			reservation.setReservationDate(resDate);
			reservation.setBookingFee(bookingFee);
			reservation.setTotalFare(totalFare);
			reservation.setSSN(ssn);
			reservation.setAccountNo(accountNo);
			list.add(reservation);
		}

		return list;
	}

	public static List<Bids> queryBidHistory(Connection conn) throws SQLException {
		String sql = "SELECT A.accountNo, A.airlineId, A.flightNo, A.Class, A.date, A.NYOP "
				+ "FROM	ABSTravellings.Auctions A, ABSTravellings.Customer C "
				+ "WHERE C.Id = 2 AND C.AccountNo = A.AccountNo;";

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();
		List<Bids> list = new ArrayList<Bids>();
		while (rs.next()) {
			int accountNo = rs.getInt("accountNo");
			String airlineID = rs.getString("airlineID");
			int flightNo = rs.getInt("flightNo");
			String flightClass = rs.getString("Class");
			Date date = rs.getDate("date");
			float NYOP = rs.getFloat("NYOP");

			Bids bids = new Bids();
			bids.setAccountNo(accountNo);
			bids.setAirlineID(airlineID);
			bids.setFlightNo(flightNo);
			bids.setFlightClass(flightClass);
			bids.setDate(date);
			bids.setNYOP(NYOP);
			list.add(bids);
		}
		return list;
	}
}