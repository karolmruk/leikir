package kmr.rt.cdp.leikir.games.domain.dto;


public record Game(Id id, Details details) {
  public record Id(long value) {}
  public record Details(String title, double price, String description) {}

  public record Create(long draftId, String title, double price, String description) {}

  public record Criteria(String title) {}
}

