package org.eleven.util;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil<V> {

    @Resource
    private RedisTemplate<String, V> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, V> valueOperations;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, V> hashOperations;

    @Resource(name = "redisTemplate")
    private ListOperations<String, V> listOperations;

    @Resource(name = "redisTemplate")
    private SetOperations<String, V> setOperations;

    @Resource(name = "redisTemplate")
    private ZSetOperations<String, V> zSetOperations;

    //** -------------------key相关操作--------------------- */

    /**
     * Determine if given {@code key} exists.
     *
     * @param key must not be {@literal null}.
     * @return key exists return true
     * @see <a href="http://redis.io/commands/exists">Redis Documentation: EXISTS</a>
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * Delete given {@code key}.
     *
     * @param key must not be {@literal null}.
     * @see <a href="http://redis.io/commands/del">Redis Documentation: DEL</a>
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Delete given {@code keys}.
     *
     * @param keys must not be {@literal null}.
     * @see <a href="http://redis.io/commands/del">Redis Documentation: DEL</a>
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * Determine the type stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return value's datatype
     * @see <a href="http://redis.io/commands/type">Redis Documentation: TYPE</a>
     */
    public DataType type(String key) {
        return redisTemplate.type(key);
    }

    /**
     * Find all keys matching the given {@code pattern}.
     *
     * @param pattern must not be {@literal null}.
     * @return keys
     * @see <a href="http://redis.io/commands/keys">Redis Documentation: KEYS</a>
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * Return a random key from the keyspace.
     *
     * @return random key.
     * @see <a href="http://redis.io/commands/randomkey">Redis Documentation: RANDOMKEY</a>
     */
    public String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * Rename key {@code oldKey} to {@code newKey}.
     *
     * @param oldKey must not be {@literal null}.
     * @param newKey must not be {@literal null}.
     * @see <a href="http://redis.io/commands/rename">Redis Documentation: RENAME</a>
     */
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * Rename key {@code oleName} to {@code newKey} only if {@code newKey} does not exist.
     *
     * @param oldKey must not be {@literal null}.
     * @param newKey must not be {@literal null}.
     * @return newKey not exist return true.
     * @see <a href="http://redis.io/commands/renamenx">Redis Documentation: RENAMENX</a>
     */
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * Set time to live for given {@code key}..
     *
     * @param key     must not be {@literal null}.
     * @param timeout time
     * @param unit    must not be {@literal null}.
     * @return set time ok return true.
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * Set the expiration for given {@code key} as a {@literal date} timestamp.
     *
     * @param key  must not be {@literal null}.
     * @param date must not be {@literal null}.
     * @return set time ok return true.
     */
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * Remove the expiration from given {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return remove expire ok return true.
     * @see <a href="http://redis.io/commands/persist">Redis Documentation: PERSIST</a>
     */
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * Move given {@code key} to database with {@code index}.
     *
     * @param key     must not be {@literal null}.
     * @param dbIndex db index
     * @return move key to db
     * @see <a href="http://redis.io/commands/move">Redis Documentation: MOVE</a>
     */
    public Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * Retrieve serialized version of the value stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return serialized key
     * @see <a href="http://redis.io/commands/dump">Redis Documentation: DUMP</a>
     */
    public byte[] dump(String key) {
        return redisTemplate.dump(key);
    }

    /**
     * Get the time to live for {@code key} in seconds.
     *
     * @param key must not be {@literal null}.
     * @return seconds.
     * @see <a href="http://redis.io/commands/ttl">Redis Documentation: TTL</a>
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * Get the time to live for {@code key} in and convert it to the given {@link TimeUnit}.
     *
     * @param key      must not be {@literal null}.
     * @param timeUnit must not be {@literal null}.
     * @return time of timeUnit
     * @since 1.8
     */
    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    //** -------------------string相关操作--------------------- */

    /**
     * Set {@code value} for {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param value string value
     * @see <a href="http://redis.io/commands/set">Redis Documentation: SET</a>
     */
    public void set(String key, V value) {
        valueOperations.set(key, value);
    }

    /**
     * Set the {@code value} and expiration {@code timeout} for {@code key}.
     *
     * @param key     must not be {@literal null}.
     * @param value   string value.
     * @param timeout time
     * @param unit    must not be {@literal null}.
     * @see <a href="http://redis.io/commands/setex">Redis Documentation: SETEX</a>
     */
    public void set(String key, V value, long timeout, TimeUnit unit) {
        valueOperations.set(key, value, timeout, unit);
    }

    /**
     * Set {@code key} to hold the string {@code value} if {@code key} is absent.
     *
     * @param key   must not be {@literal null}.
     * @param value string value.
     * @see <a href="http://redis.io/commands/setnx">Redis Documentation: SETNX</a>
     */
    public boolean setIfAbsent(String key, V value) {
        return valueOperations.setIfAbsent(key, value);
    }

    /**
     * Set multiple keys to multiple values using key-value pairs provided in {@code tuple}.
     *
     * @param map must not be {@literal null}.
     * @see <a href="http://redis.io/commands/mset">Redis Documentation: MSET</a>
     */
    public void multiSet(Map<String, V> map) {
        valueOperations.multiSet(map);
    }

    /**
     * Set multiple keys to multiple values using key-value pairs provided in {@code tuple} only if the provided key does
     * not exist.
     *
     * @param map must not be {@literal null}.
     * @see <a href="http://redis.io/commands/mset">Redis Documentation: MSET</a>
     */
    public boolean multiSetIfAbsent(Map<String, V> map) {
        return valueOperations.multiSetIfAbsent(map);
    }

    /**
     * Get the value of {@code key}.
     *
     * @param key must not be {@literal null}.
     * @see <a href="http://redis.io/commands/get">Redis Documentation: GET</a>
     */
    public V get(String key) {
        return valueOperations.get(key);
    }

    /**
     * Set {@code value} of {@code key} and return its old value.
     *
     * @param key must not be {@literal null}.
     * @see <a href="http://redis.io/commands/getset">Redis Documentation: GETSET</a>
     */
    public V getAndSet(String key, V value) {
        return valueOperations.getAndSet(key, value);
    }

    /**
     * Get multiple {@code keys}. Values are returned in the order of the requested keys.
     *
     * @param keys must not be {@literal null}.
     * @return values.
     * @see <a href="http://redis.io/commands/mget">Redis Documentation: MGET</a>
     */
    public List<V> multiGet(Collection<String> keys) {
        return valueOperations.multiGet(keys);
    }

    /**
     * Increment an integer value stored as string value under {@code key} by {@code delta}.
     *
     * @param key   must not be {@literal null}.
     * @param delta delta
     * @see <a href="http://redis.io/commands/incr">Redis Documentation: INCR</a>
     */
    public Long increment(String key, long delta) {
        return valueOperations.increment(key, delta);
    }

    /**
     * Increment a floating point number value stored as string value under {@code key} by {@code delta}.
     *
     * @param key   must not be {@literal null}.
     * @param delta delta
     * @see <a href="http://redis.io/commands/incrbyfloat">Redis Documentation: INCRBYFLOAT</a>
     */
    public Double increment(String key, double delta) {
        return valueOperations.increment(key, delta);
    }

    /**
     * Append a {@code value} to {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param value string value
     * @see <a href="http://redis.io/commands/append">Redis Documentation: APPEND</a>
     */
    public Integer append(String key, String value) {
        return valueOperations.append(key, value);
    }

    /**
     * Get a substring of value of {@code key} between {@code begin} and {@code end}.
     *
     * @param key   must not be {@literal null}.
     * @param start start index
     * @param end   end index
     * @see <a href="http://redis.io/commands/getrange">Redis Documentation: GETRANGE</a>
     */
    public String get(String key, long start, long end) {
        return valueOperations.get(key, start, end);
    }

    /**
     * Overwrite parts of {@code key} starting at the specified {@code offset} with given {@code value}.
     *
     * @param key    must not be {@literal null}.
     * @param value  string value
     * @param offset offset
     * @see <a href="http://redis.io/commands/setrange">Redis Documentation: SETRANGE</a>
     */
    public void set(String key, V value, long offset) {
        valueOperations.set(key, value, offset);
    }

    /**
     * Get the length of the value stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return size.
     * @see <a href="http://redis.io/commands/strlen">Redis Documentation: STRLEN</a>
     */
    public Long size(String key) {
        return valueOperations.size(key);
    }

    /**
     * Sets the bit at {@code offset} in value stored at {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param offset offset
     * @param value  boolean value
     * @see <a href="http://redis.io/commands/setbit">Redis Documentation: SETBIT</a>
     * @since 1.5
     */
    public boolean setBit(String key, long offset, boolean value) {
        return valueOperations.setBit(key, offset, value);
    }

    /**
     * Get the bit value at {@code offset} of value at {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param offset offset
     * @return boolean value
     * @see <a href="http://redis.io/commands/setbit">Redis Documentation: GETBIT</a>
     */
    public Boolean getBit(String key, long offset) {
        return valueOperations.getBit(key, offset);
    }

    //** -------------------hash相关操作------------------------- */

    /**
     * Delete given hash {@code hashKeys}.
     *
     * @param key      must not be {@literal null}.
     * @param hashKeys must not be {@literal null}.
     * @return deleted num
     */
    public Long hDelete(String key, Object... hashKeys) {
        return hashOperations.delete(key, hashKeys);
    }

    /**
     * Determine if given hash {@code hashKey} exists.
     *
     * @param key     must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @return exists hash key return true
     */
    public boolean hHasKey(String key, String hashKey) {
        return hashOperations.hasKey(key, hashKey);
    }

    /**
     * Get value for given {@code hashKey} from hash at {@code key}.
     *
     * @param key     must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @return value
     */
    public V hGet(String key, String hashKey) {
        return hashOperations.get(key, hashKey);
    }

    /**
     * Get values for given {@code hashKeys} from hash at {@code key}.
     *
     * @param key      must not be {@literal null}.
     * @param hashKeys must not be {@literal null}.
     * @return values
     */
    public List<V> hMultiGet(String key, Collection<String> hashKeys) {
        return hashOperations.multiGet(key, hashKeys);
    }

    /**
     * Increment {@code value} of a hash {@code hashKey} by the given {@code delta}.
     *
     * @param key     must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @param delta   delta
     * @return long
     */
    public Long hIncrement(String key, String hashKey, long delta) {
        return hashOperations.increment(key, hashKey, delta);
    }

    /**
     * Increment {@code value} of a hash {@code hashKey} by the given {@code delta}.
     *
     * @param key     must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @param delta   delta
     * @return double
     */
    public Double hIncrement(String key, String hashKey, double delta) {
        return hashOperations.increment(key, hashKey, delta);
    }

    /**
     * Get key set (fields) of hash at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return hash keys
     */
    public Set<String> hKeys(String key) {
        return hashOperations.keys(key);
    }

    /**
     * Get size of hash at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return hash size
     */
    public Long hSize(String key) {
        return hashOperations.size(key);
    }

    /**
     * Set multiple hash fields to multiple values using data provided in {@code m}.
     *
     * @param key must not be {@literal null}.
     * @param map must not be {@literal null}.
     */
    public void hPutAll(String key, Map<String, V> map) {
        hashOperations.putAll(key, map);
    }

    /**
     * Set the {@code value} of a hash {@code hashKey}.
     *
     * @param key     must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @param value   value
     */
    public void hPut(String key, String hashKey, V value) {
        hashOperations.put(key, hashKey, value);
    }

    /**
     * Set the {@code value} of a hash {@code hashKey} only if {@code hashKey} does not exist.
     *
     * @param key     must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @param value   value
     * @return hashKey no exist return true
     */
    public Boolean hPutIfAbsent(String key, String hashKey, V value) {
        return hashOperations.putIfAbsent(key, hashKey, value);
    }

    /**
     * Get entry set (values) of hash at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return hash values
     */
    public List<V> hValues(String key) {
        return hashOperations.values(key);
    }

    /**
     * Get entire hash stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return hash map
     */
    public Map<String, V> hGetAll(String key) {
        return hashOperations.entries(key);
    }

    /**
     * Use a {@link Cursor} to iterate over entries in hash at {@code key}. <br />
     * <strong>Important:</strong> Call {@link Cursor#close()} when done to avoid resource leak.
     *
     * @param key     must not be {@literal null}.
     * @param options options
     * @return cursor
     * @since 1.4
     */
    public Cursor<Entry<String, V>> hScan(String key, ScanOptions options) {
        return hashOperations.scan(key, options);
    }


    //** ------------------------list相关操作---------------------------- */

    /**
     * Get all elements from list at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return values
     * @see <a href="http://redis.io/commands/lrange">Redis Documentation: LRANGE</a>
     */
    public List<V> lRange(String key) {
        return lRange(key, 0, -1);
    }

    /**
     * Get elements between {@code begin} and {@code end} from list at {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param start start index
     * @param end   end index
     * @return values
     * @see <a href="http://redis.io/commands/lrange">Redis Documentation: LRANGE</a>
     */
    public List<V> lRange(String key, long start, long end) {
        return listOperations.range(key, start, end);
    }

    /**
     * Trim list at {@code key} to elements between {@code start} and {@code end}.
     *
     * @param key   must not be {@literal null}.
     * @param start start index
     * @param end   end index
     * @see <a href="http://redis.io/commands/ltrim">Redis Documentation: LTRIM</a>
     */
    public void lTrim(String key, long start, long end) {
        listOperations.trim(key, start, end);
    }

    /**
     * Get the size of list stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return size
     * @see <a href="http://redis.io/commands/llen">Redis Documentation: LLEN</a>
     */
    public Long lSize(String key) {
        return listOperations.size(key);
    }

    /**
     * Prepend {@code value} to {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param value value
     * @return size
     * @see <a href="http://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     */
    public Long lLeftPush(String key, V value) {
        return listOperations.leftPush(key, value);
    }

    /**
     * Prepend {@code values} to {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param values values
     * @return size
     * @see <a href="http://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     */
    public Long lLeftPushAll(String key, V[] values) {
        return listOperations.leftPushAll(key, values);
    }

    /**
     * Prepend {@code values} to {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param values must not be {@literal null}.
     * @return size
     * @see <a href="http://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     * @since 1.5
     */
    public Long lLeftPushAll(String key, Collection<V> values) {
        return listOperations.leftPushAll(key, values);
    }

    /**
     * Prepend {@code values} to {@code key} only if the list exists.
     *
     * @param key   must not be {@literal null}.
     * @param value value
     * @return size
     * @see <a href="http://redis.io/commands/lpushx">Redis Documentation: LPUSHX</a>
     */
    public Long lLeftPushIfPresent(String key, V value) {
        return listOperations.leftPushIfPresent(key, value);
    }

    /**
     * Prepend {@code values} to {@code key} before {@code value}.
     *
     * @param key   must not be {@literal null}.
     * @param pivot pivot
     * @param value value
     * @return size
     * @see <a href="http://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     */
    public Long lLeftPush(String key, V pivot, V value) {
        return listOperations.leftPush(key, pivot, value);
    }

    /**
     * Append {@code value} to {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param value value
     * @return size
     * @see <a href="http://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     */
    public Long lRightPush(String key, V value) {
        return listOperations.rightPush(key, value);
    }

    /**
     * Append {@code values} to {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param values values
     * @return size
     * @see <a href="http://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     */
    public Long lRightPushAll(String key, V[] values) {
        return listOperations.rightPushAll(key, values);
    }

    /**
     * Append {@code values} to {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param values values
     * @return size
     * @see <a href="http://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     * @since 1.5
     */
    public Long lRightPushAll(String key, Collection<V> values) {
        return listOperations.rightPushAll(key, values);
    }

    /**
     * Append {@code values} to {@code key} only if the list exists.
     *
     * @param key   must not be {@literal null}.
     * @param value value
     * @return size
     * @see <a href="http://redis.io/commands/rpushx">Redis Documentation: RPUSHX</a>
     */
    public Long lRightPushIfPresent(String key, V value) {
        return listOperations.rightPushIfPresent(key, value);
    }

    /**
     * Append {@code values} to {@code key} before {@code value}.
     *
     * @param key   must not be {@literal null}.
     * @param pivot pivot
     * @param value value
     * @return size
     * @see <a href="http://redis.io/commands/lpush">Redis Documentation: RPUSH</a>
     */
    public Long lRightPush(String key, V pivot, V value) {
        return listOperations.rightPush(key, pivot, value);
    }

    /**
     * Set the {@code value} list element at {@code index}.
     *
     * @param key   must not be {@literal null}.
     * @param index index
     * @param value value
     * @see <a href="http://redis.io/commands/lset">Redis Documentation: LSET</a>
     */
    public void lSet(String key, long index, V value) {
        listOperations.set(key, index, value);
    }

    /**
     * Removes the first {@code count} occurrences of {@code value} from the list stored at {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param count count
     * @param value value
     * @return size
     * @see <a href="http://redis.io/commands/lrem">Redis Documentation: LREM</a>
     */
    public Long lRemove(String key, long count, V value) {
        return listOperations.remove(key, count, value);
    }

    /**
     * Get element at {@code index} form list at {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param index index
     * @return value
     * @see <a href="http://redis.io/commands/lindex">Redis Documentation: LINDEX</a>
     */
    public V lIndex(String key, long index) {
        return listOperations.index(key, index);
    }

    /**
     * Removes and returns first element in list stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return value
     * @see <a href="http://redis.io/commands/lpop">Redis Documentation: LPOP</a>
     */
    public V lLeftPop(String key) {
        return listOperations.leftPop(key);
    }

    /**
     * Removes and returns first element from lists stored at {@code key} . <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param key     must not be {@literal null}.
     * @param timeout time
     * @param unit    must not be {@literal null}.
     * @return value
     * @see <a href="http://redis.io/commands/blpop">Redis Documentation: BLPOP</a>
     */
    public V lLeftPop(String key, long timeout, TimeUnit unit) {
        return listOperations.leftPop(key, timeout, unit);
    }

    /**
     * Removes and returns last element in list stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return last value
     * @see <a href="http://redis.io/commands/rpop">Redis Documentation: RPOP</a>
     */
    public V lRightPop(String key) {
        return listOperations.rightPop(key);
    }

    /**
     * Removes and returns last element from lists stored at {@code key}. <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param key     must not be {@literal null}.
     * @param timeout time
     * @param unit    must not be {@literal null}.
     * @return last value
     * @see <a href="http://redis.io/commands/brpop">Redis Documentation: BRPOP</a>
     */
    public V lRightPop(String key, long timeout, TimeUnit unit) {
        return listOperations.rightPop(key, timeout, unit);
    }

    /**
     * Remove the last element from list at {@code sourceKey}, append it to {@code destinationKey} and return its value.
     *
     * @param sourceKey      must not be {@literal null}.
     * @param destinationKey must not be {@literal null}.
     * @return removed value
     * @see <a href="http://redis.io/commands/rpoplpush">Redis Documentation: RPOPLPUSH</a>
     */
    public V lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        return listOperations.rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * Remove the last element from list at {@code srcKey}, append it to {@code dstKey} and return its value.<br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param sourceKey      must not be {@literal null}.
     * @param destinationKey must not be {@literal null}.
     * @param timeout        time
     * @param unit           must not be {@literal null}.
     * @return removed value
     * @see <a href="http://redis.io/commands/brpoplpush">Redis Documentation: BRPOPLPUSH</a>
     */
    public V lRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        return listOperations.rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }

    //** --------------------set相关操作-------------------------- */

    /**
     * Add given {@code values} to set at {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param values values
     * @return size
     * @see <a href="http://redis.io/commands/sadd">Redis Documentation: SADD</a>
     */
    public Long sAdd(String key, V[] values) {
        return setOperations.add(key, values);
    }

    /**
     * Remove given {@code values} from set at {@code key} and return the number of removed elements.
     *
     * @param key    must not be {@literal null}.
     * @param values values
     * @return size
     * @see <a href="http://redis.io/commands/srem">Redis Documentation: SREM</a>
     */
    public Long sRemove(String key, Object... values) {
        return setOperations.remove(key, values);
    }

    /**
     * Remove and return a random member from set at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return removed value
     * @see <a href="http://redis.io/commands/spop">Redis Documentation: SPOP</a>
     */
    public V sPop(String key) {
        return setOperations.pop(key);
    }

    /**
     * Move {@code value} from {@code key} to {@code destKey}
     *
     * @param key     must not be {@literal null}.
     * @param value   value
     * @param destKey must not be {@literal null}.
     * @return ok return true
     * @see <a href="http://redis.io/commands/smove">Redis Documentation: SMOVE</a>
     */
    public Boolean sMove(String key, V value, String destKey) {
        return setOperations.move(key, value, destKey);
    }

    /**
     * Get size of set at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return size
     * @see <a href="http://redis.io/commands/scard">Redis Documentation: SCARD</a>
     */
    public Long sSize(String key) {
        return setOperations.size(key);
    }

    /**
     * Check if set at {@code key} contains {@code value}.
     *
     * @param key   must not be {@literal null}.
     * @param value value
     * @return set contains value return true
     * @see <a href="http://redis.io/commands/sismember">Redis Documentation: SISMEMBER</a>
     */
    public Boolean sIsMember(String key, V value) {
        return setOperations.isMember(key, value);
    }

    /**
     * Returns the members intersecting all given sets at {@code key} and {@code otherKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @return two set same values
     * @see <a href="http://redis.io/commands/sinter">Redis Documentation: SINTER</a>
     */
    public Set<V> sIntersect(String key, String otherKey) {
        return setOperations.intersect(key, otherKey);
    }

    /**
     * Returns the members intersecting all given sets at {@code key} and {@code otherKeys}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @return all sets same values
     * @see <a href="http://redis.io/commands/sinter">Redis Documentation: SINTER</a>
     */
    public Set<V> sIntersect(String key, Collection<String> otherKeys) {
        return setOperations.intersect(key, otherKeys);
    }

    /**
     * Intersect all given sets at {@code key} and {@code otherKey} and store result in {@code destKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @param destKey  must not be {@literal null}.
     * @return size
     * @see <a href="http://redis.io/commands/sinterstore">Redis Documentation: SINTERSTORE</a>
     */
    public Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return setOperations.intersectAndStore(key, otherKey, destKey);
    }

    /**
     * Intersect all given sets at {@code key} and {@code otherKeys} and store result in {@code destKey}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @return size
     * @see <a href="http://redis.io/commands/sinterstore">Redis Documentation: SINTERSTORE</a>
     */
    public Long sIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return setOperations.intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * Union all sets at given {@code keys} and {@code otherKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @return two set all values
     * @see <a href="http://redis.io/commands/sunion">Redis Documentation: SUNION</a>
     */
    public Set<V> sUnion(String key, String otherKey) {
        return setOperations.union(key, otherKey);
    }

    /**
     * Union all sets at given {@code keys} and {@code otherKeys}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @return all sets values
     * @see <a href="http://redis.io/commands/sunion">Redis Documentation: SUNION</a>
     */
    public Set<V> sUnion(String key, Collection<String> otherKeys) {
        return setOperations.union(key, otherKeys);
    }

    /**
     * Union all sets at given {@code key} and {@code otherKey} and store result in {@code destKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @param destKey  must not be {@literal null}.
     * @return two set all values save to destKey
     * @see <a href="http://redis.io/commands/sunionstore">Redis Documentation: SUNIONSTORE</a>
     */
    public Long sUnionAndStore(String key, String otherKey, String destKey) {
        return setOperations.unionAndStore(key, otherKey, destKey);
    }

    /**
     * Union all sets at given {@code key} and {@code otherKeys} and store result in {@code destKey}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @return all sets values save to destKey
     * @see <a href="http://redis.io/commands/sunionstore">Redis Documentation: SUNIONSTORE</a>
     */
    public Long sUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return setOperations.unionAndStore(key, otherKeys, destKey);
    }

    /**
     * Diff all sets for given {@code key} and {@code otherKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @return two set diff all values
     * @see <a href="http://redis.io/commands/sdiff">Redis Documentation: SDIFF</a>
     */
    public Set<V> sDifference(String key, String otherKey) {
        return setOperations.difference(key, otherKey);
    }

    /**
     * Diff all sets for given {@code key} and {@code otherKeys}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @return all sets diff all values
     * @see <a href="http://redis.io/commands/sdiff">Redis Documentation: SDIFF</a>
     */
    public Set<V> sDifference(String key, Collection<String> otherKeys) {
        return setOperations.difference(key, otherKeys);
    }

    /**
     * Diff all sets for given {@code key} and {@code otherKey} and store result in {@code destKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @param destKey  must not be {@literal null}.
     * @return two set diff all values save to {@code destKey}
     * @see <a href="http://redis.io/commands/sdiffstore">Redis Documentation: SDIFFSTORE</a>
     */
    public Long sDifference(String key, String otherKey, String destKey) {
        return setOperations.differenceAndStore(key, otherKey, destKey);
    }

    /**
     * Diff all sets for given {@code key} and {@code otherKeys} and store result in {@code destKey}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @return all sets diff all values save to {@code destKey}
     * @see <a href="http://redis.io/commands/sdiffstore">Redis Documentation: SDIFFSTORE</a>
     */
    public Long sDifference(String key, Collection<String> otherKeys, String destKey) {
        return setOperations.differenceAndStore(key, otherKeys, destKey);
    }

    /**
     * Get all elements of set at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return values
     * @see <a href="http://redis.io/commands/smembers">Redis Documentation: SMEMBERS</a>
     */
    public Set<V> sMembers(String key) {
        return setOperations.members(key);
    }

    /**
     * Get random element from set at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return random value
     * @see <a href="http://redis.io/commands/srandmember">Redis Documentation: SRANDMEMBER</a>
     */
    public V sRandomMember(String key) {
        return setOperations.randomMember(key);
    }

    /**
     * Get {@code count} distinct random elements from set at {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param count count
     * @return random {@code count} values
     * @see <a href="http://redis.io/commands/srandmember">Redis Documentation: SRANDMEMBER</a>
     */
    public List<V> sRandomMembers(String key, long count) {
        return setOperations.randomMembers(key, count);
    }

    /**
     * Get {@code count} random elements from set at {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param count count
     * @return random {@code count} values
     * @see <a href="http://redis.io/commands/srandmember">Redis Documentation: SRANDMEMBER</a>
     */
    public Set<V> sDistinctRandomMembers(String key, long count) {
        return setOperations.distinctRandomMembers(key, count);
    }

    /**
     * Iterate over elements in set at {@code key}. <br />
     * <strong>Important:</strong> Call {@link Cursor#close()} when done to avoid resource leak.
     *
     * @param key     key
     * @param options options
     * @return cursor
     * @since 1.4
     */
    public Cursor<V> sScan(String key, ScanOptions options) {
        return setOperations.scan(key, options);
    }

    //** ------------------zSet相关操作-------------------------------- */

    /**
     * Add {@code value} to a sorted set at {@code key}, or update its {@code score} if it already exists.
     *
     * @param key   must not be {@literal null}.
     * @param score the score.
     * @param value the value.
     * @return ok return true
     * @see <a href="http://redis.io/commands/zadd">Redis Documentation: ZADD</a>
     */
    public Boolean zAdd(String key, V value, double score) {
        return zSetOperations.add(key, value, score);
    }

    /**
     * Add {@code tuples} to a sorted set at {@code key}, or update its {@code score} if it already exists.
     *
     * @param key    must not be {@literal null}.
     * @param tuples must not be {@literal null}.
     * @return size
     * @see <a href="http://redis.io/commands/zadd">Redis Documentation: ZADD</a>
     */
    public Long zAdd(String key, Set<TypedTuple<V>> tuples) {
        return zSetOperations.add(key, tuples);
    }

    /**
     * Remove {@code values} from sorted set. Return number of removed elements.
     *
     * @param key    must not be {@literal null}.
     * @param values must not be {@literal null}.
     * @return size
     * @see <a href="http://redis.io/commands/zrem">Redis Documentation: ZREM</a>
     */
    public Long zRemove(String key, Object... values) {
        return zSetOperations.remove(key, values);
    }

    /**
     * Increment the score of element with {@code value} in sorted set by {@code increment}.
     *
     * @param key   must not be {@literal null}.
     * @param delta delta
     * @param value the value.
     * @return double
     * @see <a href="http://redis.io/commands/zincrby">Redis Documentation: ZINCRBY</a>
     */
    public Double zIncrementScore(String key, V value, double delta) {
        return zSetOperations.incrementScore(key, value, delta);
    }

    /**
     * Determine the index of element with {@code value} in a sorted set.
     *
     * @param key   must not be {@literal null}.
     * @param value the value.
     * @return size
     * @see <a href="http://redis.io/commands/zrank">Redis Documentation: ZRANK</a>
     */
    public Long zRank(String key, V value) {
        return zSetOperations.rank(key, value);
    }

    /**
     * Determine the index of element with {@code value} in a sorted set when scored high to low.
     *
     * @param key   must not be {@literal null}.
     * @param value the value.
     * @return ranking
     * @see <a href="http://redis.io/commands/zrevrank">Redis Documentation: ZREVRANK</a>
     */
    public Long zReverseRank(String key, V value) {
        return zSetOperations.reverseRank(key, value);
    }

    /**
     * Get elements between {@code start} and {@code end} from sorted set.
     *
     * @param key   must not be {@literal null}.
     * @param start start index
     * @param end   end index
     * @return seq values
     * @see <a href="http://redis.io/commands/zrange">Redis Documentation: ZRANGE</a>
     */
    public Set<V> zRange(String key, long start, long end) {
        return zSetOperations.range(key, start, end);
    }

    /**
     * Get set of {@link redis.clients.jedis.Tuple}s between {@code start} and {@code end} from sorted set.
     *
     * @param key   must not be {@literal null}.
     * @param start start index
     * @param end   end index
     * @return set typed tuple
     * @see <a href="http://redis.io/commands/zrange">Redis Documentation: ZRANGE</a>
     */
    public Set<TypedTuple<V>> zRangeWithScores(String key, long start, long end) {
        return zSetOperations.rangeWithScores(key, start, end);
    }

    /**
     * Get elements where score is between {@code min} and {@code max} from sorted set.
     *
     * @param key must not be {@literal null}.
     * @param min min score
     * @param max max score
     * @return seq values
     * @see <a href="http://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    public Set<V> zRangeByScore(String key, double min, double max) {
        return zSetOperations.rangeByScore(key, min, max);
    }

    /**
     * Get set of {@link redis.clients.jedis.Tuple}s where score is between {@code min} and {@code max} from sorted set.
     *
     * @param key must not be {@literal null}.
     * @param min min score
     * @param max max score
     * @return seq values
     * @see <a href="http://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    public Set<TypedTuple<V>> zRangeByScoreWithScores(String key, double min, double max) {
        return zSetOperations.rangeByScoreWithScores(key, min, max);
    }

    /**
     * Get elements in range from {@code start} to {@code end} where score is between {@code min} and {@code max} from
     * sorted set.
     *
     * @param key    must not be {@literal null}.
     * @param min    min score
     * @param max    max score
     * @param offset offset
     * @param count  count
     * @return seq values
     * @see <a href="http://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    public Set<V> zRangeByScore(String key, double min, double max, long offset, long count) {
        return zSetOperations.rangeByScore(key, min, max, offset, count);
    }

    /**
     * Get set of {@link redis.clients.jedis.Tuple}s where score is between {@code min} and {@code max} from sorted set.
     *
     * @param key must not be {@literal null}.
     * @param min min score
     * @param max max score
     * @return set typed tuple
     * @see <a href="http://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    public Set<TypedTuple<V>> zRangeByScoreWithScores(String key, double min, double max, long start, long end) {
        return zSetOperations.rangeByScoreWithScores(key, min, max, start, end);
    }

    /**
     * Get elements in range from {@code start} to {@code end} from sorted set ordered from high to low.
     *
     * @param key   must not be {@literal null}.
     * @param start start
     * @param end   end
     * @return seq values
     * @see <a href="http://redis.io/commands/zrevrange">Redis Documentation: ZREVRANGE</a>
     */
    public Set<V> zReverseRange(String key, long start, long end) {
        return zSetOperations.reverseRange(key, start, end);
    }

    /**
     * Get set of {@link redis.clients.jedis.Tuple}s in range from {@code start} to {@code end} from sorted set ordered from high to low.
     *
     * @param key   must not be {@literal null}.
     * @param start start
     * @param end   end
     * @return seq values
     * @see <a href="http://redis.io/commands/zrevrange">Redis Documentation: ZREVRANGE</a>
     */
    public Set<TypedTuple<V>> zReverseRangeWithScores(String key, long start, long end) {
        return zSetOperations.reverseRangeWithScores(key, start, end);
    }

    /**
     * Get elements where score is between {@code min} and {@code max} from sorted set ordered from high to low.
     *
     * @param key must not be {@literal null}.
     * @param min min score
     * @param max max score
     * @return seq values
     * @see <a href="http://redis.io/commands/zrevrange">Redis Documentation: ZREVRANGE</a>
     */
    public Set<V> zReverseRangeByScore(String key, double min, double max) {
        return zSetOperations.reverseRangeByScore(key, min, max);
    }

    /**
     * Get set of {@link redis.clients.jedis.Tuple} where score is between {@code min} and {@code max} from sorted set ordered from high to
     * low.
     *
     * @param key must not be {@literal null}.
     * @param min min score
     * @param max max score
     * @return set typed tuple
     * @see <a href="http://redis.io/commands/zrevrangebyscore">Redis Documentation: ZREVRANGEBYSCORE</a>
     */
    public Set<TypedTuple<V>> zReverseRangeByScoreWithScores(String key, double min, double max) {
        return zSetOperations.reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * Get elements in range from {@code start} to {@code end} where score is between {@code min} and {@code max} from
     * sorted set ordered high -> low.
     *
     * @param key    must not be {@literal null}.
     * @param min    min score
     * @param max    max score
     * @param offset offset
     * @param count  count
     * @return seq values
     * @see <a href="http://redis.io/commands/zrevrangebyscore">Redis Documentation: ZREVRANGEBYSCORE</a>
     */
    public Set<V> zReverseRangeByScore(String key, double min, double max, long offset, long count) {
        return zSetOperations.reverseRangeByScore(key, min, max, offset, count);
    }

    /**
     * Get set of {@link TypedTuple} in range from {@code start} to {@code end} where score is between {@code min} and
     * {@code max} from sorted set ordered high -> low.
     *
     * @param key    must not be {@literal null}.
     * @param min    min score
     * @param max    max score
     * @param offset offset
     * @param count  count
     * @return set typed tuple
     * @see <a href="http://redis.io/commands/zrevrangebyscore">Redis Documentation: ZREVRANGEBYSCORE</a>
     */
    public Set<TypedTuple<V>> zReverseRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return zSetOperations.reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * Count number of elements within sorted set with scores between {@code min} and {@code max}.
     *
     * @param key must not be {@literal null}.
     * @param min min score
     * @param max max score
     * @return count
     * @see <a href="http://redis.io/commands/zcount">Redis Documentation: ZCOUNT</a>
     */
    public Long zCount(String key, double min, double max) {
        return zSetOperations.count(key, min, max);
    }

    /**
     * Returns the number of elements of the sorted set stored with given {@code key}.
     *
     * @param key key
     * @return size
     * @see <a href="http://redis.io/commands/zcard">Redis Documentation: ZCARD</a>
     */
    public Long zSize(String key) {
        return zSetOperations.size(key);
    }

    /**
     * Get the size of sorted set with {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return size
     * @see <a href="http://redis.io/commands/zcard">Redis Documentation: ZCARD</a>
     */
    public Long zZCard(String key) {
        return zSetOperations.zCard(key);
    }

    /**
     * Get the score of element with {@code value} from sorted set with key {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param value the value.
     * @return double score
     * @see <a href="http://redis.io/commands/zscore">Redis Documentation: ZSCORE</a>
     */
    public Double zScore(String key, V value) {
        return zSetOperations.score(key, value);
    }

    /**
     * Remove elements in range between {@code start} and {@code end} from sorted set with {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param start start index
     * @param end   end index
     * @return size
     * @see <a href="http://redis.io/commands/zremrangebyrank">Redis Documentation: ZREMRANGEBYRANK</a>
     */
    public Long zRemoveRange(String key, long start, long end) {
        return zSetOperations.removeRange(key, start, end);
    }

    /**
     * Remove elements with scores between {@code min} and {@code max} from sorted set with {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param min min score
     * @param max max score
     * @return size
     * @see <a href="http://redis.io/commands/zremrangebyscore">Redis Documentation: ZREMRANGEBYSCORE</a>
     */
    public Long zRemoveRangeByScore(String key, double min, double max) {
        return zSetOperations.removeRangeByScore(key, min, max);
    }

    /**
     * Union sorted sets at {@code key} and {@code otherKeys} and store result in destination {@code destKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @param destKey  must not be {@literal null}.
     * @return size
     * @see <a href="http://redis.io/commands/zunionstore">Redis Documentation: ZUNIONSTORE</a>
     */
    public Long zUnionAndStore(String key, String otherKey, String destKey) {
        return zSetOperations.unionAndStore(key, otherKey, destKey);
    }

    /**
     * Union sorted sets at {@code key} and {@code otherKeys} and store result in destination {@code destKey}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @return size
     * @see <a href="http://redis.io/commands/zunionstore">Redis Documentation: ZUNIONSTORE</a>
     */
    public Long zUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return zSetOperations.unionAndStore(key, otherKeys, destKey);
    }

    /**
     * Intersect sorted sets at {@code key} and {@code otherKey} and store result in destination {@code destKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @param destKey  must not be {@literal null}.
     * @return size
     * @see <a href="http://redis.io/commands/zinterstore">Redis Documentation: ZINTERSTORE</a>
     */
    public Long zIntersectAndStore(String key, String otherKey, String destKey) {
        return zSetOperations.intersectAndStore(key, otherKey, destKey);
    }

    /**
     * Intersect sorted sets at {@code key} and {@code otherKeys} and store result in destination {@code destKey}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @return size
     * @see <a href="http://redis.io/commands/zinterstore">Redis Documentation: ZINTERSTORE</a>
     */
    public Long zIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return zSetOperations.intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * Iterate over elements in zset at {@code key}. <br />
     * <strong>Important:</strong> Call {@link Cursor#close()} when done to avoid resource leak.
     *
     * @param key     key
     * @param options options
     * @return cursor
     * @see <a href="http://redis.io/commands/zscan">Redis Documentation: ZSCAN</a>
     * @since 1.4
     */
    public Cursor<TypedTuple<V>> zScan(String key, ScanOptions options) {
        return zSetOperations.scan(key, options);
    }

    /**
     * Get all elements with lexicographical ordering from {@literal ZSET} at {@code key} with a value between
     * {@link RedisZSetCommands.Range#getMin()} and {@link RedisZSetCommands.Range#getMax()}.
     *
     * @param key   must not be {@literal null}.
     * @param range must not be {@literal null}.
     * @return seq values
     * @see <a href="http://redis.io/commands/zrangebylex">Redis Documentation: ZRANGEBYLEX</a>
     * @since 1.7
     */
    public Set<V> zRangeByLex(String key, RedisZSetCommands.Range range) {
        return zSetOperations.rangeByLex(key, range);
    }

    /**
     * Get all elements {@literal n} elements, where {@literal n = } {@link RedisZSetCommands.Limit#getCount()}, starting at
     * {@link RedisZSetCommands.Limit#getOffset()} with lexicographical ordering from {@literal ZSET} at {@code key} with a value between
     * {@link RedisZSetCommands.Range#getMin()} and {@link RedisZSetCommands.Range#getMax()}.
     *
     * @param key   must not be {@literal null}
     * @param range must not be {@literal null}.
     * @param limit can be {@literal null}.
     * @return seq values
     * @see <a href="http://redis.io/commands/zrangebylex">Redis Documentation: ZRANGEBYLEX</a>
     * @since 1.7
     */
    public Set<V> zRangeByLex(String key, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit) {
        return zSetOperations.rangeByLex(key, range, limit);
    }

}
