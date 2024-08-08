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
<#list annotations as annotation>
    @${annotation}
</#list>
public interface ${className} <#if implements?has_content>extends <#list implements as interface>${interface}<#if interface_has_next>, </#if></#list></#if>{

<#if fields?has_content>
    <#list fields as field>
        <#if field.javaDoc?has_content>
            /**
            <#list field.javaDoc as javaDocLine>
                * ${javaDocLine}
            </#list>
            */
        </#if>
        <#if field.deprecated?has_content>
            @${field.deprecated.annotation}
        </#if>
        <#list field.annotations as annotation>
            @${annotation}
        </#list>
        ${field.type} ${field.name?lower_case}(<#list field.inputParameters as param><#list param.annotations as paramAnnotation>@${paramAnnotation}<#if param.annotations?has_content> </#if></#list>${param.type} ${param.name}<#if param_has_next>, </#if></#list>);

    </#list>
</#if>
}
