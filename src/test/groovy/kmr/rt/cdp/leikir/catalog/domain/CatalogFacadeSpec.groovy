package kmr.rt.cdp.leikir.catalog.domain

import kmr.rt.cdp.leikir.catalog.domain.dto.Draft
import spock.lang.Specification

class CatalogFacadeSpec extends Specification {
  CatalogFacade gameCatalogFacade = new CatalogConfiguration().create()

  def "I can create draft"() {
    when:
      var key = gameCatalogFacade.createGameDraft(createCyberpunkDto())
    and:
      var draft = gameCatalogFacade.getDraftsDetails(key)
    then:
      draft.get() == detailsCyberpunkDto()
  }

  def "I can update draft"() {
    given:
    var cyberpunk = createCyberpunk()
    when:
    var key = gameCatalogFacade.updateGameDraft(createCyberpunk(), updateCyberpunkDto())
    then:
    key.isPresent()
    and:
    key.get().version().value() != cyberpunk.version().value()
    when:
    var draft = gameCatalogFacade.getDraftsDetails(key.get())
    then:
    draft.get() == detailsDto(createCyberpunkDto().withPrice(99.00))
    and:
    draft.get()
  }

  def "I can see both versions drafts"() {
    given:
    var first = gameCatalogFacade.createGameDraft(createCyberpunkDto())
    and:
    var second = gameCatalogFacade.updateGameDraft(createCyberpunk(), updateCyberpunkDto())
    when:
    var drafts = gameCatalogFacade.getDrafts()
    then:
    drafts.contains(first)
    and:
    drafts.contains(second.get())
  }

  def "I can create multiple drafts"() {
    when:
    var cyberpunk = gameCatalogFacade.createGameDraft(createCyberpunkDto())
    and:
    var witcher = gameCatalogFacade.createGameDraft(createWitcherDto())
    and:
    var cyberpunkDetails = gameCatalogFacade.getDraftsDetails(cyberpunk)
    and:
    var witcherDetails = gameCatalogFacade.getDraftsDetails(witcher)
    then:
    cyberpunkDetails.get() == detailsCyberpunkDto()
    and:
    witcherDetails.get() == detailsWitcherDto()
  }

  def "I can publish draft"() {
    given:
    var cyberpunk = gameCatalogFacade.createGameDraft(createCyberpunkDto())
    when:
    gameCatalogFacade.publish(cyberpunk)
    then:
    gameCatalogFacade.getDraftsDetails(cyberpunk).get().published()
  }

  def "I can unpublish draft"() {
    given:
    var cyberpunk = gameCatalogFacade.createGameDraft(createCyberpunkDto())
    when:
    gameCatalogFacade.unpublish(cyberpunk)
    then:
    !gameCatalogFacade.getDraftsDetails(cyberpunk).get().published()
  }


  private Draft.Create createCyberpunkDto() {
    return createDto(1, "Cyberpunk 2077", 199.00, "Cyberpunk 2077 is an open-world, action-adventure RPG set in the megalopolis of Night City, where you play as a cyberpunk mercenary wrapped up in a do-or-die fight for survival.")
  }

  private Draft.Update updateCyberpunkDto() {
    return new Draft.Update("Cyberpunk 2077", 99.00, "Cyberpunk 2077 is an open-world, action-adventure RPG set in the megalopolis of Night City, where you play as a cyberpunk mercenary wrapped up in a do-or-die fight for survival.")
  }

  private Draft.Create createWitcherDto() {
    return createDto(2, "The Witcher 3: Wild Hunt", 99.00, "The Witcher: Wild Hunt is a story-driven open world RPG set in a visually stunning fantasy universe full of meaningful choices and impactful consequences.")
  }

  private Draft.Details detailsCyberpunkDto() {
    return detailsDto(createCyberpunkDto())
  }

  private Draft.Details detailsWitcherDto() {
    return detailsDto(createWitcherDto())
  }

  private Draft.Key createCyberpunk() {
    return create(createCyberpunkDto())
  }

  private Draft.Create createDto(long gameId, String title, double price, String description) {
    return new Draft.Create(gameId, title, price, description)
  }

  private Draft.Details detailsDto(Draft.Create from) {
    return detailsDto(from.gameId(), from.title(), from.price(), from.description(), false)
  }

  private Draft.Details detailsDto(long gameId, String title, double price, String description, boolean published) {
    return new Draft.Details(gameId, title, price, description, published)
  }

  private Draft.Key create(Draft.Create dto) {
    return gameCatalogFacade.createGameDraft(dto)
  }
}
