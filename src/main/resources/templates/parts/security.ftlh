<#assign
    know = Session.SPRING_SECURITY_CONTEXT??
>
<#if know>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        isAdmin = user.isAdmin()
        isUser = user.isUser()
        currentUserId = user.getId()
    >
<#else>
    <#assign
        name = "Guest"
        isAdmin = false
        isUser = false
        currentUserId = -1
    >
</#if>
