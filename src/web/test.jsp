<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Javacode.Javacoe.DatabaseConnection" %>

<%
    DatabaseConnection dbConn = new DatabaseConnection();
    if (request.getMethod().equals("POST")) {
        String assetId = request.getParameter("assetId");
        String assetName = request.getParameter("assetName");
        String assetDescription = request.getParameter("assetDescription");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date acquisitionDate = dateFormat.parse(request.getParameter("acquisitionDate"));
        boolean forRent = Boolean.parseBoolean(request.getParameter("forRent"));
        double assetValue = Double.parseDouble(request.getParameter("assetValue"));
        String typeAsset = request.getParameter("typeAsset");
        String status = request.getParameter("status");
        double locLatitude = Double.parseDouble(request.getParameter("locLatitude"));
        double locLongitude = Double.parseDouble(request.getParameter("locLongitude"));
        String hoaName = request.getParameter("hoaName");
        String enclosingAsset = request.getParameter("enclosingAsset");

        dbConn.insertToAsset(assetId, assetName, assetDescription, (java.sql.Date) acquisitionDate,
                forRent, assetValue, typeAsset, status,
                locLatitude, locLongitude, hoaName, enclosingAsset);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Asset Form</title>
</head>
<body>
<form method="POST">
    Asset ID: <input type="text" name="assetId"><br>
    Asset Name: <input type="text" name="assetName"><br>
    Asset Description: <input type="text" name="assetDescription"><br>
    Acquisition Date (yyyy-mm-dd): <input type="text" name="acquisitionDate"><br>
    For Rent: <input type="checkbox" name="forRent"><br>
    Asset Value: <input type="text" name="assetValue"><br>
    Type Asset: <input type="text" name="typeAsset"><br>
    Status: <input type="text" name="status"><br>
    Location Latitude: <input type="text" name="locLatitude"><br>
    Location Longitude: <input type="text" name="locLongitude"><br>
    HOA Name: <input type="text" name="hoaName"><br>
    Enclosing Asset: <input type="text" name="enclosingAsset"><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>