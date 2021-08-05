package gary.spring.mybatis.mybatisintegrate;

import gary.spring.mybatis.mybatisintegrate.dao.CityDao;
import gary.spring.mybatis.mybatisintegrate.model.City;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class StartData implements ApplicationListener<ContextRefreshedEvent> {

    private final CityDao cityDao;

    public StartData(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        saveCityList();
    }

    private void saveCityList(){

        List<String> cityNameArray = new ArrayList<>();
        cityNameArray.add("APPLEPAY");
        cityNameArray.add("CATDOGCITY");
        cityNameArray.add("CALIFORNIA");
        cityNameArray.add("DENMARK");
        cityNameArray.add("DENNIS");

        for( String cityName : cityNameArray){
            City newCity = new City();
            newCity.setState("USA");
            newCity.setCityName(cityName);
            cityDao.insertCity(newCity);
        }

        List<String> secondCityNameArray = new ArrayList<>();
        secondCityNameArray.add("DONWON");
        secondCityNameArray.add("FOREST");
        secondCityNameArray.add("MONSTRE");
        secondCityNameArray.add("ZAPPLU");

        for( String cityName : secondCityNameArray){
            City newCity = new City();
            newCity.setState("TAIPEI");
            newCity.setCityName(cityName);
            cityDao.insertCity(newCity);
        }
    }
}