package gary.spring.mybatis.mybatisintegrate.dao;

import gary.spring.mybatis.mybatisintegrate.model.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface CityDao {

    List<City> finaAllCity();

    List<City> findByState(@Param("state") String state, @Param("orderBy") String orderBy, @Param("orderType") String orderType);

    Integer insertCity(@Param("city") City city);
}
