package athl.logistics.athl_logistics.models.enums;

// Usage interne uniquement (colonne discriminante en base) — jamais exposé en JSON :
// JobOfferDTO sépare déjà missions/profile en deux tableaux distincts.
public enum JobBulletKind {
    MISSION,
    PROFILE,
}
