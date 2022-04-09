package kmr.rt.cdp.leikir.games.domain;

import kmr.rt.cdp.leikir.games.domain.dto.Game;

import java.util.*;

public class GameFacade {
  Map<Game.Id, Game.Details> games = new HashMap<>();

  public Game.Id create(Game.Create create) {
    Game.Id id = new Game.Id(create.draftId());
    games.put(id, new Game.Details(create.title(), create.price(), create.description()));
    return id;
  }

  public void removeGame(Game.Id id) {
    games.remove(id);
  }

  public List<Game.Id> getGames() {
    return new ArrayList<>(games.keySet());
  }

  public Optional<Game.Details> getGamesDetails(Game.Id id) {
    return Optional.ofNullable(games.get(id));
  }

  public List<Game.Id> search(Game.Criteria criteria) {
    return games.entrySet().stream().filter(entry -> entry.getValue().title().contains(criteria.title()))
      .map(Map.Entry::getKey).toList();
  }
}
