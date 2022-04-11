package kmr.rt.cdp.leikir.catalog.domain;

import kmr.rt.cdp.leikir.catalog.domain.dto.Draft;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CatalogFacade {
  private final CatalogRepository repository;
  private final CatalogFactory factory;

  public Draft.Key createGameDraft(Draft.Create draft) {
    return repository.save(factory.create(draft)).key();
  }

  public List<Draft.Key> getDrafts() {
    return repository.findAll().stream().map(CatalogEntry::key).toList();
  }

  public Optional<Draft.Details> getDraftsDetails(Draft.Key key) {
    return repository.findByGroupAndVersion(key.id().value(), key.version().value()).map(CatalogEntry::details);
  }

  public Optional<Draft.Key> updateGameDraft(Draft.Key key, Draft.Update draft) {
    return repository.findByGroupAndVersion(key.id().value(), key.version().value())
      .map(catalogEntry -> catalogEntry.update(draft))
      .map(repository::save)
      .map(CatalogEntry::key);
  }

  public void publish(Draft.Key key) {
    repository.findByGroup(key.id().value()).stream().peek(CatalogEntry::unpublish).forEach(repository::save);
    repository.findByGroupAndVersion(key.id().value(), key.version().value()).ifPresent(catalogEntry -> {
      catalogEntry.publish();
      repository.save(catalogEntry);
    });
  }

  public void unpublish(Draft.Key key) {
    repository.findByGroupAndVersion(key.id().value(), key.version().value()).ifPresent(catalogEntry -> {
      catalogEntry.unpublish();
      repository.save(catalogEntry);
    });
  }
}
