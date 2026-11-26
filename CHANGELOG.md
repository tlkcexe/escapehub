# Changelog

Όλες οι αξιοσημείωτες αλλαγές του EscapeHub καταγράφονται εδώ.
Η μορφή βασίζεται στο [Keep a Changelog](https://keepachangelog.com/en/1.1.0/) και οι εκδόσεις
ακολουθούν [Semantic Versioning](https://semver.org/lang/el/).

Κάθε Sprint Review = ένα tag + ένα GitHub release. Πριν το tag, μεταφέρουμε το περιεχόμενο
του `[Unreleased]` κάτω από τη νέα έκδοση.

## [Unreleased]

## [0.1.0] - 2026-11-26

Πρώτη έκδοση (Sprint 0): υποδομή έργου, project skeleton, CI/CD και τεκμηρίωση ανάλυσης και σχεδίασης.
Δεν περιλαμβάνει ακόμα λειτουργικότητα παιχνιδιού.

### Added

**Αποθετήριο και διαδικασία (US-01, US-02)**
- Αρχικά αρχεία αποθετηρίου: `README.md`, `CONTRIBUTING.md`, `CHANGELOG.md`, `.gitignore`.
- Templates για issues (user story, technical task, bug report) και για pull requests, με πεδίο δήλωσης χρήσης AI.
- Branch protection στον `main`: αλλαγές μόνο με pull request, επιτυχές CI και έγκριση από άλλο μέλος.
- Definition of Done (`docs/DEFINITION_OF_DONE.md`) και AI-usage log (`docs/AI_USAGE_LOG.md`).
- Σύνθεση ομάδας και πρόγραμμα εναλλαγής Scrum Master (`docs/TEAM.md`).
  **Project skeleton και CI (US-03, US-04)**
- Project σε Java 21 και Spring Boot 3.4 με Maven, με πακέτα ανά τομέα (`common`, `external`, `journal`, `mission.domain`, `mission.engine`, `user`).
- `StatusController` (βασικό endpoint ελέγχου λειτουργίας) και `StageState` (καταστάσεις σταδίου αποστολής).
- Ρυθμίσεις `application.yml` και προφίλ δοκιμών `application-test.yml` (βάση H2 στα tests).
- Πρώτα unit tests: `EscapeHubApplicationTests` και `StageStateTest`.
- GitHub Actions: `ci.yml` (μεταγλώττιση, tests και στατική ανάλυση με Checkstyle, SpotBugs και PMD μέσω `mvn verify`) και `pr-checks.yml` (έλεγχος ότι κάθε PR συνδέεται με issue).
- Κανόνες στυλ κώδικα στο `config/checkstyle/checkstyle.xml`.
- Προετοιμασία mutation testing με PIT στο module κανόνων αποστολής (`mission.engine`).
  **Docker και εκδόσεις (US-05)**
- `Dockerfile` με multi-stage build (Maven και JDK 21 για το build, JRE 21 για την εκτέλεση), χρήστη χωρίς δικαιώματα root και healthcheck στο `/actuator/health`.
- `.dockerignore` και `docker-compose.yml` με PostgreSQL 16 και την εφαρμογή, που αναφέρεται μόνο στο image του registry.
- Workflow `release.yml`: σε κάθε tag `v*.*.*` εκτελεί `mvn verify`, δημοσιεύει το Docker image στο GitHub Container Registry (`ghcr.io/tlkcexe/escapehub`) με το ίδιο version tag και δημιουργεί GitHub Release με το `.jar`.
- Τεκμηρίωση της διαδικασίας στο `docs/docker-release.md`.
  **Ορατή έκδοση (US-06)**
- `VersionInfoContributor`: το `/actuator/info` επιστρέφει την έκδοση της εφαρμογής και το Git commit.
- `ActuatorInfoTest`: έλεγχος του endpoint σε κάθε εκτέλεση του CI.
  **Ανάλυση και σχεδίαση (US-07 έως US-13)**
- Θέμα και περίγραμμα της πρώτης αποστολής «Το μυστήριο της Villa Diodati»: τρεις ανεξάρτητες διαδρομές, τέσσερις γρίφοι και τελικό βήμα με συνδυασμό ευρημάτων (`docs/mission-outline.md`).
- Έρευνα των δημόσιων APIs: `docs/apis/Wikidata_API_Research.md` και `docs/apis/OpenLibrary_API_Research.md` (δυνατότητες, όρια χρήσης, User-Agent, μορφή αποκρίσεων).
- Αρχιτεκτονικό σχέδιο (`docs/architecture.md`), πρότυπο ADR (`docs/adr/0000-template.md`) και ADR-0003 για την επιλογή αρχιτεκτονικής.
- Μοντέλο δεδομένων με οντότητες, πεδία και σχέσεις (`docs/data-model.md`).
- Ιεραρχημένο product backlog 63 στοιχείων σε 14 θέματα (epics) για τα Sprints 1–4.
- Τεκμηρίωση παραδοτέων του Sprint 0 και αναφορά ολοκλήρωσης.
<!-- Προγραμματισμένες εκδόσεις: v0.2.0 (Sprint 1), v0.3.0 (Sprint 2),
     v0.4.0 (Sprint 3), v1.0.0 (Sprint 4) -->

[Unreleased]: https://github.com/tlkcexe/escapehub/compare/v0.1.0...HEAD
[0.1.0]: https://github.com/tlkcexe/escapehub/releases/tag/v0.1.0
