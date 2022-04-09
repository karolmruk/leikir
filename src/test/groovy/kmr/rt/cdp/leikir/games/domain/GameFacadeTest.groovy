package kmr.rt.cdp.leikir.games.domain


import kmr.rt.cdp.leikir.games.domain.dto.Game
import spock.lang.Specification

class GameFacadeTest extends Specification {
  GameFacade gameFacade = new GameFacade()

  def "I can see created games"() {
    when:
    var id = gameFacade.create(createCyberpunkDto())
    then:
    gameFacade.getGamesDetails(id).get() == detailsCyberpunkDto()
  }

  def "I can't see deleted games"() {
    given:
    var id = gameFacade.create(createCyberpunkDto())
    when:
    gameFacade.removeGame(id)
    then:
    gameFacade.getGamesDetails(id).isEmpty()
  }

  def "I can see multiple games"() {
    given:
    var cyberpunk = gameFacade.create(createCyberpunkDto())
    var witcher = gameFacade.create(createWitcherDto())
    when:
    var games = gameFacade.getGames()
    then:
    games.contains(cyberpunk)
    and:
    games.contains(witcher)
  }

  def "I can see details of multiple games"() {
    given:
    var cyberpunk = gameFacade.create(createCyberpunkDto())
    var witcher = gameFacade.create(createWitcherDto())
    when:
    var cyberpunkDetail = gameFacade.getGamesDetails(cyberpunk)
    and:
    var witcherDetail = gameFacade.getGamesDetails(witcher)
    then:
    cyberpunkDetail.get() == detailsCyberpunkDto()
    and:
    witcherDetail.get() == detailsWitcherDto()
  }

  def "I can see search by title"() {
    given:
    var cyberpunk = gameFacade.create(createCyberpunkDto())
    var witcher = gameFacade.create(createWitcherDto())
    when:
    var games = gameFacade.search(new Game.Criteria("Witcher"))
    then:
    games.contains(witcher)
    and:
    !games.contains(cyberpunk)
  }

  private Game.Create createCyberpunkDto() {
    return createDto(1, "Cyberpunk 2077", 199.00, "Cyberpunk 2077 is an open-world, action-adventure RPG set in the megalopolis of Night City, where you play as a cyberpunk mercenary wrapped up in a do-or-die fight for survival.")
  }

  private Game.Create createWitcherDto() {
    return createDto(2, "The Witcher 3: Wild Hunt", 99.00, "The Witcher: Wild Hunt is a story-driven open world RPG set in a visually stunning fantasy universe full of meaningful choices and impactful consequences.")
  }

  private Game.Details detailsCyberpunkDto() {
    return detailsDto(createCyberpunkDto())
  }

  private Game.Details detailsWitcherDto() {
    return detailsDto(createWitcherDto())
  }

  private Game.Create createDto(long draftId, String title, double price, String description) {
    return new Game.Create(draftId, title, price, description)
  }

  private Game.Details detailsDto(Game.Create create) {
    return new Game.Details(create.title(), create.price(), create.description())
  }

  private Game.Details detailsDto(String title, double price, String description) {
    return new Game.Details(title, price, description)
  }
}
