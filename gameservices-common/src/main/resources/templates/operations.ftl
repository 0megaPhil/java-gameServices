<#if package?has_content>
    package ${package};

</#if>
import com.firmys.gameservices.common.CommonResolver;
import com.firmys.gameservices.generated.models.Options;
import com.firmys.gameservices.generated.models.${className?replace('QueryResolver', '')};
import com.firmys.gameservices.common.CommonQueryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("${className?lower_case?replace('queryresolver', '')}")
@Accessors(chain = true, fluent = true)
public class ${className} implements CommonResolver {

private final CommonQueryService service;
private final Class<${className?replace('QueryResolver', '')}> entityClass = ${className?replace('QueryResolver', '')}.class;

@SchemaMapping(typeName = "${className?replace('Resolver', '')}")
public Mono<${className?replace('QueryResolver', '')}> create(@Argument ${className?replace('QueryResolver', '')} input) {
return service.create(input);
}

@SchemaMapping(typeName = "${className?replace('Resolver', '')}")
public Mono<${className?replace('QueryResolver', '')}> update(@Argument ${className?replace('QueryResolver', '')} input) {
return service.update(input);
}

@SchemaMapping(typeName = "${className?replace('Resolver', '')}")
public Flux<${className?replace('QueryResolver', '')}> find(@Argument Options options) {
return service.find(options, ${className?replace('QueryResolver', '')}.class);
}

@SchemaMapping(typeName = "${className?replace('Resolver', '')}")
public Mono<${className?replace('QueryResolver', '')}> get(@Argument UUID uuid) {
return service.get(uuid, ${className?replace('QueryResolver', '')}.class);
}

@SchemaMapping(typeName = "${className?replace('Resolver', '')}")
public Mono<${className?replace('QueryResolver', '')}> flavor(@Argument UUID uuid) {
return service.flavor(uuid, ${className?replace('QueryResolver', '')}.class);
}

}
