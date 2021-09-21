package com.jaffer.mad;

public class PrevActivity {
    private String date, Activity1,Activity2,Activity3,Activity4,Activity5;
    private String progress;

    PrevActivity(){};
    PrevActivity(String date,String activity1,String activity2,String activity3,String activity4,String activity5, String progress){
        this.date = date;
        this.Activity1 = activity1;
        this.Activity2 = activity2;
        this.Activity3 = activity3;
        this.Activity4 = activity4;
        this.Activity5 = activity5;
        this.progress = progress;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getActivity1() {
        return Activity1;
    }

    public void setActivity1(String Activity1) {
        this.Activity1 = Activity1;
    }

    public String getActivity2() {
        return Activity2;
    }

    public void setActivity2(String Activity2) {
        this.Activity2 = Activity2;
    }

    public String getActivity3() {
        return Activity3;
    }

    public void setActivity3(String Activity3) {
        this.Activity3 = Activity3;
    }

    public String getActivity4() {
        return Activity4;
    }

    public void setActivity4(String Activity4) {
        this.Activity4 = Activity4;
    }

    public String getActivity5() {
        return Activity5;
    }

    public void setActivity5(String Activity5) {
        this.Activity5 = Activity5;
    }
}
