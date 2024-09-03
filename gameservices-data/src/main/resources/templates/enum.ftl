<#if package?has_content>
    package ${package};

</#if>
<#function snakeToCamel(s)>
    <#return s?lower_case
    ?replace('_([a-z])', " $1", 'r')?capitalize
    ?replace(" ([A-Z])", "$1" ,'r')
    ?uncap_first
    >
</#function>
import com.fasterxml.jackson.annotation.JsonAlias;
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
public enum ${className}<#if implements?has_content> implements <#list implements as interface>${interface}<#if interface_has_next>, </#if></#list></#if> {

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
        @JsonAlias({"${field.graphqlName?lower_case}", "${snakeToCamel(field.graphqlName?lower_case)}"})
        ${field.javaName}("${field.graphqlName}")<#if field_has_next>,<#else>;</#if>
    </#list>
</#if>

private final String graphqlName;

private ${className}(String graphqlName) {
this.graphqlName = graphqlName;
}

@Override
public String toString() {
return this.graphqlName;
}

}
