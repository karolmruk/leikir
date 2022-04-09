package kmr.rt.cdp.leikir.catalog.domain.events;

import kmr.rt.cdp.leikir.catalog.domain.dto.Draft;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;


@EqualsAndHashCode(callSuper = false)
public class UnpublishEvent extends ApplicationEvent {
  private final long id;

  public UnpublishEvent(Object source, Clock clock, Draft.Id id) {
    super(source, clock);
    this.id = id.value();
  }

  public Draft.Id getDraftId() {
    return new Draft.Id(this.id);
  }
}
