package kmr.rt.cdp.leikir

import spock.lang.Specification

class AcceptanceSpec extends Specification {
  def "Positive buying scenario"() {
    given: 'inventory has an "Cyberpunk 2077" and "Witcher 3: Wild Hunt" games'

    when: 'I go to /games'
    then: 'I see both games'

    when: 'I search to /games/search with "Cyberpunk"'
    then: 'I can see only "Cyberpunk 2077"'

    when: 'I post to /cart with "Cyberpunk 2077"'
    then: '"Cyberpunk 2077" is added to my cart'

    when: 'I go to /cart'
    then: 'I can see "Cyberpunk 2077" in my cart'

    when: 'I post to /shop'
    then: 'I have bought games from cart'
  }

  def "Positive editing catalog scenario"() {
    when: 'I go to /drafts'
    then: 'I see no drafts'

    when: 'I post to /drafts with "Cyberpunk 2077"'
    then: 'Draft for "Cyberpunk 2077" is created'

    when: 'I go to /drafts for "Cyberpunk 2077"'
    then: 'I can draft for "Cyberpunk 2077"'

    when: 'I post to /draft with "Cyberpunk 2077" with new details'
    then: 'I can see "Cyberpunk 2077" two drafts'

    when: 'I post to /drafts/publish with "Cyberpunk 2077" and the latest draft version'
    then: 'Draft is published'

    when: 'I go to /games'
    then: 'I can see "Cyberpunk 2077"'

    when: 'I post to /unpublish with "Cyberpunk 2077"'
    then: '"Cyberpunk 2077" is unpublished'

    when: 'I go to /games'
    then: 'I see no games'
  }
}
