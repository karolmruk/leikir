package kmr.rt.cdp.leikir.catalog;

import kmr.rt.cdp.leikir.catalog.domain.CatalogFacade;
import kmr.rt.cdp.leikir.catalog.domain.dto.Draft;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
class CatalogDomainController {
  private final CatalogFacade facade;

  @GetMapping(path = "/drafts")
  List<DraftKey> drafts() {
    return facade.getDrafts().stream().map(DraftKey::new).toList();
  }

  @PostMapping(path = "/drafts")
  DraftKey create(@RequestBody DraftCreate draftCreate) {
    return new DraftKey(facade.createGameDraft(draftCreate.domain()));
  }

  @GetMapping(path = "/drafts/{id}/{version}")
  DraftDetails draftsDetails(@PathVariable("id") String id, @PathVariable("version") long version) {
    return facade.getDraftsDetails(new Draft.Key(id, version)).map(DraftDetails::new).orElseThrow();
  }

  @PostMapping(path = "/drafts/{id}/{version}/update")
  DraftKey update(@PathVariable("id") String id, @PathVariable("version") long version, @RequestBody DraftUpdate draftCreate) {
    return facade.updateGameDraft(new Draft.Key(id, version), draftCreate.domain()).map(DraftKey::new).orElseThrow();
  }

  @PostMapping(path = "/drafts/{id}/{version}/publish")
  void publish(@PathVariable("id") String id, @PathVariable("version") long version) {
    facade.publish(new Draft.Key(id, version));
  }

  @PostMapping(path = "/drafts/{id}/{version}/unpublish")
  void unpublish(@PathVariable("id") String id, @PathVariable("version") long version) {
    facade.unpublish(new Draft.Key(id, version));
  }

  @Getter
  private static class DraftKey {
    private final String id;
    private final long version;

    DraftKey(Draft.Key key) {
      this.id = key.id().value();
      this.version = key.version().value();
    }
  }

  @Getter
  private static class DraftDetails {
    private final long gameId;
    private final String title;
    private final double price;
    private final String description;
    private final boolean published;

    DraftDetails(Draft.Details details) {
      this.gameId = details.gameId();
      this.title = details.title();
      this.price = details.price();
      this.description = details.description();
      this.published = details.published();
    }
  }

  @Getter
  @NoArgsConstructor(force = true)
  private static class DraftCreate {
    private final long gameId;
    private final String title;
    private final double price;
    private final String description;

    Draft.Create domain() {
      return new Draft.Create(
        this.gameId,
        this.title,
        this.price,
        this.description
      );
    }
  }

  @Getter
  @NoArgsConstructor(force = true)
  private static class DraftUpdate {
    private final String title;
    private final double price;
    private final String description;

    Draft.Update domain() {
      return new Draft.Update(
        this.title,
        this.price,
        this.description
      );
    }
  }
}
