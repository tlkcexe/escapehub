package gr.ihu.ict.escapehub.mission.engine;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StageStateTest {

    @Test
    void lockedStageIsNotAccessible() {
        assertFalse(StageState.LOCKED.isAccessible());
    }

    @Test
    void availableAndCompletedStagesAreAccessible() {
        assertTrue(StageState.AVAILABLE.isAccessible());
        assertTrue(StageState.COMPLETED.isAccessible());
    }
}
