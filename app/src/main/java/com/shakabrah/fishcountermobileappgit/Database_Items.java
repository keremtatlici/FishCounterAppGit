package com.shakabrah.fishcountermobileappgit;

public class Database_Items {
    private String Count;
    private String Start;
    private String End;
    private String SayimKey;
    private String DateKey;
    private String MachineKey;
    private String Email;

    public Database_Items(String count, String start, String end, String sayimKey, String dateKey, String machineKey, String email) {
        Count = count;
        Start = start;
        End = end;
        SayimKey = sayimKey;
        DateKey = dateKey;
        MachineKey = machineKey;
        Email = email;
    }
public Database_Items(){

}

    public String getMachineKey() {
        return MachineKey;
    }

    public void setMachineKey(String machineKey) {
        MachineKey = machineKey;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }
    public String getSayimKey() {
        return SayimKey;
    }
    public  void setSayimKey(String sayimKey)
    {
        SayimKey = sayimKey;
    }

    public String getDateKey() {
        return DateKey;
    }

    public void setDateKey(String dateKey) {
        DateKey = dateKey;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
