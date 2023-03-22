package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeZone {

    @SerializedName("week_number")
    @Expose
    private Integer weekNumber;
    @SerializedName("utc_offset")
    @Expose
    private String utcOffset;
    @SerializedName("utc_datetime")
    @Expose
    private String utcDatetime;
    @SerializedName("unixtime")
    @Expose
    private Integer unixtime;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("raw_offset")
    @Expose
    private Integer rawOffset;
    @SerializedName("dst_until")
    @Expose
    private Object dstUntil;
    @SerializedName("dst_offset")
    @Expose
    private Integer dstOffset;
    @SerializedName("dst_from")
    @Expose
    private Object dstFrom;
    @SerializedName("dst")
    @Expose
    private Boolean dst;
    @SerializedName("day_of_year")
    @Expose
    private Integer dayOfYear;
    @SerializedName("day_of_week")
    @Expose
    private Integer dayOfWeek;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("client_ip")
    @Expose
    private String clientIp;
    @SerializedName("abbreviation")
    @Expose
    private String abbreviation;

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getUtcDatetime() {
        return utcDatetime;
    }

    public void setUtcDatetime(String utcDatetime) {
        this.utcDatetime = utcDatetime;
    }

    public Integer getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(Integer unixtime) {
        this.unixtime = unixtime;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getRawOffset() {
        return rawOffset;
    }

    public void setRawOffset(Integer rawOffset) {
        this.rawOffset = rawOffset;
    }

    public Object getDstUntil() {
        return dstUntil;
    }

    public void setDstUntil(Object dstUntil) {
        this.dstUntil = dstUntil;
    }

    public Integer getDstOffset() {
        return dstOffset;
    }

    public void setDstOffset(Integer dstOffset) {
        this.dstOffset = dstOffset;
    }

    public Object getDstFrom() {
        return dstFrom;
    }

    public void setDstFrom(Object dstFrom) {
        this.dstFrom = dstFrom;
    }

    public Boolean getDst() {
        return dst;
    }

    public void setDst(Boolean dst) {
        this.dst = dst;
    }

    public Integer getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(Integer dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

}
