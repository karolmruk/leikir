package kmr.rt.cdp.leikir.games.domain;

import kmr.rt.cdp.leikir.catalog.domain.CatalogFacade;
import kmr.rt.cdp.leikir.catalog.domain.dto.Draft;
import kmr.rt.cdp.leikir.catalog.domain.events.PublishEvent;
import kmr.rt.cdp.leikir.games.domain.dto.Game;
import org.springframework.context.ApplicationListener;

class PublishingGameEventListener implements ApplicationListener<PublishEvent> {
  GameFacade gameFacade;
  CatalogFacade catalogFacade;

  @Override
  public void onApplicationEvent(PublishEvent event) {
    Draft.Details draft = catalogFacade.getDraftsDetails(event.getDraftKey()).orElseThrow(UnsupportedOperationException::new);
    gameFacade.create(new Game.Create(event.getDraftKey().id().value(), draft.title(), draft.price(), draft.description()));
  }
}
