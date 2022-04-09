package kmr.rt.cdp.leikir.catalog.domain;

import org.springframework.context.ApplicationEventPublisher;

import java.time.Clock;

class CatalogConfiguration {

  CatalogFacade create(ApplicationEventPublisher publisher, Clock clock) {
    return new CatalogFacade(publisher, clock);
  }
}
