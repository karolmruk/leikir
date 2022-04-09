package kmr.rt.cdp.leikir.catalog.domain.dto;

public record Draft (Key key, Details details) {
  public record Id (long value) {}
  public record Version (long value) {}
  public record Key (Id id, Version version) {
    public Key(long id, long version) {
      this(new Id(id), new Version(version));
    }
  }


  public record Create(String title, double price, String description) {
    public Create withPrice(double price) {
      return new Create(this.title, price, this.description);
    }
  }

  public record Details(String title, double price, String description) {}
}
