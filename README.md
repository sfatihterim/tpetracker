# tpetracker
TPE Tracker for NSFL

# Players Table
CREATE TABLE players(id SERIAL PRIMARY KEY, player_id TEXT, date TEXT, draft_year TEXT, team TEXT, name TEXT, position TEXT, tpe INTEGER);
DELETE FROM players WHERE date='date';
INSERT INTO players VALUES(DEFAULT,'player_id','date','draft_year','team','name','position','tpe');