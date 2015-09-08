package edu.oregonstate.mist.webapiskeleton

import edu.oregonstate.mist.webapiskeleton.core.Sample
import org.junit.Test
import static org.junit.Assert.*

class SampleTest {
    @Test
    public void testSample() {
        assertTrue(new Sample().message == 'hello world')
    }
}
