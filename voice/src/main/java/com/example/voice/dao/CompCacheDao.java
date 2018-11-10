package com.example.voice.dao;

import com.example.voice.entities.CompCache;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jianhui.Yang
 * @version $Id CompCacheDao.java, v 0.1 2018-11-09 10:07 jianhui.Yang Exp $$
 */
@Repository
public interface CompCacheDao extends PagingAndSortingRepository<CompCache,String> {
}
