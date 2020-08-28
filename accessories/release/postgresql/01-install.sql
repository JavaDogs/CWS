-- =============================================================================
-- This script will setup the CWS Database for PostgreSQL. It will wipe any
-- existing database and roles and create everything from scratch.
--   The script must be run by a user with the permissions to create a new
-- database, and grant permissions. To run the script, simply use this command:
-- $ psql -f 01-install.sql postgres
-- =============================================================================


-- -----------------------------------------------------------------------------
-- Part ONE: Creating Database & Roles
-- -----------------------------------------------------------------------------
-- Create the users needed for CWS. Please note, that the
-- default AppServer Configuration file is referring to this user & password.
-- So, if you use a different name and password, please also update this.
-- DO $$
--     BEGIN
--     IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'cws_user') THEN
        CREATE ROLE cws_user WITH LOGIN PASSWORD 'cws';
--     END IF;
--     COMMIT;
-- END $$;


-- Create the CWS database for the CWS User
-- DO $$
--     BEGIN
--     IF NOT EXISTS (SELECT FROM pg_catalog.pg_database WHERE datname = 'cws') THEN
        CREATE DATABASE cws WITH OWNER = cws_user;
--     END IF;
--     COMMIT;
-- END $$;
-- -----------------------------------------------------------------------------


-- -----------------------------------------------------------------------------
-- Part TWO: Creating Tables
-- -----------------------------------------------------------------------------
-- Now, we're ready to create the actual database. Do so so by first connecting
-- to our newly created CWS database.
\connect cws cws_user

-- Now, we can fill the database with tables, views & data
\ir 02-tables.sql
\ir 03-update.sql
-- -----------------------------------------------------------------------------


-- =============================================================================
-- Done, database is now created :-D
-- =============================================================================
