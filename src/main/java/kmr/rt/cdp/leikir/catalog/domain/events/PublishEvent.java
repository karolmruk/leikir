package kmr.rt.cdp.leikir.catalog.domain.events;

import kmr.rt.cdp.leikir.catalog.domain.dto.Draft;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@EqualsAndHashCode(callSuper = false)
public class PublishEvent extends ApplicationEvent {
  private final long id;
  private final long version;

  public PublishEvent(Object source, Clock clock, Draft.Key key) {
    super(source, clock);
    this.id = key.id().value();
    this.version = key.version().value();
  }

  public Draft.Key getDraftKey() {
    return new Draft.Key(this.id, this.version);
  }
}
