<#if package?has_content>
    package ${package};

</#if>
import com.firmys.gameservices.common.CommonResolver;
import com.firmys.gameservices.generated.models.Options;

<#list operations as operation>
    <#if !operation.type?contains("List") && !operation.type?contains("Input")>
        import com.firmys.gameservices.generated.models.${operation.type};
    </#if>
</#list>

import com.firmys.gameservices.common.CommonQueryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

<#if className == "QueryResolver">
    @Controller
    @RequiredArgsConstructor
    @Accessors(chain = true, fluent = true)
</#if>
public class ${className} <#if className == "QueryResolver">implements CommonResolver</#if> {
<#if className == "QueryResolver">
    private final CommonQueryService service;
    <#list operations as operation>

        <#if operation.name?contains("create")>
            @QueryMapping
            public Mono<${operation.type}> ${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list>@Argument ${param.type?remove_ending('Input')} ${param.name}<#if param_has_next>, </#if></#list>) {
            return service.create(input);
            }
        </#if>
        <#if operation.name?contains("update")>
            @QueryMapping
            public Mono<${operation.type}> ${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list>@Argument ${param.type?remove_ending('Input')} ${param.name}<#if param_has_next>, </#if></#list>) {
            return service.update(input);
            }
        </#if>
        <#if operation.name?contains("find")>
            @QueryMapping
            public Flux<${operation.type?remove_beginning("java.util.List<")?remove_ending(">")}> ${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list>@Argument ${param.type?remove_ending('Input')} ${param.name}<#if param_has_next>, </#if></#list>) {
            return service.find(options, ${operation.type?remove_beginning("java.util.List<")?remove_ending(">")}.class);
            }
        </#if>
        <#if operation.name?contains("get")>
            @QueryMapping
            public Mono<${operation.type}> ${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list>@Argument ${param.type?remove_ending('Input')} ${param.name}<#if param_has_next>, </#if></#list>) {
            return service.get(uuid, ${operation.type}.class);
            }
        </#if>
        <#if operation.name?contains("flavor")>
            @QueryMapping
            public Mono<${operation.type}> ${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list>@Argument ${param.type?remove_ending('Input')} ${param.name}<#if param_has_next>, </#if></#list>) {
            return service.flavor(uuid, ${operation.type}.class);
            }
        </#if>
    <#--    ${operation.type} ${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list>${param.type} ${param.name}<#if param_has_next>, </#if></#list>)<#if operation.throwsException> throws Exception</#if>;-->

    </#list>
</#if>
}
