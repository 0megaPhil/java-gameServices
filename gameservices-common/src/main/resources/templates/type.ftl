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
import java.util.UUID;
import lombok.With;
import lombok.Builder;
<#if implements?has_content><#list implements as interface>
    import com.firmys.gameservices.common.Common${interface};
    <#if interface_has_next>;
    </#if></#list></#if>
<#list annotations as annotation>
    @${annotation}
</#list>

@With
@Builder(toBuilder = true)
public record ${className}(<#if fields?has_content>
    <#list fields as field>
        ${field.type} ${field.name}<#if field_has_next>,</#if>
    </#list>
</#if>) <#if implements?has_content>implements <#list implements as interface>Common${interface}<#if interface_has_next>, </#if></#list> </#if> {

}
