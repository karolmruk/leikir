package kmr.rt.cdp.leikir.games.domain;

import kmr.rt.cdp.leikir.catalog.domain.events.UnpublishEvent;
import kmr.rt.cdp.leikir.games.domain.dto.Game;
import org.springframework.context.ApplicationListener;

class UnpublishGameEventListener implements ApplicationListener<UnpublishEvent> {
  GameFacade gameFacade;

  @Override
  public void onApplicationEvent(UnpublishEvent event) {
    gameFacade.removeGame(new Game.Id(event.getDraftId().value()));
  }
}
