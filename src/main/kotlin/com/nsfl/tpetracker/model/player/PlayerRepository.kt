package com.nsfl.tpetracker.model.player

import com.nsfl.tpetracker.model.position.Position
import com.nsfl.tpetracker.model.team.Team

class PlayerRepository {

    private val playerParser = PlayerParser()
    private val playerDatabase = PlayerDatabase()
    private val activePlayerList = ArrayList<ActivePlayer>()
    private val retiredPlayerList = ArrayList<RetiredPlayer>()

    fun update() {

        val newActivePlayerList = playerDatabase.updateActivePlayers(playerParser.parseActivePlayers())
        activePlayerList.clear()
        activePlayerList.addAll(newActivePlayerList)

        val newRetiredPlayerList = playerDatabase.getRetiredPlayers(activePlayerList)
        retiredPlayerList.clear()
        retiredPlayerList.addAll(newRetiredPlayerList)
    }

    fun initialise(){
        val newActivePlayerList = playerDatabase.initaliseAllPlayers()
        activePlayerList.addAll(newActivePlayerList)
    }

    fun getPlayer(playerId: String) =
            activePlayerList.firstOrNull { it.id == playerId }
                    ?: retiredPlayerList.first { it.id == playerId }

    fun getAllPlayers() = activePlayerList

    fun getBaltimoreHawksPlayers() =
            activePlayerList.filter { it.team == Team.BALTIMORE_HAWKS }

    fun getChicagoButchersPlayers() =
            activePlayerList.filter { it.team == Team.CHICAGO_BUTCHERS }

    fun getColoradoYetiPlayers() =
            activePlayerList.filter { it.team == Team.COLORADO_YETI }

    fun getPhiladelphiaLibertyPlayers() =
            activePlayerList.filter { it.team == Team.PHILADELPHIA_LIBERTY }

    fun getYellowknifeWraithsPlayers() =
            activePlayerList.filter { it.team == Team.YELLOWKNIFE_WRAITHS }

    fun getArizonaOutlawsPlayers() =
            activePlayerList.filter { it.team == Team.ARIZONA_OUTLAWS }

    fun getAustinCopperheadsPlayers() =
            activePlayerList.filter { it.team == Team.AUSTIN_COPPERHEADS }

    fun getNewOrleansSecondLinePlayers() =
            activePlayerList.filter { it.team == Team.NEW_ORLEANS_SECOND_LINE }

    fun getOrangeCountyOttersPlayers() =
            activePlayerList.filter { it.team == Team.ORANGE_COUNTY_OTTERS }

    fun getSanJoseSabercatsPlayers() =
            activePlayerList.filter { it.team == Team.SAN_JOSE_SABERCATS }

    fun getMyrtleBeachBuccaneersPlayers() =
            activePlayerList.filter { it.team == Team.MYRTLE_BEACH_BUCCANEERS }

    fun getKansasCityCoyotesPlayers() =
            activePlayerList.filter { it.team == Team.KANSAS_CITY_COYOTES }

    fun getPortlandPythonsPlayers() =
            activePlayerList.filter { it.team == Team.PORTLAND_PYTHONS }

    fun getNorfolkSeawolvesPlayers() =
            activePlayerList.filter { it.team == Team.NORFOLK_SEAWOLVES }

    fun getMinnesotaGreyDucksPlayers() =
            activePlayerList.filter { it.team == Team.MINNESOTA_GREY_DUCKS }

    fun getTijuanaLuchadoresPlayers() =
            activePlayerList.filter { it.team == Team.TIJUANA_LUCHADORES }

    fun getDallasBirddogsPlayers() =
            activePlayerList.filter { it.team == Team.DALLAS_BIRDDOGS }

    fun getLondonRoyalsPlayers() =
            activePlayerList.filter { it.team == Team.LONDON_ROYALS }

    fun getFreeAgents() =
            activePlayerList.filter { it.team == Team.FREE_AGENTS }

    fun getQBProspects() =
            activePlayerList.filter { it.team == Team.QB_PROSPECTS }

    fun getRBProspects() =
            activePlayerList.filter { it.team == Team.RB_PROSPECTS }

    fun getWRProspects() =
            activePlayerList.filter { it.team == Team.WR_PROSPECTS }

    fun getTEProspects() =
            activePlayerList.filter { it.team == Team.TE_PROSPECTS }

    fun getOLProspects() =
            activePlayerList.filter { it.team == Team.OL_PROSPECTS }

    fun getDEProspects() =
            activePlayerList.filter { it.team == Team.DE_PROSPECTS }

    fun getDTProspects() =
            activePlayerList.filter { it.team == Team.DT_PROSPECTS }

    fun getLBProspects() =
            activePlayerList.filter { it.team == Team.LB_PROSPECTS }

    fun getCBProspects() =
            activePlayerList.filter { it.team == Team.CB_PROSPECTS }

    fun getSProspects() =
            activePlayerList.filter { it.team == Team.S_PROSPECTS }

    fun getKPProspects() =
            activePlayerList.filter { it.team == Team.KP_PROSPECTS }

    fun getQBPlayers() =
            activePlayerList.filter { it.position == Position.QB }

    fun getRBPlayers() =
            activePlayerList.filter { it.position == Position.RB }

    fun getWRPlayers() =
            activePlayerList.filter { it.position == Position.WR }

    fun getTEPlayers() =
            activePlayerList.filter { it.position == Position.TE }

    fun getOLPlayers() =
            activePlayerList.filter { it.position == Position.OL }

    fun getDEPlayers() =
            activePlayerList.filter { it.position == Position.DE }

    fun getDTPlayers() =
            activePlayerList.filter { it.position == Position.DT }

    fun getLBPlayers() =
            activePlayerList.filter { it.position == Position.LB }

    fun getCBPlayers() =
            activePlayerList.filter { it.position == Position.CB }

    fun getSPlayers() =
            activePlayerList.filter { it.position == Position.S }

    fun getKPPlayers() =
            activePlayerList.filter { it.position == Position.KP }

    fun getRetiredPlayers() = retiredPlayerList
}