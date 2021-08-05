package gary.spring.mybatis.mybatisintegrate.controller;

import gary.spring.mybatis.mybatisintegrate.dao.CityDao2;
import gary.spring.mybatis.mybatisintegrate.dto.CustomerDto;
import gary.spring.mybatis.mybatisintegrate.model.City;
import gary.spring.mybatis.mybatisintegrate.dao.CityDao;
import gary.spring.mybatis.mybatisintegrate.model.Customer;
import gary.spring.mybatis.mybatisintegrate.model.Name;
import gary.spring.mybatis.mybatisintegrate.service.CityService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CityController {

    private final CityService cityService;
    private final CityDao cityDao;
    private final CityDao2 cityDao2;
    private final ModelMapper mapper;

    @Autowired
    public CityController(CityService cityService, CityDao cityDao, CityDao2 cityDao2, ModelMapper mapper) {
        this.cityService = cityService;
        this.cityDao = cityDao;
        this.cityDao2 = cityDao2;
        this.mapper = mapper;
    }

    @GetMapping(value = "/getCustomerDetail")
    public CustomerDto findCustomerDetail() {
        Converter<String, String> toUppercase =
                ctx -> ctx.getSource() == null ? null : ctx.getSource().toUpperCase();

        mapper.typeMap(Customer.class, CustomerDto.class).addMappings(mapper -> {
            mapper.using(toUppercase).map(src -> src.getLivingCity().getState(), CustomerDto::setCustomerState);
        });
        mapper.validate();

        City city = new City();
        city.setCityName("Taipei");
        city.setState("Taiwan");
        Name name = new Name();
        name.setFirstName("Gary");
        name.setLastName("Liao");

        Customer customer = new Customer();
        customer.setLivingCity(city);
        customer.setName(name);
        customer.setMember(true);
        return mapper.map(customer,CustomerDto.class);
    }

    @GetMapping(value = "/getAllCity")
    public List<City> findAllCity() {
        return cityService.finaAllCity();
    }

    @GetMapping(value = "/getAllCities")
    public List<City> findAllCity2() {
        return cityDao.finaAllCity();
    }

    @GetMapping(value = "/getCityInfo")
    public List<City> findCityDetail(@RequestParam("state") String state,
                                     @RequestParam(value = "orderBy", required = false) String orderBy,
                                     @RequestParam(value = "orderType", required = false) String orderType) {
        return cityDao.findByState(state, orderBy, orderType);
    }

    @GetMapping(value = "/insertCity")
    public Integer insertCity( @RequestParam("cityName") String cityName,
                               @RequestParam("state") String state) {
        City city = new City();
        city.setCityName(cityName);
        city.setState(state);
        return cityDao.insertCity(city);
    }

}
