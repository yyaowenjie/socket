package test.base;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
/**
 * Junit ���Ի��࣬��Ҫ��Ϊ�˼��������ļ�
 * ʹ�ü̳д�������಻��Ҫ��ȥ���������ļ�
 * @User yaowenjie
 * @Time 2016-06-14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:springContext-*.xml","classpath*:applicationContext.xml"})
@WebAppConfiguration
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class TestCase{

    @PersistenceContext
    protected EntityManager entityManager;

    @Test
	public void test () {

    }
}