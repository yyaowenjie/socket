package com.sq.job.trigger.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.sq.job.trigger.domain.ScheduleJob;
/**
 * Schedule仓库实现类
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time 下午2:05:42
 */
@Repository
public class ScheduleJobRepositoryImpl implements ScheduleJobRepository{
	private EntityManagerFactory emf;

	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public List<ScheduleJob> getAll() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String sql = "select * from t_scheduleJob";
		Query query = em.createNativeQuery(sql, ScheduleJob.class);
		List<ScheduleJob> list = query.getResultList();
		em.getTransaction().commit();
		em.close();
		return list;
	}

}
