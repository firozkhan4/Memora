/**
 * Entry
 */
public class Entry {

  private Object value;
  private long expiry;

  public Entry(Object value) {
    this.value = value;
    this.expiry = 0;
  }

  public Entry(Object value, long expiry) {
    this.value = value;
    this.expiry = System.currentTimeMillis() + expiry;
  }

  public Object getValue() {
    return value;
  }

  public long getExpiry() {
    return expiry;
  }

  public boolean isExpired() {
    if (expiry == 0) {
      return false;
    }
    return expiry < System.currentTimeMillis();
  }
}
