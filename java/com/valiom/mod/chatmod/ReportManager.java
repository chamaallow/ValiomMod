package com.valiom.mod.chatmod;

import com.valiom.ValiomCore;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReportManager {

    public static void saveReport(Report report) {
        try (Connection connection = ValiomCore.getDatabaseManager().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO chat_reports (reporter_uuid, reported_uuid, reported_message, timestamp) VALUES (?, ?, ?, ?)")) {

            ps.setString(1, report.getReporterUuid().toString());
            ps.setString(2, report.getReportedUuid().toString());
            ps.setString(3, report.getReportedMessage());
            ps.setTimestamp(4, Timestamp.valueOf(report.getTimestamp()));
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Report> getAllReports() {
        List<Report> reports = new ArrayList<>();
        try (Connection connection = ValiomCore.getDatabaseManager().getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM chat_reports")) {

            while (rs.next()) {
                UUID reporter = UUID.fromString(rs.getString("reporter_uuid"));
                UUID reported = UUID.fromString(rs.getString("reported_uuid"));
                String message = rs.getString("reported_message");
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                reports.add(new Report(reporter, reported, message, timestamp));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public static void deleteReport(Report report) {
        try (Connection connection = ValiomCore.getDatabaseManager().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM chat_reports WHERE reporter_uuid = ? AND reported_uuid = ? AND reported_message = ? AND timestamp = ?")) {

            ps.setString(1, report.getReporterUuid().toString());
            ps.setString(2, report.getReportedUuid().toString());
            ps.setString(3, report.getReportedMessage());
            ps.setTimestamp(4, Timestamp.valueOf(report.getTimestamp()));
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Report getReportByName(String name) {
        return getAllReports().stream()
                .filter(r -> name.equalsIgnoreCase(r.getReportedName()))
                .findFirst()
                .orElse(null);
    }
}
