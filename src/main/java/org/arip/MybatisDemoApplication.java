package org.arip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class MybatisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner demo(UserMapper userMapper) {
		return args -> {

			List<User> users = Arrays.asList(
					new User(null, "Arip", "Wisma 46"),
					new User(null, "Alisiana", "Jl. Tanah Abang III")
			);

			users.forEach(user -> {
				userMapper.insert(user);
				System.out.println(user.toString());
			});

			System.out.println("-------------------------------");
			userMapper.selectAll().forEach(System.out::println);

			System.out.println("-------------------------------");
			userMapper.search("Arip", null).forEach(System.out::println);

			System.out.println("-------------------------------");
			userMapper.search(null, "Jl. Tanah Abang III").forEach(System.out::println);

            System.out.println("-------------------------------");
            userMapper.deleteById(1);
            userMapper.selectAll().forEach(System.out::println);
		};
	}
}

@Mapper
interface UserMapper {

	@Options(useGeneratedKeys = true)
	@Insert("insert into users(name, address) values(#{name}, #{address})")
	void insert(User user);

    @Delete("delete from users where id = #{id}")
    void deleteById(long id);

	@Select("select * from users")
	Collection<User> selectAll();

	Collection<User> search(@Param("name") String name,
							@Param("address") String address);
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class User {
	private Long id;
	private String name, address;
}