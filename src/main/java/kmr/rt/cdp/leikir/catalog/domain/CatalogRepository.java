package kmr.rt.cdp.leikir.catalog.domain;

import org.bson.types.ObjectId;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
interface CatalogRepository extends Repository<CatalogEntry, ObjectId> {
  <S extends CatalogEntry> S save(S entry);

  List<CatalogEntry> findAll();

  Optional<CatalogEntry> findByGroupAndVersion(String group, long version);

  List<CatalogEntry> findByGroup(String group);
}
