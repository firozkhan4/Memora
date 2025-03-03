import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * InMemoryDB
 */
public class InMemoryDB {

  private static InMemoryDB instance;
  private final ConcurrentHashMap<String, Entry> storage; 
  private final ScheduledExecutorService scheduler;

  private InMemoryDB(){
    storage = new ConcurrentHashMap<>();
    scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(this::clearExpired, 30, 30, TimeUnit.MINUTES);
  }

  public static synchronized InMemoryDB getInstance(){
    if(instance == null){
      instance = new InMemoryDB();
    }
    return instance;
  }

  public void set(String key, Entry value, long ttl){
    if(ttl > 0){
      storage.put(key, new Entry(value, ttl));
    }else{
      storage.put(key, new Entry(value));
    }
  }

  public Entry get(String key){
    if(storage.containsKey(key) && !storage.get(key).isExpired()){
      return storage.get(key);
    }
    storage.remove(key);
    return null; 
  }

  public void delete(String key){
    storage.remove(key);
  }

  public boolean containsKey(String key){
    return storage.containsKey(key);
  }

  public void clear(){
    storage.clear();
  }

  public int size(){
    return storage.size();
  }

  public boolean isEmpty(){
    return storage.isEmpty();
  }

  public ConcurrentHashMap<String, Entry> getStorage(){
    return storage;
  }

  public void clearExpired(){
    storage.entrySet().removeIf(entry -> entry.getValue().isExpired());
  }
}
