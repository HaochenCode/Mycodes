import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test; // this allows the JUnit testing framework, like methods preceded by @Test


class MovieTicketTest {

    @Test
    void ageRestricted() {

        assertFalse( MovieTicket.ageRestricted(18,"G"));

        assertTrue( MovieTicket.ageRestricted(5,"PG13"));

        assertFalse( MovieTicket.ageRestricted(5,"PG"));

        assertTrue( MovieTicket.ageRestricted(15,"R"));

    }

    @Test
    void getPrice() {
        MovieTicket mt1 = new MovieTicket();
        assertEquals(5, MovieTicket.getPrice(10));
        assertEquals(9, MovieTicket.getPrice(14));
        assertEquals(12, MovieTicket.getPrice(20));
    }

}