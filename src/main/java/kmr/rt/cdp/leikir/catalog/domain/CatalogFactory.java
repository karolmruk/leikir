package kmr.rt.cdp.leikir.catalog.domain;

import kmr.rt.cdp.leikir.catalog.domain.dto.Draft;

import java.util.UUID;

class CatalogFactory {

  CatalogEntry create(Draft.Create draft) {
    return new CatalogEntry(
      null,
      UUID.randomUUID().toString(),
      1,
      false,
      draft.gameId(),
      draft.title(),
      draft.price(),
      draft.description()
    );
  }
}
