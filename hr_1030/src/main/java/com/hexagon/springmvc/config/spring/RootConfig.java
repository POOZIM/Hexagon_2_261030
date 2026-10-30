package com.hexagon.springmvc.config.spring;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/*----------------------------------------------------------------------------------------------------------------------------
	이 RootConfig 클래스에 등록되는 빈들은, 서블릿 수준이 아닌 ServletContext 수준의 빈으로 등록되므로
	톰켓이 가동될때 즉 애플이케이션이 가동될때 함께 빈들도 메모리에 올라와서 ServletCOntext가 보유한 스프링 컨테이너에 의해 결정된다
-------------------------------------------------------------------------------------------------------------------------------*/

@Configuration //이 클래스는 java의 로직을 작성하기 위함이 아니라, 기존의 xml 과 같은 설정을 대신하기 위한 설정용 클래스임을 선언
@EnableTransactionManagement //스프링이 트랜잭션을 자동동으로 처리해주기 위한 설정
@MapperScan(basePackages = {"com.hexagon.springmvc.mapper"})
@ComponentScan(basePackages = {
		"com.hexagon.springmvc.service",
		"com.hexagon.springmvc.security"//시큐리티는 필터이기 때문에 서블릿보다도 먼저 생성 및 등록 되어야함 Root 수준으로 등록되어야함
})//서블릿들이 볼 수 있는 scope 인 ServletContext 수준에 등록될 컴포넌트들의 위치 등록
public class RootConfig {
	
	//스프링이 사용하는 데이터 소스
	//데이터베이스 설정 정보
	@Bean
	public DataSource dataSource() {
		
		//기존에는 우리가 Tomcat 이 지원하는 커넥션풀을 이용했다면, 스프링에서는 월씬 더 성능이 좋은 커넥션 풀인 히카리 커넥션 풀을 쓴다 (스프링부트에서는 다른)
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setJdbcUrl("jdbc:oracle:thin:@localhost:1521/XEPDB1");
		dataSource.setUsername("web");
		dataSource.setPassword("1234");
		
		//(선택) 추가 권장
		dataSource.setMaximumPoolSize(10);
		dataSource.setMinimumIdle(5);
		
		return dataSource;
	}
	
	//우리의 그냥 순수 Mybatis를 직접 사용하는게 아니라, Spring으로 감싼(스프링이 가공한) Mybatis 를 사용할 것이므로, 그와 관련된 설정
	@Bean
	@DependsOn("flyway")//Flyway 실행 완료 후 Mybatis 초기화 = DB 구축 후 Mybatis 연동해라!!
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		//SqlSessionFactoryBean에게 어떤한 DataSource 를 사용할지를 알려줘야 함
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		
		//Mybatis 설정 ㅠㅏ일의 위치를 스프링에서 알려주자
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("com/hexagon/springmvc/config/mybatis/mybatis-config.xml"));
		
		//쿼리문을 담당하는 Mapper xml의 위치(Maven 기반의 스프링의 경우 resources 하위에 만듬)
		//PathMatchingResourcePatternResolver().getResources은 resources 디렉토리를 의미하고, 그 안의 자원은 classpath:/ 명시해야함
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mappers/**/*.xml"));
		
		return sqlSessionFactoryBean.getObject();
	}
	
	//쿼리문을 수행해주는 SqlSession을 꺼내갈 수 있도록 제공하는 메서드
	//단 Mybatis-Spring 에서는 SqlSession이 아닌 SqlSessionTemplate을 제공함(명칭만 틀려질 뿐 용도는 같다)
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	/*
	스프링이 지원하는 트랜젝션 관리자 등록(스프링 DB분야에서 압도적으로 중요한 비중을 차지)
	스프링이 지원하는 DB기술을 사용하려면 spring jdbc 의존성을 추가해야함
	DB관련된 기술은 다향하다(순수 JDBC, Mybatis, JPAm Hibernate 등)
	이렇게 다양한 기술을 현재 우리 스프링 애플이케이션에서 사용하더라고 각 기술이 발생시키는 예외를 스프링이 하나의 자료형으로 알아서 묶어주고
	각 기술마다 트랜젝션을 처리하는 방법 또한 다양함에더 불구하고 스프링이 일관된 하나의 방법을 제공함으로써 개발자는 어떤한 DB기술을 사용하더라고 코드에 큰 변화가 없게 해준다
	*/
	@Bean
	public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	/*-------------------------
			Flyway 관련 설정
	----------------------------*/
	@Bean(initMethod = "migrate")
	public Flyway flyway(DataSource dataSource) {
		
		return Flyway.configure()
				.dataSource(dataSource)//사용할 DB
				.locations("classpath:db/migration")//SQL 파일의 위치
				.baselineOnMigrate(true)//운영 중이거나 테이블이 있는 DB 라면 true로 설정
				//Flyway의 파일명은 규칙이 있다(반드시 대문자 V여야 한다 그 뒤에 버전 숫자를 붙인다 그리고 개발자가 원하는 파일명은 언더바 2개로 구분하여 확장자는 .sql)
				//  V1__init.sql
				.baselineVersion("0")
				.load();
	}
}
