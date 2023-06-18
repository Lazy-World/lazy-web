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
            <div class="alert alert-${messageType}" role="alert" style="font-size: 16px">${message}</div>
        </#if>
    </div>

        <div class="VkIdLogin">
            <div class="page_block VkIdSignIn">
                <div class="VkIdForm__icon">
                    <svg class="svg-inline--fa fa-dragon fa-w-20" xmlns="http://www.w3.org/2000/svg" style="width: 48px; height: 48px;" fill="currentColor" viewBox="0 0 16 16">
                        <path d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278z"/> <path d="M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z"/>
                    </svg>
                </div>

                <h2 class="VkIdForm__header">Вход в учетную запись</h2>

                <form action="/login" method="post">
                    <div id="login_message"><div class="msg info_msg"><div class="msg_text">Войдите в аккаунт для просмотра страницы.</div></div></div>

                    <@auth.loginField/>
                    <@auth.passwordField />
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />

                    <button class="FlatButton FlatButton--primary FlatButton--size-l FlatButton--wide VkIdForm__button VkIdForm__signInButton" type="submit">
                        <span class="FlatButton__in">
                            <span class="FlatButton__content">Войти</span>
                        </span>
                    </button>
                </form>
            </div>

            <div class="page_block VkIdSignUp">
                <button class="FlatButton FlatButton--positive FlatButton--size-l FlatButton--wide VkIdForm__button VkIdForm__signUpButton" type="button">
                    <span class="FlatButton__in">
                        <span class="FlatButton__content">Регистрация</span>
                    </span>
                </button>

                <div class="JoinForm__connectAgreement">После регистрации откроется доступ к чату<br></div>
            </div>
        </div>

    <script>
        document.querySelector('.VkIdForm__signUpButton').addEventListener('click', function() {
            window.location.href = '/registration';
        });
    </script>
</@c.page>