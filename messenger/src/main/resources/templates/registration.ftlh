<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    Add new user
    ${message!}
    <@l.auth "/registration" />
</@c.page>