package kmr.rt.cdp.leikir.catalog.domain

import lombok.Getter
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher

class FakeApplicationEventPublisher implements ApplicationEventPublisher {
  @Getter
  List<ApplicationEvent> events = new LinkedList<>()

  @Override
  void publishEvent(ApplicationEvent event) {
    events.add(event)
  }

  @Override
  void publishEvent(Object event) {
    throw new UnsupportedOperationException()
  }


}
