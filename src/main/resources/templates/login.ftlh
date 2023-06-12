<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as auth>

<@c.page>
    <div class="text-center">
        <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
            <div class="alert alert-danger" role="alert">
                ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
            </div>
        </#if>

        <#if message??>
            <div class="alert alert-${messageType}" role="alert">${message}</div>
        </#if>

        <form action="/login" method="post">
            <@auth.loginField/>
            <@auth.passwordField />
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <a href="/registration">Регистрация</a>

            <div class="col-auto mt-2 text-center">
                <button type="submit" class="btn btn-primary">Войти</button>
            </div>
        </form>
    </div>
</@c.page>