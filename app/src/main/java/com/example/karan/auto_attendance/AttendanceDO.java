package com.example.karan.auto_attendance;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "classattendance-mobilehub-1377230471-attendance")

public class AttendanceDO {
    private String _userId;
    private String _name;
    private String _weekEight;
    private String _weekEleven;
    private String _weekFive;
    private String _weekFour;
    private String _weekNine;
    private String _weekOne;
    private String _weekSeven;
    private String _weekSix;
    private String _weekTen;
    private String _weekThree;
    private String _weekTwelve;
    private String _weekTwo;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }
    @DynamoDBAttribute(attributeName = "week_eight")
    public String getWeekEight() {
        return _weekEight;
    }

    public void setWeekEight(final String _weekEight) {
        this._weekEight = _weekEight;
    }
    @DynamoDBAttribute(attributeName = "week_eleven")
    public String getWeekEleven() {
        return _weekEleven;
    }

    public void setWeekEleven(final String _weekEleven) {
        this._weekEleven = _weekEleven;
    }
    @DynamoDBAttribute(attributeName = "week_five")
    public String getWeekFive() {
        return _weekFive;
    }

    public void setWeekFive(final String _weekFive) {
        this._weekFive = _weekFive;
    }
    @DynamoDBAttribute(attributeName = "week_four")
    public String getWeekFour() {
        return _weekFour;
    }

    public void setWeekFour(final String _weekFour) {
        this._weekFour = _weekFour;
    }
    @DynamoDBAttribute(attributeName = "week_nine")
    public String getWeekNine() {
        return _weekNine;
    }

    public void setWeekNine(final String _weekNine) {
        this._weekNine = _weekNine;
    }
    @DynamoDBAttribute(attributeName = "week_one")
    public String getWeekOne() {
        return _weekOne;
    }

    public void setWeekOne(final String _weekOne) {
        this._weekOne = _weekOne;
    }
    @DynamoDBAttribute(attributeName = "week_seven")
    public String getWeekSeven() {
        return _weekSeven;
    }

    public void setWeekSeven(final String _weekSeven) {
        this._weekSeven = _weekSeven;
    }
    @DynamoDBAttribute(attributeName = "week_six")
    public String getWeekSix() {
        return _weekSix;
    }

    public void setWeekSix(final String _weekSix) {
        this._weekSix = _weekSix;
    }
    @DynamoDBAttribute(attributeName = "week_ten")
    public String getWeekTen() {
        return _weekTen;
    }

    public void setWeekTen(final String _weekTen) {
        this._weekTen = _weekTen;
    }
    @DynamoDBAttribute(attributeName = "week_three")
    public String getWeekThree() {
        return _weekThree;
    }

    public void setWeekThree(final String _weekThree) {
        this._weekThree = _weekThree;
    }
    @DynamoDBAttribute(attributeName = "week_twelve")
    public String getWeekTwelve() {
        return _weekTwelve;
    }

    public void setWeekTwelve(final String _weekTwelve) {
        this._weekTwelve = _weekTwelve;
    }
    @DynamoDBAttribute(attributeName = "week_two")
    public String getWeekTwo() {
        return _weekTwo;
    }

    public void setWeekTwo(final String _weekTwo) {
        this._weekTwo = _weekTwo;
    }

}
