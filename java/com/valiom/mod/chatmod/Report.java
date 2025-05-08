package com.valiom.mod.chatmod;

import java.time.LocalDateTime;
import java.util.UUID;

public class Report {
    private final UUID reporterUuid;
    private final UUID reportedUuid;
    private final String reportedMessage;
    private final LocalDateTime timestamp;

    public Report(UUID reporterUuid, UUID reportedUuid, String reportedMessage, LocalDateTime timestamp) {
        this.reporterUuid = reporterUuid;
        this.reportedUuid = reportedUuid;
        this.reportedMessage = reportedMessage;
        this.timestamp = timestamp;
    }

    public UUID getReporterUuid() {
        return reporterUuid;
    }

    public UUID getReportedUuid() {
        return reportedUuid;
    }

    public String getReportedMessage() {
        return reportedMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getReportedName() {
        return org.bukkit.Bukkit.getOfflinePlayer(reportedUuid).getName();
    }

    public String getReporterName() {
        return org.bukkit.Bukkit.getOfflinePlayer(reporterUuid).getName();
    }

    public String getMessage() {
        return reportedMessage;
    }

    public LocalDateTime getDate() {
        return timestamp;
    }
}
