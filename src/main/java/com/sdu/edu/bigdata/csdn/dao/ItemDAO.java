package com.sdu.edu.bigdata.csdn.dao;

import com.sdu.edu.bigdata.csdn.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * ItemDAO
 *
 * @author Allen Jin
 * @date 2017/05/19
 */
@Repository
public interface ItemDAO extends JpaRepository<Item, Integer>, JpaSpecificationExecutor<Item> {

}
