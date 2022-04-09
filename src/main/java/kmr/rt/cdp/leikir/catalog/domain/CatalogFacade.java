package kmr.rt.cdp.leikir.catalog.domain;

import kmr.rt.cdp.leikir.catalog.domain.dto.Draft;
import kmr.rt.cdp.leikir.catalog.domain.events.PublishEvent;
import kmr.rt.cdp.leikir.catalog.domain.events.UnpublishEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Clock;
import java.util.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CatalogFacade {
  ApplicationEventPublisher applicationEventPublisher;
  Clock clock;
  Map<Draft.Key, Draft.Details> drafts = new HashMap<>();

  public Draft.Key createGameDraft(Draft.Create draft) {
    Draft.Key key = new Draft.Key(drafts.size() + 1L, 1);
    Draft.Details details = new Draft.Details(draft.title(), draft.price(), draft.description());
    drafts.put(key, details);
    return key;
  }

  public List<Draft.Key> getDrafts() {
    return new ArrayList<>(drafts.keySet());
  }

  public Optional<Draft.Details> getDraftsDetails(Draft.Key key) {
    return Optional.ofNullable(drafts.get(key));
  }

  public Draft.Key updateGameDraft(Draft.Id id, Draft.Create draft) {
    Draft.Key key = new Draft.Key(id.value(), calcVersion(id));
    Draft.Details details = new Draft.Details(draft.title(), draft.price(), draft.description());
    drafts.put(key, details);
    return key;
  }

  private long calcVersion(Draft.Id id) {
    return drafts.entrySet().stream().filter(key -> key.getKey().id().equals(id)).count() + 1;
  }

  public void publish(Draft.Key key) {
    applicationEventPublisher.publishEvent(new PublishEvent(this, clock, key));
  }

  public void unpublish(Draft.Key key) {
    applicationEventPublisher.publishEvent(new UnpublishEvent(this, clock, key.id()));
  }
}
