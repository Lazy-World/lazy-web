<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    <div class="mb-2">
        <h5>Add new user</h5>
    </div>
    ${message!}
    <@l.auth "/registration" true/>
</@c.page>