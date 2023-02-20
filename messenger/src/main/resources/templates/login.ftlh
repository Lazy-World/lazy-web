<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    Login page
    <@l.auth "/login" />
    <a href="/registration">Add new user</a>
</@c.page>