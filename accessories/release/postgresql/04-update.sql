-- =============================================================================
-- PostgreSQL Upgrade Script to upgrade CWS from 1.1 to 1.2
-- -----------------------------------------------------------------------------

-- Start the changes;
BEGIN;

-- Second feature release, CWS 1.2.x results requires an update of the DB
INSERT INTO cws_versions(schema_version, cws_version, db_vendor) VALUES (3, '1.2.0', 'PostgreSQL');

-- Support for Sessions, is the most important add-on for CWS 1.2. It requires
-- that the Members table is extended with 2 new fields:
ALTER TABLE cws_members ADD COLUMN login_retries INT DEFAULT 0;
ALTER TABLE cws_members ADD COLUMN locked_until  TIMESTAMP;

-- Save all changes
COMMIT;
