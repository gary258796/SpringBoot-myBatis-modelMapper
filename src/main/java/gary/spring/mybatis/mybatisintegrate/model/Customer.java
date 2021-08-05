package gary.spring.mybatis.mybatisintegrate.model;

import lombok.Data;

@Data
public class Customer {

    private Name name;

    private City livingCity;

    private boolean isMember;
}
