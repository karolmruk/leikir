package kmr.rt.cdp.leikir.catalog.domain;

import kmr.rt.cdp.leikir.catalog.domain.dto.Draft;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class InMemoryCatalogRepository implements CatalogRepository {
  Map<Draft.Key, CatalogEntry> dataset = new HashMap<>();

  @Override
  public <S extends CatalogEntry> S save(S entry) {
    dataset.put(entry.key(), entry);
    return entry;
  }

  @Override
  public List<CatalogEntry> findAll() {
    return dataset.values().stream().toList();
  }

  @Override
  public Optional<CatalogEntry> findByGroupAndVersion(String group, long version) {
    return Optional.ofNullable(dataset.get(new Draft.Key(group, version)));
  }

  @Override
  public List<CatalogEntry> findByGroup(String group) {
    return dataset.entrySet().stream().filter(entry -> entry.getKey().id().value().equals(group)).map(Map.Entry::getValue).toList();
  }
}
