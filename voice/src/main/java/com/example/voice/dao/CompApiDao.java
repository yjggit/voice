package com.example.voice.dao;

import com.example.voice.entities.CompApi;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jianhui.Yang
 * @version $Id CompApiDao.java, v 0.1 2018-11-09 15:15 jianhui.Yang Exp $$
 */
@Repository
public interface CompApiDao extends PagingAndSortingRepository<CompApi, String> {
}
