package kmr.rt.cdp.leikir.catalog.domain

import kmr.rt.cdp.leikir.catalog.domain.dto.Draft
import kmr.rt.cdp.leikir.catalog.domain.events.PublishEvent
import kmr.rt.cdp.leikir.catalog.domain.events.UnpublishEvent
import spock.lang.Specification

import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

class CatalogFacadeSpec extends Specification {
  Clock clock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC)
  FakeApplicationEventPublisher publisher = new FakeApplicationEventPublisher()
  CatalogFacade gameCatalogFacade = new CatalogConfiguration().create(publisher, clock)

  def "I can create draft"() {
    when:
      var key = gameCatalogFacade.createGameDraft(createCyberpunkDto())
    and:
      var draft = gameCatalogFacade.getDraftsDetails(key)
    then:
      draft.get() == detailsCyberpunkDto()
  }

  def "I can update draft"() {
    when:
    var key = gameCatalogFacade.updateGameDraft(createCyberpunk().id(), createCyberpunkDto().withPrice(99.00))
    and:
    var draft = gameCatalogFacade.getDraftsDetails(key)
    then:
    draft.get() == detailsDto(createCyberpunkDto().withPrice(99.00))
  }

  def "I can see both versions drafts"() {
    given:
    var first = gameCatalogFacade.createGameDraft(createCyberpunkDto())
    and:
    var second = gameCatalogFacade.updateGameDraft(createCyberpunk().id(), createCyberpunkDto().withPrice(99.00))
    when:
    var drafts = gameCatalogFacade.getDrafts()
    then:
    drafts.contains(first)
    and:
    drafts.contains(second)
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


  def "Publish event fired"() {
    given:
    var draft = gameCatalogFacade.createGameDraft(createCyberpunkDto())
    when:
    gameCatalogFacade.publish(draft)
    then:
    publisher.getEvents().size() == 1
    and:
    publisher.getEvents().contains(new PublishEvent(gameCatalogFacade, clock, draft))
    and:
    publisher.getEvents().first().timestamp == clock.millis()
  }

  def "Unpublish event fired"() {
    given:
    var draft = gameCatalogFacade.createGameDraft(createCyberpunkDto())
    when:
    gameCatalogFacade.unpublish(draft)
    then:
    publisher.getEvents().size() == 1
    and:
    publisher.getEvents().contains(new UnpublishEvent(gameCatalogFacade, clock, draft.id()))
    and:
    publisher.getEvents().first().timestamp == clock.millis()
  }

  private Draft.Create createCyberpunkDto() {
    return createDto("Cyberpunk 2077", 199.00, "Cyberpunk 2077 is an open-world, action-adventure RPG set in the megalopolis of Night City, where you play as a cyberpunk mercenary wrapped up in a do-or-die fight for survival.")
  }

  private Draft.Create createWitcherDto() {
    return createDto("The Witcher 3: Wild Hunt", 99.00, "The Witcher: Wild Hunt is a story-driven open world RPG set in a visually stunning fantasy universe full of meaningful choices and impactful consequences.")
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

  private Draft.Create createDto(String title, double price, String description) {
    return new Draft.Create(title, price, description)
  }

  private Draft.Details detailsDto(Draft.Create from) {
    return detailsDto(from.title(), from.price(), from.description())
  }

  private Draft.Details detailsDto(String title, double price, String description) {
    return new Draft.Details(title, price, description)
  }

  private Draft.Key create(Draft.Create dto) {
    return gameCatalogFacade.createGameDraft(dto)
  }
}
