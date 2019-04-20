package com.nsfl.tpetracker

import com.nsfl.tpetracker.html.HTMLGenerator
import com.nsfl.tpetracker.model.team.Team
import com.nsfl.tpetracker.model.player.PlayerRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@SpringBootApplication
@EnableScheduling
class Application {

    private val logger = LoggerFactory.getLogger("TPETrackerApplication ")
    private val playerRepository = PlayerRepository()
    private var lastUpdateInfo = ""
    private val htmlGenerator = HTMLGenerator()

    init {
        updatePlayers("Initial")
    }

    @Scheduled(cron = "0 0 10 * * MON-FRI")
    fun updatePlayersWeekday() {
        updatePlayers("Weekday")
    }

    @Scheduled(cron = "0 0 1,4,7,10,13,16,19,22 * * SUN,SAT")
    fun updatePlayersWeekend() {
        updatePlayers("Weekend")
    }

    private fun updatePlayers(type: String) {
        val start = System.currentTimeMillis()
        playerRepository.update()
        lastUpdateInfo = "Last Update =>" +
                " Type: $type," +
                " Started At: ${Date(start)}," +
                " Duration: ${System.currentTimeMillis() - start} ms"
    }

    @RequestMapping
    fun getIndex() = htmlGenerator.createIndex()

    @RequestMapping("/last_update")
    fun getLastUpdateInformation() = lastUpdateInfo

    @RequestMapping("/all_players")
    fun getAllPlayers() =
            htmlGenerator.createAllPlayers(playerRepository.getAllPlayers())

    @RequestMapping("/team_stats")
    fun getTeamStats() = htmlGenerator.createTeamStats(
            listOf(
                    Pair(Team.BALTIMORE_HAWKS, playerRepository.getBaltimoreHawksPlayers()),
                    Pair(Team.COLORADO_YETI, playerRepository.getColoradoYetiPlayers()),
                    Pair(Team.PHILADELPHIA_LIBERTY, playerRepository.getPhiladelphiaLibertyPlayers()),
                    Pair(Team.YELLOWKNIFE_WRAITHS, playerRepository.getYellowknifeWraithsPlayers()),
                    Pair(Team.ARIZONA_OUTLAWS, playerRepository.getArizonaOutlawsPlayers()),
                    Pair(Team.NEW_ORLEANS_SECOND_LINE, playerRepository.getNewOrleansSecondLinePlayers()),
                    Pair(Team.ORANGE_COUNTY_OTTERS, playerRepository.getOrangeCountyOttersPlayers()),
                    Pair(Team.SAN_JOSE_SABERCATS, playerRepository.getSanJoseSabercatsPlayers())
            ),
            listOf(
                    Pair(Team.PALM_BEACH_SOLAR_BEARS, playerRepository.getPalmBeachSolarBearsPlayers()),
                    Pair(Team.KANSAS_CITY_COYOTES, playerRepository.getKansasCityCoyotesPlayers()),
                    Pair(Team.PORTLAND_PYTHONS, playerRepository.getPortlandPythonsPlayers()),
                    Pair(Team.NORFOLK_SEAWOLVES, playerRepository.getNorfolkSeawolvesPlayers()),
                    Pair(Team.SAN_ANTONIO_MARSHALS, playerRepository.getSanAntonioMarshalsPlayers()),
                    Pair(Team.TIJUANA_LUCHADORES, playerRepository.getTijuanaLuchadoresPlayers())
            )
    )

    @RequestMapping("/baltimore_hawks")
    fun getBaltimoreHawksPlayers() = htmlGenerator.createTeam(
            Team.BALTIMORE_HAWKS,
            playerRepository.getBaltimoreHawksPlayers()
    )

    @RequestMapping("/colorado_yeti")
    fun getColoradoYetiPlayers() = htmlGenerator.createTeam(
            Team.COLORADO_YETI,
            playerRepository.getColoradoYetiPlayers()
    )

    @RequestMapping("/philadelphia_liberty")
    fun getPhiladelphiaLibertyPlayers() = htmlGenerator.createTeam(
            Team.PHILADELPHIA_LIBERTY,
            playerRepository.getPhiladelphiaLibertyPlayers()
    )

    @RequestMapping("/yellowknife_wraiths")
    fun getYellowknifeWraithsPlayers() = htmlGenerator.createTeam(
            Team.YELLOWKNIFE_WRAITHS,
            playerRepository.getYellowknifeWraithsPlayers()
    )

    @RequestMapping("/arizona_outlaws")
    fun getArizonaOutlawsPlayers() = htmlGenerator.createTeam(
            Team.ARIZONA_OUTLAWS,
            playerRepository.getArizonaOutlawsPlayers()
    )

