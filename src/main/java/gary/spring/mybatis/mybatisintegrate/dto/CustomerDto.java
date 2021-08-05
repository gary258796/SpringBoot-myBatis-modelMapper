package gary.spring.mybatis.mybatisintegrate.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private String customerFirstName;

    private String customerLastName;

    private String customerState;

    private String customerCityName;

    private Boolean member;

}
