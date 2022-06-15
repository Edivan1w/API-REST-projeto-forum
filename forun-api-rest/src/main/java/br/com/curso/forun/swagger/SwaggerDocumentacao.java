package br.com.curso.forun.swagger;


import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;




@Configuration
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "jwt",
		scheme = "Bearer"
		 
		)
public class SwaggerDocumentacao {}

//	@Bean
//	public GroupedOpenApi publicApi() {
//	    return GroupedOpenApi.builder()
//	            .group("forun")
//	            .pathsToMatch("/**")
//	            .build();
//	}
//
//	@Bean
//	public OpenAPI forumAluraOpenAPI() {
//	    return new OpenAPI()
//	            .info(new Info().title("Forum API")
//	                            .description("Projeto de Documentacao de API da Alura")
//	                            .version("v0.0.1")
//	                            .license(new License().name("Apache 2.0").url("http://springdoc.org")))
//	            .components(new Components().addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP)
//	                                                                                                  .scheme("bearer")
//	                                                                                                  .bearerFormat("JWT")))
//	            .externalDocs(new ExternalDocumentation()
//	                    .description("SpringShop Wiki Documentation")
//	                    .url("https://springshop.wiki.github.org/docs"));
//	}
//
//

	    //Static resources configuration (css, js, img, etc.)
	
	



//}
