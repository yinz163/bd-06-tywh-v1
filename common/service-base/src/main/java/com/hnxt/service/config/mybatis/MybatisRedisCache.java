package com.hnxt.service.config.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * mybatis二级缓存、待完成
 * @Author yinz
 * @Date 2021/9/15 - 11:36
 */
@Slf4j
public class MybatisRedisCache implements Cache {

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

	private Random random = new Random();

	private String id;

	public MybatisRedisCache(final String id) {
		if(id == null) {
			throw new IllegalArgumentException("cache instances require an ID");
		}
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void putObject(Object key, Object value) {

	}

	@Override
	public Object getObject(Object key) {
		return null;
	}

	@Override
	public Object removeObject(Object key) {
		return null;
	}

	@Override
	public void clear() {

	}

	@Override
	public int getSize() {
		return 0;
	}
}
