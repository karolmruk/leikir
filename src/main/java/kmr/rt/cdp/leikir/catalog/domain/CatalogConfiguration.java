package kmr.rt.cdp.leikir.catalog.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CatalogConfiguration {

  CatalogFacade create() {
    CatalogRepository repository = new InMemoryCatalogRepository();
    return new CatalogFacade(repository, new CatalogFactory());
  }

  @Bean
  CatalogFacade catalogEntry(CatalogRepository repository) {
    return new CatalogFacade(repository, new CatalogFactory());
  }
}
