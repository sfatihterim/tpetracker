# TPE Tracker

TPE Tracker for the [NSFL](http://nsfl.jcink.net/index.php).


# PostgreSQL

- Download & Install [PostgreSQL](https://www.postgresql.org/).

- Run the following commands to setup the database.

```CREATE ROLE tpetracker_local WITH LOGIN PASSWORD 'tpetracker_local';```

```ALTER ROLE tpetracker_local SUPERUSER;```

```CREATE DATABASE tpetracker_local;```

```CREATE TABLE players(id SERIAL PRIMARY KEY, player_id TEXT, date TEXT, draft_year TEXT, team TEXT, name TEXT, position TEXT, tpe INTEGER);```

- Add the following variable to your environment.

```JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/tpetracker_local?user=tpetracker_local&password=tpetracker_local```


# Gradle

- Download & Install [Gradle](https://gradle.org/).

- Run the following command to run the application.

```./gradlew bootrun```

- App will be accesible at ```localhost:8080```.