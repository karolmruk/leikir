package kmr.rt.cdp.leikir.catalog.domain;

import kmr.rt.cdp.leikir.catalog.domain.dto.Draft;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Document(collection = "catalog")
class CatalogEntry {
  @Id
  private ObjectId id;
  private String group;
  private long version;
  private boolean published;

  private long gameId;
  private String title;
  private double price;
  private String description;

  Draft.Key key() {
    return new Draft.Key(group, version);
  }

  Draft.Details details() {
    return new Draft.Details(gameId, title, price, description, published);
  }

  void publish() {
    this.published = true;
  }

  void unpublish() {
    this.published = false;
  }

  public CatalogEntry update(Draft.Update draft) {
    return new CatalogEntry(null, this.group, this.version + 1, false, this.gameId, draft.title(), draft.price(), draft.description());
  }
}
