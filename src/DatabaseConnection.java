import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {
    Connection connection;
    Statement statement;
    public DatabaseConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/hoadb";
            connection = DriverManager.getConnection(url,"root","12345678");
            statement = connection.createStatement();
            connection.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    public void insertToAsset(String assetId, String assetName, String assetDescription, Date acquisitionDate,
                              boolean forRent, double assetValue, String typeAsset, String status,
                              double locLatitude, double locLongitude, String hoaName, String enclosingAsset) {
        try {
            String query = "INSERT INTO assets (asset_id, asset_name, asset_description, acquisition_date, forrent," +
                    " asset_value, type_asset, status, loc_lattitude, loc_longiture, hoa_name, enclosing_asset) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, assetId);
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

    public void deleteAsset(String assetID) {
        try {
            String query = "DELETE FROM assets WHERE asset_id=" + assetID;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void disposeAsset(String assetID) {
        try {
            String query = "UPDATE assets SET status='D' WHERE asset_id=" + assetID;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

    }
}