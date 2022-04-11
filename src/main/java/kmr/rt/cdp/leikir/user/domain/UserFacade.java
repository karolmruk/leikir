package kmr.rt.cdp.leikir.user.domain;

import java.util.Random;

public class UserFacade {
  private final Random random = new Random();

  public long getLoggedInUser() {
    return random.nextLong();
  }
}
