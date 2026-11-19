# Docker Image και Διαδικασία Release (US-05)

**Εργασία:** EscapeHub  
**User Story:** US-05 — Docker image και release  
**Απαιτήσεις:** NFR-05, PR-06  
**Sprint:** 0  
**Συντάκτης:** Μ3

## 1. Σκοπός

Σκοπός της παρούσας αναφοράς είναι να περιγράψει τον σχεδιασμό και τη ροή εργασίας για την αυτοματοποιημένη δημιουργία, δημοσίευση και εκτέλεση του Docker image του EscapeHub.

Με την υλοποίηση του US-05 διασφαλίζεται ότι:
- Κάθε επισημασμένη έκδοση (`vX.Y.Z`) παράγει αυτόματα Docker image με το ίδιο version tag στο GitHub Container Registry (GHCR).
- Οποιοσδήποτε χρήστης ή μέλος της ομάδας μπορεί να εκκινήσει αναπαραγώγιμα την εφαρμογή με `docker compose up`, αναφερόμενος μόνο στο δημοσιευμένο image του registry (χωρίς τοπική μεταγλώττιση).

## 2. Σχεδιασμός Docker Image (`Dockerfile`)

Για το πακετάρισμα της εφαρμογής (Java 21 / Spring Boot 3.4 / Maven) θα χρησιμοποιηθεί **multi-stage build** με δύο διακριτά στάδια:

1. **Στάδιο μεταγλώττισης (Build stage):** Χρησιμοποιεί image με Maven και JDK 21 (`maven:3.9-eclipse-temurin-21-alpine`), κατεβάζει τις εξαρτήσεις του `pom.xml` αξιοποιώντας το layer caching και παράγει το εκτελέσιμο `.jar`.
2. **Στάδιο εκτέλεσης (Runtime stage):** Χρησιμοποιεί ελαφρύ image μόνο με JRE 21 (`eclipse-temurin:21-jre-alpine`), στο οποίο αντιγράφεται αποκλειστικά το τελικό `.jar`.

Βασικές πρακτικές ασφάλειας και αξιοπιστίας που θα εφαρμοστούν:
- **Αρχείο `.dockerignore`:** Εξαιρεί τοπικά/μη απαραίτητα αρχεία (`target/`, `.git/`, `.idea/`, `docs/`) ώστε το build context να παραμένει μικρό και γρήγορο.
- **Non-root χρήστης:** Η εφαρμογή εκτελείται από ξεχωριστό χρήστη περιορισμένων δικαιωμάτων εντός του container και όχι ως `root`.
- **Healthcheck:** Ενσωματωμένος έλεγχος υγείας στο endpoint `/actuator/health`, ώστε το Docker να γνωρίζει πότε η εφαρμογή είναι έτοιμη να δεχτεί αιτήματα.

## 3. Αναπαραγώγιμη εκτέλεση με Docker Compose

Επειδή το backend της εφαρμογής απαιτεί βάση δεδομένων PostgreSQL, το αρχείο `docker-compose.yml` θα ενορχηστρώνει δύο υπηρεσίες στο ίδιο εσωτερικό δίκτυο:

| Υπηρεσία | Image | Ρόλος |
|---|---|---|
| `db` | `postgres:16-alpine` | Βάση δεδομένων PostgreSQL με healthcheck (`pg_isready`) και persistent volume |
| `app` | `ghcr.io/tlkcexe/escapehub:${IMAGE_TAG:-latest}` | Backend εφαρμογή Spring Boot που εκκινεί αφού η βάση γίνει `healthy` |

Βασικές αρχές ρύθμισης:
- **Αναφορά μόνο στο image (NFR-05):** Η υπηρεσία `app` ορίζεται με `image:` (όχι `build:`), ώστε η εντολή `docker compose up` να κατεβάζει και να εκτελεί απευθείας τη ζητούμενη έκδοση από το GHCR.
- **Χωρίς hardcoded credentials:** Οι παράμετροι σύνδεσης (`DB_URL`, `DB_USER`, `DB_PASSWORD`) περνούν μέσω μεταβλητών περιβάλλοντος.
- **Επαλήθευση έκδοσης:** Μετά την εκκίνηση, το `/actuator/health` επιβεβαιώνει τη λειτουργία (`UP`), ενώ το `/actuator/info` (σε συνδυασμό με το US-06) εμφανίζει την τρέχουσα έκδοση και το Git commit.

## 4. Αυτοματοποίηση Release & GHCR (GitHub Actions)

Η διαχείριση εκδόσεων ακολουθεί **Semantic Versioning (`vMAJOR.MINOR.PATCH`)**, σύμφωνα με την απαίτηση `PR-06` και το `CONTRIBUTING.md`.

Για την αυτοματοποίηση θα προστεθεί ξεχωριστό workflow (`.github/workflows/release.yml`) το οποίο ενεργοποιείται αυτόματα σε κάθε push νέου version tag μορφής `v*.*.*`:

```text
Push tag (π.χ. v0.1.0)
   │
   ▼
1. Checkout & Setup Java 21
   │
   ▼
2. Ποιοτικός έλεγχος (mvn -B verify: tests, Checkstyle, SpotBugs, PMD)
   │
   ▼
3. Σύνδεση στο GitHub Container Registry (ghcr.io) με GITHUB_TOKEN
   │
   ▼
4. Build & Push Docker image με tags vX.Y.Z και latest
   │
   ▼
5. Δημιουργία GitHub Release με αυτόματες σημειώσεις έκδοσης & .jar