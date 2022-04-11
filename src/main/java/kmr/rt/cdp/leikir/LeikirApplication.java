package kmr.rt.cdp.leikir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class LeikirApplication {

  public static void main(String[] args) {
    SpringApplication.run(LeikirApplication.class, args);
  }

  @Bean
  MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory mongoDbFactory, MongoMappingContext mongoMappingContext) {
    DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
    MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
    converter.setTypeMapper(new DefaultMongoTypeMapper(null));
    return converter;
  }
}

