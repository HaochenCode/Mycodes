public class Date {
    private String date = "";

    public String getDate (){ return date;}

    public void setDate (String date){ this.date = date;}

    public int getMonth () { return Integer.valueOf(date.substring(0,2));}

    public void setMonth(int month) {
        String month1 = String.format("%02d", month);
        date = month1 + date.substring(2,10);
    }

    public int getDay () { return Integer.valueOf(date.substring(3,5));}

    public void setDay (int day) {
        String day1 = String.format("%02d", day);
        date = date.substring(0,3) + day1 + date.substring(5,10);
    }

    public int getYear () { return Integer.valueOf(date.substring(6,10));}

    public void setYear (int year) {
        String year1 = String.format("%04d",year);
        date = date.substring(0,6) + year1;
    }

    public static int getDifferent(Date date1, Date date2){
        int dayDifferent = date1.getDay() - date2.getDay();
        int yearDifferent = date1.getYear() - date2.getYear();
        int monthDifferent = (date1.getMonth() - date2.getMonth());
        return Math.abs((dayDifferent + yearDifferent * 365 + monthDifferent * 30));
    }

}
