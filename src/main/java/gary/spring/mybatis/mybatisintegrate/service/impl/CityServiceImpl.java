package gary.spring.mybatis.mybatisintegrate.service.impl;

import gary.spring.mybatis.mybatisintegrate.dao.CityDao;
import gary.spring.mybatis.mybatisintegrate.model.City;
import gary.spring.mybatis.mybatisintegrate.service.CityService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityDao cityDao;
    private final SqlSessionFactory sqlSessionFactory;


    @Autowired
    public CityServiceImpl(CityDao cityDao, SqlSessionFactory sqlSessionFactory) {
        this.cityDao = cityDao;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
//    @Transactional
    public List<City> finaAllCity() {
        return callBySession();
//        return callByDao();
    }

    private List<City> callByDao() {
        System.out.println("0");
        cityDao.finaAllCity();
        System.out.println("1");
        cityDao.finaAllCity();
        System.out.println("2");
        return cityDao.finaAllCity();
    }


    private List<City> callBySession() {
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);

        CityDao cityDao = sqlSession1.getMapper(CityDao.class);
        CityDao cityDao2 = sqlSession2.getMapper(CityDao.class);

        // When testing local-cache
        // 1. will access database and do query.
        // 2. will hit local cache of sqlSession1.
        // 3.
        // 4. cause level one cache is session independent.
        //    Therefore, sqlSession2 can't share cache with sqlSession1. Need query database.
        // 5. will hit local cache of sqlSession2.

        // When testing second cache.
        // 1. First time query data, will access database and do query.
        // 2. Before sqlSession close, so will hit local cache here.
        // 3. SqlSession1 closed, local cache of it will be stored to second level cache area of CityDao mapper.
        // 4. Same mapper, therefore will hit second level cache.
        // 5. Same mapper, therefore will hit second level cache.

        cityDao.finaAllCity(); // 1.
        cityDao.finaAllCity(); // 2.
        sqlSession1.close();   // 3.
        cityDao2.finaAllCity(); // 4.
        List<City> returnData = cityDao2.finaAllCity(); // 5.
        return returnData;
    }
}
