package kmr.rt.cdp.leikir.catalog.domain.dto;

public record Draft(Key key, Details details) {
  public record Id(String value) {
  }

  public record Version(long value) {
  }

  public record Key(Id id, Version version) {
    public Key(String id, long version) {
      this(new Id(id), new Version(version));
    }
  }

  public record Create(long gameId, String title, double price, String description) {
    public Create withPrice(double price) {
      return new Create(this.gameId, this.title, price, this.description);
    }
  }

  public record Update(String title, double price, String description) {
  }

  public record Details(long gameId, String title, double price, String description, boolean published) {
  }
}
