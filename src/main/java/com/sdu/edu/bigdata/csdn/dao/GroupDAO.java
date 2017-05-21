package com.sdu.edu.bigdata.csdn.dao;

import com.sdu.edu.bigdata.csdn.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * GroupDAO
 *
 * @author Allen Jin
 * @date 2017/05/19
 */
@Repository
public interface GroupDAO extends JpaRepository<Group, Integer> {

    List<Group> findByName(String user);

}
