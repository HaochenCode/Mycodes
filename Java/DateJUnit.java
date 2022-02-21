import static org.junit.jupiter.api.Assertions.*; // this allows to use assertEquals function
import org.junit.jupiter.api.Test; // this allows the JUnit testing framework, like methods preceded by @Test
public class DateJUnit {

    // Test for getDate function
    @Test
    public void getDate(){
        // Create a new object in class Date
        Date date1 = new Date();

        //Set the date and retrieve it
        date1.setDate("01-01-1998");
        assertEquals(date1.getDate(),"01-01-1998");

        //Second Test
        date1.setDate("02-02-1988");
        assertEquals(date1.getDate(),"02-02-1988");

        //Third Test
        date1.setDate("12-22-0988");
        assertEquals(date1.getDate(),"12-22-0988");

    }

    // Test for setDate function
    @Test
    public void setDate(){
        // Create a new object in class Date
        Date date1 = new Date();

        // Set a date and retrieve it
        date1.setDate("01-01-1998");
        assertEquals(date1.getDate(),"01-01-1998");

        // Second Test
        date1.setDate("02-02-1988");
        assertEquals(date1.getDate(),"02-02-1988");

        //Third Test
        date1.setDate("12-22-0988");
        assertEquals(date1.getDate(),"12-22-0988");
    }

    //Test for getMonth function
    @Test
    public void getMonth(){
        // Create a new object in class Date
        Date date1 = new Date();

        // Set a date and reset the month
        date1.setDate("02-03-2002");
        assertEquals(date1.getMonth(),2);

        //Second Test
        date1.setDate("12-03-2002");
        assertEquals(date1.getMonth(),12);

        //Third Test
        date1.setDate("10-03-2002");
        assertEquals(date1.getMonth(),10);
    }

    //Test fort setMonth function
    @Test
    public void setMonth(){
        // Create a new object in class Date
        Date date1 = new Date();

        //Initialize the testing date
        date1.setDate("02-03-2002");

        //Set a date and retrieve the month
        date1.setMonth(10);
        assertEquals(date1.getMonth(),10);

        //Second Test
        date1.setMonth(5);
        assertEquals(date1.getMonth(),5);

        //Third Test
       date1.setMonth(7);
        assertEquals(date1.getMonth(),7);
    }

    //Test for getDay function
    @Test
    public void getDay(){
        // Create a new object in class Date
        Date date1 = new Date();

        //Set the date and get the day from getDay function
        date1.setDate("02-03-2002");
        assertEquals(date1.getDay(),3);

        //Second Test
        date1.setDate("12-13-2002");
        assertEquals(date1.getDay(),13);

        //Third Test
        date1.setDate("10-01-2002");
        assertEquals(date1.getDay(),1);
    }

    //Test for setDay function
    @Test
    public void setDay(){
        //Create a new object of class Date
        Date date1 = new Date();

        // Set the date and reset the day, checking if it's changed
        date1.setDate("02-03-2002");
        date1.setDay(10);
        assertEquals(date1.getDay(),10);

        //Second Test
        date1.setDay(5);
        assertEquals(date1.getDay(),5);

        //Third Test
        date1.setDay(7);
        assertEquals(date1.getDay(),7);
    }

    // Test for getYear function
    @Test
    public void getYear(){
        // Create a new object in class Date
        Date date1 = new Date();

        //Set a date and retrieve the year
        date1.setDate("02-03-2002");
        assertEquals(date1.getYear(),2002);

        //Second Test
        date1.setYear(1998);
        assertEquals(date1.getYear(),1998);

        //Third Test
        date1.setYear(2020);
        assertEquals(date1.getYear(),2020);
    }

    //Test for setYear function
    @Test
    public void setYear(){
        // Create a new object in class Date
        Date date1 = new Date();

        //Set a date and reset a year, and retrieve it
        date1.setDate("02-03-2002");
        date1.setYear(2010);
        assertEquals(date1.getYear(),2010);

        //Second Test
        date1.setYear(2020);
        assertEquals(date1.getYear(),2020);

        //Third Test
        date1.setYear(1990);
        assertEquals(date1.getYear(),1990);
    }

    //Test the getDifferent function
    @Test
    public void getDifferent(){

        //Create two objects for compare
        Date date1 = new Date();
        Date date2 = new Date();

        //Test the difference in days of different years' dates
        date1.setDate("01-01-2000");
        date2.setDate("01-01-2010");
        assertEquals(Date.getDifferent(date1,date2), 3650);

        //Test the difference in days of different days' dates
        date1.setDate("01-02-1998");
        date2.setDate("01-03-1998");
        assertEquals(Date.getDifferent(date1,date2), 1);

        //Test the difference in days of different months' dates
        date1.setDate("02-01-1998");
        date2.setDate("03-01-1998");
        assertEquals(Date.getDifferent(date1,date2), 30);

        //Test total different dates
        date1.setDate("03-31-1998");
        date2.setDate("11-19-1969");
        assertEquals(Date.getDifferent(date1,date2), 10357);



    }
}

