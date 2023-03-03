<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    ${message!}
    <@l.auth "/login" false/>
</@c.page>