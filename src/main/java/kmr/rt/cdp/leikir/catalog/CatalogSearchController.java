package kmr.rt.cdp.leikir.catalog;

import kmr.rt.cdp.leikir.catalog.query.CatalogQueryRepository;
import kmr.rt.cdp.leikir.catalog.query.PublishedCatalogEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class CatalogSearchController {
  private final CatalogQueryRepository repository;


  ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
    .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
    .withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
    .withIgnorePaths("id", "gameId", "published", "price");


  @GetMapping(path = "/games")
  List<PublishedCatalogEntry> games() {
    return repository.publishedIsTrue();
  }

  @GetMapping(path = "/games/search")
  List<PublishedCatalogEntry> search(@RequestParam(required = false) String q) {
    PublishedCatalogEntry probe = PublishedCatalogEntry.builder().title(q).description(q).build();
    return repository.findAll(Example.of(probe, customExampleMatcher));
  }
}
