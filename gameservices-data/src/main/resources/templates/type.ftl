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
import com.firmys.gameservices.data.conversion.DataJsonConverters;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.Optional;
import lombok.Builder;
import lombok.Singular;
import lombok.With;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.core.mapping.Document;

<#list annotations as annotation>
    @${annotation}
</#list>

@With
<#if implements?has_content>
    <#list implements as interface>
        <#if interface == "CommonEntity">
            @Document
        </#if>
    </#list>
</#if>
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record ${className}(<#if fields?has_content>
    <#list fields as field>
        <#if field.name == "name">
            @Indexed(unique = true, sparse = true)
        </#if>
        <#if field.type?contains("List")>
            @Singular
            ${field.type?replace("java.util.List<", "Set<")} ${field.name}<#if field_has_next>,</#if>
        </#if>
        <#if !field.type?contains("List")
        && field.name != "name"
        && field.name != "id"
        && field.type != "String"
        && field.type != "Integer"
        && field.name != "value"
        && field.name != "percentile"
        && field.type != "Float"
        && field.name != "created"
        && field.name != "updated"
        && field.name != "type"
        && field.name != "sex"
        && field.name != "likelihood"
        && field.type != "Entities"
        && field.type != "Rarity"
        && field.type != "Contexts"
        && !field.type?contains("Double")>
            @Reference
        </#if>
        <#if !field.type?contains("List")>
            <#if field.type == "double">
                Double ${field.name}<#if field_has_next>,</#if>
            <#elseif field.type == "DateTime" || field.name == "created" || field.name == "updated">
                OffsetDateTime ${field.name}<#if field_has_next>,</#if>
            <#elseif field.name == "version">
                @Version
                ${field.type} ${field.name}<#if field_has_next>,</#if>
            <#elseif field.name == "id">
                @JsonSerialize(using = DataJsonConverters.IdJsonSerializer.class)
                @JsonDeserialize(using = DataJsonConverters.IdJsonDeserializer.class)
                @Id
                ObjectId ${field.name}<#if field_has_next>,</#if>
            <#else>
                ${field.type} ${field.name}<#if field_has_next>,</#if>
            </#if>
        </#if>
    </#list>
</#if>)
<#if implements?has_content>implements <#list implements as interface>${interface}<#if interface_has_next>, </#if></#list>
</#if>
<#if !implements?has_content>
    implements CommonObject
</#if> {

public ${className} {
<#list fields as field>
    <#if field.defaultValue?has_content>
        ${field.name} = Optional.ofNullable(${field.name}).orElse(<#if field.defaultValue?contains("emptyList")>java.util.Collections.emptySet()<#else>${field.defaultValue}</#if><#if field.type?contains("Double")>D</#if>);
    </#if>
</#list>
}

}
