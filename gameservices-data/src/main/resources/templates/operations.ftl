<#if package?has_content>
    package ${package};

</#if>
<#assign imported = [], objectType = className?remove_beginning("Update")?remove_beginning("Delete")?remove_beginning("Create")?remove_beginning("Get")?remove_beginning("Find")?remove_beginning("Flavor")?remove_ending("MutationResolver")?remove_ending("QueryResolver")/>
<#if operations?has_content>
    <#list operations as operation>
        <#if operation.name?contains("update") || operation.name?contains("get") || operation.name?contains("create") || operation.name?contains("find") || operation.name?contains("flavor")>
            <#if !imported?seq_contains(operation.name)>
                import com.firmys.gameservices.generated.models.${operation.type?remove_beginning("java.util.List<")?remove_ending(">")};
                <#list 1..10 as i>
                    <#assign imported = imported + [operation.type?remove_beginning("java.util.List<")?remove_ending(">") + " " + i] />
                </#list>
            </#if>
        </#if>
    </#list>
</#if>
import com.firmys.gameservices.generated.models.Options;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import graphql.kickstart.tools.GraphQLResolver;

<#if className != "QueryResolver" && className != "MutationResolver" && !package?contains(".app")>
    import ${package}.${objectType}Service;
<#elseif className != "QueryResolver" && className != "MutationResolver" && package?contains(".app")>
    import com.firmys.gameservices.generated.models.${objectType};
    import com.firmys.gameservices.service.GameServiceClient;
</#if>
<#if javaDoc?has_content>
    /**
    <#list javaDoc as javaDocLine>
        * ${javaDocLine}
    </#list>
    */
</#if>
<#if generatedAnnotation && generatedInfo.getGeneratedType()?has_content>
    @${generatedInfo.getGeneratedType()}(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "${generatedInfo.getDateTime()}"
    )
</#if>
<#if className != "QueryResolver" && className != "MutationResolver">
@Component
@RequiredArgsConstructor
public class ${className} implements GraphQLResolver
<<#if className?contains("Get") || className?contains("Find") || className?contains("Create") || className?contains("Update") || className?contains("Flavor")>${objectType}<#else>Void</#if>>
    {
    <#else>
        @Controller
        @RequiredArgsConstructor
        public class ${className}<#if implements?has_content> extends <#list implements as interface>${interface}<#if interface_has_next>, </#if></#list></#if> {
    </#if>
    <#if className != "QueryResolver" && className != "MutationResolver">
        <#if package?contains(".app")>
            private final GameServiceClient client;
        <#elseif !package?contains(".app")>
            private final ${objectType}Service service;
        </#if>

        <#list operations as operation>
            <#if package?contains(".app")>
                public <#if operation.type?contains("java.util.List<")>${operation.type?replace("java.util.List<", "Flux<")}<#else>Mono<${operation.type}></#if> ${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list><#if param.name == "id">ObjectId<#else>${param.type?remove_ending("Input")}</#if> ${param.name}<#if param_has_next>, </#if></#list>) {
                return client.${operation.name?remove_ending(objectType)}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list>${param.name}<#if param_has_next>, </#if></#list><#if operation.name?contains("get") || operation.name?contains("delete") || operation.name?contains("find") || operation.name?contains("flavor")>, ${operation.name?remove_beginning("get")?remove_beginning("delete")?remove_beginning("find")?remove_beginning("flavor")}.class</#if>);
                }
            <#elseif !package?contains(".app")>
                public <#if operation.type?contains("java.util.List<")>${operation.type?replace("java.util.List<", "Flux<")}<#else>Mono<${operation.type}></#if> ${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list><#if param.name == "id">ObjectId<#else>${param.type?remove_ending("Input")}</#if> ${param.name}<#if param_has_next>, </#if></#list>) {
                return service.${operation.name?remove_ending(objectType)}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list>${param.name}<#if param_has_next>, </#if></#list>);
                }
            </#if>

        </#list>
    <#elseif package?has_content && package?contains(".app")>
        <#if className?contains("Query")>
            <#list operations as operation>
                private final ${operation.name?cap_first}QueryResolver ${operation.name}Resolver;
            </#list>

            <#list operations as operation>
                @QueryMapping
                public <#if operation.type?contains("java.util.List<")>${operation.type?replace("java.util.List<", "Flux<")}<#else>Mono<${operation.type}></#if> ${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list>@Argument <#if param.name == "id">ObjectId<#else>${param.type?remove_ending("Input")}</#if> ${param.name}<#if param_has_next>, </#if></#list>) {
                return ${operation.name}Resolver.${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list> ${param.name}<#if param_has_next>, </#if></#list>);
                }

            </#list>
        <#elseif className?contains("Mutation")>
            <#list operations as operation>
                private final ${operation.name?cap_first}MutationResolver ${operation.name}Resolver;
            </#list>
            <#list operations as operation>
                @MutationMapping
                public <#if operation.type?contains("java.util.List<")>${operation.type?replace("java.util.List<", "Flux<")}<#else>Mono<${operation.type}></#if> ${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list>@Argument <#if param.name == "id">ObjectId<#else>${param.type?remove_ending("Input")}</#if> ${param.name}<#if param_has_next>, </#if></#list>) {
                return ${operation.name}Resolver.${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list> ${param.name}<#if param_has_next>, </#if></#list>);
                }
            </#list>
        </#if>
    <#else>
        <#if className?contains("Query")>
            <#list operations as operation>
                private final ${operation.name?cap_first}QueryResolver ${operation.name}Resolver;
            </#list>

            <#list operations as operation>
                @QueryMapping
                public <#if operation.type?contains("java.util.List<")>${operation.type?replace("java.util.List<", "Flux<")}<#else>Mono<${operation.type}></#if> ${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list>@Argument <#if param.name == "id">ObjectId<#else>${param.type?remove_ending("Input")}</#if> ${param.name}<#if param_has_next>, </#if></#list>) {
                return ${operation.name}Resolver.${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list> ${param.name}<#if param_has_next>, </#if></#list>);
                }

            </#list>
        <#elseif className?contains("Mutation")>
            <#list operations as operation>
                private final ${operation.name?cap_first}MutationResolver ${operation.name}Resolver;
            </#list>
            <#list operations as operation>
                @MutationMapping
                public <#if operation.type?contains("java.util.List<")>${operation.type?replace("java.util.List<", "Flux<")}<#else>Mono<${operation.type}></#if> ${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list>@Argument <#if param.name == "id">ObjectId<#else>${param.type?remove_ending("Input")}</#if> ${param.name}<#if param_has_next>, </#if></#list>) {
                return ${operation.name}Resolver.${operation.name}(<#list operation.parameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list> ${param.name}<#if param_has_next>, </#if></#list>);
                }
            </#list>
        </#if>

    </#if>
    }