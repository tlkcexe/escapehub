# Changelog

Όλες οι αξιοσημείωτες αλλαγές του EscapeHub καταγράφονται εδώ.
Η μορφή βασίζεται στο [Keep a Changelog](https://keepachangelog.com/en/1.1.0/) και οι εκδόσεις
ακολουθούν [Semantic Versioning](https://semver.org/lang/el/).

Κάθε Sprint Review = ένα tag + ένα GitHub release. Πριν το tag, μεταφέρουμε το περιεχόμενο
του `[Unreleased]` κάτω από τη νέα έκδοση.

## [Unreleased]

### Added
- Αρχικά αρχεία αποθετηρίου: README, CONTRIBUTING, PR/issue templates, Definition of Done,
  AI usage log, πρότυπο ADR και βασικό CI workflow (US-01, US-02).
- Προσθήκη `Dockerfile` (multi-stage build), `.dockerignore`, `docker-compose.yml` (με PostgreSQL) και GitHub Actions workflow (`release.yml`) για αυτόματο publish Docker image στο GHCR και GitHub Releases (US-05).

<!-- Προγραμματισμένες εκδόσεις: v0.1.0 (Sprint 0), v0.2.0 (Sprint 1), v0.3.0 (Sprint 2),
     v0.4.0 (Sprint 3), v1.0.0 (Sprint 4) -->
