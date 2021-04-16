package com.gateway.common.jedis;

import java.util.List;

public interface JedisClient {

	/**
	 * 将字符串值设置为键的值。字符串不能长于1073741824字节(1GB)。时间复杂性：O(1)
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	String set(String key, String value);
	
	/**
	 * 该命令与以下命令组完全相同：设置过期。该操作是原子的。时间复杂性：O(1)
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	String setex(String key, int seconds,String value);

	/**
	 * 获取指定键的值。如果键不存在，则返回NULL。如果在键处存储的值不是字符串，则返回错误，因为get只能处理字符串值。时间复杂度：O(1)
	 * 
	 * @param key
	 * @return
	 */
	String get(String key);

	/**
	 * 如果指定的密钥存在，则测试。如果存在密钥，则命令返回true，否则返回false。请注意，即使以空字符串设置的密钥也将返回true。时间复杂性：O
	 * (1)
	 * 
	 * @param key
	 * @return
	 */
	Boolean exists(String key);

	/**
	 * 在指定的键上设置超时。超时后，密钥将被服务器自动删除。在Redis术语中，具有关联超时的键称为易失性。
	 * 像其他键一样存储在磁盘上，超时也是持久的，就像数据集的所有其他方面一样。保存包含过期和停止服务器的数据集不会停止时间流，因为Red
	 * is将密钥不再可用的时间存储在磁盘上，而不是剩余的秒。 由于Red
	 * is2.1.3，您可以更新已有过期集的键的超时值。还可以使用Persist命令将密钥转换为普通密钥。 时间复杂度：O（1）..
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	Long expire(String key, int seconds);

	/**
	 * ttl命令返回剩余的时间，以便在具有过期集的密钥的秒内存活。这种内省功能允许Redis客户端检查给定键将继续作为数据集一部分的秒数。
	 * 
	 * @param key
	 * @return
	 */
	Long ttl(String key);

	/**
	 * 将存储在密钥中的数字递增1。如果密钥不存在或包含错误类型的值，则在执行增量操作之前，将该密钥设置为"0"的值。INCR命令限于64位带符号整数。
	 * 注意：这实际上是一个字符串操作，也就是说，在Redis中没有"整数"类型。简单地，存储在密钥中的字符串被解析为基础1064位带符号整数，递增，
	 * 然后被转换为字符串。时间复杂性：O(1)
	 * 
	 * @param key
	 * @return
	 */
	Long incr(String key);

	/**
	 * 将指定的哈希字段设置为指定值。如果不存在密钥，则创建保存散列的新密钥。时间复杂性：O(1)
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	Long hset(String key, String field, String value);

	/**
	 * 如果密钥保存哈希，请检索与指定字段关联的值。如果未找到字段或不存在密钥，则返回特殊的“nil”值。时间复杂性：O(1)
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	String hget(String key, String field);

	/**
	 * 从存储在键处的散列中移除指定的字段。时间复杂度：O(1)
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	Long hdel(String key, String... field);

	/**
	 * 测试哈希中是否存在指定的字段。时间复杂度：O(1)
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	Boolean hexists(String key, String field);

	/**
	 * 返回哈希中的所有值。时间复杂度：o(n)，其中n是条目总数
	 * 
	 * @param key
	 * @return
	 */
	List<String> hvals(String key);

	Long del(String key);
}
