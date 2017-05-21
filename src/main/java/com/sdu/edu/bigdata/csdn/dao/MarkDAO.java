package com.sdu.edu.bigdata.csdn.dao;

import com.sdu.edu.bigdata.csdn.domain.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MarkDAO
 *
 * @author Allen Jin
 * @date 2017/05/19
 */
@Repository
public interface MarkDAO extends JpaRepository<Mark, Long>, JpaSpecificationExecutor<Mark> {
    Mark findBySnAndUser(Integer sn, String user);

    List<Mark> findBySn(Integer sn);

    @Modifying
    @Transactional
    @Query("update Mark m set m.key1 = ?3, m.key2=?4, m.key3=?5 where m.sn = ?1 and m.user = ?2")
    void updateMark(Integer sn, String user, String k1, String k2, String k3);

    @Query("select m from Mark m where m.user = ?1 and (m.key1 = '' or m.key1 is null)")
    List<Mark> queryEscapeItem(String user);

    @Query("select m from Mark m where m.user = ?1 and (m.key1 != '' and m.key1 is not null)")
    List<Mark> queryFinishItem(String user);

}
