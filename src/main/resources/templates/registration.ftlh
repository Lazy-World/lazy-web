<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as auth>

<@c.page>
    <div class="mb-2 text-center">
        <h5>Создание аккаунта</h5>
    </div>

    <div class="text-center">
        <#if message??>
            <div class="alert alert-${messageType}" role="alert">${message}</div>
        </#if>
    </div>

    <form action="/registration" method="post">
        <@auth.loginField />
        <@auth.passwordField />
        <@auth.passwordConfirmField />
        <@auth.emailField />
        <@auth.recaptchaField />
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <div class="col-auto mt-2 text-center">
            <button type="submit" class="btn btn-primary">Создать</button>
        </div>
    </form>
</@c.page>