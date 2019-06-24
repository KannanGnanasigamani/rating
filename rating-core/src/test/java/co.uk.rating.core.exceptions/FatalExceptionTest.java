package co.uk.hygiene.rating.core.exceptions;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * @author Kannan Gnanasigamani
 *
 */
public class FatalExceptionTest {

    private static final String EXAMPLE_MESSAGE = "Sample Exception Message";
    private static final Throwable thrownException = new Throwable(EXAMPLE_MESSAGE);

    @Test
    public void checkExceptionConstructors() {

        Exception e1 = new FatalException(thrownException);
        Exception e2 = new FatalException(EXAMPLE_MESSAGE);
        Exception e3 = new FatalException(EXAMPLE_MESSAGE, thrownException);

        assertEquals(thrownException, e1.getCause());
        assertEquals(EXAMPLE_MESSAGE, e2.getMessage());
        assertEquals(thrownException, e3.getCause());
        assertEquals(EXAMPLE_MESSAGE, e3.getMessage());
    }
}