package kmr.rt.cdp.leikir.catalog.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "catalog")
public class PublishedCatalogEntry {
  @Id
  @JsonIgnore
  private ObjectId id;
  @JsonIgnore
  private boolean published;
  private Long gameId;
  private String title;
  private Double price;
  private String description;

  PublishedCatalogEntry(String title, String description) {
    published = true;
    this.title = title;
    this.description = description;
  }

  public static PublishedCatalogEntryBuilder builder() {
    return new PublishedCatalogEntryBuilder();
  }

  public static class PublishedCatalogEntryBuilder {
    private String title;
    private String description;

    public PublishedCatalogEntryBuilder title(String title) {
      this.title = title;
      return this;
    }

    public PublishedCatalogEntryBuilder description(String description) {
      this.description = description;
      return this;
    }

    public PublishedCatalogEntry build() {
      return new PublishedCatalogEntry(title, description);
    }
  }
}
