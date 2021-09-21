package com.jaffer.mad;

public class dlyActivity {
    private String date, Activity1,Activity2,Activity3,Activity4,Activity5;
    private String progress;
    private String[]completedTasks;

    public dlyActivity(String date, String activity1, String activity2, String activity3, String activity4, String activity5, String progress, String[] completedTasks) {
        this.date = date;
        Activity1 = activity1;
        Activity2 = activity2;
        Activity3 = activity3;
        Activity4 = activity4;
        Activity5 = activity5;
        this.progress = progress;
        this.completedTasks = completedTasks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActivity1() {
        return Activity1;
    }

    public void setActivity1(String activity1) {
        Activity1 = activity1;
    }

    public String getActivity2() {
        return Activity2;
    }

    public void setActivity2(String activity2) {
        Activity2 = activity2;
    }

    public String getActivity3() {
        return Activity3;
    }

    public void setActivity3(String activity3) {
        Activity3 = activity3;
    }

    public String getActivity4() {
        return Activity4;
    }

    public void setActivity4(String activity4) {
        Activity4 = activity4;
    }

    public String getActivity5() {
        return Activity5;
    }

    public void setActivity5(String activity5) {
        Activity5 = activity5;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String[] getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(String[] completedTasks) {
        this.completedTasks = completedTasks;
    }
}
