<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as auth>

<@c.page>
    <div class="text-center">
        <#if message??>
            <div class="alert alert-${messageType}" role="alert">${message}</div>
        </#if>
    </div>

    <div class="VkIdLogin">
        <div class="page_block VkIdSignUp">
            <h2 class="VkIdForm__header mt-0">Создание аккаунта</h2>

            <form action="/registration" method="post">
                <div id="login_message"><div class="msg info_msg"><div class="msg_text">Наличие аккаунта позволяет получить доступ ко всем функциям сайта.</div></div></div>

                <@auth.loginField />
                <@auth.passwordField />
                <@auth.passwordConfirmField />
                <@auth.emailField />
                <@auth.recaptchaField />
                <input type="hidden" name="_csrf" value="${_csrf.token}" />

                <button class="FlatButton FlatButton--positive FlatButton--size-l FlatButton--wide VkIdForm__button VkIdForm__signUpButton" type="submit">
                    <span class="FlatButton__in">
                        <span class="FlatButton__content">Создать учетную запись</span>
                    </span>
                </button>
            </form>
        </div>
    </div>
</@c.page>