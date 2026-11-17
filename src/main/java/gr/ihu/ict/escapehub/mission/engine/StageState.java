package gr.ihu.ict.escapehub.mission.engine;

/**
 * Οι επιτρεπτές καταστάσεις ενός σταδίου αποστολής.
 * Θα επεκταθεί στο Sprint 1 (US-22) με τη μηχανή μεταβάσεων και mutation testing (PIT).
 */
public enum StageState {
    LOCKED,
    AVAILABLE,
    COMPLETED;

    public boolean isAccessible() {
        return this == AVAILABLE || this == COMPLETED;
    }
}
