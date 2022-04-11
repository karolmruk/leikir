package kmr.rt.cdp.leikir.catalog.query;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


@org.springframework.stereotype.Repository
public interface CatalogQueryRepository extends MongoRepository<PublishedCatalogEntry, ObjectId> {
  List<PublishedCatalogEntry> publishedIsTrue();
}
