package Javacode.Javacoe;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {
    Connection connection;
    Statement statement;
    public DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/hoadb";
            connection = DriverManager.getConnection(url, "root", "12345678");
            statement = connection.createStatement();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    public void insertToAsset(int assetId, String assetName, String assetDescription, Date acquisitionDate,
                              boolean forRent, double assetValue, String typeAsset, String status,
                              double locLatitude, double locLongitude, String hoaName, String enclosingAsset) {
        try {
            String query = "INSERT INTO assets (asset_id, asset_name, asset_description, acquisition_date, forrent," +
                    " asset_value, type_asset, status, loc_lattitude, loc_longiture, hoa_name, enclosing_asset) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, assetId);
            pstmt.setString(2, assetName);
            pstmt.setString(3, assetDescription);
            pstmt.setDate(4, new java.sql.Date(acquisitionDate.getTime()));
            pstmt.setBoolean(5, forRent);
            pstmt.setDouble(6, assetValue);
            pstmt.setString(7, typeAsset);
            pstmt.setString(8, status);
            pstmt.setDouble(9, locLatitude);
            pstmt.setDouble(10, locLongitude);
            pstmt.setString(11, hoaName);
            pstmt.setString(12, enclosingAsset);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }
    public void insertToAsset(int assetId, String assetName, String assetDescription, Date acquisitionDate,
                              boolean forRent, double assetValue, String typeAsset, String status,
                              double locLatitude, double locLongitude, String hoaName) {
        try {
            String query = "INSERT INTO assets (asset_id, asset_name, asset_description, acquisition_date, forrent," +
                    " asset_value, type_asset, status, loc_lattitude, loc_longiture, hoa_name) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, assetId);
            pstmt.setString(2, assetName);
            pstmt.setString(3, assetDescription);
            pstmt.setDate(4, new java.sql.Date(acquisitionDate.getTime()));
            pstmt.setBoolean(5, forRent);
            pstmt.setDouble(6, assetValue);
            pstmt.setString(7, typeAsset);
            pstmt.setString(8, status);
            pstmt.setDouble(9, locLatitude);
            pstmt.setDouble(10, locLongitude);
            pstmt.setString(11, hoaName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public ArrayList<String> displayAssets() {
        ArrayList<String> assetList = new ArrayList<>();
        try {
            String query = "SELECT asset_id, asset_name, asset_description, asset_value, hoa_name,status FROM assets";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String assetId = rs.getString("asset_id");
                String assetName = rs.getString("asset_name");
                String assetDescription = rs.getString("asset_description");
                double assetValue = rs.getDouble("asset_value");
                String hoaName = rs.getString("hoa_name");
                String status = rs.getString("status");
                assetList.add(assetId + ", " + assetName + ", " + assetDescription + ", " + assetValue + ", " + hoaName+", "+ status);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return assetList;
    }

    public void deleteAsset(int assetID) {
        try {
            String query = "DELETE FROM assets WHERE status !='D' AND enclosing_asset IS null AND asset_id=" + assetID;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }
    public void updateAsset(int assetID){
        String query ="";
    }

    public void disposeAsset(int assetID) {
        try {
            String query = "UPDATE assets SET status='D' WHERE asset_id=" + assetID;
            statement.executeUpdate(query);
            query="UPDATE  assets SET enclosing_asset= null WHERE enclosing_asset ="+assetID;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void recordRental(int assetId, Date rentDate, Date reserveDate, int residentId, double rentAmt,
                             double discount, String status, String inspectDetails, double assessedVal,
                             int acceptHOId, String acceptPos, Date acceptElecDate, Date returnDate) {
        try {
            String query = "INSERT INTO asset_rentals (asset_id, rental_date, reservation_date, resident_id," +
                    " rental_amount, discount, status, inspection_details, assessed_value, accept_hoid," +
                    " accept_position, accept_electiondate, return_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, assetId);
            pstmt.setDate(2, new java.sql.Date(rentDate.getTime()));
            pstmt.setDate(3, new java.sql.Date(reserveDate.getTime()));
            pstmt.setInt(4, residentId);
            pstmt.setDouble(5, rentAmt);
            pstmt.setDouble(6, discount);
            pstmt.setString(7, status);
            pstmt.setString(8, inspectDetails);
            pstmt.setDouble(9, assessedVal);
            pstmt.setInt(10, acceptHOId);
            pstmt.setString(11, acceptPos);
            pstmt.setDate(12, new java.sql.Date(acceptElecDate.getTime()));
            pstmt.setDate(13, new java.sql.Date(returnDate.getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void returnRental(String assetId) {
        try {
            String query = "UPDATE asset_rentals " +
                    "SET status='N' WHERE asset_id=" + assetId;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void updateRental(String assetId, Date rentDate, Date reserveDate, int residentId, double rentAmt,
                             double discount, String status, String inspectDetails, double assessedVal,
                             int acceptHOId, String acceptPos, Date acceptElecDate, Date returnDate) {
        try {
            String query = "UPDATE asset_rentals " +
                    "SET rental_date=" + rentDate + ", " +
                    "reservation_date=" + reserveDate + ", " +
                    "resident_id=" + residentId + ", " +
                    "rental_amount=" + rentAmt + ", " +
                    "discount=" + discount + ", " +
                    "status='" + status + "', " +
                    "inspection_details='" + inspectDetails + "', " +
                    "assessed_value=" + assessedVal + ", " +
                    "accept_hoid=" + acceptHOId + ", " +
                    "accept_position='" + acceptPos + "', " +
                    "acccept_electiondate=" + acceptElecDate + ", " +
                    "return_date=" + returnDate + " " +
                    "WHERE asset_id=" + assetId;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void deleteRental(String assetId) {
        try {
            String query = "UPDATE asset_rentals " +
                    "SET status='N' WHERE asset_id=" + assetId;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        DatabaseConnection databaseConnection= new DatabaseConnection();
        databaseConnection.insertToAsset(1222, "chair", "String assetDescription", new Date(2023,4,15),
                true, 312.21,"P", "W",
                312.21, 312.21, "SJH");
        ArrayList<String> test=databaseConnection.displayAssets();
        for (String s : test) {
            System.out.println(s);
        }
        System.out.println("AFTER DELETE");
        databaseConnection.deleteAsset(1222);
        test=databaseConnection.displayAssets();
        for (String s : test) {
            System.out.println(s);
        }
    }
}