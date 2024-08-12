<#assign MapperUtil=statics["com.kobylynskyi.graphql.codegen.java.JavaGraphQLTypeMapper"]>
<#if package?has_content>
    package ${package};

</#if>
<#if imports??>
    <#list imports as import>
        <#list operations as operation>
            import ${import}.${operation.type};
        </#list>
    </#list>
</#if>
import java.util.Set;
import java.util.UUID;
import java.time.OffsetDateTime;

import lombok.With;
import lombok.Builder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Version;

<#list annotations as annotation>
    @${annotation}
</#list>

@With
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record ${className}(<#if fields?has_content>
    <#list fields as field>
        <#if field.type?contains("List")>
            ${field.type?replace("java.util.List<", "Set<")} ${field.name}<#if field_has_next>,</#if>
        </#if>
        <#if !field.type?contains("List")>
            <#if field.type == "double">
                Double ${field.name}<#if field_has_next>,</#if>
            <#elseif field.type == "DateTime" || field.name == "created" || field.name == "updated">
                OffsetDateTime ${field.name}<#if field_has_next>,</#if>
            <#elseif field.name == "version">
                @Version
                ${field.type} ${field.name}<#if field_has_next>,</#if>
            <#else>
                ${field.type} ${field.name}<#if field_has_next>,</#if>
            </#if>
        </#if>
    </#list>
</#if>) <#if implements?has_content>implements <#list implements as interface>${interface}<#if interface_has_next>, </#if></#list> </#if> {

}