    @RequestMapping("/new_orleans_second_line")
    fun getNewOrleansSecondLinePlayers() = htmlGenerator.createTeam(
            Team.NEW_ORLEANS_SECOND_LINE,
            playerRepository.getNewOrleansSecondLinePlayers()
    )

    @RequestMapping("/orange_county_otters")
    fun getOrangeCountyOttersPlayers() = htmlGenerator.createTeam(
            Team.ORANGE_COUNTY_OTTERS,
            playerRepository.getOrangeCountyOttersPlayers()
    )

    @RequestMapping("/san_jose_sabercats")
    fun getSanJoseSaberCatsPlayers() = htmlGenerator.createTeam(
            Team.SAN_JOSE_SABERCATS,
            playerRepository.getSanJoseSabercatsPlayers()
    )

    @RequestMapping("/palm_beach_solar_bears")
    fun getPalmBeachSolarBearsPlayers() = htmlGenerator.createTeam(
            Team.PALM_BEACH_SOLAR_BEARS,
            playerRepository.getPalmBeachSolarBearsPlayers()
    )

    @RequestMapping("/kansas_city_coyotes")
    fun getKansasCityCoyotesPlayers() = htmlGenerator.createTeam(
            Team.KANSAS_CITY_COYOTES,
            playerRepository.getKansasCityCoyotesPlayers()
    )

    @RequestMapping("/portland_pythons")
    fun getPortlandPythonsPlayers() = htmlGenerator.createTeam(
            Team.PORTLAND_PYTHONS,
            playerRepository.getPortlandPythonsPlayers()
    )

    @RequestMapping("/norfolk_seawolves")
    fun getNorfolkSeawolvesPlayers() = htmlGenerator.createTeam(
            Team.NORFOLK_SEAWOLVES,
            playerRepository.getNorfolkSeawolvesPlayers()
    )

    @RequestMapping("/san_antonio_marshals")
    fun getSanAntonioMarshalsPlayers() = htmlGenerator.createTeam(
            Team.SAN_ANTONIO_MARSHALS,
            playerRepository.getSanAntonioMarshalsPlayers()
    )

    @RequestMapping("/tijuana_luchadores")
    fun getTijuanaLuchadoresPlayers() = htmlGenerator.createTeam(
            Team.TIJUANA_LUCHADORES,
            playerRepository.getTijuanaLuchadoresPlayers()
    )

    @RequestMapping("/free_agents")
    fun getFreeAgents() = htmlGenerator.createTeam(
            Team.FREE_AGENTS,
            playerRepository.getFreeAgents()
    )

    @RequestMapping("/qb_prospects")
    fun getQBProspects() = htmlGenerator.createTeam(
            Team.QB_PROSPECTS,
            playerRepository.getQBProspects()
    )

    @RequestMapping("/rb_prospects")
    fun getRBProspects() = htmlGenerator.createTeam(
            Team.RB_PROSPECTS,
            playerRepository.getRBProspects()
    )

    @RequestMapping("/wr_prospects")
    fun getWRProspects() = htmlGenerator.createTeam(
            Team.WR_PROSPECTS,
            playerRepository.getWRProspects()
    )

    @RequestMapping("/te_prospects")
    fun getTEProspects() = htmlGenerator.createTeam(
            Team.TE_PROSPECTS,
            playerRepository.getTEProspects()
    )

    @RequestMapping("/ol_prospects")
    fun getOLProspects() = htmlGenerator.createTeam(
            Team.OL_PROSPECTS,
            playerRepository.getOLProspects()
    )

    @RequestMapping("/de_prospects")
    fun getDEProspects() = htmlGenerator.createTeam(
            Team.DE_PROSPECTS,
            playerRepository.getDEProspects()
    )

    @RequestMapping("/dt_prospects")
    fun getDTProspects() = htmlGenerator.createTeam(
            Team.DT_PROSPECTS,
            playerRepository.getDTProspects()
    )

    @RequestMapping("/lb_prospects")
    fun getLBProspects() = htmlGenerator.createTeam(
            Team.LB_PROSPECTS,
            playerRepository.getLBProspects()
    )

    @RequestMapping("/cb_prospects")
    fun getCBProspects() = htmlGenerator.createTeam(
            Team.CB_PROSPECTS,
            playerRepository.getCBProspects()
    )

    @RequestMapping("/s_prospects")
    fun getSProspects() = htmlGenerator.createTeam(
            Team.S_PROSPECTS,
            playerRepository.getSProspects()
    )

    @RequestMapping("/kp_prospects")
    fun getKPProspects() = htmlGenerator.createTeam(
            Team.KP_PROSPECTS,
            playerRepository.getKPProspects()
    )
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}